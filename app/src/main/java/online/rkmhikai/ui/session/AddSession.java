package online.rkmhikai.ui.session;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.MainActivity;
import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;
import online.rkmhikai.library.Validation;

public class AddSession extends AppCompatActivity {
    private DatePickerDialog datePicker;
    private int year, month, day;
    int id;
    String sessionType = "", sessionID = "", sessionStartDate = "", sessionEndDate = "", sessionNote = "";

    EditText etSessionId, etSessionType, etNote, etStartDate, etEndDate;
    TextInputLayout lSessionId, lSessionType, lStartDate, lEndDate;

    Button btnCancel, btnUpload;

    Spinner spSessionType;
    Dialog dialog;
    ImageView ivCancel;
    String requestBody = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onCreate: add Sesson" );
        setContentView(R.layout.activity_add_session);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnCancel = findViewById(R.id.btn_Close);
        btnUpload = findViewById(R.id.btn_update);
        etSessionId = findViewById(R.id.et_Session_Id);
        spSessionType = findViewById(R.id.sp_session_type);
        etStartDate = findViewById(R.id.et_Start_Date);
        etEndDate = findViewById(R.id.et_End_Date);
        lSessionId = findViewById(R.id.lSession_Id);
        etNote = findViewById(R.id.et_Note);
        lStartDate = findViewById(R.id.lStart_Date);
        lEndDate = findViewById(R.id.lEnd_Date);
        dialog = new Dialog(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btnUpload.setText("update");
            etNote.setText(bundle.getString("Note"));
            etEndDate.setText(bundle.getString("SessionEndDate"));
            etStartDate.setText(bundle.getString("SessionStartDate"));
            id=bundle.getInt("ID");

            etSessionId.setText(bundle.getString("SessionId"));
            Log.d("TAG", "onCreate: bundle " + bundle.getString("Note"));

        }

        //Start Session Date Picker
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddSession.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sessionStartDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                etStartDate.setText(sessionStartDate);
                            }
                        }, year, month, day);
                Toast.makeText(AddSession.this, "Clicked on Start Date: " + sessionStartDate, Toast.LENGTH_SHORT).show();
                datePicker.show();
            }
        });

        //Start Session Date Picker
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddSession.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sessionEndDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                etEndDate.setText(sessionEndDate);
                            }
                        }, year, month, day);
                Toast.makeText(AddSession.this, "Clicked on End Date: " + sessionEndDate, Toast.LENGTH_SHORT).show();
                datePicker.show();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               close();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "MyClick: " + btnUpload.getText());
                if (btnUpload.getText().equals("Save"))
                    addSession();
                else {
                    try {
                        updateSession();
                        close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });


    }

    //Add New Session
    private void addSession() {


        if (Validation.isEmpty(lSessionId, "Enter Valid Session Id") | Validation.isEmpty(lStartDate, "Select Valid Date") | Validation.isEmpty(lEndDate, "Select Valid Date")) {
            Log.d("TAG", "SessionID:" + lSessionId.getEditText().getText().toString());
            Log.d("TAG", "SessionStartDate:" + lStartDate.getEditText().getText().toString());
            Log.d("TAG", "SessionEndDate:" + lEndDate.getEditText().getText().toString());


            sessionID = lSessionId.getEditText().getText().toString();
            sessionStartDate = lStartDate.getEditText().getText().toString();
            sessionEndDate = lEndDate.getEditText().getText().toString();
            sessionNote = etNote.getText().toString();
            sessionType = spSessionType.getSelectedItem().toString();

            Log.d("ACTUAL", "addSession(sessionID): " + sessionID);
            Log.d("ACTUAL", "addSession(sessionStartDate): " + sessionStartDate);
            Log.d("ACTUAL", "addSession(sessionEndDate): " + sessionEndDate);
            Log.d("ACTUAL", "addSession(sessionNote): " + sessionNote);
            Log.d("ACTUAL", "addSession(sessionType): " + sessionType);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.showSessionUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("TAG", "onResponse: " + response);
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                Log.d("Server Response", obj.toString());
                                // if no error in response
                                if (obj.getInt("success") == 1) {
                                    Toast.makeText(AddSession.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AddSession.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddSession.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            btnSignUp.setVisibility(View.VISIBLE);
//                            vitalProgress.setVisibility(View.GONE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("sessionType", sessionType);
                    params.put("sessionID", sessionID);
                    params.put("sessionStartDate", sessionStartDate);
                    params.put("sessionEndDate", sessionEndDate);
                    params.put("sessionNote", sessionNote);
                    return params;

                }
            };

            RequestSingletonVolley.getInstance(AddSession.this).addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(AddSession.this, "Please enter valid fields", Toast.LENGTH_SHORT).show();
        }
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


    public void updateSession() throws JSONException {
        Log.d("TAG", "updateSession: "+"updateSession");
        sessionID = lSessionId.getEditText().getText().toString();
        sessionStartDate = lStartDate.getEditText().getText().toString();
        sessionEndDate = lEndDate.getEditText().getText().toString();
        sessionNote = etNote.getText().toString();
        sessionType = spSessionType.getSelectedItem().toString();
        Log.i("ID VALUE",String.valueOf(id));

        JSONObject main = new JSONObject();
        JSONObject input = new JSONObject();
        try {
            input.put("id", id);
            input.put("sessionType", sessionType);
            input.put("sessionID", sessionID);
            input.put("sessionStartDate", sessionStartDate);
            input.put("sessionEndDate", sessionEndDate);
            input.put("sessionNote", sessionNote);
            main.put("input", input);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.PUT, URLs.showSessionUrl, main, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY RS", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY ER", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestSingletonVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);

        // Adding request to request queue



    }
    public void close(){
        etEndDate.setText("");
        etNote.setText("");
        etSessionId.setText("");
        etStartDate.setText("");
        Intent intent = new Intent(AddSession.this, MainActivity.class);
        intent.putExtra("COME",1);
        startActivity(intent);
        finish();
    }


}