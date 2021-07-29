package online.rkmhikai.config.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Quiz;
import online.rkmhikai.R;
import online.rkmhikai.ui.courseList.details.quiz.AddQuestionActivity;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizView> implements Filterable {



    //List<HashMap<String,Object>> SessionList = new ArrayList<>();
    //List<HashMap<String,Object>> filteredList = new ArrayList<>();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
   // QuizDisplayFragment quizDisplayFragment;

    List<Quiz> quizList = new ArrayList<>();
    List<Quiz> filteredList = new ArrayList<>();

    CustomFilter customFilter;
    Context context;

    public QuizAdapter(List<Quiz> quizList,Context context) {
        this.quizList = quizList;
        filteredList = quizList;
        customFilter = new CustomFilter();
        this.context=context;

    }

    @NonNull
    @Override
    public QuizView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item,parent,false);
        return new QuizView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizView holder, int position) {
        //HashMap<String,Object> map = filteredList.get(position);

        Quiz quiz = filteredList.get(position);


        holder.tvQuizTitle.setText(quiz.getTitle());
        holder.tvQuizDescription.setText(quiz.getDescription());
        holder.tvGotoAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AddQuestionActivity.class);
                context.startActivity(intent);
            }
        });

        holder.tvGotoViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Clicked on: "+position+" Quiz ID: "+quiz.getQuizID(), Toast.LENGTH_SHORT).show();
//                quizDisplayFragment.gotoViewQuestion();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }


    public class QuizView extends RecyclerView.ViewHolder {

        TextView tvQuizTitle,tvQuizDescription,tvGotoAddQuestion,tvGotoViewQuestion;
        ImageView ivEditQuiz,ivDeleteQuiz;
        public QuizView(@NonNull View itemView) {
            super(itemView);
            tvQuizTitle =itemView.findViewById(R.id.tv_quiz_title);
            tvQuizDescription =itemView.findViewById(R.id.tv_quiz_description);
            tvGotoAddQuestion =itemView.findViewById(R.id.tv_goto_add_question);
            tvGotoViewQuestion =itemView.findViewById(R.id.tv_goto_view_question);
            ivEditQuiz=itemView.findViewById(R.id.iv_edit_quiz);
            ivDeleteQuiz=itemView.findViewById(R.id.iv_delete_quiz);
        }
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = QuizList;
            List<Quiz> newList = quizList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Quiz> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Quiz quiz = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String quiztitle = quiz.getTitle();

                if(quiztitle.toLowerCase().contains(searchValue)){
                    resultList.add(quiz);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Quiz>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
