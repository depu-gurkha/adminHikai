package online.rkmhikai.ui.courseList.details.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import online.rkmhikai.library.Validation;

public class AddQuizActivity extends AppCompatActivity {

    String title="",description="";

    EditText etQuizTitle,etQuizDescription;
    TextInputLayout lQuizTitle,lQuizDescription;
    Button btnAddQuiz;
    int lectureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        etQuizTitle = findViewById(R.id.et_quiz_title);
        etQuizDescription = findViewById(R.id.et_quiz_description);
        lQuizTitle = findViewById(R.id.lQuizTitle);
        lQuizDescription = findViewById(R.id.lQuizDescription);
        btnAddQuiz = findViewById(R.id.btn_add_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuiz();
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
    private void addQuiz() {



        if (Validation.isEmpty(lQuizTitle, "Enter Valid Title") | Validation.isEmpty(lQuizDescription,"Enter Valid Learning Objective")) {
            Log.d("TAG", "Quiz Tile:" + lQuizTitle.getEditText().getText().toString());
            Log.d("TAG", "Quiz Description:" + lQuizDescription.getEditText().getText().toString());


            title=lQuizTitle.getEditText().getText().toString();
            description=lQuizDescription.getEditText().getText().toString();

            Log.d("ACTUAL", "addQuiz(Quiz Title): "+title);
            Log.d("ACTUAL", "addQuiz(Quiz Description): "+description);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.showQuizUrl+lectureId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("TAG", "onResponse: "+response);
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                Log.d("Server Response", obj.toString());
                                // if no error in response
                                if (obj.getInt("success") == 1) {
                                    Toast.makeText(AddQuizActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//
                                } else {
                                    Toast.makeText(AddQuizActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
//
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddQuizActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            btnSignUp.setVisibility(View.VISIBLE);
//                            vitalProgress.setVisibility(View.GONE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("lectureID", "1177");
                    params.put("title", title);
                    params.put("description", description);
                    return params;

                }
            };

            RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
        }
        else{
            Toast.makeText(this, "Please enter valid fields", Toast.LENGTH_SHORT).show();
        }
    }
}