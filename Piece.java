/**
 * Represents a piece of a file in a peer-to-peer file sharing system.
 */
public class Piece {

	private final int whichPiece; // The index of this piece (starts from 0)
	private final byte[] pieceBytes; // The data bytes of this piece

	/**
	 * Initializes a new piece with the given index and data bytes.
	 *
	 * @param which      The index of the piece (starts from 0).
	 * @param pieceBytes The data bytes of the piece.
	 */
	public Piece(int which, byte[] pieceBytes) {

		this.whichPiece = which;
		this.pieceBytes = pieceBytes;

		// Note: In a real implementation, you would typically read the data from an
		// input stream
		// and populate the pieceBytes array with the actual data received from the
		// network.
	}

	/**
	 * Gets the data bytes of this piece.
	 *
	 * @return The data bytes of the piece.
	 */
	public byte[] getPieceBytes() {
		return pieceBytes;
	}

	/**
	 * Gets the index of this piece (starts from 0).
	 *
	 * @return The index of the piece.
	 */
	public int getWhichPiece() {
		return whichPiece;
	}
}
