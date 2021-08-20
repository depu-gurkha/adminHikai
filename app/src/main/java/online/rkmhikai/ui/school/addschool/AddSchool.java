package online.rkmhikai.ui.school.addschool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.SharedPrefManager;
import online.rkmhikai.library.Validation;
import online.rkmhikai.model.School;

public class AddSchool extends AppCompatActivity {

    Button btnPrev, btnNext, btnFinish;
    ProgressBar progressBar;
    FrameLayout frameLayoutMain;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SchoolVitalInfoFragment schoolVitalInfoFragment;
    SchoolAddressFragment schoolAddressFragment;
    SchoolMiscFragment schoolMiscFragment;

    //First Fragment Data
    TextInputLayout llSchoolName, llSchoolCategory, llSchoolManagement, llSchoolPhone, llSchoolEmail;
    EditText etSchoolName, etSchoolCategory, etSchoolManagement, etSchoolPhone, etSchoolEmail;

    //Second Fragment Data
    TextInputLayout llSchoolBlock, llSchoolArea, llSchoolVillage, llSchoolDistrict, llSchoolPincode;
    EditText etSchoolBlock, etSchoolArea, etSchoolVillage, etSchoolDistrict, etSchoolPincode;

    //Third Fragment Data
    TextInputLayout llEstdYear, llRecogYear;
    EditText etEstdYear, etRecogYear;

    private String name, category, management, area, village, block, district, pincode, phone, email, estdYear, recogYear;

    private int mStatusCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        progressBar = findViewById(R.id.progressBar);
        frameLayoutMain = findViewById(R.id.frame_layout_main);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnFinish = findViewById(R.id.btn_finish);

        progressBar.setMax(3);


        schoolVitalInfoFragment = new SchoolVitalInfoFragment();
        schoolAddressFragment = new SchoolAddressFragment();
        schoolMiscFragment = new SchoolMiscFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main, schoolVitalInfoFragment, "testID");
        //fragmentTransaction.addToBackStack("testID");
        fragmentTransaction.commitNow();

        setProgress();

        btnPrev.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.GONE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);

                if (titleFragmentById != null && titleFragmentById instanceof SchoolVitalInfoFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");

                    llSchoolName = findViewById(R.id.ll_school_name);
                    llSchoolCategory = findViewById(R.id.ll_school_category);
                    llSchoolManagement = findViewById(R.id.ll_school_management);
                    llSchoolPhone = findViewById(R.id.ll_school_phone);
                    llSchoolEmail = findViewById(R.id.ll_school_email);

                    etSchoolName = findViewById(R.id.et_school_name);
                    etSchoolCategory = findViewById(R.id.et_school_category);
                    etSchoolManagement = findViewById(R.id.et_school_management);
                    etSchoolPhone = findViewById(R.id.et_school_phone);
                    etSchoolEmail = findViewById(R.id.et_school_email);


//                    if(Validation.isEmpty(llSchoolName,"Name is Required") | Validation.isEmpty(llSchoolCategory,"Category is Required") |
//                        Validation.isEmpty(llSchoolManagement,"Management is Required") | Validation.isEmpty(llSchoolPhone,"Phone is Required")|
//                        Validation.isEmpty(llSchoolEmail,"Email is Required")){

                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, schoolAddressFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(2);
                    btnPrev.setVisibility(View.VISIBLE);

                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolName(etSchoolName.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolCategory(etSchoolCategory.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolManagement(etSchoolManagement.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolPhone(etSchoolPhone.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolEmail(etSchoolEmail.getText().toString().trim());

//                    }


                    //Toast.makeText(MainActivity.this, SharedPrefManager.getInstance(getApplicationContext()).getSchoolEmail().toString(), Toast.LENGTH_SHORT).show();
                }

                if (titleFragmentById != null && titleFragmentById instanceof SchoolAddressFragment) {
                    Log.d("TAG", "onCreate: SUCCESS");


                    llSchoolBlock = findViewById(R.id.ll_school_block);
                    llSchoolArea = findViewById(R.id.ll_school_area);
                    llSchoolVillage = findViewById(R.id.ll_school_village);
                    llSchoolDistrict = findViewById(R.id.ll_school_district);
                    llSchoolPincode = findViewById(R.id.ll_school_pincode);

                    etSchoolBlock = findViewById(R.id.et_school_block);
                    etSchoolArea = findViewById(R.id.et_school_area);
                    etSchoolVillage = findViewById(R.id.et_school_village);
                    etSchoolDistrict = findViewById(R.id.et_school_district);
                    etSchoolPincode = findViewById(R.id.et_school_pincode);

//                    if(Validation.isEmpty(llSchoolBlock,"Block is Required") | Validation.isEmpty(llSchoolArea,"Area is Required") |
//                            Validation.isEmpty(llSchoolVillage,"Village is Required") | Validation.isEmpty(llSchoolDistrict,"District is Required")|
//                            Validation.isEmpty(llSchoolPincode,"Pincode is Required")) {

                    //Toast.makeText(MainActivity.this, "Going to second Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, schoolMiscFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(3);
                    btnPrev.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);

                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolBlock(etSchoolBlock.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolArea(etSchoolArea.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolVillage(etSchoolVillage.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolDistrict(etSchoolDistrict.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolPincode(etSchoolPincode.getText().toString().trim());
                    //}
                }
                Toast.makeText(AddSchool.this, SharedPrefManager.getInstance(getApplicationContext()).getSchoolPincode(), Toast.LENGTH_SHORT).show();

            }


        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);
                if(titleFragmentById !=null && titleFragmentById instanceof SchoolAddressFragment){
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to first Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, schoolVitalInfoFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(1);
                    btnPrev.setVisibility(View.INVISIBLE);

                }
                if(titleFragmentById !=null && titleFragmentById instanceof SchoolMiscFragment){
                    Log.d("TAG", "onCreate: SUCCESS");
                    //Toast.makeText(MainActivity.this, "Going to first Fragment", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_main, schoolMiscFragment, "testID");
                    //fragmentTransaction.addToBackStack("testID");
                    fragmentTransaction.commit();
                    progressBar.setProgress(2);
                    btnNext.setVisibility(View.VISIBLE);
                    btnFinish.setVisibility(View.GONE);
                }
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddSchool.this, "Clicked in Finish", Toast.LENGTH_SHORT).show();
                llEstdYear = findViewById(R.id.ll_estd_year);
                llRecogYear = findViewById(R.id.ll_recog_year);

                etEstdYear = findViewById(R.id.et_estd_year);
                etRecogYear = findViewById(R.id.et_recog_year);

                if(Validation.isEmpty(llEstdYear,"Established Year is Required") |
                        Validation.isEmpty(llRecogYear,"Recognition Year is Required")) {

                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolEstdyear(etEstdYear.getText().toString().trim());
                    SharedPrefManager.getInstance(getApplicationContext()).setSchoolRecogyear(etRecogYear.getText().toString().trim());

                    Toast.makeText(AddSchool.this, SharedPrefManager.getInstance(getApplicationContext()).getSchoolRecogyear(), Toast.LENGTH_SHORT).show();
                    addSchool();

//                //Toast.makeText(MainActivity.this, "Going to second Fragment", Toast.LENGTH_SHORT).show();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_layout_main, thirdFragment, "testID");
//                //fragmentTransaction.addToBackStack("testID");
//                fragmentTransaction.commit();
//                progressBar.setProgress(3);
//                btnPrev.setVisibility(View.VISIBLE);
//                btnFinish.setVisibility(View.VISIBLE);
//                btnNext.setVisibility(View.GONE);
                }
//                addCourse();

            }
        });
    }
    public void setProgress(){
        Fragment titleFragmentById = fragmentManager.findFragmentById(R.id.frame_layout_main);
        Log.d("TAG", "onCreate: "+titleFragmentById);

        if(titleFragmentById !=null && titleFragmentById instanceof SchoolVitalInfoFragment){
            Log.d("TAG", "onCreate: SUCCESS");
            //Toast.makeText(this, "1st Fragment", Toast.LENGTH_SHORT).show();
            progressBar.setProgress(1);
        } else if(titleFragmentById !=null && titleFragmentById instanceof SchoolVitalInfoFragment){
            Log.d("TAG", "onCreate: SUCCESS");
            //Toast.makeText(this, "2nd Fragment", Toast.LENGTH_SHORT).show();
            progressBar.setProgress(1);
        }
    }

    //Add New Session
    private void addSchool() {

        name=SharedPrefManager.getInstance(getApplicationContext()).getSchoolName();
        category=SharedPrefManager.getInstance(getApplicationContext()).getSchoolCategory();
        management=SharedPrefManager.getInstance(getApplicationContext()).getSchoolManagement();
        area=SharedPrefManager.getInstance(getApplicationContext()).getSchoolArea();
        village=SharedPrefManager.getInstance(getApplicationContext()).getSchoolVillage();
        block=SharedPrefManager.getInstance(getApplicationContext()).getSchoolBlock();
        district=SharedPrefManager.getInstance(getApplicationContext()).getSchoolDistrict();
        pincode=SharedPrefManager.getInstance(getApplicationContext()).getSchoolPincode();
        phone=SharedPrefManager.getInstance(getApplicationContext()).getSchoolPhone();
        email=SharedPrefManager.getInstance(getApplicationContext()).getSchoolEmail();
        estdYear=SharedPrefManager.getInstance(getApplicationContext()).getSchoolEstdyear();
        recogYear=SharedPrefManager.getInstance(getApplicationContext()).getSchoolRecogyear();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://classroom.chotoly.com/school/api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //try {
                        //Log.d("TAG", "onResponse: "+response);
                        Log.d("TAG", "onResponse: "+mStatusCode);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")){
                                Toast.makeText(AddSchool.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(mStatusCode == 200){
                            Toast.makeText(AddSchool.this, "School Added Successfully", Toast.LENGTH_SHORT).show();

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
                params.put("name", name);
                params.put("category", category);
                params.put("area", area);
                params.put("village", village);
                params.put("block", block);
                params.put("district", district);
                params.put("pincode", pincode);
                params.put("phone", phone);
                params.put("email", email);
                params.put("estdYear", estdYear);
                params.put("recogYear", recogYear);
                return params;

            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                if(response != null){
                    mStatusCode = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };

        RequestSingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}