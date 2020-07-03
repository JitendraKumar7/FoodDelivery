package com.haris.meal4u.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.Base64Object;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ConnectionCallback, FacebookCallback<LoginResult> {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editFirstName;
    private EditText editLastName;
    private ImageView imageProfile;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editPassword;
    private TextView txtSignUp;
    private TextView txtLogin;
    private EditText editConfirmPassword;
    private boolean isPictureSelected;
    private String base64Picture;
    private Management management;
    private Bitmap selectedBitmap;
    private boolean isLoginRequired;
    private String TAG = SignUp.class.getSimpleName();
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        isLoginRequired = getIntent().getBooleanExtra(Constant.IntentKey.LOGIN_REQUIRED, false);
    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {



        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.create_account));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        editFirstName = (EditText) findViewById(R.id.edit_firstName);
        editLastName = (EditText) findViewById(R.id.edit_lastName);
        imageProfile = (ImageView) findViewById(R.id.image_profile);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        txtSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for userObject registration</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.SIGN_UP)
                .setConnectionCallback(this));

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "register");
            jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);
            jsonObject.accumulate("first_name", editFirstName.getText().toString());
            jsonObject.accumulate("last_name", editLastName.getText().toString());
            jsonObject.accumulate("phone", editPhone.getText().toString());
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("device_id", Constant.Credentials.DEVICE_TOKEN);
            jsonObject.accumulate("picture", base64Picture);


        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.extraData("JSON", json);
        return json;

    }


    @Override
    public void onClick(View v) {
        if (v == txtSignUp) {

            if (Utility.isEmptyString(editFirstName.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_first_name), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editLastName.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_last_name), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPhone.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_phone), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editConfirmPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }

            if (!isPictureSelected) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.select_picture), Toast.LENGTH_SHORT);
                return;
            }
            if (!(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_mis_match), Toast.LENGTH_SHORT);
                return;
            }
            base64Picture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

            showPhoneAuthenticationSheet(this,editPhone.getText().toString());

        }
        if (v == txtLogin) {

            if (isLoginRequired)
                startActivity(new Intent(this, Login.class));

            finish();
        }
        if (v == imageProfile) {
            onPictureSelector(this);
        }
        if (v == imageBack) {
            startActivity(new Intent(this, Login.class));
            finish();
        }

    }


    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layout_gallery = (LinearLayout) dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    /**
     * <P>It is used to initialize Camera for picture capture</P>
     */
    private void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    private void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(true));

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


           /*

            management.getDataFromDatabase(new DatabaseObject()
                    .setDbOperation(Constant.DB.INSERT)
                    .setTypeOperation(Constant.TYPE.REWARD)
                    .setDataObject(new DataObject()
                            .setCoin(dataObject.getCoin())
                            .setUserId(dataObject.getUserId())));*/


            finish();

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this, data, Toast.LENGTH_SHORT);
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
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedBitmap = photo;
            imageProfile.setImageBitmap(photo);
            isPictureSelected = true;
        }
        else if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageProfile.setImageURI(selectedImage);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            isPictureSelected = true;
        }
        else if (requestCode != Constant.RequestCode.GOOGLE_SIGN_IN_CODE)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        else {
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

                            showPhoneAuthenticationSheet(SignUp.this,editPhone.getText().toString());

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
                            showPhoneAuthenticationSheet(SignUp.this,editPhone.getText().toString());

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


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showPhoneAuthenticationSheet(final Context context,String phoneNo) {
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

        editPhoneNo.setText(phoneNo);

        final boolean[] isCodeSent = new boolean[1];

        isCodeSent[0] = false;
        final String[] firebaseVerificationId = {null};

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

                sendServerRequest();


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


                txtDone.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (isCodeSent[0]) {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(firebaseVerificationId[0], editPhoneNo.getText().toString());
                    signInWithPhoneAuthCredential(credential, bottomSheetDialog);

                    return;

                }


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        editPhoneNo.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        SignUp.this,               // Activity (for callback binding)
                        finalMCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


    }


    /**
     * <p>It is used to login with Phone Authentication</p>
     *
     * @param credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final BottomSheetDialog bottomSheetDialog) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userObject's information
                            Utility.Logger(TAG, "signInWithCredential:success");

                            if (bottomSheetDialog.isShowing())
                                bottomSheetDialog.dismiss();

                            sendServerRequest();

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