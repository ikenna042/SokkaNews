package com.example.android.sokkanews;


public class News {

    /**Title of the News*/
    private String mTitle;

    /**Section of the News*/
    private String mSection;

    /**Date of the publication News*/
    private String mDate;

    public News(String title, String section, String date){

        mTitle = title;
        mSection = section;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmDate() {
        return mDate;
    }
}
