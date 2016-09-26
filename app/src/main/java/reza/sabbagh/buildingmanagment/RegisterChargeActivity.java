package reza.sabbagh.buildingmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterChargeActivity extends AppCompatActivity {

    TextView tv_registertitle,tv_billtype,tv_billcalctype,tv_billdate,tv_choosebilldate;
    MaterialEditText met_unitnumber,met_buildingnumber,met_billamount;
    RadioButton rb_water,rb_power,rb_gas,rb_nafari,rb_metrazhi;
    Button btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_charge);

        initiate();

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
    }

}
