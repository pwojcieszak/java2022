package pl.edu.uj.library.model;

import java.util.ArrayList;
import java.util.List;

public class BookCollection {
    List<Book> allBooks;
    public BookCollection(){
        allBooks = new ArrayList<>();
    }
    public List<Book> getAll(){
        return allBooks;
    }
}
