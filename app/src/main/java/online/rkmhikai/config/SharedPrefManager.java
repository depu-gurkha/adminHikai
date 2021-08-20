package online.rkmhikai.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import online.rkmhikai.ui.landingpage.LandingPage;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "participant";
    private static final String URL = "oururl";
    private static final String ID = "sessionId";
    private static final String USERNAME = "keyUsername";
    private static final String USER_EMAIL = "email";
    private static final String USER_TYPE = "userType";
    private static final String USER_GENDER = "gender";
    private static final String USER_CLASS = "class";
    private static final String USER_STREAM = "stream";
    private static final String USER_DOB = "dob";
    private static final String USER_PROFILEPIC = "profilepicture";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String TOKEN_EXPIRY = "tokenExpiry";
    private static final String USER_ID = "userid";
    private static final String USER_PHONE = "phone";
    private static final String COURSE_ID = "courseId";
    private static final String TOKEN = "token";
    private static final String COURSE_TYPE = "courseType";
    private static final String COURSE_TITLE = "courseTitle";
    private static final String COURSE_CATEGORY = "courseCategory";
    private static final String COURSE_DESCRIPTION = "courseDescription";

    private static final String SCHOOL_NAME = "schoolName";
    private static final String SCHOOL_CATEGORY = "schoolCategory";
    private static final String SCHOOL_MANAGEMENT = "schoolManagement";
    private static final String SCHOOL_PHONE = "schoolPhone";
    private static final String SCHOOL_EMAIL = "schoolEmail";
    private static final String SCHOOL_BLOCK = "schoolBlock";
    private static final String SCHOOL_AREA = "schoolArea";
    private static final String SCHOOL_VILLAGE = "schoolVillage";
    private static final String SCHOOL_DISTRICT = "schoolDistrict";
    private static final String SCHOOL_PINCODE = "schoolPincode";
    private static final String SCHOOL_ESTDYEAR = "schoolEstdYear";
    private static final String SCHOOL_RECOGYEAR = "schoolRecogYear";

    private static final String PARTICIPANT_ID = "token";

    private static SharedPrefManager mInstance;// Shared Preference Instance
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }

    //This method creATES AN INSTANCE of shared preference
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setUrl(String url) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String strUrl = url.toString();
        editor.putString(URL, strUrl);
        editor.apply();
    }

    // Setting userId for vital information use
    public void setUserId(int userId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    // Setting userPhonefor vital information use
    public void setUserPhone(String phone) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHONE, phone);
        editor.apply();
    }

    public void setUserEmail(String email) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    //Course Id
    public void setCourseId(String courseId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COURSE_ID, courseId);
        editor.apply();
    }

    //Getting the url

    public int getUserId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_ID, 0);
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, "");
    }


    //Course Id
    public void setToken(String token) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    //Getting the url

    public String getToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, " ");
    }

    public String getUserPhone() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PHONE, "");
    }

    public String getUCourseId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_ID, "1042");
    }

    //Getting the url

//        public String  getUrl(){
//            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//            return sharedPreferences.getString(URL,"http://192.168.1.120/");
//        }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID, user.getSession_id());
        editor.putString(USERNAME, user.getName());
        editor.putString(USER_GENDER, user.getGender());
        editor.putString(USER_CLASS, user.getStandard());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_TYPE, user.getUserType());
        editor.putString(ACCESS_TOKEN, user.getAccessToken());
        editor.putString(TOKEN_EXPIRY, user.getTokenExpiry());
        editor.putString(REFRESH_TOKEN, user.getRefreshToken());
        editor.putString(USER_STREAM, user.getStream());
        editor.putString(USER_DOB, user.getDateOfBirth());
        editor.putString(USER_PROFILEPIC, user.getProfilePicture());
        editor.putString(PARTICIPANT_ID, user.getParticipantId());
        editor.apply();
    }

    //this method will check whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(USER_EMAIL, null),
                sharedPreferences.getString(ID, null),
                sharedPreferences.getString(USERNAME, null),
                sharedPreferences.getString(USER_TYPE, null),
                sharedPreferences.getString(USER_STREAM, null),
                sharedPreferences.getString(USER_GENDER, null),
                sharedPreferences.getString(USER_DOB, null),
                sharedPreferences.getString(USER_PROFILEPIC, null),
                sharedPreferences.getString(ACCESS_TOKEN, null),
                sharedPreferences.getString(REFRESH_TOKEN, null),
                sharedPreferences.getString(TOKEN_EXPIRY, null),
                sharedPreferences.getString(USER_CLASS, null),
                sharedPreferences.getString(PARTICIPANT_ID, null)

        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LandingPage.class));
    }

    //Getting and setting  the values of course for adding it
    public String getCourseType() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_TYPE, "null");
    }

    public String getCourseTitle() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_TITLE, "null");
    }

    public String getCourseCategory() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_CATEGORY, "null");
    }

    public String getCourseDescription() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COURSE_DESCRIPTION, "null");
    }

    public void setCourseType(String courseType) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COURSE_TYPE, courseType);
        editor.apply();
    }

    public void setCourseTitle(String courseTitle) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COURSE_TITLE, courseTitle);
        editor.apply();
    }

    public void setCourseCategory(String courseCategory) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COURSE_CATEGORY, courseCategory);
        editor.apply();
    }

    public void setCourseDescription(String courseDescription) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COURSE_DESCRIPTION, courseDescription);
        editor.apply();
    }

    //Getting and setting the values for the school adding
    public void setSchoolName(String schoolName) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_NAME, schoolName);
        editor.apply();
    }

    public void setSchoolCategory(String schoolCategory) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_CATEGORY, schoolCategory);
        editor.apply();
    }

    public void setSchoolManagement(String schoolManagement) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_MANAGEMENT, schoolManagement);
        editor.apply();
    }

    public void setSchoolPhone(String schoolPhone) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_PHONE, schoolPhone);
        editor.apply();
    }

    public void setSchoolEmail(String schoolEmail) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_EMAIL, schoolEmail);
        editor.apply();
    }

    public void setSchoolBlock(String schoolBlock) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_BLOCK, schoolBlock);
        editor.apply();
    }

    public void setSchoolArea(String schoolArea) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_AREA, schoolArea);
        editor.apply();
    }

    public void setSchoolVillage(String schoolVillage) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_VILLAGE, schoolVillage);
        editor.apply();
    }

    public void setSchoolDistrict(String schoolDistrict) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_DISTRICT, schoolDistrict);
        editor.apply();
    }

    public void setSchoolPincode(String schoolPincode) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_PINCODE, schoolPincode);
        editor.apply();
    }

    public void setSchoolEstdyear(String schoolEstdyear) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_ESTDYEAR, schoolEstdyear);
        editor.apply();
    }

    public void setSchoolRecogyear(String schoolRecogyear) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCHOOL_RECOGYEAR, schoolRecogyear);
        editor.apply();
    }


    //Getting the values
    public String getSchoolName() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_NAME, "null");
    }

    public String getSchoolCategory() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_CATEGORY, "null");
    }

    public String getSchoolManagement() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_MANAGEMENT, "null");
    }

    public String getSchoolPhone() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_PHONE, "null");
    }

    public String getSchoolEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_EMAIL, "null");
    }

    public String getSchoolBlock() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_BLOCK, "null");
    }

    public String getSchoolArea() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_AREA, "null");
    }

    public String getSchoolVillage() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_VILLAGE, "null");
    }

    public String getSchoolDistrict() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_DISTRICT, "null");
    }

    public String getSchoolPincode() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_PINCODE, "null");
    }

    public String getSchoolEstdyear() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_ESTDYEAR, "null");
    }

    public String getSchoolRecogyear() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SCHOOL_RECOGYEAR, "null");
    }

}
