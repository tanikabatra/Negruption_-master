package com.codeogic.negruption;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18-05-2017.
 */

public class StoryAdapter extends ArrayAdapter<StoryBean> {
    Context context;
    int resource;
    ArrayList<StoryBean> storyList;

    public StoryAdapter( Context context,  int resource,  ArrayList<StoryBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        storyList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtTitle = (TextView)view.findViewById(R.id.textViewStoryTitle);
        TextView txtDescription = (TextView)view.findViewById(R.id.textViewStoryDesc);
        TextView txtViews = (TextView)view.findViewById(R.id.textViewViews);
        TextView txtReadMore = (TextView)view.findViewById(R.id.textViewReadMore);

        final StoryBean story = storyList.get(position);
        txtName.setText(story.getUsername());
        txtTitle.setText(story.getStoryTitle());
        txtDescription.setText(story.getStoryDesc());
        txtViews.setText(String.valueOf(story.getViews()));
        txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),StoryActivity.class);
                intent.putExtra("keyStory",story);
                getContext().startActivity(intent);

            }
        });
        return view;
    }
}
