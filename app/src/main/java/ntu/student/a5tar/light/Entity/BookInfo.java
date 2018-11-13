package ntu.student.a5tar.light.Entity;

import java.lang.*;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class BookInfo{
    private String bookTitle;
    private String author;
    private String coverPage;
    private String subject;
    private String bookDescription;
    private String publisher;
    private String pubDate;
    private String BID;
    private String downloadLink;
    private int rank;
    private String language;
    private String ResourceURL;

    public BookInfo(){
    }

    @ParcelConstructor
    public BookInfo(String bookTitle, String author, String coverPage, String subject, String bookDescription, String publisher, String pubDate, String BID, String downloadLink, int rank, String language, String ResourceURL) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.coverPage = coverPage;
        this.subject = subject;
        this.bookDescription = bookDescription;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.BID = BID;
        this.downloadLink = downloadLink;
        this.rank = rank;
        this.language = language;
        this.ResourceURL = ResourceURL;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getLanguage(){ return language;}
    public void setLanguage(String language){ this.language = language;}

    public String getResourceURL(){return ResourceURL;}
    public void setResourceURL(){this.ResourceURL = ResourceURL;}
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getBookInfo() {
        return "test";
    }

    public String getBookContent() {
        return "test";
    }

}


