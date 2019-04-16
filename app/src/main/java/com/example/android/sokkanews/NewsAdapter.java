package com.example.android.sokkanews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> books) {
        super(context,0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sokka_news_list_item, parent, false);
        }

        /* Find the news at the given position in the list of news */
        News currentNews = getItem(position);

        //Find the TextView with the ID title
        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(currentNews.getTitle());

        //Find the TextView with the ID section
        TextView sectionTextView = listItemView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getmSection());

        //Find the TextView with the ID date
        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText(currentNews.getmDate());



        //Return the list item view
        return listItemView;
    }


}
