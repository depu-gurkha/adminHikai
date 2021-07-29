package online.rkmhikai.ui.users;

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
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Users;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.UserAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class UserFragment extends Fragment {

    private UserViewModel mViewModel;
    RecyclerView rvUser;
    Button btnAddUser;
    UserAdapter userAdapter;
    List<Users> userList;
    LinearLayoutManager layoutManager;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.user_fragment, container, false);
        rvUser=view.findViewById(R.id.rv_User);
        userList=new ArrayList<>();
        fetchJson();
        userAdapter=new UserAdapter(getContext(),userList);
        layoutManager = new LinearLayoutManager(getContext());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // TODO: Use the ViewModel
    }

    public void fetchJson()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLs.showUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    if(obj.getInt("success")==1){


                        JSONArray usersArray=obj.getJSONArray("records");
                        Log.d("TAG", "onResponse: "+usersArray);
                        for(int i=0;i<usersArray.length();i++){
                            JSONObject userdetail=usersArray.getJSONObject(i);
                            int participantId=userdetail.getInt("participantID");
                            String fName=userdetail.getString("firstName");
                            String email =userdetail.getString("email");
                            String lName=userdetail.getString("lastName");
                            int userId=userdetail.getInt("userID");

                            String phone=userdetail.getString("phone");
                            String userType=userdetail.getString("userType");
                            String userActive=userdetail.getString("useractive");
                            String permission=userdetail.getString("permission");
                            Users user=new Users(userId,participantId,fName,lName,email,phone,userType,userActive,permission);
                            userList.add(user);
                        }
                        rvUser.setLayoutManager(layoutManager);
                        rvUser.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
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