package ntu.student.a5tar.light.Entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NLBDownload implements Download{
    public static String getDownloadLink(String book_url) throws IOException {
        String URL=book_url;
        Document document = null;
        try {
            document=Jsoup.connect(URL).ignoreContentType(true).get();
        } catch(Exception e){
            e.printStackTrace();
        }
        String HtmlString = document.toString();

        String reg = "a href=\"(.*?)\"";
        Matcher m = Pattern.compile(reg).matcher(HtmlString);
        String ahref = "";
        int count = 0;
        while(m.find()){
            count++;
            if(count==46){
                ahref = m.group(0);
                break;
            }
        }

        String href = "";
        for(int i=ahref.indexOf("\"")+1;i<ahref.length()-1;i++){
            Character oneChar = ahref.charAt(i);
            href = href+oneChar;
        }
        System.out.println(href);
        return href;
    }
}
