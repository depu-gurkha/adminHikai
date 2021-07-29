package online.rkmhikai.ui.batch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class AddBatch extends AppCompatActivity {
    Button btnAdd,btnClose;
    ImageView ivCancel;
    private DatePickerDialog datePicker;
    private int year, month, day;
    String batchStartDate,batchEndDate;

    EditText etBatchName, etBatchId, etSessionId, etCourseName, etModuleName, etStartDate, etEndDate, etNote;
    TextInputLayout lBatchName, lBatchId, lSessionId, lCourseName, lModuleName, lStartDate, lEndDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);
        getSupportActionBar().setTitle("Add Batch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnClose = findViewById(R.id.btn_Close);
        btnAdd = findViewById(R.id.btn_update);
        etBatchId = findViewById(R.id.et_Batch_Id);
        etBatchName = findViewById(R.id.et_Batch_Name);
        etSessionId = findViewById(R.id.et_Session_Id);
        etCourseName = findViewById(R.id.et_Course_Name);
        etModuleName = findViewById(R.id.et_Module_Name);
        etStartDate = findViewById(R.id.et_Start_Date);
        etEndDate = findViewById(R.id.et_End_Date);
        etNote = findViewById(R.id.et_Note);
        lBatchName = findViewById(R.id.lBatch_Name);
        lBatchId = findViewById(R.id.lBatch_Id);
        lSessionId = findViewById(R.id.lSession_Id);
        lCourseName = findViewById(R.id.lCouse_Name);
        lModuleName = findViewById(R.id.lModule_Name);
        lStartDate = findViewById(R.id.lStart_Date);
        lEndDate = findViewById(R.id.lEnd_Date);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            btnAdd.setText("update");
            etBatchName.setText(bundle.getString("BatchName"));
          etBatchId.setText(bundle.getString("BatchId"));
            etModuleName.setText(bundle.getString("Module"));
            etBatchId.setText(bundle.getString("CourseName"));
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              close();
            }
        });
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddBatch.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                batchStartDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                etStartDate.setText(batchStartDate);
                            }
                        }, year, month, day);
                Toast.makeText(AddBatch.this, "Clicked on Start Date: " + batchStartDate, Toast.LENGTH_SHORT).show();
                datePicker.show();

            }
        });
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddBatch.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                batchEndDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                etEndDate.setText(batchEndDate);
                            }
                        }, year, month, day);
                Toast.makeText(AddBatch.this, "Clicked on Start Date: " + batchEndDate, Toast.LENGTH_SHORT).show();
                datePicker.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnAdd.getText().equals("Save"))
                    addBatch();
                else{
                    update();
                }
                close();
            }
        });

    }
    public void close(){
        etBatchName.setText("");
        etBatchId.setText("");
        etSessionId.setText("");
        etCourseName.setText("");
        etModuleName.setText("");
        etEndDate.setText("");
        etEndDate.setText("");
        etNote.setText("");
        finish();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                super.onBackPressed();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Adding the lecture

    public void addBatch(){


        String batchName=etBatchName.getText().toString().trim();
        String moduleName=etModuleName.getText().toString().trim();
        String courseName=etCourseName.getText().toString().trim();
        String note=etNote.getText().toString().trim();


        String sessionId=etSessionId.getText().toString();
        String batchId=etBatchId.getText().toString();
        Log.d("TAG", "addBatch: "+batchName+moduleName+courseName+note+
                sessionId+batchId);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.showBatchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("TAG", "onResponse: "+response);
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
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("sessionID",sessionId);
                params.put("batchName",batchName);
                params.put("moduleName",moduleName);
                params.put("batchID",batchId);
                params.put("courseName",courseName);
                params.put("batchStartTime",batchStartDate);
                params.put("batchEndTime",batchEndDate);
                params.put("batchInCharge","");
                params.put("batchNote",note);
                return super.getParams();
            }
        };
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //Updating the batch
    public void update(){
//        JSONObject main = new JSONObject();
//        JSONObject input = new JSONObject();
//        try {
//            input.put("id", "17");
//            input.put("sessionType", "School Academic");
//            input.put("sessionID", "245");
//            input.put("sessionStartDate", "17-06-2021");
//            input.put("sessionEndDate", "25-06-2021");
//            input.put("sessionNote", "hahhahahha");
//            main.put("input", input);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        requestBody = main.toString();
//        Log.d("our Resquest", requestBody);
//
//
//
////
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, "https://chotoly.com/Session/api", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("VOLLEY", response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("VOLLEY", error.toString());
//            }
//        }) {
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return null;
//            }
//        };
//
//        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);

    }
}