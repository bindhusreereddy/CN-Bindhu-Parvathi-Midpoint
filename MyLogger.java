import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	private FileHandler fileHandler;
	private SimpleFormatter simpleFormatter;
	private Logger logger;
	private int peerID;

	public MyLogger(int peerID) throws SecurityException, IOException {
		this.peerID = peerID;
		logger = Logger.getLogger("Peer" + peerID);
		logger.setLevel(Level.INFO);
		fileHandler = new FileHandler("log_peer_" + peerID + ".log");
		simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		logger.addHandler(fileHandler);
	}

	public void TCPConnToLog(int peerID) {
		logger.info("Peer " + this.peerID + " makes a connection to Peer " + peerID);
	}

	public void TCPConnFromLog(int peerID) {
		logger.info("Peer " + this.peerID + " is connected from Peer " + peerID);
	}

	public void changePrefLog(ArrayList<Integer> preferredNeighbors) {
		StringBuilder message = new StringBuilder("Peer " + this.peerID + " has the preferred neighbors: ");
		for (int i = 0; i < preferredNeighbors.size(); i++) {
			if (i != preferredNeighbors.size() - 1) {
				message.append(preferredNeighbors.get(i)).append(", ");
			} else {
				message.append(preferredNeighbors.get(i));
			}
		}
		logger.info(message.toString());
	}

	public void changeOptLog(int peerID) {
		logger.info("Peer " + this.peerID + " has the 'optimistically unchoked' neighbor " + peerID);
	}

	public void unchokingLog(int peerID) {
		logger.info("Peer " + this.peerID + " is 'unchoked' by " + peerID);
	}

	public void choking(int peerID) {
		logger.info("Peer " + this.peerID + " is 'choked' by " + peerID);
	}

	public void haveLog(int peerID, int pieceIndex) {
		logger.info(
				"Peer " + this.peerID + " receives a 'have' message from " + peerID + " for the piece " + pieceIndex);
	}

	public void interestedLog(int peerID) {
		logger.info("Peer " + this.peerID + " receives the 'interested' message from " + peerID);
	}

	public void notInterestedLog(int peerID) {
		logger.info("Peer " + this.peerID + " receives the 'not interested' message from " + peerID);
	}

	public void downloadLog(int peerID, int pieceIndex) {
		logger.info("Peer " + this.peerID + " has downloaded the piece " + pieceIndex + " from " + peerID);
	}

	public void compDownloadLog() {
		logger.info("Peer " + this.peerID + " has downloaded the complete file");
	}
}
