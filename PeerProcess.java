import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class PeerProcess {
    private final int peerId;
    private final int numBits;

    // Keep it simple: remotePeerID -> Socket
    private final ConcurrentHashMap<Integer, Socket> connections = new ConcurrentHashMap<>();

    public PeerProcess(int peerId, int numBits) {
        this.peerId = peerId;
        this.numBits = numBits;
    }

    public boolean connectToPeer(int remotePeerID, String host, int port) {
        try {
            Socket s = new Socket();
            // 3s connect timeout, IPv4 host is "127.0.0.1"
            s.connect(new InetSocketAddress(host, port), 3000);
            connections.put(remotePeerID, s);
            System.out.println("Connected to peer " + remotePeerID + " at " + host + ":" + port);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to connect to the peer " + remotePeerID + ": " + e.getMessage());
            return false;
        }
    }

    public void sendCommonMessage(int remotePeerID, int type, byte[] data) {
        Socket s = connections.get(remotePeerID);
        if (s == null || s.isClosed()) {
            System.out.println("Peer " + remotePeerID + " is not connected.");
            return;
        }
        try {
            // Simple frame: [length(int)][type(int)][data]
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeInt(data.length);
            out.writeInt(type);
            out.write(data);
            out.flush();
            System.out.println("Sent message to peer " + remotePeerID + " (type=" + type + ", bytes=" + data.length + ")");
        } catch (IOException e) {
            System.out.println("Failed to send to peer " + remotePeerID + ": " + e.getMessage());
        }
    }

    public void disconnectPeer(int remotePeerID) {
        Socket s = connections.remove(remotePeerID);
        if (s == null) {
            System.out.println("Peer " + remotePeerID + " is not connected.");
            return;
        }
        try {
            s.close();
            System.out.println("Disconnected from peer " + remotePeerID);
        } catch (IOException e) {
            System.out.println("Error closing peer " + remotePeerID + ": " + e.getMessage());
        }
    }
}