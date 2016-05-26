package com.example.iuliu.androiddb;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class commentAdapter extends ArrayAdapter {

    ArrayList<String> Comments;
    ArrayList<String> Poster;
    ArrayList<String> date;

    public commentAdapter(Context context, int resource, ArrayList<String> Comments,
            ArrayList<String> Poster,
            ArrayList<String> date) {
        super(context, resource, Comments);
        this.Comments = Comments;
        this.Poster = Poster;
        this.date = date;
    }
    @Override
    public  int getCount()
    {
        return  Comments.size();
    }
    @Override
    public Object getItem(int position)
    {
        return Comments.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        UserHolder userHolder;
        row = convertView;



            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.comment_layout, parent, false);
            userHolder= new UserHolder();
            userHolder.accountName=(TextView)row.findViewById(R.id.Comments);
            userHolder.message_text=(TextView)row.findViewById(R.id.Poster);
            userHolder.message_datetime=(TextView)row.findViewById(R.id.date);

            userHolder.accountName.setText(Poster.get(position));
            userHolder.message_text.setText(Comments.get(position));
            userHolder.message_datetime.setText(date.get(position));

        return row;
    }
    static class UserHolder
    {
        TextView accountName, message_text, message_datetime;
    }
}
