package fi.jamk.sgkygolfcourses;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 29.10.2016.
 */
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder>{
    // adapter data
    public ArrayList<Course> courseList;
    Context context;


    // adapter constructor, get data from activity
    public CoursesAdapter(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    // return the size of courseList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    // create a view for this card
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new ViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    // - get element from courselist at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Course course = courseList.get(position);

        Picasso
                .with(context)
                .load("http://ptm.fi/jamk/android/golfcourses/" + course.photo)
                //.fit() // will explain later
                .into((ImageView) viewHolder.courseImageView);
        viewHolder.courseNameTextView.setText(course.name);
        viewHolder.coursePositionTextView.setText(course.address);
        viewHolder.courseEmailTextView.setText(course.email);
        viewHolder.coursePhoneTextView.setText(course.phone);
    }


    // view holder class to specify card UI objects
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView courseImageView;
        public TextView courseNameTextView;
        public TextView coursePositionTextView;
        public TextView courseEmailTextView;
        public TextView coursePhoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            // get layout ids
            courseImageView = (ImageView) itemView.findViewById(R.id.courseImageView);
            courseNameTextView = (TextView) itemView.findViewById(R.id.courseNameTextView);
            coursePositionTextView = (TextView) itemView.findViewById(R.id.courseAddressTextView);
            courseEmailTextView = (TextView) itemView.findViewById(R.id.courseMailTextView);
            coursePhoneTextView = (TextView) itemView.findViewById(R.id.coursePhoneTextView);
            // add click listner for a card
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), Details.class);
                    intent.putExtra("course", (Serializable) courseList.get(position));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
