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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import online.rkmhikai.model.Session;
import online.rkmhikai.R;
import online.rkmhikai.ui.session.AddSession;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionView> implements Filterable {


    List<Session> SessionList = new ArrayList<>();
    List<Session> filteredList = new ArrayList<>();
    Context context;

    CustomFilter customFilter;

    public SessionAdapter(List<Session> SessionList,Context context) {
        this.SessionList = SessionList;
        this.context=context;
        filteredList = SessionList;
        customFilter = new CustomFilter();
    }

    @NonNull
    @Override
    public SessionView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_item,parent,false);
        return new SessionView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionView holder, int position) {
        

        Session session = filteredList.get(position);

        holder.tvSessionId.setText(session.getSessionID());
        holder.tvSessionType.setText(session.getSessionType());
        holder.tvStartDate.setText(session.getSessionStartDate());
        holder.tvEndDate.setText(session.getSessionEndDate());
        holder.tvNote.setText(session.getSessionNote());
        holder.ivEditSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on: "+position+" Session ID: "+session.getSessionID(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, AddSession.class);
                intent.putExtra("SessionType",SessionList.get(position).getSessionType());
                intent.putExtra("SessionId",SessionList.get(position).getSessionID());
                intent.putExtra("SessionStartDate",SessionList.get(position).getSessionStartDate());
                intent.putExtra("SessionEndDate",SessionList.get(position).getSessionEndDate());
                intent.putExtra("Note",SessionList.get(position).getSessionNote());
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


    public class SessionView extends RecyclerView.ViewHolder {

        TextView tvSessionId,tvSessionType,tvStartDate,tvEndDate,tvNote;
        ImageView ivEditSession;

        public SessionView(@NonNull View itemView) {
            super(itemView);
            tvSessionId =itemView.findViewById(R.id.tv_session_id);
            tvSessionType =itemView.findViewById(R.id.tv_session_type);
            tvStartDate =itemView.findViewById(R.id.tv_start_date);
            tvEndDate =itemView.findViewById(R.id.tv_end_date);
            tvNote =itemView.findViewById(R.id.tv_note);
            ivEditSession=itemView.findViewById(R.id.iv_edit_session);
        }
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();

            //List<HashMap<String,Object>> newList = SessionList;
            List<Session> newList = SessionList;
            //List<HashMap<String,Object>> resultList = new ArrayList<>();
            List<Session> resultList = new ArrayList<>();

            String searchValue = charSequence.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                //HashMap<String,Object> map = newList.get(i);
                Session session = newList.get(i);
                Log.d("TAG", "performFiltering: ");
                String sessionId = session.getSessionID();

                if(sessionId.toLowerCase().contains(searchValue)){
                    resultList.add(session);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            //filteredList = (List<HashMap<String, Object>>) filterResults.values;
            filteredList = (List<Session>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
