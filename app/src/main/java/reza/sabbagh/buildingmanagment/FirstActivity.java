package reza.sabbagh.buildingmanagment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class FirstActivity extends AppCompatActivity {

    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private database db;
    public static String globalLink="http://192.168.1.3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        db = new database(this);
        db.databasecreate();

        if(isNetworkAvailable()){
            new CountDownTimer(100,1000){
                @Override
                public void onTick(long l) {
                }
                @Override
                public void onFinish() {
                    db.open();
                    if(db.queryInfo(1).equals("yes")){
                        Intent in = new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(in);
                        db.close();
                        finish();
                    }else if(db.queryInfo(1).equals("no")){
                        Intent in = new Intent(FirstActivity.this,LoginActivity.class);
                        startActivity(in);
                        db.close();
                        finish();
                    }
                }
            }.start();
        }else{
            AlertDialog("خطا در اتصال به اینترنت","لطفا از اتصال اینترنت خود اطمینان حاصل فرمایید سپس دکمه تلاش مجدد را بفشارید.","تلاش مجدد","خروج");
        }

    }

    //Method for cheking intenet access
    private boolean isNetworkAvailable() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void AlertDialog(String title,String content,String posText,String negText){
        new MaterialDialog.Builder(this)
                .title(title)
                .content(content)
                .positiveText(posText)
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent in = new Intent(FirstActivity.this,FirstActivity.class);
                        startActivity(in);
                        finish();
                    }
                })
                .negativeText(negText)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        System.exit(0);
                    }
                })
                .show();
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
