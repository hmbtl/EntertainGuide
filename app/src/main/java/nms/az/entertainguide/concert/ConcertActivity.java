package nms.az.entertainguide.concert;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import nms.az.entertainguide.R;


public class ConcertActivity extends AppCompatActivity {


    private ListView movieListView;
    private ProgressDialog progress;
    private List<ConcertData> concerts = new LinkedList<>();
    private OnMovieImageReceive imageReceiveBroadcast;
    private boolean isFinished = false;
    private ConcertListAdapter adapter;

    private TextView firstConcertTitle, secondConcertTitle, thirdConcertTitle;
    private ImageView firstConcertImg, secondConcertImg, thirdConcertImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concert_list_layout);

        movieListView = (ListView) findViewById(R.id.concert_list_view);

        imageReceiveBroadcast = new OnMovieImageReceive();

        adapter = new ConcertListAdapter(this, R.layout.concert_list_item, concerts);
        movieListView.setAdapter(adapter);

        registerReceiver(imageReceiveBroadcast, new IntentFilter("CONCERT_RECEIVED"));


        firstConcertTitle = (TextView) findViewById(R.id.concert_first_title);
        secondConcertTitle = (TextView) findViewById(R.id.concert_second_title);
        thirdConcertTitle = (TextView) findViewById(R.id.concert_third_title);

        firstConcertImg = (ImageView) findViewById(R.id.concert_first_img);
        secondConcertImg = (ImageView) findViewById(R.id.concert_second_img);
        thirdConcertImg = (ImageView) findViewById(R.id.concert_third_img);


        new FetchMovies("http://citylife.az/content.php?lang=az&et=3").execute();

        movieListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (isFinished == false) {
                        isFinished = true;
                        new FetchMovies("http://citylife.az/content.php?lang=az&et=3&jazz=0&n=2").execute();
                    }
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageReceiveBroadcast);
    }

    class OnMovieImageReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {



            firstConcertTitle.setText(concerts.get(0).getName());
            secondConcertTitle.setText(concerts.get(1).getName());
            thirdConcertTitle.setText(concerts.get(2).getName());

            Picasso.with(context).load(concerts.get(0).getImgURL()).into(firstConcertImg);
            Picasso.with(context).load(concerts.get(1).getImgURL()).into(secondConcertImg);
            Picasso.with(context).load(concerts.get(2).getImgURL()).into(thirdConcertImg);


            adapter.notifyDataSetChanged();

        }
    }

    class FetchMovies extends AsyncTask<String, String, String> {


        private String url;

        public FetchMovies(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                Log.d("Started", "Started");

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) " +
                                "Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com").get();

                Elements all = doc.select("td[width = 755]>table[style]>tbody");

                for (int i = 0; i < all.size(); i++) {


                    String link = "http://citylife.az/" + all.get(i).select("a[href]").attr("href");
                    String desc = all.get(i).select("p").text();
                    String name = all.get(i).select("a").select("span").text();
                    String type = all.get(i).select("a[class=text_gray]").text();
                    String loca = all.get(i).select("div[style]>a[class=text_red]").text();
                    String time = all.get(i).select("span[class=text_blue_dark]").text();
                    String price = all.get(i).select("span[class=text_blue_dark]").text();
                    String img = all.get(i).select("a[href] img[alt]").attr("src");

                    Log.d("name", name);

                    concerts.add(new ConcertData(link, name, desc, type, loca, time, price, img));


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            sendBroadcast(new Intent("CONCERT_RECEIVED"));

        }

    }

}
