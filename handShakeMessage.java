import java.io.*;
import java.net.*;

/**
 * Represents a greeting (or handshake) message format for initializing
 * communication in a peer-to-peer file sharing network.
 */
public class handShakeMessage {

    // Constant header string used in the handshake message
    private static final String HANDSHAKE_HEADER = "P2PFILESHARINGPROJ";

    // Size of the handshake message header
    private static final int HEADER_SIZE = 28;

    // ID of the peer sending or receiving the handshake message
    private int peerID;

    /**
     * Constructs a new GreetingMessage instance with the provided peerID.
     *
     * @param peerID The ID of the peer sending or receiving the message.
     */
    public handShakeMessage(int peerID) {
        this.peerID = peerID;
    }

    /**
     * Sends a handshake message with the constant header and peerID to the given
     * socket.
     *
     * @param socket The socket to send the handshake message to.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    public void sendGreeting(Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        byte[] headerBytes = HANDSHAKE_HEADER.getBytes();
        out.write(headerBytes, 0, HEADER_SIZE - 4); // Subtracting 4 bytes for the peerID
        out.writeInt(peerID);
    }

    /**
     * Reads a handshake message from the given socket, validates the header,
     * and populates the peerID property.
     *
     * @param socket The socket to read the handshake message from.
     * @throws IOException If an I/O error occurs or the received handshake header
     *                     is invalid.
     */
    public void receiveGreeting(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        byte[] headerBytes = new byte[HEADER_SIZE - 4];
        in.readFully(headerBytes);
        String receivedHeader = new String(headerBytes);
        if (!receivedHeader.equals(HANDSHAKE_HEADER)) {
            throw new IOException("Invalid Handshake Header");
        }
        this.peerID = in.readInt();
    }

    /**
     * Retrieves the peerID associated with the handshake message.
     *
     * @return The ID of the peer.
     */
    public int getPeerID() {
        return this.peerID;
    }
}
