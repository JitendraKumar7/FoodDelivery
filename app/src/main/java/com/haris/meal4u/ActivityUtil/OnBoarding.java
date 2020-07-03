package com.haris.meal4u.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.haris.meal4u.AdapterUtil.FeaturePager;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.ExtensiblePageIndicator;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.FragmentUtil.OnBoardFragment;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.JsonUtil.UserUtil.FavouriteList;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.OnBoardObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;



public class OnBoarding extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>, ConnectionCallback {
    private TextView txtLogin;
    private TextView txtSignUp;
    private TextView txtSkip;
    private ExtensiblePageIndicator flexibleIndicator;
    private ViewPager viewPagerBoarding;
    private FeaturePager featurePager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private Management management;
    private ImageView imageClose;
    private FirebaseAuth mAuth;
    private LinearLayout layoutFacebook;
    private LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private String TAG = OnBoarding.class.getName();
    private GoogleSignInClient mGoogleSignInClient;
    private LinearLayout layoutGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);

        initUI(); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);
        imageClose = findViewById(R.id.image_close);


        layoutFacebook = findViewById(R.id.layout_facebook);
        layoutGoogle = findViewById(R.id.layout_google);

        management = new Management(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Add On Boarding Data into Arraylist

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_restaurant
                , Utility.getStringFromRes(this, R.string.title_01)
                , Utility.getStringFromRes(this, R.string.board_01))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_pizza
                , Utility.getStringFromRes(this, R.string.title_02)
                , Utility.getStringFromRes(this, R.string.board_02))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ph_delivery
                , Utility.getStringFromRes(this, R.string.title_03)
                , Utility.getStringFromRes(this, R.string.board_03))));

        //Initialize Pager & its indicator as well as connect it with its adapter

        flexibleIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        viewPagerBoarding = (ViewPager) findViewById(R.id.view_pager_boarding);

        featurePager = new FeaturePager(getSupportFragmentManager(), fragmentArrayList);
        viewPagerBoarding.setAdapter(featurePager);

        flexibleIndicator.initViewPager(viewPagerBoarding);


        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        layoutFacebook.setOnClickListener(this);
        layoutGoogle.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v == txtLogin) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class).putExtra(Constant.IntentKey.LOGIN_REQUIRED, true));
            finish();
        }
        if (v == imageClose) {
            finish();
        }
        if (v == layoutFacebook) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }
        if (v == layoutGoogle) {

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, Constant.RequestCode.GOOGLE_SIGN_IN_CODE);

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if userObject is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        if (requestCode != Constant.RequestCode.GOOGLE_SIGN_IN_CODE)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        else
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == Constant.RequestCode.GOOGLE_SIGN_IN_CODE) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Utility.Logger(TAG, "Google sign in failed = " + e);
                    e.printStackTrace();
                    // ...
                }
            }

    }


    /**
     * <p>It is used to handle Facebook access token</p>
     *
     * @param token
     */
    private void handleFacebookAccessToken(AccessToken token) {
        Utility.Logger(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userObject's information
                            Utility.Logger(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Utility.Logger(TAG, "Name = " + user.getDisplayName() + " Email = " + user.getEmail()
                                    + " Picture = " + user.getPhotoUrl().toString() + " UID = " + user.getUid());

                            showPhoneAuthenticationSheet(OnBoarding.this, user, Constant.LoginType.FACEBOOK_LOGIN);

                        } else {
                            // If sign in fails, display a message to the userObject.
                            Utility.Logger(TAG, "signInWithCredential:failure " + task.getException());


                        }

                        // ...
                    }
                });
    }


    /**
     * <p>It is used to authenticate Google Sign In</p>
     *
     * @param acct
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Utility.Logger(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userObject's information
                            Utility.Logger(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Utility.Logger(TAG, "Name = " + user.getDisplayName() + " Picture = " + user.getPhotoUrl()
                                    + " Email = " + user.getEmail() + " Phone = " + user.getPhoneNumber());
                            showPhoneAuthenticationSheet(OnBoarding.this, user, Constant.LoginType.GOOGLE_LOGIN);

                        } else {
                            // If sign in fails, display a message to the userObject.
                            Utility.Logger(TAG, "signInWithCredential:failure " + task.getException());

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Utility.Logger(TAG, "facebook:onSuccess : " + loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Utility.Logger(TAG, "facebook:onCancel");
        // ...
    }

    @Override
    public void onError(FacebookException error) {
        Utility.Logger(TAG, "facebook:onError = " + error);
        // ...
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {
            if (requestObject.getConnection() == Constant.CONNECTION.SOCIAL_LOGIN) {

                DataObject dataObject = (DataObject) data;

                management.savePreferences(new PrefObject()
                        .setLogin(true)
                        .setSaveLogin(true));

                management.savePreferences(new PrefObject()
                        .setSaveUserCredential(true)
                        .setLoginType(dataObject.getLogin_type())
                        .setUserId(dataObject.getUser_id())
                        .setFirstName(dataObject.getUser_fName())
                        .setLastName(dataObject.getUser_lName())
                        .setUserPhone(dataObject.getPhone())
                        .setUserPassword(dataObject.getUser_password())
                        .setUserEmail(dataObject.getUser_email())
                        .setPictureUrl(dataObject.getUser_picture()));

                if (dataObject.getUserFavourite().size() > 0) {
                    for (int i = 0; i < dataObject.getUserFavourite().size(); i++) {
                        FavouriteList favouriteList = dataObject.getUserFavourite().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FAVOURITES)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setUser_id(favouriteList.getUserId())
                                        .setObject_id(favouriteList.getRestaurantId())));
                    }

                }


                //Send request to Server for retrieving
                // Specific UserObject Favourites into Local Db

                /*management.sendRequestToServer(new RequestObject()
                        .setJson(getJson(dataObject.getUserId()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.USER_DATA)
                        .setConnectionCallback(null));*/

                finish();

            }
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }


    /**
     * <p>It is used to send request to server for userObject registration
     * in case of social login</p>
     */
    private void sendServerRequest(Constant.CONNECTION connection, String json) {

        management.sendRequestToServer(new RequestObject()
                .setJson(json)
                .setLoadingText(Utility.getStringFromRes(this, R.string.loading))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(connection)
                .setConnectionCallback(this));

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getRegisterJson(String userName, String phone, String userEmail, String userUID, String picture, String loginType) {
        String json = "";

        String[] name = Utility.splitingBySpace(userName);
        String fName = name[0];
        String lName = name.length > 1 ? name[1] : name[0];

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "register");
            jsonObject.accumulate("first_name", fName);
            jsonObject.accumulate("last_name", lName);
            jsonObject.accumulate("email", userEmail);
            jsonObject.accumulate("phone", phone);
            jsonObject.accumulate("uid", userUID);
            jsonObject.accumulate("picture", picture);
            jsonObject.accumulate("userType", loginType);
            jsonObject.accumulate("device_id", Constant.Credentials.DEVICE_TOKEN);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.extraData("JSON", json);
        return json;

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String userId) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "user_data");
            jsonObject.accumulate("user_id", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showPhoneAuthenticationSheet(final Context context, final FirebaseUser user, final String loginType) {
        final View view = getLayoutInflater().inflate(R.layout.phone_authencitation_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final EditText editPhoneNo = (EditText) view.findViewById(R.id.edit_phone_no);
        LinearLayout layoutDone = (LinearLayout) view.findViewById(R.id.layout_done);
        final TextView txtDone = (TextView) view.findViewById(R.id.txt_done);
        final GeometricProgressView progressBar = (GeometricProgressView) view.findViewById(R.id.progress_bar);
        final boolean[] isCodeSent = new boolean[1];
        isCodeSent[0] = false;
        final String[] firebaseVerificationId = {null};
        final String[] phoneNo = new String[1];

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = null;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     userObject action.
                Utility.Logger(TAG, "onVerificationCompleted:" + credential);

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                String requestJson = getRegisterJson(user.getDisplayName(), phoneNo[0], user.getEmail(), user.getUid(), user.getPhotoUrl().toString(), loginType);
                sendServerRequest(Constant.CONNECTION.SOCIAL_LOGIN, requestJson);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Utility.Logger(TAG, "onVerificationFailed " + e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the userObject to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Utility.Logger(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later

                firebaseVerificationId[0] = verificationId;

                txtDone.setVisibility(View.VISIBLE);
                txtDone.setText(Utility.getStringFromRes(context, R.string.done));

                editPhoneNo.setText(null);
                editPhoneNo.setHint(Utility.getStringFromRes(context, R.string.verification_code));

                isCodeSent[0] = true;

                progressBar.setVisibility(View.GONE);

                // ...
            }
        };

        final PhoneAuthProvider.OnVerificationStateChangedCallbacks finalMCallbacks = mCallbacks;
        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNo[0] = editPhoneNo.getText().toString();
                String requestJson = getRegisterJson(user.getDisplayName(), phoneNo[0], user.getEmail(), user.getUid(), user.getPhotoUrl().toString(), loginType);

                txtDone.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (isCodeSent[0]) {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(firebaseVerificationId[0], phoneNo[0]);
                    signInWithPhoneAuthCredential(credential, bottomSheetDialog, requestJson);

                    return;

                }


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        editPhoneNo.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        OnBoarding.this,               // Activity (for callback binding)
                        finalMCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


    }


    /**
     * <p>It is used to login with Phone Authentication</p>
     *
     * @param credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final BottomSheetDialog bottomSheetDialog, final String requestJson) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userObject's information
                            Utility.Logger(TAG, "signInWithCredential:success");

                            if (bottomSheetDialog.isShowing())
                                bottomSheetDialog.dismiss();

                            sendServerRequest(Constant.CONNECTION.SOCIAL_LOGIN, requestJson);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Utility.Logger(TAG, "signInWithCredential:failure " + task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
