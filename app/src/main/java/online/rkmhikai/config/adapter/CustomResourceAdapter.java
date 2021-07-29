package online.rkmhikai.config.adapter;

import android.content.Context;
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

import online.rkmhikai.model.Resource;
import online.rkmhikai.R;

public class CustomResourceAdapter extends RecyclerView.Adapter<CustomResourceAdapter.MyResourceViewHolder> {
    Context context;
    List<Resource> resourceList;
    View cvAdd;
    int toogleStatus=0;

    public CustomResourceAdapter(Context context,List<Resource> resourceList,View cvAdd){
        this.context=context;
        this.resourceList=resourceList;
        this.cvAdd=cvAdd;
    }

    @NonNull
    @Override
    public MyResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assignment,parent,false);
         return (new MyResourceViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyResourceViewHolder holder, int position) {
    holder.btnAddResource.setText("Add Resource");
    holder.chkTitle.setText(resourceList.get(position).getTitle());
    holder.tvCreated.setText(resourceList.get(position).getCreatedAt());
        if(!resourceList.get(position).getFile().equals("null")){
            holder.ivToggle.setVisibility(View.VISIBLE);
            holder.btnAddResource.setVisibility(View.GONE);


        }
       if (resourceList.get(position).getStatus()==1){
           holder.chkTitle.setChecked(true);
       }

        //For adding Assignment File
        holder.btnAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.llUpload.setVisibility(View.VISIBLE);
            }
        });

        //For Toggle
        holder.ivToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toogleStatus==0) {
                    toogleStatus=1;
                    holder.ivToggle.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    holder.llFile.setVisibility(View.VISIBLE);
                    holder.tvFile.setText(resourceList.get(position).getFile());
                }
                else{
                    toogleStatus=0;

                    holder.ivToggle.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    holder.llFile.setVisibility(View.GONE);

                }
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


        //For Editing
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvAdd.setVisibility(View.VISIBLE);
                Log.d("TAG", "onClick: IvEditClicked");
                EditText title = cvAdd.findViewById(R.id.et_Lecture_Title);
                EditText desc = cvAdd.findViewById(R.id.et_Lecture_Objective);

                title.setText(holder.chkTitle.getText());
                desc.setText(holder.tvResourceeDesc.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
         if(resourceList.size()==0)
             return 0;
         return resourceList.size();
    }

    public class MyResourceViewHolder extends RecyclerView.ViewHolder {
        TextView tvResourceeDesc,tvCreated,tvChoose,tvFile;
        CheckBox chkTitle;
        LinearLayout llEditable;
        Button btnAddResource,btnCloseChoose,btnChoose;
        CardView cvMain;

        ImageView ivEdit, ivDelete,ivToggle;
        LinearLayout llFile,llUpload;
        public MyResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResourceeDesc=itemView.findViewById(R.id.tv_Assignment_Desc);
            tvCreated=itemView.findViewById(R.id.tv_Created);
            chkTitle=itemView.findViewById(R.id.chk_Assignment_Title);
            llEditable=itemView.findViewById(R.id.ll_Editable);
            llFile=itemView.findViewById(R.id.ll_File);
            btnAddResource=itemView.findViewById(R.id.btn_item_AddNextElement);
            ivDelete=itemView.findViewById(R.id.iv_delete);
            ivEdit=itemView.findViewById(R.id.iv_edit);
            ivToggle=itemView.findViewById(R.id.iv_Edit_Toggle);
            llUpload=itemView.findViewById(R.id.ll_Upload);
            btnChoose=itemView.findViewById(R.id.btn_Choose);
            btnCloseChoose=itemView.findViewById(R.id.btn_Close_Choose);
            tvChoose=itemView.findViewById(R.id.tv_Choose);
            tvFile=itemView.findViewById(R.id.tv_File);
        }
    }
}
