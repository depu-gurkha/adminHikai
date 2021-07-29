package online.rkmhikai.ui.notification;

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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Notification;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.NotificationAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    public JsonObjectRequest request;

    LinearLayoutManager layoutManagerGroup;

    private RecyclerView recyclerView;
    List<Notification> notificationList;
    NotificationAdapter myadapter;

    //JSON DATA for SUBJECT
    String notification_JSON;




    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.notification_fragment, container, false);
        recyclerView = v.findViewById(R.id.rv_notification);
        layoutManagerGroup = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerGroup);
        notificationList=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        jsonrequest();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        // TODO: Use the ViewModel
    }
        private void jsonrequest() {
        request = new JsonObjectRequest(Request.Method.GET, URLs.notificationUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray = null;

                try {

                    jsonArray = response.getJSONArray("records");

                    JSONObject jobj = null;

                    for (int i=0;i<jsonArray.length();i++){
                        jobj = jsonArray.getJSONObject(i);
                        int id=jobj.getInt("id");
                        String title=jobj.getString("title");
                        String text=jobj.getString("text");
                        Notification notification=new Notification(id,title,text);
                        notificationList.add(notification);

                    }
                    myadapter = new NotificationAdapter(getContext(),notificationList);
                    recyclerView.setAdapter(myadapter);
                    myadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                setuprecyclerview(lstNotification);


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VOLLEY", "onErrorResponse: "+error.toString());
            }
        });

            RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(request);
    }

}