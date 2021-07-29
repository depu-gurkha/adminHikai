package online.rkmhikai.config.adapter;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import online.rkmhikai.R;
import online.rkmhikai.config.RecyclerViewClickInterface;
import online.rkmhikai.model.Content;


public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.LectureViewHolder> {

    private List<Content> contentList;
    RecyclerViewClickInterface recyclerViewClickInterface;
    SharedPreferences sharedPreferences;


    ContentAdapter(List<Content> contentList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.contentList = contentList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lecture_item, viewGroup, false);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LectureViewHolder subItemViewHolder, final int i) {
        final Content content = contentList.get(i);
        subItemViewHolder.tvContentTitle.setText(content.getContentTitle());
        subItemViewHolder.tvContentType.setText(content.getContentType().substring(0,1).toUpperCase()+content.getContentType().substring(1));
        subItemViewHolder.linearLayoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(content.getContentType().equalsIgnoreCase("video")) {
                    Log.d("CONTENTADAPTER", "onClick: "+content.getContentFile());
                    Toast.makeText(view.getContext(), "Playing: "+content.getContentFile(), Toast.LENGTH_SHORT).show();
                    recyclerViewClickInterface.onItemClick(content.getContentFile());
                }else{
                    Toast.makeText(view.getContext(), "Item clicked is not ready yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        subItemViewHolder.linearLayoutVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (file.exists()) {
//                    Toast.makeText(view.getContext(), "Playing: " + lecture.getLectureTitle(), Toast.LENGTH_SHORT).show();
//                    recyclerViewClickInterface.onItemClick(lecture.getLectureUrl());
//                    subItemViewHolder.cvMain.setBackgroundColor(Color.parseColor("#F5C889"));
//                } else {
//                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
//                    if (activeNetwork != null) {
//                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                            Toast.makeText(context, "WIFI Enabled", Toast.LENGTH_SHORT).show();
//                            String lecid = lecture.getLectureId().substring(0, lecture.getLectureId().indexOf('-'));
//                            String onlineUrl = lecture.getLectureUrl().replace("/storage/emulated/0/Android/data/com.demo.rkmlearn/files/Videos/", "https://rkmshillong.online/public/media/" );
//                           // Toast.makeText(view.getContext(), "Playing from net: " + onlineUrl, Toast.LENGTH_SHORT).show();
//                            Log.d("Hamro url", onlineUrl);
//                            recyclerViewClickInterface.onItemClick(onlineUrl);
//                            subItemViewHolder.cvMain.setBackgroundColor(Color.parseColor("#F5C889"));
//
//                        }
//                        if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                            Toast.makeText(context, "Data Network Enabled", Toast.LENGTH_SHORT).show();
//                            String lecid = lecture.getLectureId().substring(0, lecture.getLectureId().indexOf('-'));
//                            String onlineUrl = lecture.getLectureUrl().replace("/storage/emulated/0/Android/data/com.demo.rkmlearn/files/Videos/", "https://rkmshillong.online/public/media/" );
//                            //Toast.makeText(view.getContext(), "Playing from net: " + onlineUrl, Toast.LENGTH_SHORT).show();
//                            Log.d("Hamro url", onlineUrl);
//                            recyclerViewClickInterface.onItemClick(onlineUrl);
//                            subItemViewHolder.cvMain.setBackgroundColor(Color.parseColor("#F5C889"));
//
//                        }
//                    } else {
////            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setMessage("No Internet Connection! Please connect to internet")
//                                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
//                                    }
//                                })
//                                .setCancelable(false)
//                                .setNegativeButton("CANCEL", null);
//                        AlertDialog alert = builder.create();
//                        alert.show();
//
//
//                    }
//
//                }
//            }
//
//        });

    }


    @Override
    public int getItemCount() {
        return contentList.size();
    }


    class LectureViewHolder extends RecyclerView.ViewHolder {
        TextView tvContentTitle,tvContentType;
        LinearLayout linearLayoutVideo;
        //ImageView btnDownload;
        CardView cvMain;

        LectureViewHolder(View itemView) {
            super(itemView);
            tvContentTitle = itemView.findViewById(R.id.tv_lecture_title);
            tvContentType = itemView.findViewById(R.id.tv_lecture_type);
            linearLayoutVideo = itemView.findViewById(R.id.linear_layout_video);
            //btnDownload = itemView.findViewById(R.id.btn_download_video);
            cvMain = itemView.findViewById(R.id.cv_main);
        }
    }


}
