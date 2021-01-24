package nms.az.entertainguide.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nms.az.entertainguide.R;
import nms.az.entertainguide.movie.MovieData;

/**
 * Created by anar on 6/10/15.
 */
public class MovieAdapter extends BaseAdapter {

    private Context context;
    private List<MovieData> movies;


    public MovieAdapter(Context context, List<MovieData> movies) {
        this.movies = movies;
        this.context = context;
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        ItemHolder holder;

        if (row == null) {

            holder = new ItemHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.movie_item, parent, false);

            holder.title = (TextView) row.findViewById(R.id.movie_title);
            holder.date = (TextView) row.findViewById(R.id.movie_date);
            holder.poster = (ImageView) row.findViewById(R.id.movie_poster);

            row.setTag(holder);
        } else
            holder = (ItemHolder) row.getTag();

        MovieData movie = movies.get(position);


        holder.title.setText(movie.getName());
        if (!movie.getImgURL().isEmpty())
            Picasso.with(context).load(movie.getImgURL()).into(holder.poster);


        return row;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MovieData getItem(int i) {
        return movies.get(i);
    }

    class ItemHolder {
        TextView title, date;
        ImageView poster;
    }
}
