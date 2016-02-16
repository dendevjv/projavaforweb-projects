package projava4webbook.simulatedcluster;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/clusterNodeSocket/{nodeId}")
public class ClusterNodeEndpoint {
    private static final List<Session> nodes = new ArrayList<>();
    
    @OnOpen
    public void onOpen(Session session, @PathParam("nodeId") String nodeId) {
        System.out.println("INFO: Node [" + nodeId + "] connected to cluster");
        ClusterMessage message = new ClusterMessage(nodeId, "Joined the cluster.");
        byte[] bytes;
        try {
            bytes = toByteArray(message);
            for (Session node : nodes) {
                node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
            }
        } catch (IOException e) {
            System.err.println("ERROR: Exception when notifying of new node");
            e.printStackTrace();
        }
        nodes.add(session);
    }
    
    @OnMessage
    public void onMessage(Session session, byte[] message) {
        try {
            for (Session node : nodes) {
                if (node != session) {
                    node.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Exception when handling message on server");
            e.printStackTrace();
        }
    }
    
    @OnClose
    public void onClose(Session session, @PathParam("nodeId") String nodeId) {
        System.out.println("INFO: Node [" + nodeId + "] disconnected");
        nodes.remove(session);
        ClusterMessage message = new ClusterMessage(nodeId, "Left the cluster.");
        byte[] bytes;
        try {
            bytes = toByteArray(message);
            for (Session node : nodes) {
                node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
            }
        } catch (IOException e) {
            System.err.println("ERROR: Exception when notifying of left node");
            e.printStackTrace();
        }
    }
    
    private static byte[] toByteArray(ClusterMessage message) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
                ObjectOutputStream stream = new ObjectOutputStream(output);) {
            stream.writeObject(message);
            return output.toByteArray();
        }
    }
}
