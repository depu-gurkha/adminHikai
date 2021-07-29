package online.rkmhikai.config.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Question;
import online.rkmhikai.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionView> implements Filterable {



    //List<HashMap<String,Object>> SessionList = new ArrayList<>();
    //List<HashMap<String,Object>> filteredList = new ArrayList<>();


    List<Question> questionList = new ArrayList<>();
    List<Question> filteredList = new ArrayList<>();

    CustomFilter customFilter;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
        filteredList = questionList;
        customFilter = new CustomFilter();

    }

    @NonNull
    @Override
    public QuestionView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);
        return new QuestionView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionView holder, int position) {
        //HashMap<String,Object> map = filteredList.get(position);

        Question question = filteredList.get(position);

//        holder.tvQuestionName.setText(String.valueOf(map.get("QuestionName")));
//        holder.tvQuestionId.setText(String.valueOf(map.get("QuestionId")));
//        holder.tvModuleName.setText(String.valueOf(map.get("ModuleName")));
//        holder.tvCourseName.setText(String.valueOf(map.get("CourseName")));

        holder.tvQuestion.setText(question.getQuestion());
        holder.tvOptionA.setText(question.getOptionA());
        holder.tvOptionB.setText(question.getOptionB());
        holder.tvOptionC.setText(question.getOptionC());
        holder.tvOptionD.setText(question.getOptionD());
        holder.tvAnswer.setText(question.getAnswer());
        holder.ivEditQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on: "+position+" Question ID: "+question.getQuestion(), Toast.LENGTH_SHORT).show();

//                fragmentManager = questionDisplayFragment.getFragmentManager();
//                fragmentTransaction =fragmentManager.beginTransaction();
//                AddQuestionFragment addQuestionFragment = new AddQuestionFragment();
//                fragmentTransaction.replace(R.id.container,addQuestionFragment,null);
//                fragmentTransaction.commit();
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


    public class QuestionView extends RecyclerView.ViewHolder {

        TextView tvQuestion,tvOptionA,tvOptionB,tvOptionC,tvOptionD,tvAnswer;
        ImageView ivEditQuestion;
        public QuestionView(@NonNull View itemView) {
            super(itemView);
            tvQuestion =itemView.findViewById(R.id.tv_question);
            tvOptionA =itemView.findViewById(R.id.tv_option_a);
            tvOptionB =itemView.findViewById(R.id.tv_option_b);
            tvOptionC =itemView.findViewById(R.id.tv_option_c);
            tvOptionD =itemView.findViewById(R.id.tv_option_d);
            tvAnswer =itemView.findViewById(R.id.tv_answer);
            ivEditQuestion=itemView.findViewById(R.id.iv_edit_question);
        }
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = QuestionList;
            List<Question> newList = questionList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Question> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Question question = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String questiontitle = question.getQuestion();

                if(questiontitle.toLowerCase().contains(searchValue)){
                    resultList.add(question);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Question>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
