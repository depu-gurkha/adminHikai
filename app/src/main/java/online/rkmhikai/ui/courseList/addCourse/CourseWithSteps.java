package online.rkmhikai.ui.courseList.addCourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.SharedPrefManager;

public class CourseWithSteps extends AppCompatActivity {
    Button btnPrev, btnNext, btnFinish;
    ProgressBar progressBar;
    FrameLayout frameLayoutMain;
    int total_slide = 4;
    int current_slide = 1;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    FourthFragment fourthFragment;

    //First Fragment Data
    RadioGroup rgCourseType;
    RadioButton rbCourseType;

    //Second Fragment Data
    EditText etCourseTitle;

    //Third Fragment Data
    Spinner spCourseCategory;

    //Fourth Fragment Data
    EditText etCourseDescription;
    private String title, description, courseType, category;

    private int mStatusCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_with_steps);
        progressBar = findViewById(R.id.progressBar);
        frameLayoutMain = findViewById(R.id.frame_layout_main);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnFinish = findViewById(R.id.btn_finish);

        progressBar.setMax(4);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main, firstFragment, "testID");
        //fragmentTransaction.addToBackStack("testID");
        fragmentTransaction.commitNow();

        setProgress();
//
        btnPrev.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.GONE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);
                if (titleFragmentById != null && titleFragmentById instanceof FirstFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");

                    rgCourseType = findViewById(R.id.rg_course_type);
//                    if(rgCourseType !=null){
//                        Log.d("TAG", "onCreate: INIIALIZED");
//                    }else{
//                        Log.d("TAG", "onCreate: NOT INIIALIZED");
//                    }
                    int selectedId = rgCourseType.getCheckedRadioButtonId();
                    rbCourseType = findViewById(selectedId);

                    if (selectedId == -1) {
                        Toast.makeText(getApplicationContext(), "Nothing selected Please Select", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(MainActivity.this, "Going to second Fragment", Toast.LENGTH_SHORT).show();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout_main, secondFragment, "testID");
                        //fragmentTransaction.addToBackStack("testID");
                        fragmentTransaction.commit();
                        progressBar.setProgress(2);
                        btnPrev.setVisibility(View.VISIBLE);
                        SharedPrefManager.getInstance(getApplicationContext()).setCourseType(rbCourseType.getText().toString());
                        //Toast.makeText(MainActivity.this,courseradioButton.getText(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,SharedPrefManager.getInstance(getApplicationContext()).getCourseType(), Toast.LENGTH_SHORT).show();
                    }

                }
                if (titleFragmentById != null && titleFragmentById instanceof SecondFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to second Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, thirdFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    etCourseTitle = findViewById(R.id.et_course_title);
                    SharedPrefManager.getInstance(getApplicationContext()).setCourseTitle(etCourseTitle.getText().toString().trim());
                    Log.d("TAG", "Course Title: " + SharedPrefManager.getInstance(getApplicationContext()).getCourseTitle());
                    progressBar.setProgress(3);
                    btnPrev.setVisibility(View.VISIBLE);
                }
                if (titleFragmentById != null && titleFragmentById instanceof ThirdFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to second Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, fourthFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    spCourseCategory = findViewById(R.id.sp_course_category);
                    SharedPrefManager.getInstance(getApplicationContext()).setCourseCategory(spCourseCategory.getSelectedItem().toString().trim());
                    Log.d("TAG", "Course Category: " + SharedPrefManager.getInstance(getApplicationContext()).getCourseCategory());
                    progressBar.setProgress(4);
                    btnPrev.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);
                if (titleFragmentById != null && titleFragmentById instanceof SecondFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to first Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, firstFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(1);
                    btnPrev.setVisibility(View.INVISIBLE);

                    if (rgCourseType != null) {
                        Log.d("TAG", "onCreate: INIIALIZED");
                    } else {
                        Log.d("TAG", "onCreate: NOT INIIALIZED");
                    }

//                    if(SharedPrefManager.getInstance(getApplicationContext()).getCourseType().equalsIgnoreCase("practical")){
//                        //Toast.makeText(MainActivity.this, "Theory", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", "onClick: "+rgCourseType);
//                        rgCourseType.check(R.id.rb_theory);
//                    }else if(SharedPrefManager.getInstance(getApplicationContext()).getCourseType().equalsIgnoreCase("theory")){
//                        //Toast.makeText(MainActivity.this, "Theory", Toast.LENGTH_SHORT).show();
//                        rgCourseType.check(R.id.rb_theory);
//                    }
                }
                if (titleFragmentById != null && titleFragmentById instanceof ThirdFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to first Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, secondFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(2);
                }
                if (titleFragmentById != null && titleFragmentById instanceof FourthFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to first Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, thirdFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(3);
                    btnNext.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.GONE);
                }
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked in Finish", Toast.LENGTH_SHORT).show();
                etCourseDescription = findViewById(R.id.et_course_description);
                SharedPrefManager.getInstance(getApplicationContext()).setCourseDescription(etCourseDescription.getText().toString().trim());
                Toast.makeText(getApplicationContext(), "Course Description: " + SharedPrefManager.getInstance(getApplicationContext()).getCourseDescription(), Toast.LENGTH_SHORT).show();
                addCourse();

            }
        });

    }

    public void setProgress() {
        Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);
        Log.d("TAG", "onCreate: " + titleFragmentById);

        if (titleFragmentById != null && titleFragmentById instanceof FirstFragment) {
            Log.d("TAG", "onCreate: SUCCESS");
            //Toast.makeText(this, "1st Fragment", Toast.LENGTH_SHORT).show();
            progressBar.setProgress(1);
        } else if (titleFragmentById != null && titleFragmentById instanceof FirstFragment) {
            Log.d("TAG", "onCreate: SUCCESS");
            //Toast.makeText(this, "2nd Fragment", Toast.LENGTH_SHORT).show();
            progressBar.setProgress(1);
        }
    }

    //Add New Session
    private void addCourse() {

        title = SharedPrefManager.getInstance(getApplicationContext()).getCourseTitle();
        description = SharedPrefManager.getInstance(getApplicationContext()).getCourseDescription();
        category = SharedPrefManager.getInstance(getApplicationContext()).getCourseCategory();
        courseType = SharedPrefManager.getInstance(getApplicationContext()).getCourseType();
//            subtitle=lSessionStartDate.getEditText().getText().toString();
//            language=lSessionNote.getEditText().getText().toString();
//            subcategory= spSessionType.getSelectedItem().toString();
//            currency= spSessionType.getSelectedItem().toString();
//            price= spSessionType.getSelectedItem().toString();

        Log.d("ACTUAL", "addSession(Title): " + title);
        Log.d("ACTUAL", "addSession(Description): " + description);
        Log.d("ACTUAL", "addSession(Category): " + category);
        Log.d("ACTUAL", "addSession(CourseType): " + courseType);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chotoly.com/Course/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //try {
                        //Log.d("TAG", "onResponse: "+response);
                        Log.d("TAG", "onResponse: " + mStatusCode);
                        if (mStatusCode == 200) {
                            Toast.makeText(getApplicationContext(), "Course Added Successfully", Toast.LENGTH_SHORT).show();

                        }
//                                //converting response to json object
//                                JSONObject obj = new JSONObject(response);
//                                Log.d("Server Response", obj.toString())
                        //}
                        //catch (JSONException e) {
                        //e.printStackTrace();
//                                btnSignUp.setVisibility(View.VISIBLE);
//                                vitalProgress.setVisibility(View.GONE);
                        //}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                            btnSignUp.setVisibility(View.VISIBLE);
//                            vitalProgress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("category", category);
                params.put("title", title);
                params.put("description", description);
                params.put("courseType", courseType);
                return params;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                if (response != null) {
                    mStatusCode = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };

        RequestSingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}