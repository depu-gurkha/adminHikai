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

public class AddSubject extends AppCompatActivity {
    RecyclerView rvSubject;
    Button btnAddSubject,btnAddUpdate,btnClose;
    CardView cvAddSubject;
    List<Subject> subjectList;
    CustomItemAdapter adapter;
    LinearLayoutManager layoutManager;
    TextInputLayout lSubjectTitle,lSubjectObjective;
    EditText etSubjectTitle,etSubjectObjective;
    FloatingActionButton fabAddSubject;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NukeSSLCerts.nuke();

        setContentView(R.layout.activity_add_subject);
//        getActionBar().setTitle("Add Subject");
        getSupportActionBar().setTitle("Subject List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvSubject=findViewById(R.id.rv_Chapter);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            courseId=bundle.getInt("courseId");
            Log.d("TAG", URLs.showSubjectUrl+String.valueOf(courseId));
        }

        cvAddSubject=findViewById(R.id.cv_AddLecture);
        fabAddSubject=findViewById(R.id.fabAddSubject);
        btnClose=findViewById(R.id.btn_Close);
        lSubjectTitle=findViewById(R.id.lLectureTitle);
        lSubjectObjective=findViewById(R.id.lLecture_Objective);
        etSubjectTitle=findViewById(R.id.et_Lecture_Title);
        etSubjectObjective=findViewById(R.id.et_Lecture_Objective);
        btnAddUpdate=findViewById(R.id.btn_update);
        loadSubject();
        fabAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddSubject.setVisibility(View.VISIBLE);
            }
        });


        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: "+"saved is executed ");
                hideCard();
                saveSubject();
                //loadSubject();
            }
        });

        subjectList=new ArrayList<>();
        adapter=new CustomItemAdapter(this,subjectList,cvAddSubject);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

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



    public void loadSubject(){

        StringRequest request=new StringRequest(Request.Method.GET, URLs.showSubjectUrl+String.valueOf(courseId), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getInt("success")==1){
                        JSONArray jsonArray=obj.getJSONArray("records");
                        Log.d("TAG", "onResponse: "+jsonArray);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject subObj=jsonArray.getJSONObject(i);
                            int subId=subObj.getInt("subjectID");
                            String subTitle=subObj.getString("title");
                            String subDesc=subObj.getString("description");
                            int subStatus=subObj.getInt("status");
                            String createdAt=subObj.getString("createdAt");
                            Log.d("TAG", "onResponse: "+createdAt);
                            Subject subject=new Subject(subId,subTitle,subDesc,subStatus,createdAt);
                            subjectList.add(subject);
                            Log.d("TAG", "onResponse: "+subId);

                        }
                        rvSubject.setLayoutManager(layoutManager);
                        rvSubject.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSubject.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
            }
        });
        RequestSingletonVolley.getInstance(this).addToRequestQueue(request);

    }

    //To save the Subject
    public void saveSubject(){
        String title=etSubjectTitle.getText().toString().trim();
        String objective=etSubjectObjective.getText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.showSubjectUrl+String.valueOf(courseId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "onResponse: "+response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        Toast.makeText(AddSubject.this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
                        loadSubject();
                    }
                    else{
                        Log.d("TAG", "onResponse: "+"Not done");
                    }
                }
                catch (JSONException e) {
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
                Map<String, String> params=new HashMap<>();
                params.put("title",title);
                params.put("description",objective);
                return params;
            }
        };
        RequestSingletonVolley.getInstance(AddSubject.this).addToRequestQueue(stringRequest);
    }

    //Function to update the Subject

    public void updateSubject(){
        JSONObject main =new JSONObject();
        JSONObject input=new JSONObject();
        try {
            input.put("id","1");
            input.put("sessionType","School Academic");
            input.put("sessionID","245");
            input.put("sessionStartDate","17-06-2021");
            input.put("sessionEndDate","25-06-2021");
            input.put("sessionNote","hahhahahha");
            main.put("input",input);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                Map<String,String> params=new HashMap<>();
                params.put("input",main.toString());
                params.put("Content-Type","application/json");
                return params;
            }
        };
    }
    public void hideCard(){

        cvAddSubject.setVisibility(View.GONE);
    }
}