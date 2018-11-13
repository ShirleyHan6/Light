package ntu.student.a5tar.light.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ntu.student.a5tar.light.Activities.DatabaseHelper;
import ntu.student.a5tar.light.Activities.ImageLoadTask;
import ntu.student.a5tar.light.Activities.LibraryActivity;
import ntu.student.a5tar.light.Activities.Search;
import ntu.student.a5tar.light.Activities.SearchByBookName;
import ntu.student.a5tar.light.Activities.BookDetailActivity;
import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.R;

import org.parceler.Parcels;
import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    List<String> historyList=new ArrayList<>();
    public static int id;
    int index;
    String subject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get user id
        Bundle bundle=getArguments();
        if(bundle!=null){
            id=bundle.getInt("fragment");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        //search book
        ImageView toSearchResult = (ImageView) view.findViewById(R.id.searchIcon);
        toSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.searchBar);
                String searchedWord = editText.getText().toString();
                Intent toResult = new Intent(getActivity().getApplication(), SearchByBookName.class);
                toResult.putExtra("searchedWord", searchedWord);
                toResult.putExtra("search", id);
                startActivity(toResult);
            }
        });

        //go to library
        TextView tolibrarytext = (TextView) view.findViewById(R.id.tolibrarytext);
        tolibrarytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tolibrary = new Intent(getActivity().getApplication(), LibraryActivity.class);
                tolibrary.putExtra("library", id);
                startActivity(tolibrary);
            }
        });

        //go to library
        ImageView tolibraryimage = (ImageView) view.findViewById(R.id.tolibraryimage);
        tolibraryimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tolibrary = new Intent(getActivity().getApplication(), LibraryActivity.class);
                tolibrary.putExtra("library", id);
                startActivity(tolibrary);
            }
        });

        //trendings
        //retrieve add-to-shelf times in the database

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "UserDatabase.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SElECT book_id, download " +
                "FROM( SELECT book_id, COUNT(user_id) AS download " +
                "FROM Book " +
                "GROUP BY book_id )" +
                "ORDER BY download DESC", null);

        //load books if there is no data in the book database
        if (!cursor.moveToFirst()) {
            //book1
            Search search = new Search();
            String content = "";
            try {
                content = search.getContent("ab57e410-7171-445a-bfb6-430642fa7c19");
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<BookInfo> resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo booka = resultBookList.get(0);
            ImageView bookimagea = (ImageView) view.findViewById(R.id.bookimagea);
            new ImageLoadTask(booka.getCoverPage(), bookimagea).execute();
            //go to bookdetail
            bookimagea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(booka));
                    toBookDetail.putExtra("book", id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktexta = (TextView) view.findViewById(R.id.booktexta);
            //load non-English languages
            if (booka.getLanguage().equals("tam") || booka.getLanguage().equals("chi")) {
                Search searcha = new Search();
                try {
                    booktexta.setText(searcha.getTitle(booka.getResourceURL()));
                } catch (Exception e) {
                    e.printStackTrace();
                    booktexta.setText(booka.getBookTitle());
                }
            } else {
                booktexta.setText(booka.getBookTitle());
            }
            //book 2
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Ten-A-City: Stories Built to Last \"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book2b = resultBookList.get(0);
            ImageView bookimageb = (ImageView) view.findViewById(R.id.bookimageb);
            new ImageLoadTask(book2b.getCoverPage(), bookimageb).execute();
            bookimageb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book2b));
                    toBookDetail.putExtra("book", id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextb = (TextView) view.findViewById(R.id.booktextb);
            booktextb.setText(book2b.getBookTitle());
            if (book2b.getLanguage().equals("tam") || book2b.getLanguage().equals("chi")) {
                Search searcha = new Search();
                try {
                    booktextb.setText(searcha.getTitle(book2b.getResourceURL()));
                } catch (Exception e) {
                    e.printStackTrace();
                    booktextb.setText(book2b.getBookTitle());
                }
            } else {
                booktextb.setText(book2b.getBookTitle());
            }
            //book3
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Home\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book3c = resultBookList.get(0);
            ImageView bookimagec = (ImageView) view.findViewById(R.id.bookimagec);
            new ImageLoadTask(book3c.getCoverPage(), bookimagec).execute();
            bookimagec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book3c));
                    toBookDetail.putExtra("book", id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextc = (TextView) view.findViewById(R.id.booktextc);
            if (book3c.getLanguage().equals("tam") || book3c.getLanguage().equals("chi")) {
                Search searcha = new Search();
                try {
                    booktextc.setText(searcha.getTitle(book3c.getResourceURL()));
                } catch (Exception e) {
                    e.printStackTrace();
                    booktextc.setText(book3c.getBookTitle());
                }
            } else {
                booktextc.setText(book3c.getBookTitle());
            }
            //book4
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Set Sail\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book4d = resultBookList.get(0);
            ImageView bookimaged = (ImageView) view.findViewById(R.id.bookimaged);
            new ImageLoadTask(book4d.getCoverPage(), bookimaged).execute();
            bookimaged.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book4d));
                    toBookDetail.putExtra("book", id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextd = (TextView) view.findViewById(R.id.booktextd);
            if (book4d.getLanguage().equals("tam") || book4d.getLanguage().equals("chi")) {
                Search searcha = new Search();
                try {
                    booktextd.setText(searcha.getTitle(book4d.getResourceURL()));
                } catch (Exception e) {
                    e.printStackTrace();
                    booktextd.setText(book4d.getBookTitle());
                }
            } else {
                booktextd.setText(book4d.getBookTitle());
            }
        }

        //rank books by add-to-shelf times in the book database
        else{
            //book1
            if(cursor.moveToNext()) {
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
                ImageView bookimagea = (ImageView) view.findViewById(R.id.bookimagea);
                new ImageLoadTask(book.getCoverPage(), bookimagea).execute();
                bookimagea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        startActivity(toBookDetail);
                    }
                });
                TextView booktexta = (TextView) view.findViewById(R.id.booktexta);
                booktexta.setText(book.getBookTitle());
            }
            //book2
            if(cursor.moveToNext()) {
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
                ImageView bookimageb = (ImageView) view.findViewById(R.id.bookimageb);
                new ImageLoadTask(book.getCoverPage(), bookimageb).execute();
                bookimageb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        startActivity(toBookDetail);
                    }
                });
                TextView booktextb = (TextView) view.findViewById(R.id.booktextb);
                booktextb.setText(book.getBookTitle());
            }
            //book3
            if(cursor.moveToNext()) {
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

                ImageView bookimagec = (ImageView) view.findViewById(R.id.bookimagec);
                new ImageLoadTask(book.getCoverPage(), bookimagec).execute();

                bookimagec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        startActivity(toBookDetail);
                    }
                });

                TextView booktextc = (TextView) view.findViewById(R.id.booktextc);
                booktextc.setText(book.getBookTitle());
            }
            //book4
            if(cursor.moveToNext()) {
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
                ImageView bookimaged = (ImageView) view.findViewById(R.id.bookimaged);
                new ImageLoadTask(book.getCoverPage(), bookimaged).execute();
                bookimaged.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                        toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                        startActivity(toBookDetail);
                    }
                });
                TextView booktextd = (TextView) view.findViewById(R.id.booktextd);
                booktextd.setText(book.getBookTitle());
            }
        }
        cursor.close();

        //rank recommendation
        //retrieve the user's interested-subject list

        ArrayList<String> interest;
        SharedPreferences pref = getActivity().getSharedPreferences("account"+id, MODE_PRIVATE);
        Set<String> interesSet = new HashSet<String>(pref.getStringSet("interest", new HashSet<String>()));
        interest=new ArrayList<String>(interesSet);

        //if user does not choose any subject
        if(interest==null) {
            //book1
            Search search = new Search();
            String content = "";
            try {
                content = search.getContent("{\"book_title\":\"No Looking Back\"}");
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
            ImageView bookimageaa = (ImageView) view.findViewById(R.id.bookimageaa);
            new ImageLoadTask(book.getCoverPage(), bookimageaa).execute();
            bookimageaa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                    startActivity(toBookDetail);
                }
            });
            TextView booktextaa = (TextView) view.findViewById(R.id.booktextaa);
            booktextaa.setText(book.getBookTitle());
            //book2
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Ten-A-City: Stories Built to Last \"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book2 = resultBookList.get(0);
            ImageView bookimagebb = (ImageView) view.findViewById(R.id.bookimagebb);
            new ImageLoadTask(book2.getCoverPage(), bookimagebb).execute();
            bookimagebb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book2));
                    startActivity(toBookDetail);
                }
            });
            TextView booktextbb = (TextView) view.findViewById(R.id.booktextbb);
            booktextbb.setText(book2.getBookTitle());
            //book3
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Home\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book3 = resultBookList.get(0);
            ImageView bookimagecc = (ImageView) view.findViewById(R.id.bookimagecc);
            new ImageLoadTask(book3.getCoverPage(), bookimagecc).execute();
            bookimagecc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book3));
                    startActivity(toBookDetail);
                }
            });
            TextView booktextcc = (TextView) view.findViewById(R.id.booktextcc);
            booktextcc.setText(book3.getBookTitle());
            //book4
            content = "";
            try {
                content = search.getContent("{\"book_title\":\"Set Sail\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book4 = resultBookList.get(0);
            ImageView bookimagedd = (ImageView) view.findViewById(R.id.bookimagedd);
            new ImageLoadTask(book4.getCoverPage(), bookimagedd).execute();
            bookimagedd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book4));
                    startActivity(toBookDetail);
                }
            });
            TextView booktextdd = (TextView) view.findViewById(R.id.booktextdd);
            booktextdd.setText(book4.getBookTitle());
        }
        //recommend random books from chosen subjects
        else {
            //book1
            Random r = new Random();
            index = r.nextInt(interest.size());
            subject = interest.get(index);
            while (subject.equals("Under One Sky") && interest.size() != 1) {
                index = r.nextInt(interest.size());
                subject = interest.get(index);
            }
            Search search = new Search();
            String content = "";
            try {
                content = search.getContent("{\"subject\":\"" + subject + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<BookInfo> resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book = resultBookList.get(r.nextInt(resultBookList.size()));
            ImageView bookimageaa = (ImageView) view.findViewById(R.id.bookimageaa);
            new ImageLoadTask(book.getCoverPage(), bookimageaa).execute();
            bookimageaa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book));
                    toBookDetail.putExtra("book",id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextaa = (TextView) view.findViewById(R.id.booktextaa);
            if(book.getLanguage().equals("tam")||book.getLanguage().equals("chi")){
            Search searcha = new Search();
            try{
                booktextaa.setText(searcha.getTitle(book.getResourceURL()));
            } catch(Exception e){
                e.printStackTrace();
                booktextaa.setText("தப்புக்கணக்கு");
            }
        }
        else{
            booktextaa.setText(book.getBookTitle());
        }
            //book2
            index = r.nextInt(interest.size());
            subject = interest.get(index);
            content = "";
            try {
                content = search.getContent("{\"subject\":\"" + subject + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book2 = resultBookList.get(r.nextInt(resultBookList.size()));
            ImageView bookimagebb = (ImageView) view.findViewById(R.id.bookimagebb);
            new ImageLoadTask(book2.getCoverPage(), bookimagebb).execute();
            bookimagebb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book2));
                    toBookDetail.putExtra("book",id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextbb = (TextView) view.findViewById(R.id.booktextbb);
            if(book2.getLanguage().equals("tam")||book2.getLanguage().equals("chi")){
            Search searcha = new Search();
            try{
                booktextbb.setText(searcha.getTitle(book2.getResourceURL()));
            } catch(Exception e){
                e.printStackTrace();
                booktextbb.setText("தப்புக்கணக்கு");
            }
        }
        else{
            booktextbb.setText(book2.getBookTitle());
        }
            //book3
            index = r.nextInt(interest.size());
            subject = interest.get(index);
            content = "";
            try {
                content = search.getContent("{\"subject\":\"" + subject + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book3 = resultBookList.get(r.nextInt(resultBookList.size()));
            ImageView bookimagecc = (ImageView) view.findViewById(R.id.bookimagecc);
            new ImageLoadTask(book3.getCoverPage(), bookimagecc).execute();
            bookimagecc.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book3));
                toBookDetail.putExtra("book",id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextcc = (TextView) view.findViewById(R.id.booktextcc);
            //if book's language is Thai
        if(book3.getLanguage().equals("tam")||book3.getLanguage().equals("chi")){
            Search searcha = new Search();
            try{
                booktextcc.setText(searcha.getTitle(book3.getResourceURL()));
            } catch(Exception e){
                e.printStackTrace();
                booktextcc.setText("தப்புக்கணக்கு");
            }
        }
        else{
            booktextcc.setText(book3.getBookTitle());
        }
            //book4
            index = r.nextInt(interest.size());
            subject = interest.get(index);
            content = "";
            try {
                content = search.getContent("{\"subject\":\"" + subject + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultBookList = new ArrayList<>();
            try {
                resultBookList = search.parseJson(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final BookInfo book5 = resultBookList.get(r.nextInt(resultBookList.size()));
            ImageView bookimagedd = (ImageView) view.findViewById(R.id.bookimagedd);
            new ImageLoadTask(book5.getCoverPage(), bookimagedd).execute();
            bookimagedd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBookDetail = new Intent(getActivity().getApplication(), BookDetailActivity.class);
                    toBookDetail.putExtra("BookInfo", Parcels.wrap(book5));
                    toBookDetail.putExtra("book",id);
                    startActivity(toBookDetail);
                }
            });
            TextView booktextdd = (TextView) view.findViewById(R.id.booktextdd);
            if(book5.getLanguage().equals("tam")||book5.getLanguage().equals("chi")){
            Search searcha = new Search();
            try{
                booktextdd.setText(searcha.getTitle(book5.getResourceURL()));
            } catch(Exception e){
                e.printStackTrace();
                booktextdd.setText("தப்புக்கணக்கு");
            }
        }
        else{
            booktextdd.setText(book5.getBookTitle()); }
        }
        return view;
    }
}
