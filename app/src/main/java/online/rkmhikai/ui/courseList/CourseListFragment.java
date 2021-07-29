package online.rkmhikai.ui.courseList;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Course;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.CourseAdapter;
import online.rkmhikai.config.URLs;
import online.rkmhikai.ui.courseList.preview.SubjectPreview;

public class CourseListFragment extends Fragment {

    public JsonObjectRequest request;
    private RequestQueue requestQueue;
    LinearLayoutManager layoutManager;
    private RecyclerView rvCourse;
    private CourseAdapter courseAdapter;

    List<Course> courseList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private CourseListViewModel mViewModel;

    public static CourseListFragment newInstance() {
        return new CourseListFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list_fragment, container, false);


        rvCourse = view.findViewById(R.id.rv_course);
        layoutManager = new LinearLayoutManager(getContext());
        rvCourse.setLayoutManager(layoutManager);

        courseList = new ArrayList<>();
        fetchBatchJson();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        Log.d("TAG", "onPrepareOptionsMenu: ");
        MenuItem menuItem=menu.findItem(R.id.action_search_batch);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Course");

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                courseAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    public void fetchBatchJson(){

        request = new JsonObjectRequest(Request.Method.GET, URLs.showCourseUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY", "onResponse: "+response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("records");
                    JSONObject jsonObject = null;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        int courseID=jsonObject.getInt("courseID");
                        String title=jsonObject.getString("title");

                        Log.d("TAG", "onResponse: "+response.toString());
                        Log.d("TAG", "onResponse: "+courseID);

                        //Batch batch = new Batch(id,sessionID,batchName,batchId,moduleName,courseName,batchStartTime,batchEndTime,batchNote);
                        Course course = new Course();
                        course.setCourseID(courseID);
                        course.setTitle(title);


                        courseList.add(course);
                        Log.d("VOLLEY", "BatchName: "+jsonObject.getInt("courseID"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(courseList,CourseListFragment.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", "onErrorResponse: "+error.toString());
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }


    private void setuprecyclerview(List<Course> courseList,CourseListFragment courseFragment) {

        courseAdapter = new CourseAdapter(courseList,courseFragment);
        rvCourse.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCourse.setAdapter(courseAdapter);

    }

    public void gotoViewSubject(int courseId){
//        fragmentManager = getFragmentManager();
//        fragmentTransaction =fragmentManager.beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putInt("courseId",courseId);
//        SubjectPreviewFragment subjectFragment = new SubjectPreviewFragment();
//        subjectFragment.setArguments(bundle);
//        fragmentTransaction.replace(R.id.container,subjectFragment,null);
//        fragmentTransaction.commit();
        Intent intent=new Intent(getContext(), SubjectPreview.class);
        //Bundle bundle = new Bundle();
       //bundle.putInt("courseId",courseId);
       intent.putExtra("courseId",courseId);
        Log.d("TAG", "gotoViewSubject: "+courseId);
        startActivity(intent);

    }

}