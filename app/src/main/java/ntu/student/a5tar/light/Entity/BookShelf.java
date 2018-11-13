package ntu.student.a5tar.light.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the BookShelf Entity with the attribute bookShelf
 *
 * @author jeko lonardo
 */

public class BookShelf {
    private Map<BookInfo, BookStatus> bookShelf = new HashMap<BookInfo, BookStatus>();

    public BookShelf(Map<BookInfo, BookStatus> bookShelf) {
        this.bookShelf = bookShelf;
    }

    public Map<BookInfo, BookStatus> getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(Map<BookInfo, BookStatus> bookShelf) {
        this.bookShelf = bookShelf;
    }

    public void update(BookInfo book, BookStatus bookStatus){

    }
}
