package reza.sabbagh.buildingmanagment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UnitsInformationFragment extends Fragment {

    private FloatingActionMenu fabmenu;
    private FloatingActionButton subFabExit,subFabAdd;
    private UnitsInformationAdapter uia;
    private database db;
    public static String resUnitsInfo = "";
    private int count=0,listCount,stringIndexHolder[] = new int[12],rowArray=0,selectedItemPosition=10000, selectedItemSearchPosition =10000,upDataList=0;
    private Timer tm;
    private ProgressDialog pd;
    private RecyclerView rv;
    private String[][] dataList,dataListSearch;
    private Typeface iransans;
    private Bundle bundle = new Bundle();
    private MaterialDialog dialog;
    private String link = FirstActivity.globalLink + "UnitsInfo.php",completeProfile="",completeProfileTitle="";
    private EditText searchET;
    private Button search_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_unit_information, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.unitsInfoFrag_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        db = new database(getContext());
        db.open();
        serverWorking(link,"query",db.queryInfo(2));
        db.close();
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(uia.getItemCount() != listCount){
                            //search position
                            selectedItemSearchPosition = position;
                            completeProfile = getResources().getString(R.string.unitnumbertitle) + "  " + dataListSearch[selectedItemSearchPosition][0] + "\n\n" + getResources().getString(R.string.buildingnumbertitle) + "  " +  dataListSearch[selectedItemSearchPosition][1]
                                    + "\n\n" + getResources().getString(R.string.ownernametitle) + "  " + dataListSearch[selectedItemSearchPosition][2] + "\n\n" + getResources().getString(R.string.residentnametitle) + "  " + dataListSearch[selectedItemSearchPosition][3]
                                    + "\n\n" + getResources().getString(R.string.floornumbertitle) + "  " + dataListSearch[selectedItemSearchPosition][4] + "\n\n" + getResources().getString(R.string.areasizetitle) + "  " + dataListSearch[selectedItemSearchPosition][5]
                                    + "\n\n" + getResources().getString(R.string.residencedatetitle) + "  " + dataListSearch[selectedItemSearchPosition][6] + "\n\n" + getResources().getString(R.string.numberofresidenttitle) + "  " + dataListSearch[selectedItemSearchPosition][7]
                                    + "\n\n" + getResources().getString(R.string.postalcodetitle) + "  " + dataListSearch[selectedItemSearchPosition][8] + "\n\n" + getResources().getString(R.string.chargedefaultamounttitle) + "  " + dataListSearch[selectedItemSearchPosition][9]
                                    + "\n\n" + getResources().getString(R.string.statechargetitle) + "  " + dataListSearch[selectedItemSearchPosition][10];
                            dialog = new MaterialDialog.Builder(getActivity())
                                    .title(R.string.unitinfotitle)
                                    .content(completeProfile)
                                    .neutralText("تایید")
                                    .positiveText("ویرایش")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent in = new Intent(getContext(),RegisterUserActivity.class);
                                            in.putExtra("key1","edit_user");
                                            if(selectedItemSearchPosition != 10000){
                                                String[] data = new String[7];
                                                for(int i=0;i < 7;i++){
                                                    data[i] = dataListSearch[selectedItemSearchPosition][i];
                                                }
                                                bundle.putStringArray("keyData",data);
                                            }
                                            in.putExtras(bundle);
                                            startActivity(in);
                                            selectedItemSearchPosition = 10000;
                                        }
                                    })
                                    .negativeText("حذف")
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            /*db.open();
                                            serverWorkingDelete(FirstActivity.globalLink + "register.php","delete",db.queryInfo(2),dataListSearch[selectedItemSearchPosition][6],"s");
                                            db.close();
                                            upDataList = 1;*/
                                        }
                                    })
                                    .typeface(iransans,iransans)
                                    .icon(ContextCompat.getDrawable(getContext(),R.drawable.aboutus))
                                    .show();
                        }else if(uia.getItemCount() == listCount){
                            //normal position
                            selectedItemPosition = position;
                            completeProfile = getResources().getString(R.string.unitnumbertitle) + "  " + dataList[selectedItemPosition][0] + "\n\n" + getResources().getString(R.string.buildingnumbertitle) + "  " +  dataList[selectedItemPosition][1]
                                    + "\n\n" + getResources().getString(R.string.ownernametitle) + "  " + dataList[selectedItemPosition][2] + "\n\n" + getResources().getString(R.string.residentnametitle) + "  " + dataList[selectedItemPosition][3]
                                    + "\n\n" + getResources().getString(R.string.floornumbertitle) + "  " + dataList[selectedItemPosition][4] + "\n\n" + getResources().getString(R.string.areasizetitle) + "  " + dataList[selectedItemPosition][5]
                                    + "\n\n" + getResources().getString(R.string.residencedatetitle) + "  " + dataList[selectedItemPosition][6] + "\n\n" + getResources().getString(R.string.numberofresidenttitle) + "  " + dataList[selectedItemPosition][7]
                                    + "\n\n" + getResources().getString(R.string.postalcodetitle) + "  " + dataList[selectedItemPosition][8] + "\n\n" + getResources().getString(R.string.chargedefaultamounttitle) + "  " + dataList[selectedItemPosition][9]
                                    + "\n\n" + getResources().getString(R.string.statechargetitle) + "  " + dataList[selectedItemPosition][10];
                            dialog = new MaterialDialog.Builder(getActivity())
                                    .title(R.string.unitinfotitle)
                                    .content(completeProfile)
                                    .neutralText("تایید")
                                    .positiveText("ویرایش")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent in = new Intent(getContext(),RegisterUserActivity.class);
                                            in.putExtra("key1","edit_user");
                                            if(selectedItemPosition != 10000){
                                                String[] data = new String[7];
                                                for(int i=0;i < 7;i++){
                                                    data[i] = dataList[selectedItemPosition][i];
                                                }
                                                bundle.putStringArray("keyData",data);
                                            }
                                            in.putExtras(bundle);
                                            startActivity(in);
                                            selectedItemPosition = 10000;
                                        }
                                    })
                                    .negativeText("حذف")
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            /*db.open();
                                            serverWorkingDelete(FirstActivity.globalLink + "register.php","delete",db.queryInfo(2),dataList[selectedItemPosition][6],"n");
                                            db.close();*/
                                        }
                                    })
                                    .typeface(iransans,iransans)
                                    .icon(ContextCompat.getDrawable(getContext(),R.drawable.aboutus))
                                    .show();
                        }
                    }
                })
        );

        fabmenu = (FloatingActionMenu) rootView.findViewById(R.id.unitsInfoFrag_fab_menu);
        subFabExit = (FloatingActionButton) rootView.findViewById(R.id.unitsInfoFrag_fab_exit);
        subFabAdd = (FloatingActionButton) rootView.findViewById(R.id.unitsInfoFrag_fab_add);
        searchET = (EditText) rootView.findViewById(R.id.fragment_unitsInfo_et_search);
        search_btn = (Button) rootView.findViewById(R.id.fragment_unitsInfo_btn_search);
        iransans = Typeface.createFromAsset(getContext().getAssets(),"BHoma.ttf");
        searchET.setTypeface(iransans);
        search_btn.setTypeface(iransans);
        fabmenu.setClosedOnTouchOutside(true);
        subFabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("آیا مایل به خروج هستید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        subFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().beginTransaction().remove(UnitsInformationFragment.this).commit();
                Intent in = new Intent(getContext(),RegisterUnitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1","add_unit");
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchET.getText().length() == 0){

                }else{
                    String tempResidentName="",tempOwnerName="",tempUnitNum="",tempResidentDate="";
                    int tempCounter=0;
                    if(upDataList == 1){
                        db.open();
                        serverWorking(link,"query",db.queryInfo(2));
                        db.close();
                        searchET.setText("");
                    }
                    dataListSearch = new String[80][11];
                    for(int i=0;i < listCount;i++){
                        tempResidentName = dataList[i][3];
                        tempUnitNum = dataList[i][0];
                        tempResidentDate = dataList[i][6];
                        tempOwnerName = dataList[i][2];
                        if(tempResidentName.contains(searchET.getText().toString()) | tempUnitNum.contains(searchET.getText().toString())
                                | tempResidentDate.contains(searchET.getText().toString()) | tempOwnerName.contains(searchET.getText().toString())){
                            for (int j=0;j < 11;j++){
                                dataListSearch[tempCounter][j] = dataList[i][j];
                            }
                            tempCounter++;
                        }
                    }
                    uia = new UnitsInformationAdapter(createList(tempCounter,dataListSearch));
                    rv.setAdapter(uia);
                }
            }
        });

        return rootView;
    }

    private List<UnitsInformationAdapterData> createList(int size,String data[][]) {
        List<UnitsInformationAdapterData> result = new ArrayList<UnitsInformationAdapterData>();
        for (int i=0; i < size; i++) {
            UnitsInformationAdapterData ci = new UnitsInformationAdapterData();
            ci.resident_name = UnitsInformationAdapterData.RESIDENTNAME_PREFIX + data[i][3];
            ci.unit_number = UnitsInformationAdapterData.UNITNUMBER_PREFIX + data[i][0];
            ci.residence_date = UnitsInformationAdapterData.RESIDENCEDATE_PREFIX + data[i][6];
            result.add(ci);
        }
        return result;
    }

    private void serverWorking(String link, String requesttype, String adminusername){
        new ServerConnectorUnitsInfo(link,requesttype,adminusername).execute();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();

        tm =new Timer();
        tm.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        count++;
                        if(count == 7){
                            pd.cancel();
                            tm.cancel();
                            Toast.makeText(getContext(),"برنامه قادر به برقراری ارتباط با سرور نیست. لطفا مجددا تلاش نمایید.",Toast.LENGTH_LONG).show();
                            count = 0;
                            fabmenu.setVisibility(View.GONE);
                        }else if(resUnitsInfo.contains("no information")){
                            tm.cancel();
                            pd.cancel();
                            resUnitsInfo = "";
                            count = 0;
                            Toast.makeText(getContext(),"اطلاعاتی یافت نشد!",Toast.LENGTH_LONG).show();
                        }else if(resUnitsInfo.contains("founded")){
                            tm.cancel();
                            count = 0;
                            listCount = Integer.parseInt(resUnitsInfo.substring(resUnitsInfo.indexOf("~") + 1,resUnitsInfo.indexOf("!")));
                            dataList = new String[listCount][11];
                            for(int i=0;i < resUnitsInfo.length();i++){
                                if(resUnitsInfo.charAt(i) == '!'){
                                    stringIndexHolder[0] = i;
                                }
                                if(resUnitsInfo.charAt(i) == '@'){
                                    stringIndexHolder[1] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]).equals(""))
                                        dataList[rowArray][0] = resUnitsInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]);

                                }
                                if(resUnitsInfo.charAt(i) == '#'){
                                    stringIndexHolder[2] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]).equals(""))
                                        dataList[rowArray][1] = resUnitsInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]);
                                }
                                if(resUnitsInfo.charAt(i) == '$'){
                                    stringIndexHolder[3] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]).equals(""))
                                        dataList[rowArray][2] = resUnitsInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]);
                                }
                                if(resUnitsInfo.charAt(i) == '%'){
                                    stringIndexHolder[4] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]).equals(""))
                                        dataList[rowArray][3] = resUnitsInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]);
                                }
                                if(resUnitsInfo.charAt(i) == '^'){
                                    stringIndexHolder[5] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]).equals(""))
                                        dataList[rowArray][4] = resUnitsInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]);
                                }
                                if(resUnitsInfo.charAt(i) == '&'){
                                    stringIndexHolder[6] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]).equals(""))
                                        dataList[rowArray][5] = resUnitsInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]);
                                }
                                if(resUnitsInfo.charAt(i) == '*'){
                                    stringIndexHolder[7] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]).equals(""))
                                        dataList[rowArray][6] = resUnitsInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]);
                                }
                                if(resUnitsInfo.charAt(i) == '('){
                                    stringIndexHolder[8] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[7]+1,stringIndexHolder[8]).equals(""))
                                        dataList[rowArray][7] = resUnitsInfo.substring(stringIndexHolder[7]+1,stringIndexHolder[8]);
                                }
                                if(resUnitsInfo.charAt(i) == ')'){
                                    stringIndexHolder[9] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[8]+1,stringIndexHolder[9]).equals(""))
                                        dataList[rowArray][8] = resUnitsInfo.substring(stringIndexHolder[8]+1,stringIndexHolder[9]);
                                }
                                if(resUnitsInfo.charAt(i) == '_'){
                                    stringIndexHolder[10] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[9]+1,stringIndexHolder[10]).equals(""))
                                        dataList[rowArray][9] = resUnitsInfo.substring(stringIndexHolder[9]+1,stringIndexHolder[10]);
                                }
                                if(resUnitsInfo.charAt(i) == '+'){
                                    stringIndexHolder[11] = i;
                                    if(!resUnitsInfo.substring(stringIndexHolder[10]+1,stringIndexHolder[11]).equals(""))
                                        dataList[rowArray][10] = resUnitsInfo.substring(stringIndexHolder[10]+1,stringIndexHolder[11]);
                                    rowArray++;
                                }
                            }
                            rowArray = 0;
                            resUnitsInfo = "";
                            uia = new UnitsInformationAdapter(createList(listCount,dataList));
                            rv.setAdapter(uia);
                            pd.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }


}
