package online.rkmhikai.ui.school;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.School;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.SchoolAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class SchoolFragment extends Fragment {

    private SchoolViewModel mViewModel;
    RecyclerView rvSchool;
    SchoolAdapter schoolAdapter;
    List<School> schoolList;
    LinearLayoutManager layoutManager;

    public static SchoolFragment newInstance() {
        return new SchoolFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.school_fragment, container, false);
        rvSchool=v.findViewById(R.id.rv_school);
        schoolList=new ArrayList<>();
        fetchJson();
        schoolAdapter=new SchoolAdapter(getContext(),schoolList);
        layoutManager = new LinearLayoutManager(getContext());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SchoolViewModel.class);
        // TODO: Use the ViewModel
    }

    public void fetchJson()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLs.showSchoolUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    if(obj.getInt("success")==1){


                        JSONArray usersArray=obj.getJSONArray("records");
                        Log.d("TAG", "onResponse: "+usersArray);
                        for(int i=0;i<usersArray.length();i++){
                            JSONObject userdetail=usersArray.getJSONObject(i);
                            int id=userdetail.getInt("id");
                            String name=userdetail.getString("name");
                            String management=userdetail.getString("management");
                            String address=userdetail.getString("district")+userdetail.getString("village");
                            String pincode=userdetail.getString("pincode");
                            String phone=userdetail.getString("phone");
                            String email=userdetail.getString("email");
                            int status=userdetail.getInt("status");
                            School school=new School(id,address,phone,pincode,email,management,status,name);
                            schoolList.add(school);

                        }
                        rvSchool.setLayoutManager(layoutManager);
                        rvSchool.setAdapter(schoolAdapter);
                        schoolAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}