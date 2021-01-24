package nms.az.entertainguide.movie;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import nms.az.entertainguide.R;


public class MovieActivity extends AppCompatActivity {


    private GridView movieGridView;
    private ProgressDialog progress;
    private List<MovieData> movieData = new LinkedList<>();
    private OnMovieImageReceive imageReceiveBroadcast;
    private String url;
    private MovieAdapter adapter;
    private boolean hideToolbar = false;
    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setTitle("Movies");
            toolbar.setNavigationIcon(R.drawable.navdrawer);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        movieGridView = (GridView) findViewById(R.id.movie_grid_view);
        adapter = new MovieAdapter(this, movieData);



        imageReceiveBroadcast = new OnMovieImageReceive();

        movieGridView.setAdapter(adapter);

        registerReceiver(imageReceiveBroadcast, new IntentFilter("MOVIE_RECEIVED"));

        movieGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }


        });

        new FetchMovies().execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageReceiveBroadcast);
    }

    class OnMovieImageReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            adapter.notifyDataSetChanged();

            if (progress.isShowing())
                progress.dismiss();
        }
    }

    class FetchMovies extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(MovieActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Please wait movie list is loading");
            progress.show();
            url = "http://www.citylife.az/kino.php?lang=az";

        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String link, title, desc;

                Log.d("Started", "Started");

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) " +
                                "Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com").get();

                Elements all = doc.select("table#kinotoday tr tbody");

                for (int i = 0; i < all.size(); i++) {

                    link = "http://www.citylife.az/kino.php" + all.get(i).select("a[href]").first().attr("href");
                    title = all.get(i).select("a").last().select("span").text();
                    desc = all.get(i).select("p").text();


                    movieData.add(new MovieData(title, desc, link, ""));

                }

                sendBroadcast(new Intent("MOVIE_RECEIVED"));


                for (int i = 0; i < all.size(); i++) {

                    Document docMovie = Jsoup.connect(movieData.get(i).getLink())
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) " +
                                    "Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com").get();

                    String imgURL = "http://citylife.az/" + docMovie.select("table#event_gallery tbody tr td").select("a[href]").first().attr("href");


                    imgURL = imgURL.replace("large", "medium");
                    Log.d("image", imgURL);

                    movieData.get(i).setImgURL(imgURL);

                    sendBroadcast(new Intent("MOVIE_RECEIVED"));

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {


        }

    }

}
