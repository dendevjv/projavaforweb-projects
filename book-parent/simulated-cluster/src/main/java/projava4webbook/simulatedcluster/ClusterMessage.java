package projava4webbook.simulatedcluster;

import java.io.Serializable;

public class ClusterMessage implements Serializable {
    private static final long serialVersionUID = -5202442783048860379L;

    private String nodeId;
    private String message;

    public ClusterMessage() {
    }

    public ClusterMessage(String nodeId, String message) {
        this.nodeId = nodeId;
        this.message = message;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
