package online.rkmhikai.config.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import online.rkmhikai.model.School;
import online.rkmhikai.R;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewholder> {
    Context context;
    List<School> schoolList;
    public SchoolAdapter(Context context, List<School> schoolList){
        this.context=context;
        this.schoolList=schoolList;
    }
    @NonNull
    @NotNull
    @Override
    public SchoolViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item,parent,false);

        return (new SchoolViewholder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SchoolViewholder holder, int position) {
        holder.txtSchoolName.setText(schoolList.get(position).getName());
        holder.txtPhone.setText(schoolList.get(position).getPhone());
        holder.txtPin.setText(schoolList.get(position).getPincode());
        holder.txtEmail.setText(schoolList.get(position).getEmail());
        holder.txtAddress.setText(schoolList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return schoolList.size();
    }

    public class SchoolViewholder extends RecyclerView.ViewHolder {
        ImageView ivSchol;
        TextView txtSchoolName,txtPin,txtEmail,txtAddress,txtPhone;
        public SchoolViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtAddress=itemView.findViewById(R.id.tv_address);
            txtSchoolName=itemView.findViewById(R.id.tv_schoolname);
            txtEmail=itemView.findViewById(R.id.tv_eamil);
            txtPin=itemView.findViewById(R.id.tv_pincode);
            txtPhone=itemView.findViewById(R.id.tv_phone);
        }
    }
}
