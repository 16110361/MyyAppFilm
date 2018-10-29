package com.murniandhany.myappfilm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;
/**
 * Created by Murni on 29/10/2018.
 */

public class MovieArrayAdapter extends ArrayAdapter {

    private List<DetailFilmActivity> DetailFilmList;
    private int resource;
    private Context context;

    public MovieArrayAdapter(Context context, int resource, List<DetailFilmActivity> filmDetails) {
        super(context, resource, filmDetails);
        this.context = context;
        this.DetailFilmList = filmDetails;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailFilmActivity details = DetailFilmList.get(position);

        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView movieName = (TextView) view.findViewById(R.id.tv_title);
        TextView movieDesc = (TextView) view.findViewById(R.id.tv_description);
        TextView movieDate = (TextView) view.findViewById(R.id.tv_date);
        ImageView image = (ImageView) view.findViewById(R.id.img_movie);

        movieName.setText(details.getOriginal_title());
        movieDesc.setText(details.getOverview());
        movieDate.setText(details.getRelease_date());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(image);

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return DetailFilmList.get(position);
    }
}
