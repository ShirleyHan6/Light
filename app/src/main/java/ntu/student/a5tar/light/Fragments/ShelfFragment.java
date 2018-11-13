package ntu.student.a5tar.light.Fragments;

import android.content.SharedPreferences;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ntu.student.a5tar.light.Activities.DatabaseHelper;
import ntu.student.a5tar.light.Activities.ImageLoadTask;
import ntu.student.a5tar.light.Activities.Search;
import ntu.student.a5tar.light.Activities.BookDetailActivity;
import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.R;

import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import ntu.student.a5tar.light.R;
import static android.content.Context.MODE_PRIVATE;

public class ShelfFragment extends Fragment {

    TableLayout tableView;
    public static int id;

    //get user id
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            id=bundle.getInt("fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_shelf, container, false);

        tableView = (TableLayout) view.findViewById(R.id.tableOfShelf);

        //retrieve book database by user id
        DatabaseHelper dbHelper=new DatabaseHelper(getActivity(), "UserDatabase.db", null,1);
        SharedPreferences pref=getActivity().getSharedPreferences("account"+id, MODE_PRIVATE);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(book_id) AS bookNo FROM Book WHERE user_id="+id,null);
        c.moveToFirst();
        int bookNo=c.getInt(c.getColumnIndex("bookNo"));
        System.out.println(bookNo);

        TableLayout.LayoutParams shelfTableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        final TableLayout shelfTableLayout = new TableLayout(view.getContext());
        shelfTableLayout.setLayoutParams(shelfTableLayoutParams);

        int bound = (int) Math.ceil(bookNo/3.0);

        for (int i = 0 ; i < bound ; i++){
            TableRow imgTblRow =  new TableRow(view.getContext());
            TableRow txtTblRow = new TableRow(view.getContext());

            Cursor cursor = db.rawQuery("SELECT * FROM Book WHERE user_id="+id,null);
            cursor.moveToFirst();

            for (int j = 0 ; j < 3 ; j++){

                if(i*3+j+1 > bookNo){
                    // set dummy image and text views
                    final ImageView imgView = new ImageView(view.getContext());
                    imgView.setId((Integer) (i*3+j+1));
                    imgView.setImageResource(R.drawable.book_icon);
                    imgView.setVisibility(View.INVISIBLE);
                    setImageDimensions(imgView);
                    imgTblRow.addView(imgView);

                    TextView txtResGender = new TextView(view.getContext());
                    txtResGender.setTag("book "+String.valueOf(i*3+j+1));
                    txtResGender.setGravity(Gravity.CENTER);
                    setTextDimensions(txtResGender);
                    //txtResGender.setText(txtResGender.getTag().toString());
                    txtResGender.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    //txtResGender.setTextColor(Color.parseColor("#9C9C9C"));
                    txtTblRow.addView(txtResGender);
                    break;
                }

                String book_id_db = cursor.getString(cursor.getColumnIndex("book_id"));

                Search search = new Search();
                String content = "";
                try {
                    content = search.getContent("{\"uid\":\""+book_id_db+"\"}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<BookInfo> resultBookList = new ArrayList<>();
                try {
                    resultBookList = search.parseJson(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final BookInfo book = resultBookList.get(0);

                //load book cover
                final ImageView img = new ImageView(view.getContext());
                img.setId((Integer) (i*3+j+1));
                new ImageLoadTask(book.getCoverPage(), img).execute();
                setImageDimensions(img);
                imgTblRow.addView(img);

                //go to book detail
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(view.getContext(), "Book "+img.getId()+" is clicked", Toast.LENGTH_SHORT).show();
                        Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        startActivity(toBookDetail);
                    }
                });

                //load book title
                TextView txt = new TextView(view.getContext());
                txt.setTag("book "+String.valueOf(i*3+j+1));
                txt.setGravity(Gravity.CENTER);
                setTextDimensions(txt);
                txt.setText(book.getBookTitle());
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                txt.setTextColor(Color.parseColor("#000000"));
                txtTblRow.addView(txt);
                cursor.moveToNext();
            }
            shelfTableLayout.addView(imgTblRow);
            shelfTableLayout.addView(txtTblRow);
        }

        tableView.addView(shelfTableLayout);

        return view;
    }

    private void setImageDimensions(View view){

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int weight = 1;
        TableRow.LayoutParams params = new TableRow.LayoutParams(width, height);
        params.weight = weight;
        view.setLayoutParams(params);
    }

    private void setTextDimensions(View view){

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        int weight = 1;
        TableRow.LayoutParams params = new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = weight;
        view.setLayoutParams(params);
    }
}