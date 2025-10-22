import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
	private Config config;
	private RandomAccessFile randomAccessFile;

	public FileManager(Config config, int peerID) throws FileNotFoundException {
		this.config = config;
		String fileDirectory = "peer_" + peerID + "/";
		File directory = new File(fileDirectory);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		try {
			randomAccessFile = new RandomAccessFile(fileDirectory + config.getFileName(), "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public synchronized Piece readPiece(int which) throws IOException {
		int length = 0;

		// Calculate the length of the piece
		if (which == config.getNumPieces() - 1) {
			length = config.getFileSize() - config.getPieceSize() * which;
		} else {
			length = config.getPieceSize();
		}

		int offset = which * config.getPieceSize();
		byte[] bytes = new byte[length];

		// Seek to the appropriate position in the file and read the piece
		randomAccessFile.seek(offset);
		for (int i = 0; i < length; i++) {
			bytes[i] = randomAccessFile.readByte();
		}

		// Create a Piece object with the read data
		Piece piece = new Piece(which, bytes);
		return piece;
	}

	public synchronized void writePiece(Piece piece) throws IOException {
		int offset = piece.getWhichPiece() * config.getPieceSize();
		int length = piece.getPieceBytes().length;
		byte[] data = piece.getPieceBytes();

		// Seek to the appropriate position in the file and write the piece
		randomAccessFile.seek(offset);
		randomAccessFile.write(data, 0, length);
	}
}
