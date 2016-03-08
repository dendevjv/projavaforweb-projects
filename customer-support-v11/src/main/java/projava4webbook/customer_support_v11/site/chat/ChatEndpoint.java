package projava4webbook.customer_support_v11.site.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import projava4webbook.customer_support_v11.site.SessionRegistryInterface;
import projava4webbook.customer_support_v11.site.UserPrincipal;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Receives chat connections and coordinates them.<br />
 * A new instance of this class is created at startup as a web listener, 
 * and a new instance is created each time a client endpoint connects to the server endpoint. 
 */
@ServerEndpoint(
        value = "/chat/{sessionId}",
        encoders = ChatMessageCodec.class,
        decoders = ChatMessageCodec.class,
        configurator = ChatEndpoint.EndpointConfigurator.class
)
public class ChatEndpoint {
    
    private static final Logger log = LogManager.getLogger();
    private static final byte[] pongData = "This is PONG country.".getBytes(StandardCharsets.UTF_8);
    
    private final Consumer<HttpSession> callback = this::httpSessionRemoved;
    
    private boolean closed = false;
    private Session wsSession;
    private Session otherWsSession;
    private HttpSession httpSession;
    private ChatSession chatSession;
    private Principal principal;
    private ScheduledFuture<?> pingFuture;
    
    @Inject
    SessionRegistryInterface sessionRegistry; 
    @Inject
    ChatService chatService;
    @Inject
    TaskScheduler taskScheduler;
    
    /* WebSocket methods  */
    
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId) {
        log.entry(sessionId);
        this.httpSession = EndpointConfigurator.getExposedSession(session);
        this.principal = EndpointConfigurator.getExposedPrincipal(session);
        
        try {
            if (principal == null) {
                log.warn("Unauthorized attempt to access chat server.");
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, 
                        "You are not logged in!"));
                return;
            }
            
            if (sessionId < 1) {
                CreateResult result = chatService.createSession(principal.getName());
                chatSession = result.getChatSession();
                chatSession.setCustomer(session);
                chatSession.setOnRepresentativeJoin(s -> otherWsSession = s);
                session.getBasicRemote().sendObject(result.getCreateMessage());
            } else {
                JoinResult result = chatService.joinSession(sessionId, principal.getName());
                if (result == null) {
                    log.warn("Attempted to join non-existent chat session {}.", sessionId);
                    session.close(new CloseReason(
                            CloseReason.CloseCodes.UNEXPECTED_CONDITION,
                            "The chat session does not exist!"
                    ));
                    return;
                }
                
                chatSession = result.getChatSession();
                chatSession.setRepresentative(session);
                otherWsSession = chatSession.getCustomer();
                session.getBasicRemote().sendObject(chatSession.getCreationMessage());
                session.getBasicRemote().sendObject(result.getJoinMessage());
                otherWsSession.getBasicRemote().sendObject(result.getJoinMessage());
            }
            
            wsSession = session;
            log.debug("onMessage completed successfully for chat {}.", sessionId);
        } catch (IOException | EncodeException e) {
            this.onError(e);
        } finally {
            log.exit();
        }
    }
    
    /**
     * Echoes the message back to both clients.
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(ChatMessage message) {
        if(this.closed) {
            log.warn("Chat message received after connection closed.");
            return;
        }

        log.entry();
        message.setUser(principal.getName());
        chatService.logMessage(chatSession, message);
        try {
            wsSession.getBasicRemote().sendObject(message);
            otherWsSession.getBasicRemote().sendObject(message);
        } catch(IOException | EncodeException e) {
            this.onError(e);
        }
        log.exit();
    }
    
    @OnMessage
    public void onPong(PongMessage message) {
        ByteBuffer data = message.getApplicationData();
        if(!Arrays.equals(ChatEndpoint.pongData, data.array())) {
            log.warn("Received pong message with incorrect payload.");
        } else {
            log.debug("Received good pong message.");
        }
    }
    
    @OnClose
    public void onClose(CloseReason reason) {
        if(reason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE) {
            log.warn("Abnormal closure {} for reason [{}].", reason.getCloseCode(), reason.getReasonPhrase());
        }

        synchronized(this) {
            if(this.closed) {
                return;
            }
            this.close(ChatService.ReasonForLeaving.NORMAL, null);
        }
    }
    
    @OnError
    public void onError(Throwable e) {
        log.warn("Error received in WebSocket session.", e);

        synchronized(this) {
            if(this.closed) {
                return;
            }
            this.close(ChatService.ReasonForLeaving.ERROR, e.toString());
        }
    }
    
    /* Dependency Injection Framework methods */
    
    @PostConstruct
    public void initialize() {
        sessionRegistry.registerOnRemoveCallback(this.callback);

        pingFuture = taskScheduler.scheduleWithFixedDelay(
                this::sendPing,
                new Date(System.currentTimeMillis() + 25_000L),
                25_000L
        );
    }

    /* Private methods */
    
    private void sendPing() {
        if(!wsSession.isOpen()) {
            return;
        }
        log.debug("Sending ping to WebSocket client.");
        try {
            wsSession.getBasicRemote().sendPing(ByteBuffer.wrap(ChatEndpoint.pongData));
        }
        catch(IOException e) {
            log.warn("Failed to send ping message to WebSocket client.", e);
        }
    }

    private void httpSessionRemoved(HttpSession httpSession) {
        if (httpSession == this.httpSession) {
            synchronized (this) {
                if (this.closed) {
                    return;
                }
                log.info("Chat session ended abruptly by {} logging out.", principal.getName());
                this.close(ChatService.ReasonForLeaving.LOGGED_OUT, null);
            }
        }
    }
    
    private void close(ChatService.ReasonForLeaving reason, String unexpected) {
        closed = true;
        if(!pingFuture.isCancelled()) {
            pingFuture.cancel(true);
        }
        sessionRegistry.deregisterOnRemoveCallback(callback);
        ChatMessage message = chatService.leaveSession(chatSession, principal.getName(), reason);

        if(message != null) {
            CloseReason closeReason = reason == ChatService.ReasonForLeaving.ERROR ?
                    new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, unexpected) :
                    new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Chat Ended");

            //noinspection SynchronizeOnNonFinalField
            synchronized(wsSession) {
                if(wsSession.isOpen()) {
                    try {
                        wsSession.getBasicRemote().sendObject(message);
                        wsSession.close(closeReason);
                    } catch(IOException | EncodeException e) {
                        log.error("Error closing chat connection.", e);
                    }
                }
            }

            if(otherWsSession != null) {
                //noinspection SynchronizeOnNonFinalField
                synchronized(otherWsSession) {
                    if(this.otherWsSession.isOpen()) {
                        try {
                            otherWsSession.getBasicRemote().sendObject(message);
                            otherWsSession.close(closeReason);
                        } catch(IOException | EncodeException e) {
                            log.error("Error closing companion chat connection.", e);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Custom configurator saves at handshake time the underlying <code>HttpSession</code>
     *  which will be used in method <code>onOpen()</code>.
     */
    public static class EndpointConfigurator extends SpringConfigurator {
        private static final String HTTP_SESSION_KEY = "projava4webbook.ws.http.session";
        private static final String PRINCIPAL_KEY = "projava4webbook.ws.user.principal";
        
        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            log.entry();
            super.modifyHandshake(config, request, response);
            HttpSession httpSession = (HttpSession) request.getHttpSession(); 
            
            config.getUserProperties().put(HTTP_SESSION_KEY, httpSession);
            config.getUserProperties().put(PRINCIPAL_KEY, UserPrincipal.getPrincipal(httpSession));
            
            log.exit();
        }
        
        private static HttpSession getExposedSession(Session session) {
            return (HttpSession) session.getUserProperties().get(HTTP_SESSION_KEY);
        }
        
        private static Principal getExposedPrincipal(Session session) {
            return (Principal) session.getUserProperties().get(PRINCIPAL_KEY);
        }
    }
}
