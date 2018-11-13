package ntu.student.a5tar.light.Control;

import java.util.List;

import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.Entity.BookList;
import ntu.student.a5tar.light.Entity.BookStatus;
import ntu.student.a5tar.light.Entity.User;
import android.content.Context;
/**
 * This class implements the BookManager Control.
 *
 * @author jeko lonardo
 */

public class BookManager {
    private BookList bookList;
    private List<BookInfo> books;

    public List<BookInfo> searchBook(String keyword){
        books = bookList.searchBookList(keyword);
        if (books.size() > 0){
            return books;
        }else{
            return null;
        }
    }

    public String getBookInfo(BookInfo book){
        return book.getBookInfo();
    }

    public String readBook(BookInfo book, User self){
        String content = book.getBookContent();
        self.updateBookShelf(book, BookStatus.READING);
        return content;
    }
    private Context mContext;

    Context context = mContext.getApplicationContext();
}
