package online.rkmhikai.config.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Course;
import online.rkmhikai.R;
import online.rkmhikai.ui.courseList.CourseListFragment;
import online.rkmhikai.ui.courseList.details.AddSubject;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.BatchView> implements Filterable {



    //List<HashMap<String,Object>> batchList = new ArrayList<>();
    //List<HashMap<String,Object>> filteredList = new ArrayList<>();

    List<Course> courseList = new ArrayList<>();
    List<Course> filteredList = new ArrayList<>();

    CustomFilter customFilter;

    CourseListFragment courseFragment;

    public CourseAdapter(List<Course> courseList, CourseListFragment courseFragment) {
        this.courseList = courseList;
        filteredList = courseList;
        customFilter = new CustomFilter();
        this.courseFragment = courseFragment;
    }

    @NonNull
    @Override
    public BatchView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item,parent,false);
        return new BatchView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchView holder, int position) {
        //HashMap<String,Object> map = filteredList.get(position);

        Course course = filteredList.get(position);

        holder.ivAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(), AddSubject.class);
                intent.putExtra("courseId",course.getCourseID());
                view.getContext().startActivity(intent);
            }
        });


        holder.tvCourseTitle.setText(course.getTitle());
        //holder.tvPreview.setText(String.valueOf(course.getCourseID()));
        holder.tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), String.valueOf(course.getCourseID()), Toast.LENGTH_SHORT).show();
                courseFragment.gotoViewSubject(course.getCourseID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }


    public class BatchView extends RecyclerView.ViewHolder {

        TextView tvCourseTitle;
        TextView tvPreview;
        ImageView ivAddSubject;

        public BatchView(@NonNull View itemView) {
            super(itemView);
            tvCourseTitle =itemView.findViewById(R.id.tv_course_title);
            tvPreview = itemView.findViewById(R.id.tv_preview);
            ivAddSubject=itemView.findViewById(R.id.iv_addSubject);
        }
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = batchList;
            List<Course> newList = courseList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Course> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Course course = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String title = course.getTitle();

                if(title.toLowerCase().contains(searchValue)){
                    resultList.add(course);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Course>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}
