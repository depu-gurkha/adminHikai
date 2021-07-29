package online.rkmhikai.ui.authentication;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import online.rkmhikai.MainActivity;
import online.rkmhikai.R;
import online.rkmhikai.config.RequestSingletonVolley;
import online.rkmhikai.config.SharedPrefManager;
import online.rkmhikai.config.URLs;
import online.rkmhikai.config.User;
import online.rkmhikai.library.Validation;
import online.rkmhikai.ui.landingpage.LandingPage;


public class Login extends Fragment {
    private static final int PERMISSION_STORAGE_CODE = 1000;
    TextInputLayout lPass, lUserName;
    EditText email, pwd;
    Button btnLogin;
    public static FragmentManager fragmentManager;
    ProgressBar loginProgress;
    String profileUrl;


    public Login() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_login, container, false);
        lUserName = view.findViewById(R.id.lUsername);
        lPass = view.findViewById(R.id.lPass);
        email = view.findViewById(R.id.uname);
        pwd = view.findViewById(R.id.pwd);
        btnLogin = view.findViewById(R.id.btn_login);
        loginProgress=view.findViewById(R.id.loginProgress);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });

       return view;
    }

    private void userLogin() {
        //first getting the values
        //first getting the values
        final String username = email.getText().toString();
        final String password = pwd.getText().toString();
        if (Validation.isEmpty(lUserName, "Enter a valid Username") | Validation.isValidPassword(lPass, "Please enter a valid Passsword")) {

            btnLogin.setVisibility(View.GONE);
            loginProgress.setVisibility(View.VISIBLE);
            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.userloginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                Log.d("Server Response", obj.toString());

                                //if no error in response
                                Log.d("Server Response", String.valueOf(obj.getInt("success")));
                                if (obj.getInt("success") == 1) {

//                                    Toast.makeText(getActivity().getApplicationContext(), "Data Success", Toast.LENGTH_SHORT).show();


                                    //getting the user from the response

                                    JSONObject userJson = obj.getJSONObject("data");
                                    if(userJson.getString("userType").equals("Administrator")){
                                        Log.d("data", userJson.toString());
                                        //url for Profile Pic
                                        profileUrl= userJson.getString("profilepicture");

                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                                                //Permission Denied, Request it
                                                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                                //show popup for runtime permission
                                                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                                            }
                                            else {
                                                //Permission Already granted, perform download
                                                startDownloading(profileUrl);
                                            }
                                        }
                                        startDownloading(profileUrl);
                                        //creating a new user object
                                        User user = new User(
                                                userJson.getString("email"),
                                                userJson.getString("session_id"),
                                                userJson.getString("firstName") + userJson.getString("lastName"),
                                                userJson.getString("userType"),
                                                userJson.getString("stream"),
                                                userJson.getString("gender"),
                                                userJson.getString("dateOfBirth"),
                                                userJson.getString("profilepicture"),
                                                userJson.getString("accesstoken"),
                                                userJson.getString("refreshtoken"),
                                                userJson.getString("access_token_expires_in"),
                                                userJson.getString("class"),
                                                userJson.getString("participantID")

                                        );
                                        Log.d("ouruser", user.getName().toString());

                                        //storing the user in shared preferences
                                        SharedPrefManager.getInstance(getActivity().getApplicationContext()).userLogin(user);

                                        //starting the profile activity
                                        getActivity().finish();
                                        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Your are not the Administrator", Toast.LENGTH_SHORT).show();
                                        btnLogin.setVisibility(View.VISIBLE);
                                        loginProgress.setVisibility(View.GONE);

                                    }

                                }
                                else {
                                    Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    btnLogin.setVisibility(View.VISIBLE);
                                    loginProgress.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                btnLogin.setVisibility(View.VISIBLE);
                                loginProgress.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.GONE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userName", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
        }
    }
    private void startDownloading(String url){
        //get url/text from edit text

        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        Log.i("TAG", "startDownloading: "+url);
        Log.i("TAG", "Filename: "+ URLUtil.guessFileName(url,null,null));

        //create download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //Allow types of network to download
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading File...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis()); //get current timestamp as file name
//        request.setDestinationInExternalFilesDir(getApplicationContext(),"/Videos/abc","Hello-"+System.currentTimeMillis()+".jpg");
        request.setDestinationInExternalFilesDir(getContext(),"/ProfilePic/", URLUtil.guessFileName(url,null,null));



        //get download service and enque file
        DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue((request));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted from popup, perform download
                    startDownloading(profileUrl);
                }
                else {
                    //permission denied from popup, show error message
                    Toast.makeText(getContext(), "Permision Denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}