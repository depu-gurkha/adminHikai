package online.rkmhikai.ui.courseList.details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.rkmhikai.config.URLs;
import online.rkmhikai.model.Assignment;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.CustomAssignmentAdapter;
import online.rkmhikai.config.RecyclerViewClickInterface;
import online.rkmhikai.config.RequestSingletonVolley;

public class AddAssignment extends AppCompatActivity implements RecyclerViewClickInterface {
    private DatePickerDialog datePicker;
    private int year, month, day;
    EditText txtdte;
    RecyclerView rvAssignment;
    String DOB="";

    Button btnAddAssignment, btnAddUpdate, btnClose;
    CardView cvAddAssignment;
    List<Assignment> assignmentList;
    CustomAssignmentAdapter adapter;
    LinearLayoutManager layoutManager;
    TextInputLayout lAssignmentTitle,lAssignmentObjective;
    EditText etAssignmentTitle,etAssignmentObjective;
    FloatingActionButton fabAddAssignment;
    int lectureId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        getSupportActionBar().setTitle("Assignment List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // NukeSSLCerts.nuke();

        txtdte=findViewById(R.id.dtepicker);
        rvAssignment=findViewById(R.id.rv_Assignment);
        fabAddAssignment=findViewById(R.id.fabAddAssignment);
        cvAddAssignment = findViewById(R.id.cv_AddAssignment);

        btnClose = findViewById(R.id.btn_Close);
        btnAddUpdate = findViewById(R.id.btn_update);
        lAssignmentTitle=findViewById(R.id.lAssignmentTitle);
        lAssignmentObjective=findViewById(R.id.lAssignment_Objective);
        etAssignmentTitle=findViewById(R.id.et_Assignment_Title);
        etAssignmentObjective=findViewById(R.id.et_Assignment_Objective);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            lectureId=bundle.getInt("lectureId");
            Log.d("TAG", "assignement: "+URLs.showAssignmentUrl+lectureId);

        }
        loadAssignment();
        assignmentList = new ArrayList<>();

        adapter = new CustomAssignmentAdapter(this, assignmentList, cvAddAssignment);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAssignmentTitle.setText("");
                etAssignmentObjective.setText("");
                cvAddAssignment.setVisibility(View.GONE);

            }
        });
        fabAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAddAssignment.setVisibility(View.VISIBLE);
            }
        });

        txtdte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddAssignment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DOB=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                txtdte.setText(DOB);
                            }
                        }, year, month, day);
                datePicker.show();
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

    @Override
    public void onClick() {

    }

    @Override
    public void onItemClick(String item) {

    }


    public void loadAssignment(){
        StringRequest stringRequest =new StringRequest(Request.Method.GET, URLs.showAssignmentUrl+lectureId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getInt("success")==1){
                        Log.d("TAG", "assignment: "+response);
                        JSONArray jsonArray=object.getJSONArray("records");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int assignmentId=jsonObject.getInt("assignmentID");
                            String title=jsonObject.getString("title");
                            String desc=jsonObject.getString("description");
                            String createdAt=jsonObject.getString("createdAt");
                            String file=jsonObject.getString("assignmentFile");
                            String submissionDate=jsonObject.getString("submissionDate");
                            int status= jsonObject.getInt("status");
                            Assignment assignment=new Assignment(assignmentId,title,desc,file,createdAt,submissionDate,status);
                            assignmentList.add(assignment);

                        }
                        rvAssignment.setLayoutManager(layoutManager);
                        rvAssignment.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAssignment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
            }
        });

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //To save  the assignment new Assignment
    public void saveAssignment(){
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
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //To update the Assignment
    public void updateAssignment(){
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