/**
 * Represents the status of bits in a peer-to-peer file sharing system.
 * Each bit might represent the status of a piece of the file.
 */
public class BitStatus {

    // The total number of bits that this status is tracking
    private int numBits;

    // Array to hold the status of each bit
    private byte[] bits;

    /**
     * Constructor to initialize the BitStatus with the specified number of bits.
     * 
     * @param numBits the total number of bits to be tracked.
     */
    public BitStatus(int numBits) {
        this.numBits = numBits;

        // Initializes the bits array with all zeroes
        bits = new byte[numBits];
    }

    /**
     * Updates the status of bits using the provided data array.
     * 
     * @param data the new status of bits.
     */
    public void updateBitStatus(byte[] data) {
        for (int i = 0; i < numBits; i++) {
            bits[i] = data[i];
        }
    }

    /**
     * Checks if all bits are set (i.e., the file is fully received/downloaded).
     * 
     * @return true if all bits are set, false otherwise.
     */
    public boolean isComplete() {
        for (byte b : bits) {
            if (b == 0)
                return false;
        }
        return true;
    }

    /**
     * Returns the current status of all bits.
     * 
     * @return an array of bytes representing the status of bits.
     */
    public byte[] getBits() {
        return bits;
    }
}
