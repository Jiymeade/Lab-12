
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
public class JFile {
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

        /*

        Here is the data file we are reading:
        000001, Bilbo, Baggins, Esq., 1060
        000002, Frodo, Baggins, Esq., 1120
        000003, Samwise, Gamgee, Esq., 1125
        000004, Peregrin, Took, Esq., 1126
        000005, Meridoc, Brandybuck, Esq., 1126

        */

        final int FIELDS_LENGTH = 5;

        String id, firstName, lastName, title;
        int yob;
        int lineNumber = 0;





        try {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int line = 0;
                while (reader.ready()) {
                    rec = reader.readLine();
                    lines.add(rec);
                    line++;


                    // echo to screen
                    System.out.printf("\nLine %4d %-60s ", line, rec);

                }

                System.out.println(" ");
                System.out.println("\nText file name: " + file.getFileName());
                try {
                    file = selectedFile.toPath();
                    try(Scanner sc = new Scanner(new FileInputStream(file.toFile()))){
                        int count=0;
                        while(sc.hasNext()){
                            sc.next();
                            count++;
                        }
                        System.out.println(" ");
                        System.out.println("Number of words: " + count);
                    }

                    long count = Files.lines(file).count();
                    System.out.println(" ");
                    System.out.println("Total Lines: " + count);

                } catch (Exception e) {
                    e.getStackTrace();
                }
                System.out.println(" ");
                BufferedReader bufferedReader = null;
                FileUtil fileUtil = new FileUtil(selectedFile.getPath());
                System.out.println("No. of characters in file: " + fileUtil.getCharCount());

                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");


                class FileUtil {
                    static BufferedReader reader = null;

                    public FileUtil(String filePath) throws FileNotFoundException {
                        File file = new File(filePath);
                        FileInputStream fileStream = new FileInputStream(file);
                        InputStreamReader input = new InputStreamReader(fileStream);
                        reader = new BufferedReader(input);
                    }
                    public static int getCharCount() throws IOException {
                        int charCount = 0;
                        String data;
                        while((data = reader.readLine()) != null) {
                            charCount += data.length();
                        }
                        return charCount;
                    }
                }

                String[] fields;
                for (String l : lines) {
                    fields = l.split(",");

                    if (fields.length == FIELDS_LENGTH) {
                        id = fields[0].trim();
                        firstName = fields[1].trim();
                        lastName = fields[2].trim();
                        title = fields[3].trim();
                        yob = Integer.parseInt(fields[4].trim());



                    } else {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(l);
                    }
                }

            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }  // end of TRY
        catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
