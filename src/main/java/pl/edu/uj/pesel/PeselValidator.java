package pl.edu.uj.pesel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;

import java.text.MessageFormat;

public class PeselValidator {

  private static final Logger logger = LoggerFactory.getLogger(PeselValidator.class);

  public static void main(String[] args) {
    try {
      File read = new File(args[0]);
      Scanner myReader = new Scanner(read);
      try {
        File write = new File("dane.out.txt");
        if (write.createNewFile()) {
          System.out.println("File created: " + write.getName());
        } else {
          System.out.println("File already exists.");
        }
        try {
          FileWriter myWriter = new FileWriter("dane.out.txt");
          String arg;
          boolean valid;
          while (myReader.hasNextLine()) {
            arg = myReader.nextLine();
            valid = Pesel.check(new Pesel(arg));
            logger.info(MessageFormat.format("PESEL \"{0}\" is {1}", arg, valid ? "valid" : "invalid"));
            myWriter.write(valid ? "VALID" : "INVALID");
            myWriter.write(System.lineSeparator());
          }
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
