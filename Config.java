import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {
    private final String fileName;
    private final int fileSize;
    private final int pieceSize;
    private final int numOfPreferredNeighbors;
    private final int unchokeInterval;
    private final int optionalUnchokeInterval;
    private final int numberOfPieces;
    private final ArrayList<Integer> peerIDs;
    private final ArrayList<String> addresses;
    private final ArrayList<Integer> downloadPorts;
    private final ArrayList<Boolean> hasCompleteFileFlags;
    private final int numOfPeers;
    private final ArrayList<Integer> uploadPorts;
    private final ArrayList<Integer> havePorts;

    public int getFileSize() {
        return fileSize;
    }

    public int getNumPieces() {
        return numberOfPieces;
    }

    public int getPieceSize() {
        return pieceSize;
    }

    public int getNumPreferredNeighbors() {
        return numOfPreferredNeighbors;
    }

    public int getUnchokeInterval() {
        return unchokeInterval;
    }

    public int getOptionalUnchokeInterval() {
        return optionalUnchokeInterval;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNumPeers() {
        return numOfPeers;
    }

    public int getDownloadPort(int index) {
        return downloadPorts.get(index);
    }

    public int getUploadPort(int index) {
        return uploadPorts.get(index);
    }

    public int getHavePort(int index) {
        return havePorts.get(index);
    }

    public ArrayList<Integer> getPeerIDs() {
        return peerIDs;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public ArrayList<Boolean> getHasCompleteFileFlags() {
        return hasCompleteFileFlags;
    }

    public Config(String commonConfigPath, String peerInfoPath) throws FileNotFoundException {
        Scanner commonConfigScanner = new Scanner(new FileReader(commonConfigPath));
        numOfPreferredNeighbors = Integer.parseInt(commonConfigScanner.nextLine().trim());
        unchokeInterval = Integer.parseInt(commonConfigScanner.nextLine().trim());
        optionalUnchokeInterval = Integer.parseInt(commonConfigScanner.nextLine().trim());
        fileName = commonConfigScanner.nextLine().trim();
        fileSize = Integer.parseInt(commonConfigScanner.nextLine().trim());
        pieceSize = Integer.parseInt(commonConfigScanner.nextLine().trim());

        if (fileSize % pieceSize == 0) {
            numberOfPieces = fileSize / pieceSize;
        } else {
            numberOfPieces = fileSize / pieceSize + 1;
        }

        commonConfigScanner.close();

        Scanner peerInfoScanner = new Scanner(new FileReader(peerInfoPath));
        peerIDs = new ArrayList<>();
        addresses = new ArrayList<>();
        downloadPorts = new ArrayList<>();
        hasCompleteFileFlags = new ArrayList<>();
        uploadPorts = new ArrayList<>();
        havePorts = new ArrayList<>();

        int count = 0;
        while (peerInfoScanner.hasNextLine()) {
            String s = peerInfoScanner.nextLine();
            String[] split = s.split(" ");
            peerIDs.add(Integer.parseInt(split[0].trim()));
            addresses.add(split[1].trim());
            downloadPorts.add(Integer.parseInt(split[2].trim()));
            uploadPorts.add(Integer.parseInt(split[2].trim()) + 1);
            havePorts.add(Integer.parseInt(split[2].trim()) + 2);
            hasCompleteFileFlags.add(split[3].trim().equals("1"));
            count++;
        }

        numOfPeers = count;
    }
}
