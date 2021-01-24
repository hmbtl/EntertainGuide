package nms.az.entertainguide.movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anar on 6/18/15.
 */
public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class GetMovieDetails extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


}
