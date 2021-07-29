package online.rkmhikai.ui.scheduler;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;

import online.rkmhikai.R;

public class Scheduler extends Fragment {

    public static Scheduler newInstance() {
        return new Scheduler();
    }

    private SchedulerViewModel mViewModel;
    CalendarView calendarView;
    Dialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.scheduler_fragment, container, false);
        calendarView=view.findViewById(R.id.calendar);
        dialog = new Dialog(getContext());
        //set custom dialog
        dialog.setContentView(R.layout.custom_event_add);
        //Set custom height and width
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //Set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dialog.show();
                EditText etDate=dialog.findViewById(R.id.et_Date);
                EditText etEventName=dialog.findViewById(R.id.et_Event);
                EditText etEventDesc=dialog.findViewById(R.id.et_Event_Desc);
                Button btnSave=dialog.findViewById(R.id.btn_Save);
                Button btnCancel=dialog.findViewById(R.id.btn_Cancel);

                etDate.setText(i2+"-"+(i1+1)+"-"+i);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);
        // TODO: Use the ViewModel
    }

}