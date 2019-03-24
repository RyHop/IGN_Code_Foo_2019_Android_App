package com.example.ryan.ign_code_foo_2019_android_app;

public class Article {
    private String aURL;
    private String aTitle;
    private String aAuthor;
    private String aDate;
    private String aURLDate;
    private String apictureURL;
    private String aCommentNumber;
    private String aDescription;
    private String aContentID;

    //Add thumbnail later.

    public Article() {

    }

    public Article(String URL, String Title, String Description, String Date, String URLDate, String Picture, String ID) {
        this.aURL = URL;
        this.aTitle = Title;
        this.aDescription = Description;
        this.aDate = Date + " mins ago";
        this.aURLDate = URLDate;
        this.apictureURL = Picture;
        this.aCommentNumber = "";
        this.aContentID = ID;

    }


    public String getaURL() {
        return aURL;
    }

    public String getaURLDate() {
        return aURLDate;
    }

    public String getaContentID() {
        return aContentID;
    }

    public String getaDescription() {
        return aDescription;
    }

    public String getaTitle() {
        return aTitle;
    }


    public String getaDate() {
        return aDate;
    }

    public String getaPictureURL() {
        return apictureURL;
    }

    public String getaCommentNumber() {
        return aCommentNumber;
    }

    //Only need a setter comment Number method
    public void setaCommentNumber(String commentCount) {
        this.aCommentNumber = commentCount;

    }
}
