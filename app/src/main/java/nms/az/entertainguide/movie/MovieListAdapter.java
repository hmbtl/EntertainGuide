package nms.az.entertainguide.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nms.az.entertainguide.R;

/**
 * Created by Azad on 5/5/2015.
 */

public class MovieListAdapter extends ArrayAdapter<MovieData> {

    private List<MovieData> movieData;
    private int layoutResouceId;
    private Context context;

    public MovieListAdapter(Context context, int layoutResourceId, List<MovieData> movieData){
        super(context,layoutResourceId, movieData);

        this.context = context;
        this.layoutResouceId = layoutResourceId;
        this.movieData = movieData;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        ItemHolder holder ;

        if (row == null){

            holder = new ItemHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =  inflater.inflate(layoutResouceId, parent, false);

            holder.name = (TextView) row.findViewById(R.id.movie_name);
            holder.desc = (TextView) row.findViewById(R.id.movie_desc);
            holder.img = (ImageView) row.findViewById(R.id.movie_img);

            row.setTag(holder);
        } else
            holder = (ItemHolder) row.getTag();

        MovieData movie = movieData.get(position);

        holder.name.setText(movie.getName());
        holder.desc.setText(movie.getDesc());
        if(!movie.getImgURL().isEmpty())
        Picasso.with(context).load(movie.getImgURL()).into(holder.img);


        return row;
    }

    @Override
    public int getCount() {
        return movieData.size();
    }

    class ItemHolder {
        TextView name, desc;
        ImageView img;
    }
}
