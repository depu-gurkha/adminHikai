package online.rkmhikai.config.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.model.Subject;
import online.rkmhikai.ui.courseList.details.AddChapter;
import online.rkmhikai.ui.courseList.details.AddLecture;

public class

CustomItemAdapter extends RecyclerView.Adapter<CustomItemAdapter.CustomViewHolder> {

    Context context;
    List<Subject> subjectsList;
    View cvAdd;

    int status,id;
    String ourClass;

    public CustomItemAdapter(Context context, List<Subject> subjectsList, View cvAdd) {
        this.subjectsList = subjectsList;
        this.context = context;
        this.cvAdd = cvAdd;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_item, parent, false);
        return new CustomViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Subject subject = subjectsList.get(position);
        holder.chkTitle.setText(subject.getTitle());
        holder.tvItemDesc.setText(subject.getDesc());
        holder.tvCreated.setText("Created At " + subject.getCreatedAt());
        if(subject.getStatus()==1){
            holder.chkTitle.setChecked(true);
        }
        ourClass = context.getClass().toString();
        if (ourClass.equals("class online.rkmhikai.ui.courseList.details.AddSubject")) {
            holder.btnAddNextElement.setText("Chapter");
        } else {
            holder.btnAddNextElement.setText("Lecture");
        }

        Log.d("TAG", "onBindViewHolder: " + ourClass);
        holder.btnAddNextElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ourClass.equals("class online.rkmhikai.ui.courseList.details.AddSubject")) {
                    Intent intent = new Intent(context, AddChapter.class);
                    intent.putExtra("subjectId", subjectsList.get(position).getSubId());
                    context.startActivity(intent);

                    Log.d("TAG", "onClick: " + context.getClass());
                    Log.d("TAG", "onClick:  ifcalled");

                } else {
                    Intent intent = new Intent(context, AddLecture.class);

                    intent.putExtra("chapterId",subjectsList.get(position).getChapterId());
                    context.startActivity(intent);
                    Log.d("Listof chapter", String.valueOf(subjectsList.get(position).getChapterId()));
                    Log.d("TAG", "onClick: " + context.getClass());
                    Log.d("TAG", "onClick:  elsecalled");
                }
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAdd.setVisibility(View.VISIBLE);
                Log.d("TAG", "onClick: IvEditClicked");
                EditText title = cvAdd.findViewById(R.id.et_Lecture_Title);
                EditText desc = cvAdd.findViewById(R.id.et_Lecture_Objective);

                title.setText(holder.chkTitle.getText());
                desc.setText(holder.tvItemDesc.getText());
            }
        });
        holder.cvMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.llEditable.setVisibility(View.VISIBLE);
            }
        });
        //This is for the checked box
        holder.chkTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("TAG", "onCheckedChanged: "+holder.chkTitle.isChecked());
                if(holder.chkTitle.isChecked()){
                    status=0;
                    //If the calling class is the subAddsubject then we use the subject id else we use the chapter Id
                    if (ourClass.equals("class online.rkmhikai.ui.courseList.details.AddSubject")) {
                        id=subjectsList.get(position).getSubId();
                    }
                    else{
                        id=subjectsList.get(position).getChapterId();
                    }
                    updateStatus();
                }else{
                    status=1;
                    updateStatus();
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        if (subjectsList.size() == 0) {
            return 0;
        }
        return subjectsList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDesc, tvCreated;
        CheckBox chkTitle;
        LinearLayout llEditable;
        Button btnAddNextElement;
        CardView cvMain;
        ImageView ivEdit, ivDelete;

        public CustomViewHolder(@NonNull View itemView) {
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
    private void updateStatus() {
        String url;
        if (ourClass.equals("class online.rkmhikai.ui.courseList.details.AddSubject")) {
             url="https://classroom.chotoly.com/Subject/updateStatus";
        } else {
             url="https://classroom.chotoly.com/Lecture/updateStatus";
        }
        StringRequest request=new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "onResponse: "+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        Log.d("TAG", "updateds: ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("status",String.valueOf(status));
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        RequestSingletonVolley.getInstance(context).addToRequestQueue(request);
    }


}
