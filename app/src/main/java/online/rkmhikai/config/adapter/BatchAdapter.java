package online.rkmhikai.config.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import online.rkmhikai.model.Batch;
import online.rkmhikai.R;
import online.rkmhikai.ui.batch.AddBatch;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.BatchView> implements Filterable {
    Context context;
    List<Batch> batchList;
    List<Batch> filteredList = new ArrayList<>();

    CustomFilter customFilter;

    public BatchAdapter(Context context, List<Batch> batchList){
        this.context=context;
        this.batchList=batchList;
        filteredList = batchList;
        customFilter = new CustomFilter();
    }

    @NonNull
    @NotNull
    @Override
    public BatchView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_item,parent,false);
        return(new BatchView(view) );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BatchView holder, int position) {
        Batch batch = filteredList.get(position);
        holder.tvBatchName.setText(batch.getBatchName());
        holder.tvBatchId.setText(batch.getBatchID());
        holder.tvModuleName.setText(batch.getModuleName());
        holder.tvCourseName.setText(batch.getCourseName());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AddBatch.class);
                intent.putExtra("BatchName",batchList.get(position).getBatchName());
                intent.putExtra("BatchId",batchList.get(position).getBatchID());
                intent.putExtra("Module",batchList.get(position).getModuleName());
                intent.putExtra("CourseName",batchList.get(position).getCourseName());
                //intent.putExtra("StartDate",batchList.get(position).getBatchName());
                context.startActivity(intent);
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

    public class BatchView extends RecyclerView.ViewHolder {
        TextView tvBatchName,tvBatchId,tvModuleName,tvCourseName,tvStartDate,tvEndDate,tvNote;
        ImageButton ivEdit;
        public BatchView(@NonNull @NotNull View itemView) {
            super(itemView);
            tvBatchName =itemView.findViewById(R.id.tv_batch_name);
            tvBatchId =itemView.findViewById(R.id.tv_batch_id);
            tvModuleName =itemView.findViewById(R.id.tv_module_name);
            tvCourseName =itemView.findViewById(R.id.tv_course_name);
            tvStartDate=itemView.findViewById(R.id.tv_start_date);
            tvEndDate =itemView.findViewById(R.id.tv_end_date);
            tvNote =itemView.findViewById(R.id.tv_note);
            ivEdit=itemView.findViewById(R.id.iv_edit_batch);

        }
    }
    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = batchList;
            List<Batch> newList = batchList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Batch> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Batch batch = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String title = batch.getBatchName();

                if(title.toLowerCase().contains(searchValue)){
                    resultList.add(batch);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Batch>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
