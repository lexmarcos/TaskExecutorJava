package Utils;

import java.io.*;

public class FileManager {
    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeInLine("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            writeInLine("0");
        }
    }

    public synchronized void writeInLine(String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        String firstLine = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            firstLine = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstLine != null ? firstLine : "";
    }
}
