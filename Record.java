import java.net.*;
import java.util.concurrent.atomic.*;

/**
 * Represents a record in a peer-to-peer file sharing system.
 * Each record pertains to a specific peer and contains details
 * such as sockets for communication, download status, and bit status of the
 * file.
 */
public class Record {

	private final int peerId;
	private final Socket downloadSocket;
	private final Socket uploadSocket;
	private final Socket notificationSocket;
	private volatile int downloadedAmount;
	private AtomicInteger communicationState; // 0: choked, 1: unchoked, 2: optimistically unchoked
	private BitStatus bitStatus;

	/**
	 * Constructor to initialize the PeerRecord with the specified details.
	 *
	 * @param numBits the total number of bits to be tracked.
	 * @param peerId  ID of the peer.
	 * @param socket1 Download socket.
	 * @param socket2 Upload socket.
	 * @param socket3 Notification socket.
	 */
	public Record(int numBits, int peerId, Socket socket1, Socket socket2, Socket socket3) {
		this.peerId = peerId;
		this.downloadSocket = socket1;
		this.uploadSocket = socket2;
		this.notificationSocket = socket3;
		this.downloadedAmount = 0;
		this.communicationState = new AtomicInteger(0);
		this.bitStatus = new BitStatus(numBits);
	}

	/**
	 * Checks if the peer has completed downloading the file.
	 *
	 * @return True if the peer has completed downloading, otherwise false.
	 */
	public boolean isComplete() {
		return bitStatus.isComplete();
	}

	/**
	 * Gets the ID of the peer.
	 *
	 * @return The peer's ID.
	 */
	public int getPeerId() {
		return peerId;
	}

	/**
	 * Increments the downloaded amount.
	 */
	public void incrementDownloadedAmount() {
		downloadedAmount++;
	}

	/**
	 * Resets the downloaded amount to zero.
	 */
	public void resetDownloadedAmount() {
		downloadedAmount = 0;
	}

	/**
	 * Gets the downloaded amount.
	 *
	 * @return The downloaded amount.
	 */
	public int getDownloadedAmount() {
		return downloadedAmount;
	}

	/**
	 * Updates the bit status at the specified position with the given value.
	 *
	 * @param position The position to update.
	 * @param value    The new value.
	 */
	public void updateBitStatus(int position, byte value) {
		byte[] currentBits = bitStatus.getBits();
		currentBits[position] = value;
		bitStatus.updateBitStatus(currentBits);
	}

	/**
	 * Gets the bit status of the peer.
	 *
	 * @return The bit status.
	 */
	public BitStatus getBitStatus() {
		return bitStatus;
	}

	/**
	 * Gets the download socket.
	 *
	 * @return The download socket.
	 */
	public Socket getDownloadSocket() {
		return downloadSocket;
	}

	/**
	 * Gets the upload socket.
	 *
	 * @return The upload socket.
	 */
	public Socket getUploadSocket() {
		return uploadSocket;
	}

	/**
	 * Gets the notification socket.
	 *
	 * @return The notification socket.
	 */
	public Socket getNotificationSocket() {
		return notificationSocket;
	}

	/**
	 * Gets the communication state (choked, unchoked, or optimistically unchoked).
	 *
	 * @return The communication state.
	 */
	public AtomicInteger getCommunicationState() {
		return communicationState;
	}
}
