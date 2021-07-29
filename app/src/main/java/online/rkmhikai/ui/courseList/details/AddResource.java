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

import online.rkmhikai.config.URLs;
import online.rkmhikai.model.Resource;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.CustomResourceAdapter;
import online.rkmhikai.config.RequestSingletonVolley;

public class AddResource extends AppCompatActivity {
    RecyclerView rvResource;

    Button btnAddResource, btnAddUpdate, btnClose;
    CardView cvAddResource;
    List<Resource> resourceList;
    CustomResourceAdapter adapter;
    LinearLayoutManager layoutManager;
    TextInputLayout lResourceTitle,lResourceObjective;
    EditText etResourceTitle,etResourceObjective;
    FloatingActionButton fabAddResource;
    int lectureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);
        getSupportActionBar().setTitle("Resource List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rvResource=findViewById(R.id.rv_Resource);

        cvAddResource = findViewById(R.id.cv_AddResource);
        fabAddResource=findViewById(R.id.fabAddResource);

        btnClose = findViewById(R.id.btn_Close);
        btnAddUpdate = findViewById(R.id.btn_update);
        lResourceTitle=findViewById(R.id.lResourceTitle);
        lResourceObjective=findViewById(R.id.lResource_Objective);
        etResourceTitle=findViewById(R.id.et_Resource_Title);
        etResourceObjective=findViewById(R.id.et_Resource_Objective);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            lectureId=bundle.getInt("lectureId");
            Log.d("TAG", "resource: "+URLs.showResourcesUrl+lectureId);

        }
        loadResources();
        resourceList = new ArrayList<>();

        adapter = new CustomResourceAdapter(this, resourceList, cvAddResource);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fabAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddResource.setVisibility(View.VISIBLE);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etResourceTitle.setText("");
                etResourceObjective.setText("");
                cvAddResource.setVisibility(View.GONE);
            }
        });

    }

    public void loadResources(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLs.showResourcesUrl+lectureId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    Log.d("TAG", "onResponse: "+object);
                    if(object.getInt("success")==1){
                        Log.d("TAG", "onResponse: "+response);
                        JSONArray jsonArray=object.getJSONArray("records");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int resourceID=jsonObject.getInt("resourcesID");
                            String title=jsonObject.getString("title");
                            String desc=jsonObject.getString("description");
                            String createdAt=jsonObject.getString("createdAt");
                            String file=jsonObject.getString("upload");
                            int status=jsonObject.getInt("status");

                            Resource resource=new Resource(resourceID,title,desc,file,createdAt,status);
                            resourceList.add(resource);

                        }
                        rvResource.setLayoutManager(layoutManager);
                        rvResource.setAdapter(adapter);
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
    //Function to save the Resource
    public void saveResource(){
        String title=etResourceTitle.getText().toString().trim();
        String objective=etResourceObjective.getText().toString().trim();

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
                Map<String ,String> params =new HashMap<>();
                return params;
            }
        };
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //To update the Resource
    public void updateResource(){
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
}