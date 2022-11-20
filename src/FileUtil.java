import java.io.*;

public class FileUtil {
        static BufferedReader reader = null;

        public FileUtil(String filePath) throws FileNotFoundException {
            File file = new File(filePath);
            FileInputStream fileStream = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(fileStream);
            reader = new BufferedReader(input);
        }

    public int getCharCount() {
        int charCount = 0;
        String data;
        while(true) {
            try {
                if (!((data = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            charCount += data.length();
        }
        return charCount;
    }
}
