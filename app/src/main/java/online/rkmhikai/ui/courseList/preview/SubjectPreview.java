package online.rkmhikai.ui.courseList.preview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;
import online.rkmhikai.config.adapter.SubjectAdapter;
import online.rkmhikai.model.Subject;

public class SubjectPreview extends AppCompatActivity {
    public JsonObjectRequest request;
    private RequestQueue requestQueue;



    RecyclerView rvSubject;

    List<Subject> subjectList;

    private SubjectAdapter subjectAdapter;

    int courseId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_preview);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            courseId =bundle.getInt("courseId");
            Toast.makeText(SubjectPreview.this, "From Course View--> CourseId= "+String.valueOf(courseId), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onCreate: "+courseId);
        }

        rvSubject =findViewById(R.id.rv_subject);
        subjectList = new ArrayList<>();
        fetchSubjectJson(courseId);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_session,menu);
        return true;
    }
    public void fetchSubjectJson(int courseId){

        request = new JsonObjectRequest(Request.Method.GET, URLs.showSubjectUrl+courseId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY", "onResponse: "+response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("records");
                    JSONObject jsonObject = null;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        int subjectID=jsonObject.getInt("subjectID");
                        int courseID=jsonObject.getInt("courseID");
                        String title=jsonObject.getString("title");
                        String description=jsonObject.getString("description");


                        Subject subject = new Subject();
                        subject.setSubId(subjectID);
                        subject.setCourseID(courseID);
                        subject.setTitle(title);
                        subject.setDesc(description);

                        subjectList.add(subject);
                        Log.d("VOLLEY", "Subject Title: "+jsonObject.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(subjectList,SubjectPreview.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", "onErrorResponse: "+error.toString());
            }
        });

        RequestSingletonVolley.getInstance(this).addToRequestQueue(request);


    }

    private void setuprecyclerview(List<Subject> subjectList, Context context) {

        subjectAdapter = new SubjectAdapter(subjectList,context);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SubjectPreview.this,2,GridLayoutManager.VERTICAL,false);
        rvSubject.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        rvSubject.setAdapter(subjectAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}