package online.rkmhikai.ui.users;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class AddUsers extends AppCompatActivity {
    ImageView iv_profile;
    EditText etFName, etLName, etEmail, etPhone, etPermission, etUserType;
    TextInputLayout lEmail, lPhone, lPermission, lUserType;
    Button btnUpdate, btnCancel;
    ImageView ivCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iv_profile = findViewById(R.id.iv_Profile);
        etFName = findViewById(R.id.et_First_Name);
        etLName = findViewById(R.id.et_Last_Name);
        etEmail = findViewById(R.id.et_Email);
        etPhone = findViewById(R.id.et_Phone);
        etPermission = findViewById(R.id.et_Permission);
        etUserType = findViewById(R.id.et_UserType);
        lEmail = findViewById(R.id.lEmail);
        lPhone = findViewById(R.id.lPhone);
        lPermission = findViewById(R.id.lPermission);
        lUserType = findViewById(R.id.lUsertype);
        btnUpdate = findViewById(R.id.btn_update);
        btnCancel = findViewById(R.id.btn_Close);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btnUpdate.setText("update");
            etFName.setText(bundle.getString("FirstName"));
            etLName.setText(bundle.getString("LastName"));
            etEmail.setText(bundle.getString("Email"));
            etPermission.setText(bundle.getString("Permission"));
            etPhone.setText(bundle.getString("Phone"));
            etUserType.setText(bundle.getString("UserType"));

        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnUpdate.getText().equals("Add")) {
                    addUsers();
                } else {
                    update();
                }
                close();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 close();

            }
        });


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

    public void close() {
        etFName.setText("");
        etLName.setText("");
        etEmail.setText("");
        etPermission.setText("");
        etUserType.setText("");
        finish();
    }

    public void addUsers() {
        String fname = etFName.getText().toString().trim();
        String lName = etFName.getText().toString().trim();
        String email = etFName.getText().toString().trim();
        String permission = etFName.getText().toString().trim();
        String userType
                = etFName.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.showUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("TAG", "onResponse: " + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                return super.getParams();
            }
        };
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    //Updating the batch
    public void update() {
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