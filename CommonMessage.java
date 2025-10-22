import java.io.*;
import java.net.*;

/**
 * Represents a common message format in a peer-to-peer communication system.
 */
public class CommonMessage {

    // The length of the message data
    private int length;

    // The type of message (can be used to determine the message's purpose or
    // action)
    private int type;

    // The actual data or payload of the message
    private byte[] data;

    /**
     * Constructor to initialize a CommonMessage with the specified attributes.
     *
     * @param length the length of the data
     * @param type   the type of the message
     * @param data   the actual message data
     */
    public CommonMessage(int length, int type, byte[] data) {
        this.length = length;
        this.type = type;
        this.data = data;
    }

    /**
     * Reads a CommonMessage from the provided socket's input stream.
     * 
     * @param socket the socket from which the message should be read
     * @throws IOException if an I/O error occurs while reading from the socket
     */
    public void readFrom(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Read the length and type from the input stream
        length = in.readInt();
        type = in.readInt();

        // Read the actual message data based on the specified length
        data = new byte[length];
        in.readFully(data);
    }

    /**
     * Writes this CommonMessage to the provided socket's output stream.
     * 
     * @param socket the socket to which the message should be written
     * @throws IOException if an I/O error occurs while writing to the socket
     */
    public void writeTo(Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Write the length, type, and actual data to the output stream
        out.writeInt(length);
        out.writeInt(type);
        out.write(data);
    }

    // Getter and Setter methods for `length`, `type`, and `data`
}
