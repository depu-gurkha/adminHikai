package online.rkmhikai.ui.session;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Session;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.SessionAdapter;
import online.rkmhikai.config.URLs;

public class SessionFragment extends Fragment {

    private SessiionViewModel galleryViewModel;
    public JsonObjectRequest request;
    private RequestQueue requestQueue;
    LinearLayoutManager layoutManager;
    private RecyclerView rvSession;
    private Button btnGotoAddSession;
    private SessionAdapter sessionAdapter;
    List<Session> sessionList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(SessiionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_session, container, false);
        Log.d("TAG", "onCreateView: display");
        //final TextView textView = root.findViewById(R.id.text_gallery);
//                       galleryViewModel.notify();
//

        rvSession = root.findViewById(R.id.rv_Session);
        layoutManager = new LinearLayoutManager(getContext());
        rvSession.setLayoutManager(layoutManager);

        sessionList = new ArrayList<>();






        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchSessionJson();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        Log.d("TAG", "onPrepareOptionsMenu: ");
        MenuItem menuItem=menu.findItem(R.id.action_search_batch);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Session");

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                sessionAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void fetchSessionJson(){

        request = new JsonObjectRequest(Request.Method.GET, URLs.showSessionUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY", "onResponse: "+response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("records");
                    JSONObject jsonObject = null;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        int id=jsonObject.getInt("id");
                        String sessionType=jsonObject.getString("sessionType");
                        String sessionID=jsonObject.getString("sessionID");
                        String sessionStartDate=jsonObject.getString("sessionStartDate");
                        String sessionEndDate=jsonObject.getString("sessionEndDate");
                        String sessionNote=jsonObject.getString("sessionNote");


                        Session session = new Session();
                        session.setId(id);
                        session.setSessionID(sessionID);
                        session.setSessionType(sessionType);
                        session.setSessionStartDate(sessionStartDate);
                        session.setSessionEndDate(sessionEndDate);
                        session.setSessionNote(sessionNote);


                        sessionList.add(session);
                        Log.d("VOLLEY", "BatchName: "+jsonObject.getString("sessionID"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(sessionList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", "onErrorResponse: "+error.toString());
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }


    private void setuprecyclerview(List<Session> sessionList) {

        sessionAdapter = new SessionAdapter(sessionList,getContext());
        rvSession.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSession.setAdapter(sessionAdapter);

    }

}