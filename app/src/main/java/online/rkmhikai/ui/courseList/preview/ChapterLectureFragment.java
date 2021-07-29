package online.rkmhikai.ui.courseList.preview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.R;
import online.rkmhikai.config.RecyclerViewClickInterface;
import online.rkmhikai.config.URLs;
import online.rkmhikai.config.adapter.ChapterAdapter;
import online.rkmhikai.model.Chapter;
import online.rkmhikai.model.Content;

public class ChapterLectureFragment extends Fragment implements RecyclerViewClickInterface {

    RecyclerView rvChapter;

    LinearLayoutManager layoutManagerGroup;

    public JsonArrayRequest request;
    private RequestQueue requestQueue;

    List<Chapter> chapterList;

    ChapterAdapter chapterAdapter;

    ProgressBar pbLoad;

    int subjectId,courseId;
    String subjectTitle;


    public ChapterLectureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chapter_lecture, container, false);
        rvChapter = v.findViewById(R.id.rv_chapter_lecture);
        pbLoad = v.findViewById(R.id.pb_load);

        PlayerActivity activity = (PlayerActivity) getActivity();
        subjectId = activity.getMySubject();
        subjectTitle = activity.getMySubjectTitle();
        courseId = activity.getMyCourse();

        //Toast.makeText(getContext(), String.valueOf(subjectId), Toast.LENGTH_SHORT).show();

        layoutManagerGroup = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvChapter.setLayoutManager(layoutManagerGroup);
        chapterList = new ArrayList<>();

        fetchChapterJson(1042);
        return v;
    }

    public void fetchChapterJson(int courseId){

        request = new JsonArrayRequest(Request.Method.GET, URLs.showClasssRoomUrl+"?subject="+subjectId+"&courseID="+courseId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d("VOLLEY", "onResponse: "+response.toString());
                //JSONArray jsonArray = null;
                try {

                    JSONObject jsonObject = null;
                    for(int i=0;i<response.length();i++){
                        Log.d("TAG", "onResponse: Inside FOR");
                        jsonObject = response.getJSONObject(i);

                        //int subjectID=jsonObject.getInt("subjectID");
                        int totalLecture =jsonObject.getInt("items");
                        int chapterNo =i;
                        String title=jsonObject.getString("title");
                        String description=jsonObject.getString("description");


                        Chapter chapter = new Chapter();
                        chapter.setChapterNo(chapterNo);
                        chapter.setTotalLecture(totalLecture);
                        chapter.setChapterTitle(title);
                        chapter.setDescription(description);

                        JSONArray content = jsonObject.getJSONArray("content");

                        chapter.setContentList(buildContentList(content));

                        chapterList.add(chapter);
                        Log.d("VOLLEY", "Subject Title: "+jsonObject.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(chapterList);
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

    private void setuprecyclerview(List<Chapter> chapterList) {
        chapterAdapter = new ChapterAdapter(chapterList,ChapterLectureFragment.this);
        rvChapter.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChapter.setAdapter(chapterAdapter);
        chapterAdapter.notifyDataSetChanged();
        pbLoad.setVisibility(View.GONE);
        rvChapter.setVisibility(View.VISIBLE);

    }

    private List<Content> buildContentList(JSONArray contentArray) {
        final List<Content> contentList = new ArrayList<>();

        try {
            for (int i=0; i<contentArray.length(); i++) {

                JSONObject contentObject=contentArray.getJSONObject(i);
                String type = contentObject.getString("type");
                String title = contentObject.getString("title");
                String description = contentObject.getString("description");
                int contentID=-1;
                String file="";
                if(type.equalsIgnoreCase("quiz")){
                    contentID = contentObject.getInt("quizID");
                    Log.d("TAG", "buildContentList: QUIZ");
                }else if(type.equalsIgnoreCase("assignment")){
                    contentID = contentObject.getInt("assignmentID");
                    file = contentObject.getString("file");
                    Log.d("TAG", "buildContentList: ASSIGNMENT");
                }else if(type.equalsIgnoreCase("resources")){
                    contentID = contentObject.getInt("resourcesID");
                    file = contentObject.getString("file");
                    Log.d("TAG", "buildContentList: RESOURCE");
                }else if(type.equalsIgnoreCase("video")){
                    contentID = contentObject.getInt("lectureID");
                    file = contentObject.getString("file");
                    Log.d("TAG", "buildContentList: LECTURE"+file);
                }

                Content content = new Content();
                content.setContentID(contentID);
                content.setContentTitle(title);
                content.setContentDescription(description);
                content.setContentFile(file);
                content.setContentType(type);

                contentList.add(content);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentList;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onItemClick(String name) {
        Log.d("CHAP", "onItemClick: "+name);
        VideoViewMainFragment f = new VideoViewMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path",name);
        bundle.putLong("seek",0);
        bundle.putInt("Subject_ID",subjectId);
        bundle.putString("Subject_Title",subjectTitle);
        f.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_layout_main,f).commit();
    }
}