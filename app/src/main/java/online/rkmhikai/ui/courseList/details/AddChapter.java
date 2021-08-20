package online.rkmhikai.ui.courseList.details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.rkmhikai.model.Subject;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.CustomItemAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class AddChapter extends AppCompatActivity {
    RecyclerView rvChapter;
    Button btnAddChapter, btnAddUpdate, btnClose;
    CardView cvAddChapter;
    List<Subject> subjectList;
    CustomItemAdapter adapter;
    LinearLayoutManager layoutManager;
    TextInputLayout lChapterTitle,lChapterObjective;
    EditText etChapterTitle,etChapterObjective;
    FloatingActionButton fabAddCourse;
    int subjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        getSupportActionBar().setTitle("Chapter List");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // NukeSSLCerts.nuke();

        rvChapter = findViewById(R.id.rv_Chapter);
        cvAddChapter = findViewById(R.id.cv_AddLecture);

        btnClose = findViewById(R.id.btn_Close);

        lChapterTitle=findViewById(R.id.lLectureTitle);
        lChapterObjective=findViewById(R.id.lLecture_Objective);
        etChapterTitle=findViewById(R.id.et_Lecture_Title);
        etChapterObjective=findViewById(R.id.et_Lecture_Objective);
        fabAddCourse=findViewById(R.id.fab_addChapter);
        btnAddUpdate=findViewById(R.id.btn_update);
        subjectList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Bundle bundle =getIntent().getExtras();
        if(bundle!=null){
            subjectID=bundle.getInt("subjectId");
            Log.d("TAG", "chapter: "+URLs.showChapterUrl+subjectID);

        }
        loadChapter();
        fabAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddChapter.setVisibility(View.VISIBLE);
            }
        });


//        btnAddChapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cvAddChapter.setVisibility(View.VISIBLE);
//            }
//        });
        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChapter();
                hideCard();
                loadChapter();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideCard();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadChapter() {
        Log.d("TAG", "loadChapter: "+"loadChapter() called  ");

        StringRequest request = new StringRequest(Request.Method.GET, URLs.showChapterUrl+subjectID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        JSONArray jsonArray = obj.getJSONArray("records");
                        Log.d("TAG", "onResponse: " + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject subObj = jsonArray.getJSONObject(i);
                            int subId = subObj.getInt("subjectID");
                            String subTitle = subObj.getString("title");
                            String subDesc = subObj.getString("description");
                            int subStatus = subObj.getInt("status");
                            int chapterId=subObj.getInt("chapterID");
                            String createdAt = subObj.getString("createdAt");
                            Log.d("TAG", "onResponse: " + createdAt);
                            Subject subject = new Subject(subId,chapterId, subTitle, subDesc, subStatus, createdAt);
                            subjectList.add(subject);
                            Log.d("TAG", "onResponse: " + subId);

                        }
                        adapter = new CustomItemAdapter(AddChapter.this, subjectList, cvAddChapter);
                        rvChapter.setLayoutManager(layoutManager);
                        rvChapter.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        Log.d("TAG", "onResponse: "+"adapter Notified");
                        Toast.makeText(AddChapter.this, "after notifying", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddChapter.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
            }
        });
        RequestSingletonVolley.getInstance(this).addToRequestQueue(request);

    }

    //Function to save the Chapter
    public void saveChapter(){
        String title=etChapterTitle.getText().toString().trim();
        String objective=etChapterObjective.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.showChapterUrl+String.valueOf(subjectID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getInt("success")==1){
                        Toast.makeText(AddChapter.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params =new HashMap<>();
                params.put("courseID",String.valueOf(1042));
                params.put("title",title);
                params.put("description",objective);
                return params;
            }
        };
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //To update the Chapter
    public void updateChapter(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                return params;
            }
        };

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void hideCard(){
        etChapterTitle.setText("");
        etChapterObjective.setText("");
        cvAddChapter.setVisibility(View.GONE);
    }


}