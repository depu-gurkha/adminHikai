package online.rkmhikai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import online.rkmhikai.config.SharedPrefManager;
import online.rkmhikai.ui.batch.AddBatch;
import online.rkmhikai.ui.school.AddSchool;
import online.rkmhikai.ui.session.AddSession;
import online.rkmhikai.ui.users.AddUsers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_session, R.id.nav_batch, R.id.nav_course_list, R.id.nav_users, R.id.nav_scheduler, R.id.nav_school, R.id.nav_notification,
                R.id.nav_contact, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        View hView = navigationView.getHeaderView(0);
        TextView participantName = hView.findViewById(R.id.tv_participant_name);
        participantName.setText(SharedPrefManager.getInstance(this).getUser().getName());
        //TextView participantClass = hView.findViewById(R.id.tv);
        TextView participantId = hView.findViewById(R.id.tv_participant_id);
        ImageView participantProfile = hView.findViewById(R.id.iv_profile_pic);
        NavigationUI.setupWithNavController(navigationView, navController);
        // This line needs to be after setupWithNavController()
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                        Log.d("TAG", "onDestinationChanged: " + destination.getLabel());
                        Intent intent;
                        switch (destination.getId()) {
                            case R.id.nav_batch:
                                intent = new Intent(MainActivity.this, AddBatch.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_session:
                                intent = new Intent(MainActivity.this, AddSession.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_users:
                                intent = new Intent(MainActivity.this, AddUsers.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_school:
                                intent = new Intent(MainActivity.this, AddSchool.class);
                                startActivity(intent);

                        }
                    }
                });
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_home || destination.getId() == R.id.nav_scheduler || destination.getId() == R.id.nav_contact ||
                        destination.getId() == R.id.nav_about||destination.getId()==R.id.nav_notification||destination.getId()==R.id.nav_course_list) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_session,menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.nav_logout:
                Log.d("TAG", "onNavigationItemSelected: ");
                SharedPrefManager.getInstance(this).logout();
                break;
            default:
                Log.d("TAG", "onNavigationItemSelected: "+item.getItemId());
                // Trigger the default action of replacing the current
                // screen with the one matching the MenuItem's ID
                NavigationUI.onNavDestinationSelected(item, navController);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}