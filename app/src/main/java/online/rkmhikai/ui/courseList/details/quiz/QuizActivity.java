package online.rkmhikai.ui.courseList.details.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Quiz;
import online.rkmhikai.R;
import online.rkmhikai.config.adapter.QuizAdapter;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.URLs;

public class QuizActivity extends AppCompatActivity {
    RecyclerView rvQuizView;
    FloatingActionButton fabAddQuiz;
    public JsonObjectRequest request;
    LinearLayoutManager layoutManager;
    private QuizAdapter quizAdapter;
    int lectureId;

    List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvQuizView=findViewById(R.id.rv_quiz);
        fabAddQuiz=findViewById(R.id.fab_add_quiz);
        layoutManager = new LinearLayoutManager(QuizActivity.this);
        rvQuizView.setLayoutManager(layoutManager);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            lectureId=bundle.getInt("lectureId");
            Log.d("TAG", "quiz: "+URLs.showQuizUrl+lectureId);

        }


        quizList = new ArrayList<>();
        fabAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuizActivity.this,AddQuizActivity.class);
                startActivity(intent);
            }
        });
        fetchQuizJson();
    }
    public void fetchQuizJson(){

        request = new JsonObjectRequest(Request.Method.GET, URLs.showQuizUrl+lectureId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY", "onResponse: "+response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("records");
                    JSONObject jsonObject = null;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        int quizID=jsonObject.getInt("quizID");
                        int lectureID=jsonObject.getInt("lectureID");
                        String title=jsonObject.getString("title");
                        String description=jsonObject.getString("description");


                        Quiz quiz = new Quiz();
                        quiz.setQuizID(quizID);
                        quiz.setLectureID(lectureID);
                        quiz.setTitle(title);
                        quiz.setDescription(description);


                        quizList.add(quiz);
                        Log.d("VOLLEY", "Quiz Title: "+jsonObject.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(quizList,QuizActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", "onErrorResponse: "+error.toString());
            }
        });

        RequestSingletonVolley.getInstance(this).addToRequestQueue(request);


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

    private void setuprecyclerview(List<Quiz> quizList, Context context) {

        quizAdapter = new QuizAdapter(quizList,context);
        rvQuizView.setLayoutManager(new LinearLayoutManager(QuizActivity.this));
        rvQuizView.setAdapter(quizAdapter);

    }
}