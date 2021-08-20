package online.rkmhikai.config.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import online.rkmhikai.R;
import online.rkmhikai.model.Subject;

public class ChapterDetailAdapter extends RecyclerView.Adapter<ChapterDetailAdapter.ChapterViewHolder> {
    Context context;
    List<Subject> subjectsList;
    View cvAdd;

    int status,id;
    String ourClass;
   public ChapterDetailAdapter(Context context, List<Subject> subjectsList, View cvAdd){
        this.subjectsList = subjectsList;
        this.context = context;
        this.cvAdd = cvAdd;
    }
    @NonNull
    @NotNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_item, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChapterViewHolder holder, int position) {
        Subject subject = subjectsList.get(position);
        Log.d("TAG", "onBindViewHolder: "+subject.toString());
        holder.chkTitle.setText(subject.getTitle());
        holder.tvItemDesc.setText(subject.getDesc());
        holder.tvCreated.setText("Created At " + subject.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        if (subjectsList.size() == 0) {
            return 0;
        }
        Log.d("TAG", "getItemCount: "+subjectsList.size());
        return subjectsList.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDesc, tvCreated;
        CheckBox chkTitle;
        LinearLayout llEditable;
        Button btnAddNextElement;
        CardView cvMain;
        ImageView ivEdit, ivDelete;
        public ChapterViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chkTitle = itemView.findViewById(R.id.chk_Item_Title);
            tvItemDesc = itemView.findViewById(R.id.tv_Item_Desc);
            llEditable = itemView.findViewById(R.id.ll_Editable);
            cvMain = itemView.findViewById(R.id.cv_main_item);
            btnAddNextElement = itemView.findViewById(R.id.btn_item_AddNextElement);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            tvCreated = itemView.findViewById(R.id.tv_Created);
        }
    }
}
