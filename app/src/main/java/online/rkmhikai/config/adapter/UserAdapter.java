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

import online.rkmhikai.model.Users;
import online.rkmhikai.R;
import online.rkmhikai.ui.users.AddUsers;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserView> implements Filterable {
    Context context;
    List<Users> userList=new ArrayList<>();
    List<Users> filteredList = new ArrayList<>();
    CustomFilter customFilter;
    public  UserAdapter(Context context,List<Users> userList){
        this.context=context;
        this.userList=userList;
        filteredList = userList;
        customFilter = new CustomFilter();

    }
    @NonNull
    @NotNull
    @Override
    public UserView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
       return (new UserView(view));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserView holder, int position) {
        holder.txtEmail.setText(userList.get(position).getEmail());
        holder.txtParticipantId.setText(String.valueOf(userList.get(position).getParticipantId()));
        holder.txtName.setText(userList.get(position).getFirstName()+" "+userList.get(position).getLastName());
        holder.txtPhone.setText(userList.get(position).getPhoneNo());
        holder.txtUserType.setText(userList.get(position).getUserType());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddUsers.class);
                intent.putExtra("Email",userList.get(position).getEmail());
                intent.putExtra("ParticipantId",userList.get(position).getParticipantId());
                intent.putExtra("FirstName",userList.get(position).getFirstName());
                intent.putExtra("LastName",userList.get(position).getLastName());

                intent.putExtra("UserType",userList.get(position).getUserType());
                intent.putExtra("UserActive",userList.get(position).getUserActive());
                intent.putExtra("Phone",userList.get(position).getPhoneNo());
                intent.putExtra("Permission",userList.get(position).getPermission());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        if(userList.size()==0)
            return 0;
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class UserView extends RecyclerView.ViewHolder {
        TextView txtParticipantId,txtName,txtEmail,txtPhone,txtUserType;
        ImageButton btnUpdate;
       // Check
        public UserView(@NonNull @NotNull View itemView) {
            super(itemView);
            txtParticipantId=itemView.findViewById(R.id.tv_user_participant_id);
            txtName=itemView.findViewById(R.id.tv_name);
            txtEmail=itemView.findViewById(R.id.tv_email);
            txtPhone=itemView.findViewById(R.id.tv_phone);
            txtUserType=itemView.findViewById(R.id.tv_userType);
            btnUpdate=itemView.findViewById(R.id.iv_edit_user);

        }
    }
    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = SessionList;
            List<Users> newList = userList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Users> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Users user = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String participantId = String.valueOf(user.getParticipantId());

                if(participantId.toLowerCase().contains(searchValue)){
                    resultList.add(user);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Users>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
