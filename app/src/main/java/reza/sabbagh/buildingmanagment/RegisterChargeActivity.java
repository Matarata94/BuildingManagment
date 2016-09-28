package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterChargeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView tv_registertitle,tv_billtype,tv_billcalctype,tv_billdate,tv_choosebilldate;
    MaterialEditText met_unitnumber,met_buildingnumber,met_billamount;
    RadioButton rb_water,rb_power,rb_gas,rb_nafari,rb_metrazhi;
    Button btn_done;
    private Typeface bhomaFont;
    private database db;
    public static String resRegisterCharge="";
    private int count=0;
    private Timer tm;
    private ProgressDialog pd;
    private String link = FirstActivity.globalLink + "RegisterCharge.php",date="",requestType,unitnumber,oldunitnumber,buildingnumber,calculatetype,billtype,oldbilltype,billdate,billamount,oldbillamount,adminusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_register);

        initiate();

        rb_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_power.setChecked(false);
                rb_gas.setChecked(false);
            }
        });
        rb_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_water.setChecked(false);
                rb_gas.setChecked(false);
            }
        });
        rb_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_power.setChecked(false);
                rb_water.setChecked(false);
            }
        });
        rb_nafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_metrazhi.setChecked(false);
            }
        });
        rb_metrazhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_nafari.setChecked(false);
            }
        });
        tv_choosebilldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        RegisterChargeActivity.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfAllFill()) {
                    if (btn_done.getText().toString().equals("ثبت")) {
                        requestType = "insert";
                        unitnumber = met_unitnumber.getText().toString();
                        oldunitnumber = "a";
                        buildingnumber = met_buildingnumber.getText().toString();
                        if (rb_water.isChecked()) {
                            billtype = "water";
                        } else if (rb_power.isChecked()) {
                            billtype = "power";
                        } else if (rb_gas.isChecked()) {
                            billtype = "gas";
                        }
                        oldbilltype = "a";
                        if (rb_nafari.isChecked()) {
                            calculatetype = "nafari";
                        } else if (rb_metrazhi.isChecked()) {
                            calculatetype = "metrazhi";
                        }
                        billdate = tv_choosebilldate.getText().toString();
                        billamount = met_billamount.getText().toString();
                        oldbillamount = "a";
                        db.open();
                        adminusername = db.queryInfo(2);
                        db.close();
                        serverWorking(link, requestType, unitnumber, oldunitnumber, buildingnumber, billtype,oldbilltype, billdate, billamount,oldbillamount, calculatetype, adminusername);
                    } else if (btn_done.getText().toString().equals("ویرایش")) {
                        requestType = "update";
                        unitnumber = met_unitnumber.getText().toString();
                        buildingnumber = met_buildingnumber.getText().toString();
                        if (rb_water.isChecked()) {
                            billtype = "water";
                        } else if (rb_power.isChecked()) {
                            billtype = "power";
                        } else if (rb_gas.isChecked()) {
                            billtype = "gas";
                        }
                        if (rb_nafari.isChecked()) {
                            calculatetype = "nafari";
                        } else if (rb_metrazhi.isChecked()) {
                            calculatetype = "metrazhi";
                        }
                        billdate = tv_choosebilldate.getText().toString();
                        billamount = met_billamount.getText().toString();
                        db.open();
                        adminusername = db.queryInfo(2);
                        db.close();
                        serverWorking(link, requestType, unitnumber, oldunitnumber, buildingnumber, billtype,oldbilltype, billdate, billamount,oldbillamount, calculatetype, adminusername);
                    }
                }
            }
        });
    }

    private void initiate(){
        tv_registertitle = (TextView) findViewById(R.id.RegisterChargeActivity_tv_registerTitle);
        tv_billtype = (TextView) findViewById(R.id.RegisterChargeActivity_tv_‌BillType);
        tv_billcalctype = (TextView) findViewById(R.id.RegisterChargeActivity_tv_‌BillCalcType);
        tv_billdate = (TextView) findViewById(R.id.RegisterChargeActivity_tv_‌BillDate);
        tv_choosebilldate = (TextView) findViewById(R.id.RegisterChargeActivity_tv_‌ChooseBillDate);
        met_unitnumber = (MaterialEditText) findViewById(R.id.RegisterChargeActivity_Text_UnitNumber);
        met_buildingnumber = (MaterialEditText) findViewById(R.id.RegisterChargeActivity_Text_BuildingNumber);
        met_billamount = (MaterialEditText) findViewById(R.id.RegisterChargeActivity_Text_BillAmount);
        rb_water = (RadioButton) findViewById(R.id.RegisterChargeActivity_rb_water);
        rb_power = (RadioButton) findViewById(R.id.RegisterChargeActivity_rb_power);
        rb_gas = (RadioButton) findViewById(R.id.RegisterChargeActivity_rb_gas);
        rb_nafari = (RadioButton) findViewById(R.id.RegisterChargeActivity_rb_nafari);
        rb_metrazhi = (RadioButton) findViewById(R.id.RegisterChargeActivity_rb_metrazhi);
        btn_done = (Button) findViewById(R.id.RegisterChargeActivity_btn_done);
        bhomaFont = Typeface.createFromAsset(getAssets(),"BHoma.ttf");
        tv_registertitle.setTypeface(bhomaFont);
        tv_billtype.setTypeface(bhomaFont);
        tv_billcalctype.setTypeface(bhomaFont);
        tv_billdate.setTypeface(bhomaFont);
        tv_choosebilldate.setTypeface(bhomaFont);
        met_unitnumber.setTypeface(bhomaFont);
        met_buildingnumber.setTypeface(bhomaFont);
        met_billamount.setTypeface(bhomaFont);
        rb_water.setTypeface(bhomaFont);
        rb_power.setTypeface(bhomaFont);
        rb_gas.setTypeface(bhomaFont);
        rb_nafari.setTypeface(bhomaFont);
        rb_metrazhi.setTypeface(bhomaFont);
        btn_done.setTypeface(bhomaFont);
        db = new database(this);

        if(getIntent().getExtras() != null) {
            String[] arrayReceived = getIntent().getExtras().getStringArray("keyCharge");
            if (arrayReceived[7].equals("add_bill")) {
                btn_done.setText("ثبت");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
            } else if (arrayReceived[7].equals("edit_bill")) {
                btn_done.setText("ویرایش");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
                met_unitnumber.setText(arrayReceived[1]);
                oldunitnumber = arrayReceived[1];
                met_buildingnumber.setText(arrayReceived[0]);
                if(arrayReceived[2].equals("water")){
                    rb_water.setChecked(true);
                    rb_power.setChecked(false);
                    rb_gas.setChecked(false);
                    oldbilltype = "water";
                }else if(arrayReceived[2].equals("power")){
                    rb_water.setChecked(false);
                    rb_power.setChecked(true);
                    rb_gas.setChecked(false);
                    oldbilltype = "power";
                }else if(arrayReceived[2].equals("gas")){
                    rb_water.setChecked(false);
                    rb_power.setChecked(false);
                    rb_gas.setChecked(true);
                    oldbilltype = "gas";
                }
                met_billamount.setText(arrayReceived[4]);
                oldbillamount = arrayReceived[4];
                if(arrayReceived[5].equals("nafari")){
                    rb_nafari.setChecked(true);
                    rb_metrazhi.setChecked(false);
                }else if(arrayReceived[5].equals("metrazhi")){
                    rb_nafari.setChecked(false);
                    rb_metrazhi.setChecked(true);
                }
                tv_choosebilldate.setText(arrayReceived[3]);
                met_unitnumber.requestFocus();
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tv_choosebilldate.setText(date);
    }

    private boolean checkIfAllFill(){
        if(met_unitnumber.getText().toString().equals("") |
                met_buildingnumber.getText().equals("") |
                met_billamount.getText().equals("") |
                date.equals("")){
            Toast.makeText(RegisterChargeActivity.this, "لطفا تمام کادرها را پر نمایید!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private void serverWorking(String link, String requestType, String unitnumber,String oldunitnumber, String buildingnumber, String billtype
            , String oldbilltype, String billdate, String billamount, String oldbillamount, String calculatetype,final String adminusername){
        new ServerConnectorRegisterCharge(link,requestType,unitnumber,oldunitnumber,buildingnumber,billtype,oldbilltype,billdate,billamount,oldbillamount,calculatetype,adminusername).execute();
        pd = new ProgressDialog(RegisterChargeActivity.this);
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
                        }else if(resRegisterCharge.contains("bill founded")) {
                            tm.cancel();
                            pd.cancel();
                            resRegisterCharge = "";
                            Toast.makeText(getApplicationContext(), "قبضی با این مشخصات قبلا ثبت گردیده است!", Toast.LENGTH_LONG).show();
                        }else if(resRegisterCharge.equals("add fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterCharge = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت نام. لطفا زمان دیگری امتحان نمایید!",Toast.LENGTH_LONG).show();
                        }else if(resRegisterCharge.equals("added")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterCharge = "";
                            Toast.makeText(getApplicationContext(),"انجام شد!",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterChargeActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }else if(resRegisterCharge.equals("update fail")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterCharge = "";
                            Toast.makeText(getApplicationContext(),"خطا در ثبت اطلاعات. لطفا در زمان دیگری امتحان نمایید!",Toast.LENGTH_LONG).show();
                        }else if(resRegisterCharge.equals("updated")){
                            tm.cancel();
                            pd.cancel();
                            resRegisterCharge = "";
                            Toast.makeText(getApplicationContext(),"انجام شد!",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterChargeActivity.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

}
