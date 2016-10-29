package fi.jamk.sgkygolfcourses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sabina on 29.10.2016.
 */
public class Details extends AppCompatActivity{
    public ImageView courseImageView;
    public TextView coursePositionTextView;
    public TextView courseEmailTextView;
    public TextView coursePhoneTextView;
    public TextView courseDescriptionTextView;
    public TextView courseUrlTextView;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        course = (Course) getIntent().getSerializableExtra("course");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(course.name);
        ActionBar ab = getSupportActionBar();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        courseImageView = (ImageView) findViewById(R.id.imageView);
        coursePositionTextView = (TextView) findViewById(R.id.positionTextView);
        courseEmailTextView = (TextView) findViewById(R.id.emailTextView);
        coursePhoneTextView = (TextView) findViewById(R.id.phoneTextView);
        courseDescriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        courseUrlTextView = (TextView) findViewById(R.id.urlTextView);

        Picasso
                .with(this)
                .load("http://ptm.fi/jamk/android/golfcourses/" + course.photo)
                .into((ImageView) courseImageView);
        coursePositionTextView.setText(course.address);
        courseEmailTextView.setText(course.email);
        coursePhoneTextView.setText(course.phone);
        courseUrlTextView.setText(course.url);
        courseDescriptionTextView.setText(course.description);
    }

    public void openMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:<" + course.lat + ">,<" + course.lng + ">?q=<" + course.lat + ">,<" + course.lng + ">(" + course.name + ")"));
        startActivity(intent);
    }
}
