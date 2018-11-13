package ntu.student.a5tar.light.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ntu.student.a5tar.light.R;
import ntu.student.a5tar.light.Entity.BookInfo;
import org.parceler.Parcels;

public class SearchByBookName extends AppCompatActivity {
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_bookname);

        //get user id
        id = getIntent().getIntExtra("search", 0);

        //search keyword
        String keyword= getIntent().getStringExtra("searchedWord");
        Search search = new Search();
        String content = "";
        try{
            content = search.getContent("{\"book_title\":\""+keyword+"\"}");
        } catch(IOException e){
            e.printStackTrace();
        }

        List<BookInfo> resultBookList=new ArrayList<>();
        try{
            resultBookList = search.parseJson(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int size = resultBookList.size();
        final TableLayout layout = (TableLayout) findViewById(R.id.seachResultTable);
        TableRow[] tableRows = new TableRow[size * 2];

        //display multiple search results
        if(size!=0){
            for(int i = 0; i < size; i++){
                final BookInfo book = resultBookList.get(i);

                tableRows[2 * i] = new TableRow(this);
                tableRows[2 * i + 1] = new TableRow(this);

                ImageView cover = new ImageView(this);
                cover.setImageResource(R.drawable.book_icon);
                new ImageLoadTask(book.getCoverPage(), cover).execute();
                TableRow.LayoutParams cover_param = new TableRow.LayoutParams(500, 800);
                cover_param.setMargins(60,90,0,0);
                cover.setLayoutParams(cover_param);
                tableRows[2 * i].addView(cover);
                layout.addView(tableRows[2 * i]);
                TextView title = new TextView(this);
                title.setText(book.getBookTitle());
                TableRow.LayoutParams title_param = new TableRow.LayoutParams(500,250);
                title_param.setMargins(60,0,0,0);
                title.setTextColor(Color.parseColor("#000000"));
                title.setTextSize(18);
                title.setLayoutParams(title_param);
                tableRows[2 * i + 1].addView(title);
                layout.addView(tableRows[2 * i + 1]);

                tableRows[2 * i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(SearchByBookName.this, BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        toBookDetail.putExtra("book",id);
                        startActivity(toBookDetail);
                        finish();
                    }
                });
            }
        } else{
            TableRow tableRow = new TableRow(this);
            ImageView no_result = new ImageView(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(1200,1400);
            no_result.setLayoutParams(layoutParams);
            tableRow.addView(no_result);
            layout.addView(tableRow);
            no_result.setImageResource(R.drawable.no_results_found);
        }
        ImageView backforsearch = (ImageView)findViewById(R.id.backforsearchpage);
        backforsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
