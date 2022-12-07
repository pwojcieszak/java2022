package pl.edu.uj.library.model;


public class Book {
    int bookId;
    String title;
    Integer currentReader = null;
    String from = null;
    String to = null;
    int rentsCount = 0;

    public Book(String bId, String curRead, String from, String to){
        this.bookId=Integer.parseInt(bId);
        this.currentReader=Integer.parseInt(curRead);
        this.from = from;
        this.to = to;
    }
    public int getBookId(){
        return bookId;
    }
    public Integer getReaderId(){
        return currentReader;
    }
}
