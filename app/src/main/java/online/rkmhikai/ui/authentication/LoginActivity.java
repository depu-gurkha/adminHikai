package online.rkmhikai.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import online.rkmhikai.R;

public class LoginActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager=getSupportFragmentManager();
        if(findViewById(R.id.fragment_container)!=null){
            Log.d("TAG", "onCreate: ");
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            Login login=new Login();
            fragmentTransaction.add(R.id.fragment_container,login,null).commit();

        }
    }
}