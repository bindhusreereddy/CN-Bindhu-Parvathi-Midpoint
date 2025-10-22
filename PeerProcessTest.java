public class PeerProcessTest {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java PeerProcessTest <myPeerID> <numBits> <remotePeerID>");
            return;
        }

        int myPeerID = Integer.parseInt(args[0]);
        int numBits = Integer.parseInt(args[1]);
        int remotePeerID = Integer.parseInt(args[2]);

        PeerProcess myPeer = new PeerProcess(myPeerID, numBits);

        // Example: Connect to another peer with hardcoded host and port
        String remoteHost = "127.0.0.1"; // This should be dynamic or read from config
        int remotePort = 8000; // This should be dynamic or read from config
        myPeer.connectToPeer(remotePeerID, remoteHost, remotePort);

        // Example: Sending a message to the remote peer
        String message = "Hello from peer " + myPeerID;
        myPeer.sendCommonMessage(remotePeerID, 2, message.getBytes());

        // Disconnect from the remote peer
        myPeer.disconnectPeer(remotePeerID);
    }
}
