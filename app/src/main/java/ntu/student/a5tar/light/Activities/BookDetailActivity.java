package ntu.student.a5tar.light.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ntu.student.a5tar.light.R;
import ntu.student.a5tar.light.Entity.BookInfo;
import org.parceler.Parcels;

public class BookDetailActivity extends AppCompatActivity{

    TextView addToShelfButton;
    DatabaseHelper dbHelper=new DatabaseHelper(this, "UserDatabase.db", null,1);
    public String book_id;
    public boolean check=true;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        final BookInfo book = (BookInfo)Parcels.unwrap(getIntent().getParcelableExtra("BookInfo"));
        id = getIntent().getIntExtra("book", 0);

        TextView bookName= (TextView)findViewById(R.id.bookName);
        bookName.setText(book.getBookTitle());

        TextView descriptionText= (TextView)findViewById(R.id.descriptionText);
        descriptionText.setText(book.getBookDescription());

        ImageView cover = (ImageView)findViewById(R.id.bookCover);
        new ImageLoadTask(book.getCoverPage(), cover).execute();

        //Set values for the info of the book with the BookInfo object passed in.

        TextView readNow = (TextView)findViewById(R.id.readNowButton);
        readNow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(BookDetailActivity.this, ChapterDetailActivity.class);
                intent.putExtra("BookInfo", Parcels.wrap(book));
                startActivity(intent);
                finish();
            };
        });
        ImageView gobacktolast = (ImageView)findViewById(R.id.goBackForDetailPage);
        gobacktolast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //add to bookshelf

        addToShelfButton = (TextView) findViewById(R.id.addToShelfButton);
        book_id=book.getBID();

        addToShelfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                SharedPreferences pref=getSharedPreferences("account"+id, MODE_PRIVATE);
                Cursor cursor = db.rawQuery("SELECT * FROM Book WHERE user_id="+id,null);

                //check whether the book has been added to bookshelf before

                if (cursor.moveToFirst()) {
                    do {
                        String book_id_db = cursor.getString(cursor.getColumnIndex("book_id"));
                        if (book_id.equals(book_id_db)) {
                            Toast.makeText(BookDetailActivity.this, "Added repeatedly!",
                                    Toast.LENGTH_SHORT).show();
                            check = false;
                            break;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();

                if (check) {
                    ContentValues values = new ContentValues();
                    values.put("book_id", book_id);
                    values.put("user_id", id);
                    db.insert("Book", null, values);
                    Toast.makeText(BookDetailActivity.this, "Successfully Added!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
