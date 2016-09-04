package reza.sabbagh.buildingmanagment;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Timer;
import java.util.TimerTask;

public class BuildingInformationFragment extends Fragment {

    private View rootView;
    private Shimmer shimmer;
    private Button btn_insert_edit;
    private Typeface bhomaFont;
    private ShimmerTextView shmtv_numberBuilding,shmtv_typeBuilding,shmtv_nameBuilding,shmtv_numberOfUnit,shmtv_Plaque,shmtv_Adrress;
    private MaterialEditText text_numberbuiding,text_typebuilding,text_namebuilding,text_numberofunit,text_plaque,text_address;
    public static String resBuildingInfo ="";
    private int count=0;
    private Timer tm;
    private ProgressDialog pd;
    private database db;
    private String link = FirstActivity.globalLink + "BuildingInfo.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_building_information, container, false);

        initiate();

        String requesttype = "query";
        db.open();
        String badminusername = db.queryInfo(2);
        db.close();
        String bnumber = "s";
        String btype = "s";
        String bname = "s";
        String bunitscount = "s";
        String bplaque = "s";
        String baddress = "s";
        serverWorking(link,requesttype,badminusername,bnumber,btype,bname,bunitscount,bplaque,baddress);

        btn_insert_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_numberbuiding.getText().length() == 0 |
                    text_typebuilding.getText().length() == 0 |
                    text_namebuilding.getText().length() == 0 |
                    text_numberofunit.getText().length() == 0 |
                    text_plaque.getText().length() == 0 |
                    text_address.getText().length() == 0){
                    Toast.makeText(getContext(),"لطفا تمام کادرها را پر نمایید!",Toast.LENGTH_LONG).show();
                }else{
                    if(btn_insert_edit.getText().toString().equals("ثبت")){
                        String requesttype = "insert";
                        db.open();
                        String badminusername = db.queryInfo(2);
                        db.close();
                        String bnumber = text_numberbuiding.getText().toString();
                        String btype = text_typebuilding.getText().toString();
                        String bname = text_namebuilding.getText().toString();
                        String bunitscount = text_numberofunit.getText().toString();
                        String bplaque = text_plaque.getText().toString();
                        String baddress = text_address.getText().toString();
                        serverWorking(link,requesttype,badminusername,bnumber,btype,bname,bunitscount,bplaque,baddress);
                    }else if(btn_insert_edit.getText().toString().equals("ویرایش")){
                        enableET(text_numberbuiding,text_typebuilding,text_namebuilding,text_numberofunit,text_plaque,text_address);
                        btn_insert_edit.setText("اعمال");
                    }else if(btn_insert_edit.getText().toString().equals("اعمال")){
                        String requesttype = "update";
                        db.open();
                        String badminusername = db.queryInfo(2);
                        db.close();
                        String bnumber = text_numberbuiding.getText().toString();
                        String btype = text_typebuilding.getText().toString();
                        String bname = text_namebuilding.getText().toString();
                        String bunitscount = text_numberofunit.getText().toString();
                        String bplaque = text_plaque.getText().toString();
                        String baddress = text_address.getText().toString();
                        serverWorking(link,requesttype,badminusername,bnumber,btype,bname,bunitscount,bplaque,baddress);
                    }
                }
            }
        });

        return rootView;
    }

    private void serverWorking(String link, String requesttype, String badminusername, String bnumber, String btype, String bname,
                          String bunitscount, String bplaque, String baddress){
        new ServerConnectorBuildingInfo(link,requesttype,badminusername,bnumber,btype,bname,bunitscount,bplaque,baddress).execute();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();

        tm =new Timer();
        tm.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        count++;
                        if(count == 6){
                            pd.cancel();
                            tm.cancel();
                            Toast.makeText(getContext(),"برنامه قادر به برقراری ارتباط با سرور نیست. لطفا مجددا تلاش نمایید.",Toast.LENGTH_LONG).show();
                            count = 0;
                        }else if(resBuildingInfo.equals("no information")){
                            tm.cancel();
                            pd.cancel();
                            resBuildingInfo = "";
                            Toast.makeText(getContext(),"اطلاعاتی یافت نشد!",Toast.LENGTH_LONG).show();
                            count = 0;
                        }else if(resBuildingInfo.contains("founded")){
                            tm.cancel();
                            pd.cancel();
                            String field1 = resBuildingInfo.substring(resBuildingInfo.indexOf("!") + 1,resBuildingInfo.indexOf("@"));
                            String field2 = resBuildingInfo.substring(resBuildingInfo.indexOf("@") + 1,resBuildingInfo.indexOf("#"));
                            String field3 = resBuildingInfo.substring(resBuildingInfo.indexOf("#") + 1,resBuildingInfo.indexOf("$"));
                            String field4 = resBuildingInfo.substring(resBuildingInfo.indexOf("$") + 1,resBuildingInfo.indexOf("%"));
                            String field5 = resBuildingInfo.substring(resBuildingInfo.indexOf("%") + 1,resBuildingInfo.indexOf("^"));
                            String field6 = resBuildingInfo.substring(resBuildingInfo.indexOf("^") + 1);
                            text_numberbuiding.setText(field1);
                            text_typebuilding.setText(field2);
                            text_namebuilding.setText(field3);
                            text_numberofunit.setText(field4);
                            text_plaque.setText(field5);
                            text_address.setText(field6);
                            btn_insert_edit.setText("ویرایش");
                            disableET(text_numberbuiding,text_typebuilding,text_namebuilding,text_numberofunit,text_plaque,text_address);
                            resBuildingInfo = "";
                            count = 0;
                        }else if(resBuildingInfo.equals("insert fail")){
                            tm.cancel();
                            pd.cancel();
                            resBuildingInfo = "";
                            Toast.makeText(getContext(),"خطا در ثبت اطلاعات! لطفا زمان دیگری امتحان کنید!",Toast.LENGTH_LONG).show();
                            count = 0;
                        }else if(resBuildingInfo.equals("inserted")){
                            tm.cancel();
                            pd.cancel();
                            resBuildingInfo = "";
                            Toast.makeText(getContext(),"اطلاعات با موفقیت ثبت گردید!",Toast.LENGTH_LONG).show();
                            btn_insert_edit.setText("ویرایش");
                            count = 0;
                            disableET(text_numberbuiding,text_typebuilding,text_namebuilding,text_numberofunit,text_plaque,text_address);
                        }else if(resBuildingInfo.equals("update fail")){
                            tm.cancel();
                            pd.cancel();
                            resBuildingInfo = "";
                            Toast.makeText(getContext(),"خطا در ثبت اطلاعات! لطفا زمان دیگری امتحان کنید!",Toast.LENGTH_LONG).show();
                            count = 0;
                        }else if(resBuildingInfo.equals("updated")){
                            tm.cancel();
                            pd.cancel();
                            resBuildingInfo = "";
                            Toast.makeText(getContext(),"اطلاعات با موفقیت ثبت گردید!",Toast.LENGTH_LONG).show();
                            btn_insert_edit.setText("ویرایش");
                            disableET(text_numberbuiding,text_typebuilding,text_namebuilding,text_numberofunit,text_plaque,text_address);
                            count = 0;
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    private void disableET(MaterialEditText Et1,MaterialEditText Et2,MaterialEditText Et3,MaterialEditText Et4,MaterialEditText Et5,MaterialEditText Et6){
        MaterialEditText[] et = {Et1,Et2,Et3,Et4,Et5,Et6};
        for(int i=0;i < 6;i++){
            et[i].setCursorVisible(false);
            et[i].setLongClickable(false);
            et[i].setClickable(false);
            et[i].setFocusable(false);
            et[i].setSelected(false);
        }
    }

    private void enableET(MaterialEditText Et1,MaterialEditText Et2,MaterialEditText Et3,MaterialEditText Et4,MaterialEditText Et5,MaterialEditText Et6){
        MaterialEditText[] et = {Et1,Et2,Et3,Et4,Et5,Et6};
        for(int i=0;i < 6;i++){
            et[i].setCursorVisible(true);
            et[i].setLongClickable(true);
            et[i].setClickable(true);
            et[i].setFocusable(true);
            et[i].setEnabled(true);
            et[i].setFocusableInTouchMode(true);
            if(i==0){
                et[i].requestFocus();
            }
        }
    }

    private void initiate(){
        shmtv_numberBuilding =(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_numberBuilding);
        shmtv_typeBuilding=(ShimmerTextView)rootView.findViewById(R.id.BuildingInfoFrag_shimtv_typeBuilding);
        shmtv_nameBuilding=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_nameBuilding);
        shmtv_numberOfUnit=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_numberOfUnit);
        shmtv_Plaque=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Plaque);
        shmtv_Adrress=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Adrress);
        text_numberbuiding = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_numberBuilding);
        text_typebuilding = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_typeBuilding);
        text_namebuilding = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_nameBuilding);
        text_numberofunit = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_numberOfUnit);
        text_plaque = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_shimtv_Plaque);
        text_address = (MaterialEditText) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Text_Adrress);
        shimmer = new Shimmer();
        shimmer.setDuration(2000)
                .setStartDelay(100)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shmtv_numberBuilding);
        shimmer.start(shmtv_typeBuilding);
        shimmer.start(shmtv_nameBuilding);
        shimmer.start(shmtv_numberOfUnit);
        shimmer.start(shmtv_Plaque);
        shimmer.start(shmtv_Adrress);

        bhomaFont = Typeface.createFromAsset(getContext().getAssets(),"iraniansans.ttf");
        btn_insert_edit =(Button) rootView.findViewById(R.id.buildinginformation_btn_edit);
        shmtv_numberBuilding.setTypeface(bhomaFont);
        shmtv_typeBuilding.setTypeface(bhomaFont);
        shmtv_nameBuilding.setTypeface(bhomaFont);
        shmtv_numberOfUnit.setTypeface(bhomaFont);
        shmtv_Plaque.setTypeface(bhomaFont);
        shmtv_Adrress.setTypeface(bhomaFont);
        btn_insert_edit.setTypeface(bhomaFont);
        text_address.setTypeface(bhomaFont);
        text_plaque.setTypeface(bhomaFont);
        text_numberofunit.setTypeface(bhomaFont);
        text_namebuilding.setTypeface(bhomaFont);
        text_numberbuiding.setTypeface(bhomaFont);
        text_typebuilding.setTypeface(bhomaFont);
        db = new database(getActivity());
    }

}