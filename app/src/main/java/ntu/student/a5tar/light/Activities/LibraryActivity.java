package ntu.student.a5tar.light.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import nl.siegmann.epublib.domain.Book;
import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.R;
import android.content.Intent;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    public static int id;
    public List<BookInfo> checkContent(String subject){
        String content = "";
        List<BookInfo> resultBookList=new ArrayList<>();
        Search search = new Search();
        try{

            content = search.getContent("{\"subject\":\"" + subject + "\"}");
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            resultBookList = search.parseJson(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBookList;
    }

    //load books into library according to subject categories
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        id = getIntent().getIntExtra("library", 0);

        String[] subjects = new String[]{"Roads Less Travelled", "Ties That Bind", "Transition", "Tales from East Asia","Under One Sky",
                "Dreams and Choices", "Bridges", "Ten-A-City: Stories Built to Last", "Cekal-Tekal-Mekar: Karya Sepanjang Zaman"};
        Search search = new Search();
        for (Integer count = 1; count <= 9; count++)
        {
            int maxbooknum = 3;
            if (count == 6)
                maxbooknum = 2;
            if (count == 8 || count == 9)
                maxbooknum = 1;
            for (Integer booknum = 1; booknum <= maxbooknum; booknum++)
            {
                List<BookInfo> resultBookList=new ArrayList<>();
                resultBookList = checkContent(subjects[count - 1]);
                final BookInfo book = resultBookList.get(booknum - 1);
                String title = "librarytext" + count.toString() + "_" + booknum.toString();
                int titleid =  getResources().getIdentifier(title, "id", getPackageName());
                String image = "librarybook" + count.toString() + "_" + booknum.toString();
                int imageid = getResources().getIdentifier(image, "id", getPackageName());

                //load book title and book cover
                TextView bookName = (TextView)findViewById(titleid);

                ImageView cover = (ImageView)findViewById(imageid);
                if(book.getLanguage().equals("tam")||book.getLanguage().equals("chi")){
                    Search searcha = new Search();
                    try{
                        bookName.setText(searcha.getTitle(book.getResourceURL()));
                    } catch(Exception e){
                        e.printStackTrace();
                        bookName.setText("தப்புக்கணக்கு");
                    }
                }
                new ImageLoadTask(book.getCoverPage(),cover).execute();

                //go to book detail page
                cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(LibraryActivity.this, BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        toBookDetail.putExtra("book",id);
                        startActivity(toBookDetail);
                    }
                });
            }
        }
    }

}
