package online.rkmhikai.ui.courseList.details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import online.rkmhikai.model.Lecture;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.CustomLectureAdapter;
import online.rkmhikai.config.RecyclerViewClickInterface;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class AddLecture extends AppCompatActivity implements RecyclerViewClickInterface {
    private static final int SELECT_CODE = 1;
    RecyclerView rvLecture;
    Button btnAddLecture,btnAddUpdate,btnClose;
    CardView cvAddLecture;
    List<Lecture> lectureList;
    CustomLectureAdapter adapter;
    LinearLayoutManager layoutManager;
    TextInputLayout lLectureTitle,lLectureObjective;
    EditText etLectureTitle,etLectureObjective;
    private int STORAGE_PERMISSION_CODE = 1;
    FloatingActionButton fabAddLecture;
    int chapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);
        getSupportActionBar().setTitle("Lecture List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("TAG", "onCreate: inside laecture");
        // NukeSSLCerts.nuke();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            chapterId=bundle.getInt("chapterId");
            Log.d("TAG", "lecture: "+URLs.showLectureUrl+chapterId);
        }
        rvLecture=findViewById(R.id.rv_Chapter);
        cvAddLecture=findViewById(R.id.cv_AddLecture);
        fabAddLecture=findViewById(R.id.fabAddLecture);

        btnAddUpdate=findViewById(R.id.btn_update);
        btnClose=findViewById(R.id.btn_Close);
        lLectureTitle=findViewById(R.id.lLectureTitle);
        lLectureObjective=findViewById(R.id.lLecture_Objective);
        etLectureTitle=findViewById(R.id.et_Lecture_Title);
        etLectureObjective=findViewById(R.id.et_Lecture_Objective);
        lectureList=new ArrayList<>();


        loadLecture();
        adapter=new CustomLectureAdapter(this,lectureList,cvAddLecture,AddLecture.this);


        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLecture();
                hideCard();
                loadLecture();
            }
        });
        fabAddLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddLecture.setVisibility(View.VISIBLE);
                Log.d("TAG", "onClick: "+"fabclicked");
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

    public void loadLecture(){

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLs.showLectureUrl+chapterId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    Log.d("TAG", "onResponse: "+object);
                    if(object.getInt("success")==1){
                        JSONArray jsonArray=object.getJSONArray("records");
                        Log.d("TAG", "onResponse: "+jsonArray);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject objLecture=jsonArray.getJSONObject(i);
                            int lectureId =objLecture.getInt("lectureID");
                            String title=objLecture.getString("title");
                            String desc =objLecture.getString("description");
                            String file =objLecture.getString("file");
                            int status=objLecture.getInt("status");
                            String createdAt=objLecture.getString("createdAt");
                            Lecture lecture=new Lecture(lectureId,title,desc,file,status,createdAt);
                            lectureList.add(lecture);
                        }
                        rvLecture.setLayoutManager(layoutManager);
                        rvLecture.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
            }
        });

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick() {
        if(ContextCompat.checkSelfPermission(AddLecture.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_CHOOSER);
            startActivityForResult(intent,SELECT_CODE);

        }else{
            requestStoragePermission();
        }
    }

    @Override
    public void onItemClick(String item) {

    }


    //Function to save the Lecture
    public void saveLecture(){
        String title=etLectureTitle.getText().toString().trim();
        String objective=etLectureObjective.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.showLectureUrl+String.valueOf(chapterId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        Toast.makeText(AddLecture.this, "added something", Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params =new HashMap<>();
                params.put("title",title);
                params.put("description",objective);
                params.put("chapterID", String.valueOf(chapterId));
                params.put("courseID", String.valueOf(1042));
                params.put("subjectID",String.valueOf(1086));

                return params;
            }
        };
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //To update the Lecture
    public void updateLecture(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.showLectureUrl+String.valueOf(chapterId), new Response.Listener<String>() {
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

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddLecture.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(AddLecture.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AddLecture.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(AddLecture.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==1){
            Uri uri= data.getData();
            Log.d("TAG", "onActivityResult: "+uri);
        }
    }
    public void hideCard(){
        etLectureTitle.setText("");
        etLectureObjective.setText("");
        cvAddLecture.setVisibility(View.GONE);
    }
}