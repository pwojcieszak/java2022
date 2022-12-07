package pl.edu.uj;

import org.junit.jupiter.api.Test;
import pl.edu.uj.library.data.DuplicateRentalException;
import pl.edu.uj.library.data.RentalsCSV;

public class RentalsTest {
    @Test
    public void testRental() throws DuplicateRentalException {
        RentalsCSV.readRentalsCSV("src/main/resources/rental.csv");
    }
}
