package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterUnitActivity extends AppCompatActivity {

    private TextView tv_registerTitle,tv_staticChargeState,tv_yes,tv_no;
    private MaterialEditText et_unitNUmber,et_buildingNumber,et_ownerName,et_residentName,et_floorNumber,et_areaSize,et_residenceDate,et_residentCount,et_postalCode,et_defaultChargeAmount;
    private Button btn_done;
    private RadioButton rb_yes,rb_no;
    private Typeface bhomaFont;
    private database db;
    public static String resRegisterUnit="";
    private int count=0;
    private Timer tm;
    private ProgressDialog pd;
    private String link = FirstActivity.globalLink + "RegisterUnit.php",requestType,unitnumber,oldunitnumber,buildingnumber,ownername,residentname,floornumber
            ,areasize,residencedate,numberofresidence,postalcode,chargedefaultamount,staticchargestate,adminusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_register);

        initiate();

        rb_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_no.setChecked(false);
            }
        });
        rb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_yes.setChecked(false);
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(checkIfAllFill()){
                if(btn_done.getText().toString().equals("ثبت")){
                    requestType = "insert";
                    unitnumber = et_unitNUmber.getText().toString();
                    oldunitnumber = "a";
                    buildingnumber = et_buildingNumber.getText().toString();
                    ownername = et_ownerName.getText().toString();
                    residentname = et_residentName.getText().toString();
                    floornumber = et_floorNumber.getText().toString();
                    areasize = et_areaSize.getText().toString();
                    residencedate = et_residenceDate.getText().toString();
                    numberofresidence = et_residentCount.getText().toString();
                    postalcode = et_postalCode.getText().toString();
                    chargedefaultamount = et_defaultChargeAmount.getText().toString();
                    if(rb_yes.isChecked()){
                        staticchargestate = "yes";
                    }else if(rb_no.isChecked()){
                        staticchargestate = "no";
                    }
                    db.open();
                    adminusername = db.queryInfo(2);
                    db.close();
                    serverWorking(link,requestType,unitnumber,oldunitnumber,buildingnumber,ownername,residentname,floornumber,areasize,residencedate,numberofresidence
                            ,postalcode,chargedefaultamount,staticchargestate,adminusername);
                }else if(btn_done.getText().toString().equals("ویرایش")){
                    requestType = "update";
                    unitnumber = et_unitNUmber.getText().toString();
                    buildingnumber = et_buildingNumber.getText().toString();
                    ownername = et_ownerName.getText().toString();
                    residentname = et_residentName.getText().toString();
                    floornumber = et_floorNumber.getText().toString();
                    areasize = et_areaSize.getText().toString();
                    residencedate = et_residenceDate.getText().toString();
                    numberofresidence = et_residentCount.getText().toString();
                    postalcode = et_postalCode.getText().toString();
                    chargedefaultamount = et_defaultChargeAmount.getText().toString();
                    if(rb_yes.isChecked()){
                        staticchargestate = "yes";
                    }else if(rb_no.isChecked()){
                        staticchargestate = "no";
                    }
                    db.open();
                    adminusername = db.queryInfo(2);
                    db.close();
                    serverWorking(link,requestType,unitnumber,oldunitnumber,buildingnumber,ownername,residentname,floornumber,areasize,residencedate,numberofresidence
                            ,postalcode,chargedefaultamount,staticchargestate,adminusername);
                }
            }
            }
        });
    }

    private void initiate(){
        tv_registerTitle = (TextView) findViewById(R.id.RegisterUnitActivity_tv_registerTitle);
        tv_staticChargeState = (TextView) findViewById(R.id.RegisterUnitActivity_tv_staticChargeState);
        tv_yes = (TextView) findViewById(R.id.RegisterUnitActivity_tv_yes);
        tv_no = (TextView) findViewById(R.id.RegisterUnitActivity_tv_no);
        et_unitNUmber = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_UnitNumber);
        et_buildingNumber = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_BuildingNumber);
        et_ownerName = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_Ownername);
        et_residentName = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_nameResident);
        et_floorNumber = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_Floor);
        et_areaSize = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_area);
        et_residenceDate = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_residentDate);
        et_residentCount = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_numberOfResident);
        et_postalCode = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_Postalcode);
        et_defaultChargeAmount = (MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_defaultChargeAmount);
        rb_yes = (RadioButton) findViewById(R.id.RegisterUnitActivity_rb_yes);
        rb_no = (RadioButton) findViewById(R.id.RegisterUnitActivity_rb_no);
        btn_done = (Button) findViewById(R.id.RegisterUnitActivity_Button_done);
        bhomaFont = Typeface.createFromAsset(getAssets(),"BHoma.ttf");
        tv_registerTitle.setTypeface(bhomaFont);
        tv_staticChargeState.setTypeface(bhomaFont);
        tv_yes.setTypeface(bhomaFont);
        tv_no.setTypeface(bhomaFont);
        et_unitNUmber.setTypeface(bhomaFont);
        et_buildingNumber.setTypeface(bhomaFont);
        et_ownerName.setTypeface(bhomaFont);
        et_residentName.setTypeface(bhomaFont);
        et_floorNumber.setTypeface(bhomaFont);
        et_areaSize.setTypeface(bhomaFont);
        et_residenceDate.setTypeface(bhomaFont);
        et_residentCount.setTypeface(bhomaFont);
        et_postalCode.setTypeface(bhomaFont);
        et_defaultChargeAmount.setTypeface(bhomaFont);
        btn_done.setTypeface(bhomaFont);
        db = new database(this);

        if(getIntent().getExtras() != null){
            String[] arrayReceived = getIntent().getExtras().getStringArray("key1");
            if(arrayReceived[11].equals("add_unit")){
                btn_done.setText("ثبت");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
            }else if(arrayReceived[11].equals("edit_unit")){
                btn_done.setText("ویرایش");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
                et_unitNUmber.setText(arrayReceived[0]);
                oldunitnumber = arrayReceived[0];
                et_buildingNumber.setText(arrayReceived[1]);
                et_ownerName.setText(arrayReceived[2]);
                et_residentName.setText(arrayReceived[3]);
                et_floorNumber.setText(arrayReceived[4]);
                et_areaSize.setText(arrayReceived[5]);
                et_residenceDate.setText(arrayReceived[6]);
                et_residentCount.setText(arrayReceived[7]);
                et_postalCode.setText(arrayReceived[8]);
                et_defaultChargeAmount.setText(arrayReceived[9]);
                if(arrayReceived[10].equals("yes")){
                    rb_yes.setChecked(true);
                    rb_no.setChecked(false);
                }else if(arrayReceived[10].equals("no")){
                    rb_yes.setChecked(false);
                    rb_no.setChecked(true);
                }
                et_unitNUmber.requestFocus();
            }
        }

    }

    private boolean checkIfAllFill(){
         if(et_unitNUmber.getText().toString().equals("") |
             et_buildingNumber.getText().equals("") |
             et_ownerName.getText().equals("") |
             et_ownerName.getText().equals("") |
             et_residentName.getText().equals("") |
             et_floorNumber.getText().equals("") |
             et_areaSize.getText().equals("") |
             et_residenceDate.getText().equals("") |
             et_residentCount.getText().equals("") |
             et_postalCode.getText().equals("") |
             et_defaultChargeAmount.getText().equals("")){
             Toast.makeText(RegisterUnitActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
             return false;
         }else if(et_ownerName.getText().length() < 6 | et_residentName.getText().length() < 6){
             Toast.makeText(RegisterUnitActivity.this,"نام و نام خانوادگی باید بیش از شش حرف باشد!", Toast.LENGTH_LONG).show();
             return false;
         }else if(et_postalCode.getText().length() != 11){
             Toast.makeText(RegisterUnitActivity.this,"کدپستی باید ۱۱ رقم باشد!", Toast.LENGTH_LONG).show();
             return false;
         }else{
             return true;
         }
     }

    private void serverWorking(String link, String requestType, String unitnumber,String oldunitnumber, String buildingnumber, String ownername, String residentname
            , String floornumber, String areasize, String residencedate, String numberofresidence, String postalcode, String chargedefaultamount
            , String staticchargestate, final String adminusername){
        new ServerConnectorRegisterUnit(link,requestType,unitnumber,oldunitnumber,buildingnumber,ownername,residentname,floornumber,areasize,residencedate
                ,numberofresidence,postalcode,chargedefaultamount,staticchargestate,adminusername).execute();
        pd = new ProgressDialog(RegisterUnitActivity.this);
        pd.setMessage("Loading...");
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
                        }else if(resRegisterUnit.contains("unit founded")) {
                            tm.cancel();
                            pd.cancel();
                            resRegisterUnit = "";
                            Toast.makeText(getApplicationContext(), "مشخصات این واحد قبلا ثبت گردیده است!", Toast.LENGTH_LONG).show();
                        }else if(resRegisterUnit.equals("add fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterUnit = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت نام. لطفا زمان دیگری امتحان نمایید!",Toast.LENGTH_LONG).show();
                        }else if(resRegisterUnit.equals("added")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterUnit = "";
                            Toast.makeText(getApplicationContext(),"انجام شد!",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterUnitActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }else if(resRegisterUnit.equals("update fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterUnit = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت اطلاعات. لطفا در زمان دیگری امتحان نمایید!",Toast.LENGTH_LONG).show();
                        }else if(resRegisterUnit.equals("updated")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterUnit = "";
                            Toast.makeText(getApplicationContext(),"انجام شد!",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterUnitActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

}
