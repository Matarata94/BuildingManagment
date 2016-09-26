package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Timer;

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
    private String link = FirstActivity.globalLink + "RegisterCharge.php";

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
            if (arrayReceived[6].equals("add_bill")) {
                btn_done.setText("ثبت");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
            } else if (arrayReceived[6].equals("edit_bill")) {
                btn_done.setText("ویرایش");
                btn_done.setTextColor(Color.parseColor("#ffffff"));
                /*et_unitNUmber.setText(arrayReceived[0]);
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
                }*/
                met_unitnumber.requestFocus();
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tv_choosebilldate.setText(date);
    }

}
