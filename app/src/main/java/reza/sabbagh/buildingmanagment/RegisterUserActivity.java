package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterUserActivity extends AppCompatActivity {

    private Shimmer shimmer;
    private ShimmerTextView Register_shtv_alreadyRegistered;
    private Button ButtonRegistration;
    private Typeface bhomaFont;
    private MaterialEditText text_Fname,text_Lname,text_namekarbari,Text_Mobile,Text_NumberBuilding,Text_NumberOfUnit,Text_Pass,RepeatPass,text_email,text_adminUsername,text_HomeNum;
    private View bottomView;
    private TextView tv_registerTitle,tv_registerAs,tv_registerAsAdmin,tv_registerAsUser;
    private RadioButton rb_registerAsAdmin,rb_registerAsUser;
    private database db;
    public static String resRegister ="";
    private int count=0;
    private Timer tm;
    private ProgressDialog pd;
    private String link = FirstActivity.globalLink + "register.php",requestType,registerAs="admin",username,password,fname,lname,phonenumber,homenumber
    ,email,buildingnumber,unitnumber,oldunitnumber,adminusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Initiate();

        Register_shtv_alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RegisterUserActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        ButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ButtonRegistration.getText().toString().equals("ثبت")){
                    if(checkIfAllFill("register")){
                        requestType = "insert";
                        if(rb_registerAsAdmin.isChecked()){
                            registerAs = "admin";
                        }else if(rb_registerAsUser.isChecked()){
                            registerAs = "user";
                        }
                        username = text_namekarbari.getText().toString();
                        password = md5(Text_Pass.getText().toString());
                        fname = text_Fname.getText().toString();
                        lname = text_Lname.getText().toString();
                        phonenumber = Text_Mobile.getText().toString();
                        homenumber = text_HomeNum.getText().toString();
                        email = text_email.getText().toString();
                        buildingnumber = Text_NumberBuilding.getText().toString();
                        unitnumber = Text_NumberOfUnit.getText().toString();
                        oldunitnumber = "";
                        adminusername = text_adminUsername.getText().toString();
                        Register(link,requestType,registerAs,username,password,fname,lname,phonenumber,homenumber,email,buildingnumber,unitnumber,oldunitnumber,adminusername);
                    }
                }
                else if(ButtonRegistration.getText().toString().equals("افزودن کاربر")) {
                    if(checkIfAllFill("add")){
                        requestType = "insert";
                        registerAs = "user";
                        username = text_namekarbari.getText().toString();
                        password = md5(Text_Pass.getText().toString());
                        fname = text_Fname.getText().toString();
                        lname = text_Lname.getText().toString();
                        phonenumber = Text_Mobile.getText().toString();
                        homenumber = text_HomeNum.getText().toString();
                        email = text_email.getText().toString();
                        buildingnumber = Text_NumberBuilding.getText().toString();
                        unitnumber = Text_NumberOfUnit.getText().toString();
                        oldunitnumber = "";
                        db.open();
                        adminusername = db.queryInfo(2);
                        db.close();
                        Register(link,requestType,registerAs,username,password,fname,lname,phonenumber,homenumber,email,buildingnumber,unitnumber,oldunitnumber,adminusername);
                    }
                }else if(ButtonRegistration.getText().toString().equals("اعمال تغییرات")) {
                    if(checkIfAllFill("edit")){
                        requestType = "update";
                        registerAs = "";
                        username = text_namekarbari.getText().toString();
                        password = md5(Text_Pass.getText().toString());
                        fname = text_Fname.getText().toString();
                        lname = text_Lname.getText().toString();
                        phonenumber = Text_Mobile.getText().toString();
                        homenumber = text_HomeNum.getText().toString();
                        email = text_email.getText().toString();
                        buildingnumber = Text_NumberBuilding.getText().toString();
                        unitnumber = Text_NumberOfUnit.getText().toString();
                        db.open();
                        adminusername = db.queryInfo(2);
                        db.close();
                        Register(link,requestType,registerAs,username,password,fname,lname,phonenumber,homenumber,email,buildingnumber,unitnumber,oldunitnumber,adminusername);
                    }
                }
            }
        });
        rb_registerAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_registerAsUser.setChecked(false);
                if(text_adminUsername.getVisibility() == View.VISIBLE){
                    text_adminUsername.setVisibility(View.GONE);
                }
            }
        });
        rb_registerAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_registerAsAdmin.setChecked(false);
                if(text_adminUsername.getVisibility() == View.GONE){
                    text_adminUsername.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void Initiate(){
        Register_shtv_alreadyRegistered =(ShimmerTextView) findViewById(R.id.RegisterActivity_shtv_alreadyRegistered);
        shimmer = new Shimmer();
        shimmer.setDuration(1000)
                .setStartDelay(100)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(Register_shtv_alreadyRegistered);
        bhomaFont = Typeface.createFromAsset(getAssets(),"BHoma.ttf");
        tv_registerTitle = (TextView) findViewById(R.id.RegisterActivity_tv_registerTitle);
        text_Fname = (MaterialEditText) findViewById(R.id.RegisterActivity_Text_Fname);
        text_Lname = (MaterialEditText) findViewById(R.id.RegisterActivity_Text_Lname);
        text_namekarbari = (MaterialEditText) findViewById(R.id.RegisterActivity_Text_namekarbari);
        Text_Mobile =(MaterialEditText) findViewById(R.id.RegisterActivity_Text_PhoneNum);
        Text_NumberBuilding =(MaterialEditText) findViewById(R.id.RegisterActivity_Text_NumberBuilding);
        Text_NumberOfUnit=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_NumberOfUnit);
        ButtonRegistration=(Button) findViewById(R.id.RegisterActivity_btn_register);
        Text_Pass=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_Pass);
        RepeatPass=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_RepeatPass);
        text_HomeNum = (MaterialEditText) findViewById(R.id.RegisterActivity_Text_HomeNum);
        text_email=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_Email);
        text_adminUsername=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_AdminUsername);
        tv_registerAs = (TextView) findViewById(R.id.RegisterActivity_tv_registerAs);
        tv_registerAsAdmin = (TextView) findViewById(R.id.RegisterActivity_tv_registerAsAdmin);
        tv_registerAsUser = (TextView) findViewById(R.id.RegisterActivity_tv_registerAsUser);
        rb_registerAsAdmin = (RadioButton) findViewById(R.id.RegisterActivity_rb_registerAsAdmin);
        rb_registerAsUser = (RadioButton) findViewById(R.id.RegisterActivity_rb_registerAsUser);
        bottomView = (View) findViewById(R.id.RegisterActivity_view1);
        tv_registerTitle.setTypeface(bhomaFont);
        text_Fname.setTypeface(bhomaFont);
        text_Lname.setTypeface(bhomaFont);
        text_namekarbari.setTypeface(bhomaFont);
        Text_Mobile.setTypeface(bhomaFont);
        tv_registerAs.setTypeface(bhomaFont);
        tv_registerAsAdmin.setTypeface(bhomaFont);
        tv_registerAsUser.setTypeface(bhomaFont);
        Text_NumberBuilding.setTypeface(bhomaFont);
        Text_NumberOfUnit.setTypeface(bhomaFont);
        Register_shtv_alreadyRegistered.setTypeface(bhomaFont);
        ButtonRegistration.setTypeface(bhomaFont);
        Text_Pass.setTypeface(bhomaFont);
        RepeatPass.setTypeface(bhomaFont);
        text_adminUsername.setTypeface(bhomaFont);
        text_email.setTypeface(bhomaFont);
        text_HomeNum.setTypeface(bhomaFont);
        db = new database(this);

        if(getIntent().getExtras() != null){
            String stringRecieved = getIntent().getExtras().getString("key1");
            String[] arrayReceived = getIntent().getExtras().getStringArray("keyData");
            if(stringRecieved.equals("add_user")){
                ButtonRegistration.setText("افزودن کاربر");
                Register_shtv_alreadyRegistered.setVisibility(View.INVISIBLE);
                tv_registerAs.setVisibility(View.GONE);
                tv_registerAsAdmin.setVisibility(View.GONE);
                tv_registerAsUser.setVisibility(View.GONE);
                tv_registerTitle.setText("ثبت کاربر جدید");
                rb_registerAsAdmin.setVisibility(View.INVISIBLE);
                rb_registerAsUser.setVisibility(View.GONE);
                bottomView.setVisibility(View.GONE);
            }else if(stringRecieved.equals("edit_user")){
                ButtonRegistration.setText("اعمال تغییرات");
                Register_shtv_alreadyRegistered.setVisibility(View.GONE);
                tv_registerAs.setVisibility(View.GONE);
                tv_registerAsAdmin.setVisibility(View.GONE);
                tv_registerAsUser.setVisibility(View.GONE);
                tv_registerTitle.setText("ویرایش اطلاعات کاربر");
                rb_registerAsAdmin.setVisibility(View.INVISIBLE);
                rb_registerAsUser.setVisibility(View.GONE);
                Text_Pass.setVisibility(View.GONE);
                RepeatPass.setVisibility(View.GONE);
                bottomView.setVisibility(View.GONE);
                text_namekarbari.setVisibility(View.GONE);
                text_Fname.setText(arrayReceived[0]);
                text_Lname.setText(arrayReceived[1]);
                Text_Mobile.setText(arrayReceived[2]);
                text_HomeNum.setText(arrayReceived[3]);
                text_email.setText(arrayReceived[4]);
                Text_NumberBuilding.setText(arrayReceived[5]);
                Text_NumberOfUnit.setText(arrayReceived[6]);
                text_Fname.requestFocus();
                oldunitnumber = Text_NumberOfUnit.getText().toString();
            }
        }
    }

    private void Register(String link, String requestType, String registerAs, final String username, String password, String fname, String lname, String phonenumber,
                          String homenumber, String email, String buildingnumber, String unitnumber, String oldunitnumber, final String adminusername){
        new ServerConnectorRegister(link,requestType,registerAs,username,password,fname,lname,phonenumber,homenumber,email,buildingnumber,unitnumber,oldunitnumber,adminusername).execute();
        pd = new ProgressDialog(RegisterUserActivity.this);
        pd.setMessage("Registering...");
        pd.setCancelable(false);
        pd.show();

        tm =new Timer();
        tm.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                        count++;
                        if(count == 6){
                            pd.cancel();
                            tm.cancel();
                            Toast.makeText(getApplicationContext(),"برنامه قادر به برقراری ارتباط با سرور نیست. لطفا مجددا تلاش نمایید.",Toast.LENGTH_LONG).show();
                            count = 0;
                        }else if(resRegister.contains("username founded")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            Toast.makeText(getApplicationContext(),"این نام کاربری قبلا ثبت گردیده است!",Toast.LENGTH_LONG).show();
                        }else if(resRegister.contains("email founded")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            Toast.makeText(getApplicationContext(),"این ایمیل قبلا ثبت گردیده است!",Toast.LENGTH_LONG).show();
                        }else if(resRegister.contains("adminUsername not founded")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            Toast.makeText(getApplicationContext(),"نام کاربری مدیر موجود نمی باشد!",Toast.LENGTH_LONG).show();
                        }else if(resRegister.equals("register fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت نام. لطفا زمان دیگری امتحان نمایید!",Toast.LENGTH_LONG).show();
                        }else if(resRegister.equals("registered")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            if(ButtonRegistration.getText().toString().equals("ثبت")){
                                db.open();
                                db.updateInfo("Registered","yes");
                                db.updateInfo("Username",username);
                                db.updateInfo("AdminUsername",adminusername);
                                db.close();
                            }
                            Toast.makeText(getApplicationContext(),"انجام شد!",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterUserActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }else if(resRegister.equals("update fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegister = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت اطلاعات",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean checkIfAllFill(String type){
        if(type.equals("register")){
            if (text_Fname.getText().toString().equals("") |
                    text_Lname.getText().toString().equals("") |
                    text_namekarbari.getText().toString().equals("") |
                    Text_Mobile.getText().toString().equals("") |
                    text_HomeNum.getText().toString().equals("") |
                    Text_NumberBuilding.getText().toString().equals("") |
                    Text_NumberOfUnit.getText().toString().equals("") |
                    text_email.getText().toString().equals("") |
                    Text_Pass.getText().toString().equals("") |
                    RepeatPass.getText().toString().equals("")){
                Toast.makeText(RegisterUserActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_Fname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return  false;
            }else if(text_Lname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام خانوادگی باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_namekarbari.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام کاربری باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(Text_Mobile.length() < 11){
                Toast.makeText(RegisterUserActivity.this,"شماره همراه اشتباه است!", Toast.LENGTH_LONG).show();
                return false;
            }else if(Text_Pass.length() < 5){
                Toast.makeText(RegisterUserActivity.this,"پسورد باید بیش از پنج حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(!Text_Pass.getText().toString().equals(RepeatPass.getText().toString())){
                Toast.makeText(RegisterUserActivity.this,"تکرار پسورد با پسورد متفاوت است!", Toast.LENGTH_LONG).show();
                return false;
            }else if(rb_registerAsUser.isChecked() && text_adminUsername.getText().toString().equals("")){
                Toast.makeText(RegisterUserActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
                return false;
            }else{
                return true;
            }
        }else if(type.equals("add")){
            if (text_Fname.getText().toString().equals("") |
                    text_Lname.getText().toString().equals("") |
                    text_namekarbari.getText().toString().equals("") |
                    Text_Mobile.getText().toString().equals("") |
                    text_HomeNum.getText().toString().equals("") |
                    Text_NumberBuilding.getText().toString().equals("") |
                    Text_NumberOfUnit.getText().toString().equals("") |
                    text_email.getText().toString().equals("") |
                    Text_Pass.getText().toString().equals("") |
                    RepeatPass.getText().toString().equals("")){
                Toast.makeText(RegisterUserActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_Fname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_Lname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام خانوادگی باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_namekarbari.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام کاربری باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(Text_Mobile.length() < 11){
                Toast.makeText(RegisterUserActivity.this,"شماره همراه اشتباه است!", Toast.LENGTH_LONG).show();
                return false;
            }else if(Text_Pass.length() < 5){
                Toast.makeText(RegisterUserActivity.this,"پسورد باید بیش از پنج حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(!Text_Pass.getText().toString().equals(RepeatPass.getText().toString())){
                Toast.makeText(RegisterUserActivity.this,"تکرار پسورد با پسورد متفاوت است!", Toast.LENGTH_LONG).show();
                return false;
            }else{
                return true;
            }
        }else if(type.equals("edit")){
            if (text_Fname.getText().toString().equals("") |
                    text_Lname.getText().toString().equals("") |
                    Text_Mobile.getText().toString().equals("") |
                    text_HomeNum.getText().toString().equals("") |
                    Text_NumberBuilding.getText().toString().equals("") |
                    Text_NumberOfUnit.getText().toString().equals("") |
                    text_email.getText().toString().equals("")){
                Toast.makeText(RegisterUserActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_Fname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(text_Lname.length() < 3){
                Toast.makeText(RegisterUserActivity.this,"نام خانوادگی باید بیش از سه حرف باشد!", Toast.LENGTH_LONG).show();
                return false;
            }else if(Text_Mobile.length() < 11){
                Toast.makeText(RegisterUserActivity.this,"شماره همراه اشتباه است!", Toast.LENGTH_LONG).show();
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
