package online.rkmhikai.ui.landingpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import online.rkmhikai.R;
import online.rkmhikai.ui.authentication.LoginActivity;

public class LandingPage extends AppCompatActivity {

    Toolbar myToolbar;
    ImageView ivLanding;
    CardView cardIv;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
   // MainAdapter mainAdapter;
    Button btnClassroom,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        myToolbar=findViewById(R.id.customToolbarr);
        ivLanding=findViewById(R.id.ivLanding);
//        expandableListView=findViewById(R.id.expandableList);

        btnLogin=findViewById(R.id.btnLogin);
        listGroup=new ArrayList<>();
        listItem=new HashMap<>();
        cardIv=findViewById(R.id.cardIv);
        cardIv.setBackgroundColor(Color.TRANSPARENT);
        Glide.with(this).load("https://rkmhikai.online/public/assets/img/student.jpg").into(ivLanding);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LandingPage.this, LoginActivity.class);
                startActivity(intent);finish();

            }
        });
    }
}