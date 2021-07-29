package online.rkmhikai.config.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import online.rkmhikai.model.Lecture;
import online.rkmhikai.R;
import online.rkmhikai.config.RecyclerViewClickInterface;
import online.rkmhikai.ui.courseList.details.AddAssignment;
import online.rkmhikai.ui.courseList.details.AddResource;
import online.rkmhikai.ui.courseList.details.quiz.QuizActivity;

public class CustomLectureAdapter extends RecyclerView.Adapter<CustomLectureAdapter.MyLectureAdapter> {
    List<Lecture> lectureList;
    Context context;
    int toogleStatus = 0;
    View cvAdd;
    private int STORAGE_PERMISSION_CODE = 1;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public CustomLectureAdapter(Context context, List<Lecture> lectureList, View cvAdd, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.lectureList = lectureList;
        this.cvAdd = cvAdd;
        this.recyclerViewClickInterface=recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MyLectureAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_lecture, parent, false);
        return (new MyLectureAdapter(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyLectureAdapter holder, int position) {
        holder.chkTitle.setText(lectureList.get(position).getTitle());
        holder.tvLectureDesc.setText(lectureList.get(position).getDesc());
        holder.tvCreated.setText("Created at " + lectureList.get(position).getCreatedAt());
        if(lectureList.get(position).getStatus()==1){
            holder.chkTitle.setChecked(true);
        }
        if (!lectureList.get(position).getFile().equals("null")) {
            holder.ivToggle.setVisibility(View.VISIBLE);
            holder.btnAddVideo.setVisibility(View.GONE);

        }

        //For adding the Video
        holder.btnAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.llUpload.setVisibility(View.VISIBLE);
            }
        });

        //For toggle to get the AddQuiz,AddAssignment
        holder.ivToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toogleStatus == 0) {
                    toogleStatus = 1;
                    holder.ivToggle.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    holder.llOptions.setVisibility(View.VISIBLE);
                } else {
                    toogleStatus = 0;

                    holder.ivToggle.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    holder.llOptions.setVisibility(View.GONE);
                }
            }
        });

        //For Adding The Quiz
        holder.btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("lectureId",lectureList.get(position).getLectureID());
                context.startActivity(intent);
            }
        });
        //For Adding The Resource
        holder.btnAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddResource.class);
                intent.putExtra("lectureId",lectureList.get(position).getLectureID());
                context.startActivity(intent);
            }
        });
        //For Adding The Assignment
        holder.btnAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");
                Intent intent = new Intent(context, AddAssignment.class);
                intent.putExtra("lectureId",lectureList.get(position).getLectureID());
                context.startActivity(intent);
            }
        });


        //For Editing
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAdd.setVisibility(View.VISIBLE);
                Log.d("TAG", "onClick: IvEditClicked");
                EditText title = cvAdd.findViewById(R.id.et_Lecture_Title);
                EditText desc = cvAdd.findViewById(R.id.et_Lecture_Objective);

                title.setText(holder.chkTitle.getText());
                desc.setText(holder.tvLectureDesc.getText());

            }
        });

        //For closing the choose opdtion
        holder.btnCloseChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.llUpload.setVisibility(View.GONE);
            }
        });

        //For choosing the file from the
        holder.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            recyclerViewClickInterface.onClick();
            }
        });

        //For the checkbox
        holder.chkTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.chkTitle.isChecked()){

                }else{

                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return lectureList.size();
    }

    public class MyLectureAdapter extends RecyclerView.ViewHolder {
        TextView tvLectureDesc, tvCreated, tvChoose;
        CheckBox chkTitle;
        LinearLayout llEditable;
        Button btnAddVideo, btnCloseChoose, btnChoose, btnAddQuiz, btnAddAssignment, btnAddResource;
        CardView cvMain;

        ImageView ivEdit, ivDelete, ivToggle;
        LinearLayout llOptions, llUpload;

        public MyLectureAdapter(@NonNull View itemView) {
            super(itemView);
            tvLectureDesc = itemView.findViewById(R.id.tv_Lecture_Desc);
            tvCreated = itemView.findViewById(R.id.tv_Created);
            chkTitle = itemView.findViewById(R.id.chk_Lecture_Title);
            llEditable = itemView.findViewById(R.id.ll_Editable);
            llOptions = itemView.findViewById(R.id.ll_Options);
            btnAddVideo = itemView.findViewById(R.id.btn_item_AddVideo);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivToggle = itemView.findViewById(R.id.iv_Edit_Toggle);
            llUpload = itemView.findViewById(R.id.ll_Upload);
            btnChoose = itemView.findViewById(R.id.btn_Choose);
            btnCloseChoose = itemView.findViewById(R.id.btn_Close_Choose);
            tvChoose = itemView.findViewById(R.id.tv_Choose);
            btnAddQuiz = itemView.findViewById(R.id.btn_Add_Quiz);
            btnAddAssignment = itemView.findViewById(R.id.btn_Add_Assignment);
            btnAddResource = itemView.findViewById(R.id.btn_Add_Resources);
        }
    }






}

