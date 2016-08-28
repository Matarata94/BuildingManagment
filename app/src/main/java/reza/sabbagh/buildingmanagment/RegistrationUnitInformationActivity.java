package reza.sabbagh.buildingmanagment;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

public class RegistrationUnitInformationActivity extends AppCompatActivity {
    private Typeface Dastnevis;
    private MaterialEditText Text_NumberOfUnit,Text_NumberBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_unit_information);


        Dastnevis =Typeface.createFromAsset(getAssets(),"DastNevis.otf");
        Text_NumberOfUnit =(MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_NumberOfUnit);
        Text_NumberBuilding=(MaterialEditText) findViewById(R.id.RegisterUnitActivity_Text_NumberBuilding);

    }
}
