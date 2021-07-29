package online.rkmhikai.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import online.rkmhikai.MainActivity;
import online.rkmhikai.R;
import online.rkmhikai.config.SharedPrefManager;
import online.rkmhikai.ui.landingpage.LandingPage;

public class Splash extends AppCompatActivity {
    private static int Splash_screen = 3200;
    boolean connected = false;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView txtDesc, txtHikai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        txtHikai = findViewById(R.id.txtHikai);
        image = findViewById(R.id.logo);
        txtDesc = findViewById(R.id.txtDesc);


        txtHikai.setAnimation(topAnim);
        image.setAnimation(topAnim);
        txtDesc.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                    Intent intent=new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Splash.this, LandingPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, Splash_screen);
    }
}