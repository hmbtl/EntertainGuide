package nms.az.entertainguide;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import nms.az.entertainguide.movie.MovieData;
import nms.az.entertainguide.movie.MovieListAdapter;


public class MainActivity extends AppCompatActivity {


    private ListView movieListView;
    private ProgressDialog progress;
    private List<MovieData> movieData = new LinkedList<>();
    private OnMovieImageReceive imageReceiveBroadcast;
    private String url;
    private MovieListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListView = (ListView) findViewById(R.id.movie_listview);

        imageReceiveBroadcast = new OnMovieImageReceive();

        adapter = new MovieListAdapter(this,R.layout.movie_list_item,movieData);
        movieListView.setAdapter(adapter);

        registerReceiver(imageReceiveBroadcast, new IntentFilter("MOVIE_RECEIVED"));



        new FetchMovies().execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageReceiveBroadcast);
    }

    class OnMovieImageReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            adapter.notifyDataSetChanged();
            if(progress.isShowing())
                progress.dismiss();
        }
    }

    class FetchMovies extends AsyncTask<String, String, String> {






        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setMessage("Please wait movie list is loading");
            progress.show();
            url = "http://www.citylife.az/kino.php?lang=en";

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
