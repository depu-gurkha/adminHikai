package online.rkmhikai.ui.courseList.details.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddQuestionActivity extends AppCompatActivity {
    String question="",optionA="",optionB="",optionC="",optionD="",answer="";

    EditText etQuestion,etOptionA,etOptionB,etOptionC,etOptionD;
    TextInputLayout lQuestion,lOptionA,lOptionB,lOptionC,lOptionD;

    Button btnAddQuestion;

    Spinner spAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spAnswer = findViewById(R.id.sp_answer);
        etQuestion = findViewById(R.id.et_question);
        etOptionA = findViewById(R.id.et_option_a);
        etOptionB = findViewById(R.id.et_option_b);
        etOptionC = findViewById(R.id.et_option_c);
        etOptionD = findViewById(R.id.et_option_d);

        lQuestion = findViewById(R.id.lQuestion);
        lOptionA = findViewById(R.id.lOptionA);
        lOptionB = findViewById(R.id.lOptionB);
        lOptionC = findViewById(R.id.lOptionC);
        lOptionD = findViewById(R.id.lOptionD);

        btnAddQuestion = findViewById(R.id.btn_add_question);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
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
    private void addQuestion() {



        if (Validation.isEmpty(lQuestion, "Enter Valid Question") | Validation.isEmpty(lOptionA,"Enter Valid Option") | Validation.isEmpty(lOptionB,"Enter Valid Option")|Validation.isEmpty(lOptionC,"Enter Valid Option") | Validation.isEmpty(lOptionD,"Enter Valid Option")) {
            Log.d("TAG", "Question:" + lQuestion.getEditText().getText().toString());
            Log.d("TAG", "Option A:" + lOptionA.getEditText().getText().toString());
            Log.d("TAG", "Option B:" + lOptionB.getEditText().getText().toString());
            Log.d("TAG", "Option C:" + lOptionC.getEditText().getText().toString());
            Log.d("TAG", "Option D:" + lOptionD.getEditText().getText().toString());


            question=lQuestion.getEditText().getText().toString();
            optionA=lOptionA.getEditText().getText().toString();
            optionB=lOptionB.getEditText().getText().toString();
            optionC=lOptionC.getEditText().getText().toString();
            optionD=lOptionD.getEditText().getText().toString();
            answer= spAnswer.getSelectedItem().toString();

            Log.d("ACTUAL", "addQuestion(question): "+question);
            Log.d("ACTUAL", "addQuestion(optionA): "+optionA);
            Log.d("ACTUAL", "addQuestion(optionB): "+optionB);
            Log.d("ACTUAL", "addQuestion(optionC): "+optionC);
            Log.d("ACTUAL", "addQuestion(optionD): "+optionD);
            Log.d("ACTUAL", "addQuestion(answer): "+answer);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.showQuestionUrl,
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
                                    Toast.makeText(AddQuestionActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getActivity(), gender + standard + stream + DOB, Toast.LENGTH_SHORT).show();
//                                    fragmentManager = getFragmentManager();
//                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                    UploadImageFragment uploadImage = new UploadImageFragment();
//                                    fragmentTransaction.replace(R.id.fragment_container, uploadImage, null);
//                                    fragmentTransaction.commit();
                                } else {
                                    Toast.makeText(AddQuestionActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                    btnSignUp.setVisibility(View.VISIBLE);
//                                    vitalProgress.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                btnSignUp.setVisibility(View.VISIBLE);
//                                vitalProgress.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddQuestionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                            btnSignUp.setVisibility(View.VISIBLE);
//                            vitalProgress.setVisibility(View.GONE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("question", question);
                    params.put("optionA", optionA);
                    params.put("optionB", optionB);
                    params.put("optionC", optionC);
                    params.put("optionD", optionD);
                    params.put("answer", answer);
                    params.put("quizID", "29");
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