package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private Button Button_Login;
    private Typeface bhomaFont;
    private MaterialEditText Text_UserName,Text_Password;
    private CheckBox Login_checkBox;
    private TextView tv_register;
    private database db;
    public static String resLogin ="";
    private int count=0;
    private Timer tm;
    private ProgressDialog pd;
    private String link = FirstActivity.globalLink + "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Initiate();

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Text_UserName.getText().toString().equals("") | Text_Password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "لطفا تمام فیلدها را پر کنید.", Toast.LENGTH_LONG).show();
                }else{
                    Login(link,Text_UserName.getText().toString(),RegisterUserActivity.md5(Text_Password.getText().toString()));
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginActivity.this,RegisterUserActivity.class);
                startActivity(in);
                finish();
            }
        });


    }

    private void Initiate(){
        Button_Login =(Button) findViewById(R.id.LoginActivity_Button_Login);
        bhomaFont = Typeface.createFromAsset(getAssets(),"BHoma.ttf");
        Text_UserName =(MaterialEditText) findViewById(R.id.LoginActivity_Text_UserName);
        Text_Password =(MaterialEditText) findViewById(R.id.LoginActivity_Text_Password);
        Login_checkBox=(CheckBox) findViewById(R.id.Login_Activity_checkBox);
        tv_register = (TextView) findViewById(R.id.LoginActivity_tv_register);
        Text_UserName.setTypeface(bhomaFont);
        Text_Password.setTypeface(bhomaFont);
        Login_checkBox.setTypeface(bhomaFont);
        Button_Login.setTypeface(bhomaFont);
        Login_checkBox.setTypeface(bhomaFont);
        tv_register.setTypeface(bhomaFont);
        db = new database(this);
    }

    private void Login(String link, final String username, String password){

        new ServerConnectorLogin(link,username,password).execute();
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Loging in...");
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
                        }else if(resLogin.contains("isAdmin")){
                            tm.cancel();
                            pd.cancel();
                            if(!Login_checkBox.isChecked()){
                                resLogin = "";
                                db.open();
                                db.updateInfo("Username",username);
                                db.close();
                                Intent in = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(in);
                                finish();
                            }else if(Login_checkBox.isChecked()){
                                db.open();
                                db.updateInfo("Registered","yes");
                                db.updateInfo("Username",username);
                                db.close();
                                resLogin = "";
                                Intent in = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(in);
                                finish();
                            }
                        }else if(resLogin.contains("isUser")){
                            //Should go to his panel (panle not created)
                            tm.cancel();
                            pd.cancel();
                            if(!Login_checkBox.isChecked()){
                                resLogin = "";
                                Intent in = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(in);
                                finish();
                            }else if(Login_checkBox.isChecked()){
                                db.open();
                                db.updateInfo("Registered","yes");
                                db.updateInfo("Username",username);
                                //AdminUsername not inserted here.should be insert if needed in future!
                                db.close();
                                resLogin = "";
                                Intent in = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(in);
                                finish();
                            }
                        }else if(resLogin.equals("not admin-not user")){
                            tm.cancel();
                            pd.cancel();
                            resLogin = "";
                            Toast.makeText(getApplicationContext(),"نام کاربری یا رمز عبور اشتباه است!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }, 1, 1000);

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
