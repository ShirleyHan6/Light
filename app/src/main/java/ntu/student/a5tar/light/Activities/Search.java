package ntu.student.a5tar.light.Activities;
import java.util.List;
import java.util.ArrayList;
import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.Entity.BookList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.*;
import org.jsoup.Jsoup;
import java.io.IOException;

public class Search {
    public String getContent(String keyword) throws IOException{
        System.out.println("keyword:");
        System.out.println(keyword);

        //use API to get book info
        String base_url = "https://data.gov.sg/api/action/datastore_search?resource_id=835e630b-a03f-4f77-baa6-9c69c91883f2";
        String URL = base_url + "&q="+keyword;
        Document doc = Jsoup.connect(URL).ignoreContentType(true).get();
        if(doc!=null)
            System.out.println(URL);
        String htmlString = doc.toString();
        return htmlString;
    }

    //use API to get book title
    public String getTitle(String ResourceURL) throws IOException{
        Document doc = Jsoup.connect(ResourceURL).ignoreContentType(true).get();
        String title = doc.title();
        return title;
    }

    //parse
    public List parseJson(String json){
        List<BookInfo> bookList=new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));

            String result = obj.getString("result");
            JSONObject  jsonResult = new JSONObject(result);
            String records = jsonResult.getString("records");
            JSONArray jsonArray = new JSONArray(records) ;

            for (int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String bookTitle = jsonObject.getString("book_title");
                String uuid = jsonObject.getString("uid");
                //String language = jsonObject.getString("language");
                String cover = jsonObject.getString("cover");
                String authorName = jsonObject.getString("author_name");
                String summary = jsonObject.getString("summary");
                String subject = jsonObject.getString("subject");
                String publishedYear = jsonObject.getString("published");
                String publisher = jsonObject.getString("original_publisher");
                String resourceURL = jsonObject.getString("resource_url");
                String language = jsonObject.getString("language");
                int rank = i;
                BookInfo book = new BookInfo(bookTitle, authorName, cover, subject, summary, publisher, publishedYear,uuid,resourceURL,rank,language, resourceURL);
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BookList newBookList = new BookList(bookList);
        return bookList;
    }
}

