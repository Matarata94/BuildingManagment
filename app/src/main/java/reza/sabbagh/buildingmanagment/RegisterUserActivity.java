package reza.sabbagh.buildingmanagment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.w3c.dom.Text;

public class RegisterUserActivity extends AppCompatActivity {

    private Shimmer shimmer;
    private ShimmerTextView Register_shtv_alreadyRegistered;
    private Button ButtonRegistration;
    private Typeface bhomaFont;
    private MaterialEditText text_Fname,text_Lname,text_namekarbari,Text_Mobaile,Text_NumberBuilding,Text_NumberOfUnit,Text_Pass,RepeatPass;
    private View bottomView;
    private TextView tv_registerTitle,tv_registerAs,tv_registerAsAdmin,tv_registerAsUser;
    private RadioButton rb_registerAsAdmin,rb_registerAsUser;

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
            }
        });
        ButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Text_Mobaile =(MaterialEditText) findViewById(R.id.RegisterActivity_Text_PhoneNum);
        Text_NumberBuilding =(MaterialEditText) findViewById(R.id.RegisterActivity_Text_NumberBuilding);
        Text_NumberOfUnit=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_NumberOfUnit);
        ButtonRegistration=(Button) findViewById(R.id.RegisterActivity_btn_register);
        Text_Pass=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_Pass);
        RepeatPass=(MaterialEditText) findViewById(R.id.RegisterActivity_Text_RepeatPass);
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
        Text_Mobaile.setTypeface(bhomaFont);
        tv_registerAs.setTypeface(bhomaFont);
        tv_registerAsAdmin.setTypeface(bhomaFont);
        tv_registerAsUser.setTypeface(bhomaFont);
        Text_NumberBuilding.setTypeface(bhomaFont);
        Text_NumberOfUnit.setTypeface(bhomaFont);
        Register_shtv_alreadyRegistered.setTypeface(bhomaFont);
        ButtonRegistration.setTypeface(bhomaFont);
        Text_Pass.setTypeface(bhomaFont);
        RepeatPass.setTypeface(bhomaFont);

        if(getIntent().getExtras() != null){
            String bundle = getIntent().getExtras().getString("key1");
            if(bundle.equals("edit")){
                ButtonRegistration.setText("ویرایش");
                Register_shtv_alreadyRegistered.setVisibility(View.INVISIBLE);
                bottomView.setVisibility(View.GONE);
            }
        }
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
