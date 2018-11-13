package ntu.student.a5tar.light.Entity;

import java.util.List;

/**
 * This class implements the BookList Entity with the attributes bookList and trending.
 *
 * @author  jeko lonardo
 */

public class BookList {
    private static List<BookInfo> bookList;
    private static List<BookInfo> trending;

    public BookList(List<BookInfo> bookList) {
        this.bookList = bookList;
    }

    public List<BookInfo> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookInfo> bookList) {
        this.bookList = bookList;
    }

    public List<BookInfo> searchBookList(String keyword){
        return bookList;
    }

    public static List<BookInfo> getTrending(){
        trending.clear();
        for (BookInfo book : bookList){
            if (book.getRank() < 10){
                addTopBooks(book);
            }
        }
        return trending;
    }

    public static void addTopBooks(BookInfo book){
        trending.add(book);
    }
}
