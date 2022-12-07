package pl.edu.uj.library.data;

public class DuplicateRentalException extends Exception{
    public DuplicateRentalException(String errorMessage) {
        super(errorMessage);
    }
}
