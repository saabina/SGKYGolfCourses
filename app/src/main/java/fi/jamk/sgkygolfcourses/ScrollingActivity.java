package fi.jamk.sgkygolfcourses;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    public ArrayList<Course> mCourseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new FetchDataTask().execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");
    }

    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        private JSONArray JSONCourses;
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            StringBuffer text = new StringBuffer("");
            try {
                JSONCourses = json.getJSONArray("kentat");
                mCourseList = new ArrayList<>();

                for (int i=0;i < JSONCourses.length();i++) {
                    JSONObject cr = JSONCourses.getJSONObject(i);
                    mCourseList.add(new Course(cr.getString("Kentta"), cr.getDouble("lat"), cr.getDouble("lng"),
                            cr.getString("Osoite"), cr.getString("Sahkoposti"), cr.getString("Puhelin"), cr.getString("Kuva"),
                            cr.getString("Webbi"), cr.getString("Kuvaus")));
                }

                // connect recycler view
                mRecyclerView = (RecyclerView) findViewById(R.id.courseRecyclerView);
                // create layoutmanager
                mLayoutManager = new LinearLayoutManager(context);
                // set manager to recycler view
                mRecyclerView.setLayoutManager(mLayoutManager);
                // create adapter
                mAdapter = new CoursesAdapter(mCourseList);
                // set adapter to recycler view
                mRecyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }

        }
    }
}
