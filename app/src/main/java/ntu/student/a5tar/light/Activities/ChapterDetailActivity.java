package ntu.student.a5tar.light.Activities;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.content.Context;

import java.lang.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import ntu.student.a5tar.light.Entity.StringUtils;
import ntu.student.a5tar.light.R;
import ntu.student.a5tar.light.Entity.BookInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import org.parceler.Parcels;
import ntu.student.a5tar.light.Entity.NLBDownload;

public class ChapterDetailActivity extends AppCompatActivity {
    private static final String TAG = "ChapterDetailActivity";
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        ButterKnife.bind(this);

        // Get the book uid.
        String uid = "";
        final BookInfo abook = (BookInfo)Parcels.unwrap(getIntent().getParcelableExtra("BookInfo"));
        String bookTitle = abook.getBookTitle();
        uid = abook.getBID();

        // Get the url of the book.
        String CommonURL = "http://eresources.nlb.gov.sg/eReads/MobileReads/details?uuid=";
        String BookURL = CommonURL + uid;
        BookURL = "https://stackoverflow.com/questions/21817/why-cant-i-declare-static-methods-in-an-interface";
        final int REQUEST_CODE=1;
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        // Get the url for downloading the book.
        String downloadUrl = "";
        try{
            downloadUrl = NLBDownload.getDownloadLink(BookURL);
        } catch(Exception e){
            e.printStackTrace();
        }

        String[] splitTitle = bookTitle.split(" ");
        String storedTitle = "";
        for(int i=0;i<splitTitle.length;i++){
            storedTitle = storedTitle+splitTitle[i];
        }
        // Download the book and save it  to the external storage of the user.
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
//        request.setDestinationInExternalPublicDir("", storedTitle+".epub");
//        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        downloadManager.enqueue(request);

        // Get the book from the storage to read.
//        final File file = new File(Environment.getExternalStorageDirectory()
//                .getAbsolutePath(), storedTitle+".epub");
        String href = getIntent().getStringExtra("href");
        Log.i(TAG, "onCreate: href=" + href);
        InputStream in;
        try {
            EpubReader reader = new EpubReader();
            if(bookTitle.equals("No Looking Back")){
                in = getAssets().open("No Looking Back.epub");
            }
            else{
                in = getAssets().open("Lee.epub");
            }
            Book book = reader.readEpub(in);
            Resource content = book.getContents().get(1);
            byte[] data = content.getData();
            String strHtml1 = StringUtils.bytes2Hex(data);
            if(strHtml1==null)
                strHtml1 = "The book is currently not available";
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadDataWithBaseURL(null, strHtml1, "text/html", "utf-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
