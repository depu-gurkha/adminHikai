package online.rkmhikai.ui.batch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Batch;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.BatchAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class BatchFragment extends Fragment {
    Button btnAddBatch;
    RecyclerView rvBatch;
    public JsonObjectRequest request;
    LinearLayoutManager layoutManager;
    List<Batch> batchList;
    private BatchAdapter batchAdapter;

    private BatchViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(BatchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_batch, container, false);

        rvBatch = root.findViewById(R.id.rv_batch);
        layoutManager = new LinearLayoutManager(getContext());
        rvBatch.setLayoutManager(layoutManager);

        batchList = new ArrayList<>();

        fetchBatchJson();


        return root;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
   // @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//
//        Log.d("TAG", "onPrepareOptionsMenu: ");
//        MenuItem menuItem=menu.findItem(R.id.action_search_batch);
//        menuItem.setVisible(true);
//
//        SearchView searchView = (SearchView)menuItem.getActionView();
//        searchView.setQueryHint("Search Subject");
//
//        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(getResources().getColor(R.color.white));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                batchAdapter.getFilter().filter(s);
//                return false;
//            }
//        });
//    }

    private void fetchBatchJson() {
        request = new JsonObjectRequest(Request.Method.GET, URLs.showBatchUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY", "onResponse: " + response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("records");
                    JSONObject jsonObject = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        int sessionID = jsonObject.getInt("sessionID");
                        String batchName = jsonObject.getString("batchName");
                        String batchID = jsonObject.getString("batchID");
                        String moduleName = jsonObject.getString("moduleName");
                        String courseName = jsonObject.getString("courseName");
                        String batchStartTime = jsonObject.getString("batchStartTime");
                        String batchEndTime = jsonObject.getString("batchEndTime");
                        String batchNote = jsonObject.getString("batchNote");
                        String batchInCharge=jsonObject.getString("batchInCharge");
                        //Batch batch = new Batch(id,sessionID,batchName,batchId,moduleName,courseName,batchStartTime,batchEndTime,batchNote);
                        Batch batch = new Batch();
                        batch.setId(id);
                        batch.setSessionID(sessionID);
                        batch.setBatchName(batchName);
                        batch.setBatchID(batchID);
                        batch.setModuleName(moduleName);
                        batch.setCourseName(courseName);
                        batch.setBatchStartTime(batchStartTime);
                        batch.setBatchEndTime(batchEndTime);
                        batch.setBatchNote(batchNote);
                        batch.setBatchInCharge(batchInCharge);

                        batchList.add(batch);
                        Log.d("VOLLEY", "BatchName: " + jsonObject.getString("batchName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(batchList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", "onErrorResponse: " + error.toString());
            }
        });

        RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(request);


    }


    private void setuprecyclerview(List<Batch> batchList) {

        batchAdapter = new BatchAdapter(getContext(), batchList);
        rvBatch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBatch.setAdapter(batchAdapter);

    }
}
