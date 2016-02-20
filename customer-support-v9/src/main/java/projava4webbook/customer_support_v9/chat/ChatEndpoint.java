package projava4webbook.customer_support_v9.chat;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

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
@WebListener
public class ChatEndpoint implements HttpSessionListener {
    
    private static final String HTTP_SESSION_PROPERTY = "projava4webbook.ws.HTTP_SESSION";
    private static final String WS_SESSION_PROPERTY = "projava4webbook.http.WS_SESSION";
    
    private static long sessionIdSequence = 1L;
    private static final Object sessionIdSequenceLock = new Object();
    
    private static final Map<Long, ChatSession> chatSessions = new Hashtable<>();
    private static final Map<Session, ChatSession> sessions = new Hashtable<>();
    private static final Map<Session, HttpSession> httpSessions = new Hashtable<>();
    public static final List<ChatSession> pendingSessions = new ArrayList<>();
    
    /* WebSocket methods  */
    
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId) {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(ChatEndpoint.HTTP_SESSION_PROPERTY);
        
        try {
            if (httpSession == null || httpSession.getAttribute("username") == null) {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "You are not logged in!"));
                return;
            }
            
            String username = (String) httpSession.getAttribute("username");
            session.getUserProperties().put("username", username);
            
            ChatMessage message = new ChatMessage();
            message.setTimestamp(OffsetDateTime.now());
            message.setUser(username);
            
            ChatSession chatSession;
            if (sessionId < 1) {
                message.setType(ChatMessage.Type.STARTED);
                message.setContent(username + " started the chat session.");
                
                chatSession = new ChatSession();
                synchronized (ChatEndpoint.sessionIdSequenceLock) {
                    chatSession.setSessionId(ChatEndpoint.sessionIdSequence++);
                }
                chatSession.setCustomer(session);
                chatSession.setCustomerUsername(username);
                chatSession.setCreationMessage(message);
                
                pendingSessions.add(chatSession);
                chatSessions.put(chatSession.getSessionId(), chatSession);
            } else {
                message.setType(ChatMessage.Type.JOINED);
                message.setContent(username + " joined the chat session.");
                
                chatSession = chatSessions.get(sessionId);
                chatSession.setRepresentative(session);
                chatSession.setRepresentativeUsername(username);
                
                pendingSessions.remove(chatSession);
                session.getBasicRemote().sendObject(chatSession.getCreationMessage());
                session.getBasicRemote().sendObject(message);
            }
            
            sessions.put(session, chatSession);
            httpSessions.put(session, httpSession);
            getSessionsFor(httpSession).add(session);
            chatSession.log(message);
            chatSession.getCustomer().getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            this.onError(session, e);
        }
    }
    
    /**
     * Echoes the message back to both clients.
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, ChatMessage message) {
        ChatSession c = sessions.get(session);
        Session other = getOtherSession(c, session);
        if (c != null && other != null) {
            c.log(message);
            try {
                session.getBasicRemote().sendObject(message);
                other.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                this.onError(session, e);
            }
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if (reason.getCloseCode() == CloseReason.CloseCodes.NORMAL_CLOSURE) {
            
            ChatMessage message = new ChatMessage();
            message.setUser((String) session.getUserProperties().get("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " left the chat.");
            
            try {
                Session other = this.close(session, message);
                if (other != null) {
                    other.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnError
    public void onError(Session session, Throwable e) {
        ChatMessage message = new ChatMessage();
        message.setUser((String) session.getUserProperties().get("username"));
        message.setType(ChatMessage.Type.ERROR);
        message.setTimestamp(OffsetDateTime.now());
        message.setContent(message.getUser() + " left the chat due to an error.");

        try {
            Session other = close(session, message);
            if (other != null) {
                other.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
            }
        } catch (IOException ignore) {
        } finally {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
            } catch (IOException ignore) {
            }
        }
    }

    /* HttpSessionListener methods */
    
    @Override
    public void sessionCreated(HttpSessionEvent se) { /* do nothing */ }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        if (httpSession.getAttribute(WS_SESSION_PROPERTY) != null) {
            ChatMessage message = new ChatMessage();
            message.setUser((String)httpSession.getAttribute("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " logged out.");
            
            for (Session session : new ArrayList<>(getSessionsFor(httpSession))) {
                try {
                    session.getBasicRemote().sendObject(message);
                    Session other = close(session, message); 
                    if (other != null) {
                        other.close();
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        session.close();
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }
    
    /* Private methods */

    @SuppressWarnings("unchecked")
    private synchronized ArrayList<Session> getSessionsFor(HttpSession httpSession) {
        try {
            if (httpSession.getAttribute(WS_SESSION_PROPERTY) == null) {
                httpSession.setAttribute(WS_SESSION_PROPERTY, new ArrayList<Session>());
            }
            return (ArrayList<Session>) httpSession.getAttribute(WS_SESSION_PROPERTY);
        } catch (IllegalStateException e) {
            return new ArrayList<>();
        }
    }
    
    private Session close(Session wsSession, ChatMessage message) {
        ChatSession chatSession = sessions.get(wsSession);
        Session otherWsSession = getOtherSession(chatSession, wsSession);
        HttpSession httpSession = httpSessions.get(wsSession);
        if (httpSession != null) {
            getSessionsFor(httpSession).remove(wsSession);
        }
        if (chatSession != null) {
            chatSession.log(message);
            pendingSessions.remove(chatSession);
            chatSessions.remove(chatSession.getSessionId());
            try {
                chatSession.writeChatLog(new File("chat." + chatSession.getSessionId() + ".log"));
            } catch (Exception e) {
                System.err.println("Could not write chat log");
                e.printStackTrace();
            }
        }
        if (otherWsSession != null) {
            sessions.remove(otherWsSession);
            httpSession = httpSessions.get(otherWsSession);
            if (httpSession != null) {
                getSessionsFor(httpSession).remove(wsSession);
            }
            try {
                otherWsSession.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
        return otherWsSession;
    }
    
    private Session getOtherSession(ChatSession c,  Session s) {
        return c == null ? null :
            (s == c.getCustomer() ? c.getRepresentative() : c.getCustomer());
    }
    
    /**
     * Custom configurator saves at handshake time the underlying <code>HttpSession</code>
     *  which will be used in method <code>onOpen()</code>.
     */
    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            super.modifyHandshake(config, request, response);
            
            config.getUserProperties().put(ChatEndpoint.HTTP_SESSION_PROPERTY, request.getHttpSession());
        }
    }
}
