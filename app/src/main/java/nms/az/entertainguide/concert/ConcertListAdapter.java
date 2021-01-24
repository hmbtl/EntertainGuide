package nms.az.entertainguide.concert;

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

public class ConcertListAdapter extends ArrayAdapter<ConcertData> {

    private List<ConcertData> concerts;
    private int layoutResouceId;
    private Context context;

    public ConcertListAdapter(Context context, int layoutResourceId, List<ConcertData> concerts){
        super(context,layoutResourceId, concerts);

        this.context = context;
        this.layoutResouceId = layoutResourceId;
        this.concerts = concerts;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        ItemHolder holder ;

        if (row == null){

            holder = new ItemHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =  inflater.inflate(layoutResouceId, parent, false);

            holder.name = (TextView) row.findViewById(R.id.concert_name);
            holder.desc = (TextView) row.findViewById(R.id.concert_desc);
            holder.date = (TextView) row.findViewById(R.id.concert_date);
            holder.img = (ImageView) row.findViewById(R.id.concert_img);

            row.setTag(holder);
        } else
            holder = (ItemHolder) row.getTag();

        int index = position + 3;

        ConcertData concert = concerts.get(index);

        holder.name.setText(concert.getName());
        holder.desc.setText(concert.getDesc());
        holder.date.setText(concert.getTime());
        if(!concert.getImgURL().isEmpty())
        Picasso.with(context).load(concert.getImgURL()).into(holder.img);


        return row;
    }

    @Override
    public int getCount() {
        return concerts.size()-3;
    }

    class ItemHolder {
        TextView name, desc,date;
        ImageView img;
    }
}
