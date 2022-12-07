package pl.edu.uj.library.data;

import pl.edu.uj.library.model.Book;
import pl.edu.uj.library.model.BookCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentalsCSV {
    public static void readRentalsCSV (String filePath) throws DuplicateRentalException {
        BookCollection collection = new BookCollection();
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("[,]");
                records.add(Arrays.asList(values));
                if (i != 0){
                    for (Book element : collection.getAll()){
                        if(element.getBookId() == Integer.parseInt(values[0])){
                            throw new DuplicateRentalException("Book (bookId= "+values[0]+" rented by 2 reades(r1= "+values[1]+" and r2= " + element.getReaderId()+")");
                        }
                    }
                    collection.getAll().add(new Book(values[0], values[1], values[2], values[3]));
                }

                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
