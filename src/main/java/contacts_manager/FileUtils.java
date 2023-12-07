package contacts_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    private final static String directory = "data";
    private final static String filename = "contacts.txt";

    private final static Path dataDirectory = Paths.get(directory);
    private final static Path dataFile = Paths.get(directory, filename);

    static void createDirs(){
        try{
            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            if (! Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static void writeContactToFile(Contact contact) {
        String contactLine = String.format("%s|%s", contact.getFullName(), contact.getPhoneNumber());
        try {
            Files.write(
                    dataFile,
                    Arrays.asList(contactLine), // list with one item
                    StandardOpenOption.APPEND
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static void readFile(){
        System.out.println("Name | Phone number\n" +
                "---------------");
        try {
            List<String> lines = Files.readAllLines(dataFile);
            for (String line: lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void search(String term){
        try {
            List<String> lines = Files.readAllLines(dataFile);
            for (String line: lines) {
                if(line.toLowerCase().startsWith(term.toLowerCase())){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void delete(String name) {
        try {
            List<String> lines = Files.readAllLines(dataFile);
            List<String> writeLines = new ArrayList<>();

            for (String line: lines) {
                if(!line.toUpperCase().startsWith(name.toUpperCase())){
                    writeLines.add(line);
                }
            }

            Files.write(dataFile, writeLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
