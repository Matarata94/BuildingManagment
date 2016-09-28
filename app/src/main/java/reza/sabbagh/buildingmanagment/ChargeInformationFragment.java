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


/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeInformationFragment extends Fragment {

    private FloatingActionMenu fabmenu;
    private FloatingActionButton subFabExit, subFabAdd;
    private ChargeInformationAdapter cia;
    private database db;
    public static String resChargeInfo="",resChargeInfoDelete="";
    private int count=0,listCount,stringIndexHolder[] = new int[8],rowArray=0,selectedItemPosition=10000, selectedItemSearchPosition =10000,upDataList=0;
    private Timer tm;
    private ProgressDialog pd;
    private RecyclerView rv;
    private String[][] dataList,dataListSearch;
    private Typeface iransans;
    private Bundle bundle = new Bundle();
    private MaterialDialog dialog;
    private String link = FirstActivity.globalLink + "ChargeInfo.php",completeProfile="",completeProfileTitle="";
    private EditText searchET;
    private Button search_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charge_information, container, false);

        initiate(rootView);
        
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(cia.getItemCount() != listCount){
                            //search position
                            selectedItemSearchPosition = position;
                            completeProfileTitle = "شماره قبض: " + dataListSearch[selectedItemSearchPosition][6];
                            completeProfile = "شماره واحد:" + "  " + dataListSearch[selectedItemSearchPosition][1] + "\n\n" + "شماره ساختمان:" + "  " +  dataListSearch[selectedItemSearchPosition][0]
                                    + "\n\n" + "نوع قبض:" + "  " + dataListSearch[selectedItemSearchPosition][2] + "\n\n" + "تاریخ قبض" + "  " + dataListSearch[selectedItemSearchPosition][3]
                                    + "\n\n" + "مقدار قبض:" + "  " + dataListSearch[selectedItemSearchPosition][4] + "\n\n" + "نوع محاسبه:" + "  " + dataListSearch[selectedItemSearchPosition][5];
                            dialog = new MaterialDialog.Builder(getActivity())
                                    .title(completeProfileTitle)
                                    .content(completeProfile)
                                    .neutralText("تایید")
                                    .positiveText("ویرایش")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent in = new Intent(getContext(),RegisterChargeActivity.class);
                                            if(selectedItemSearchPosition != 10000){
                                                String[] data = new String[8];
                                                for(int i=0;i < 7;i++){
                                                    data[i] = dataListSearch[selectedItemSearchPosition][i];
                                                }
                                                data[7] = "edit_bill";
                                                bundle.putStringArray("keyCharge",data);
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
                                            serverWorkingDelete(FirstActivity.globalLink + "RegisterUnit.php","delete",db.queryInfo(2),dataListSearch[selectedItemSearchPosition][0],"s");
                                            db.close();
                                            upDataList = 1;*/
                                        }
                                    })
                                    .typeface(iransans,iransans)
                                    .icon(ContextCompat.getDrawable(getContext(),R.drawable.aboutus))
                                    .show();
                        }else if(cia.getItemCount() == listCount){
                            //normal position
                            selectedItemPosition = position;
                            completeProfileTitle = "شماره قبض: " + dataList[selectedItemPosition][6];
                            completeProfile = "شماره واحد:" + "  " + dataList[selectedItemPosition][1] + "\n\n" + "شماره ساختمان:" + "  " +  dataList[selectedItemPosition][0]
                                    + "\n\n" + "نوع قبض:" + "  " + dataList[selectedItemPosition][2] + "\n\n" + "تاریخ قبض" + "  " + dataList[selectedItemPosition][3]
                                    + "\n\n" + "مقدار قبض:" + "  " + dataList[selectedItemPosition][4] + "\n\n" + "نوع محاسبه:" + "  " + dataList[selectedItemPosition][5];
                            dialog = new MaterialDialog.Builder(getActivity())
                                    .title(completeProfileTitle)
                                    .content(completeProfile)
                                    .neutralText("تایید")
                                    .positiveText("ویرایش")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent in = new Intent(getContext(),RegisterChargeActivity.class);
                                            if(selectedItemPosition != 10000){
                                                String[] data = new String[8];
                                                for(int i=0;i < 7;i++){
                                                    data[i] = dataList[selectedItemPosition][i];
                                                }
                                                data[7] = "edit_bill";
                                                bundle.putStringArray("keyCharge",data);
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
                                            serverWorkingDelete(FirstActivity.globalLink + "RegisterUnit.php","delete",db.queryInfo(2),dataList[selectedItemPosition][0],"n");
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
                Intent in = new Intent(getActivity(),RegisterChargeActivity.class);
                String[] data = new String[8];
                data[7] = "add_bill";
                bundle.putStringArray("keyCharge",data);
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchET.getText().toString().length() == 0){

                }else{
                    String tempBillNumber="",tempBillDate="";
                    int tempCounter=0;
                    if(upDataList == 1){
                        db.open();
                        serverWorking(link,"query",db.queryInfo(2));
                        db.close();
                        searchET.setText("");
                    }
                    dataListSearch = new String[80][7];
                    for(int i=0;i < listCount;i++){
                        tempBillNumber = dataList[i][6];
                        tempBillDate = dataList[i][3];
                        if(tempBillNumber.contains(searchET.getText().toString()) | tempBillDate.contains(searchET.getText().toString())){
                            for (int j=0;j < 6;j++){
                                dataListSearch[tempCounter][j] = dataList[i][j];
                            }
                            tempCounter++;
                        }
                    }
                    cia = new ChargeInformationAdapter(createList(tempCounter,dataListSearch));
                    rv.setAdapter(cia);
                }
            }
        });

        return rootView;
    }
    
    private void initiate(View rootView){
        rv = (RecyclerView)rootView.findViewById(R.id.chargeInfoFrag_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        db = new database(getContext());
        db.open();
        serverWorking(link,"query",db.queryInfo(2));
        db.close();
        fabmenu = (FloatingActionMenu) rootView.findViewById(R.id.chargeInfoFrag_fab_menu);
        subFabExit = (FloatingActionButton) rootView.findViewById(R.id.chargeInfoFrag_fab_exit);
        subFabAdd = (FloatingActionButton) rootView.findViewById(R.id.chargeInfoFrag_fab_add);
        searchET = (EditText) rootView.findViewById(R.id.fragment_chargeInfo_et_search);
        search_btn = (Button) rootView.findViewById(R.id.fragment_chargeInfo_btn_search);
        iransans = Typeface.createFromAsset(getContext().getAssets(),"BHoma.ttf");
        searchET.setTypeface(iransans);
        search_btn.setTypeface(iransans);
        fabmenu.setClosedOnTouchOutside(true);
    }

    private List<ChargeInformationAdapterData> createList(int size,String data[][]) {
        List<ChargeInformationAdapterData> result = new ArrayList<ChargeInformationAdapterData>();
        for (int i = 0; i < size; i++) {
            ChargeInformationAdapterData ci = new ChargeInformationAdapterData();
            ci.bill_number = ChargeInformationAdapterData.BILL_NUMBER_PREFIX + data[i][6];
            ci.bill_amount = ChargeInformationAdapterData.BIL_AMOUNT_PREFIX + data[i][4];
            ci.bill_date = ChargeInformationAdapterData.BILL_DATE_PREFIX + data[i][3];
            result.add(ci);
        }
        return result;
    }

    private void serverWorking(String link, String requesttype, String adminusername){
        new ServerConnectorChargeInfo(link,requesttype,adminusername).execute();
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
                        if(count == 6){
                            pd.cancel();
                            tm.cancel();
                            Toast.makeText(getContext(),"برنامه قادر به برقراری ارتباط با سرور نیست. لطفا مجددا تلاش نمایید.",Toast.LENGTH_LONG).show();
                            count = 0;
                            fabmenu.setVisibility(View.GONE);
                        }else if(resChargeInfo.contains("no information")){
                            tm.cancel();
                            pd.cancel();
                            resChargeInfo = "";
                            count = 0;
                            Toast.makeText(getContext(),"اطلاعاتی یافت نشد!",Toast.LENGTH_LONG).show();
                        }else if(resChargeInfo.contains("founded")){
                            tm.cancel();
                            count = 0;
                            listCount = Integer.parseInt(resChargeInfo.substring(resChargeInfo.indexOf("~") + 1,resChargeInfo.indexOf("!")));
                            dataList = new String[listCount][7];
                            for(int i=0;i < resChargeInfo.length();i++){
                                if(resChargeInfo.charAt(i) == '!'){
                                    stringIndexHolder[0] = i;
                                }
                                if(resChargeInfo.charAt(i) == '@'){
                                    stringIndexHolder[1] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]).equals(""))
                                        dataList[rowArray][0] = resChargeInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]);
                                }
                                if(resChargeInfo.charAt(i) == '#'){
                                    stringIndexHolder[2] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]).equals(""))
                                        dataList[rowArray][1] = resChargeInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]);
                                }
                                if(resChargeInfo.charAt(i) == '$'){
                                    stringIndexHolder[3] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]).equals(""))
                                        dataList[rowArray][2] = resChargeInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]);
                                }
                                if(resChargeInfo.charAt(i) == '%'){
                                    stringIndexHolder[4] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]).equals(""))
                                        dataList[rowArray][3] = resChargeInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]);
                                }
                                if(resChargeInfo.charAt(i) == '^'){
                                    stringIndexHolder[5] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]).equals(""))
                                        dataList[rowArray][4] = resChargeInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]);
                                }
                                if(resChargeInfo.charAt(i) == '&'){
                                    stringIndexHolder[6] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]).equals(""))
                                        dataList[rowArray][5] = resChargeInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]);
                                }
                                if(resChargeInfo.charAt(i) == '*'){
                                    stringIndexHolder[7] = i;
                                    if(!resChargeInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]).equals(""))
                                        dataList[rowArray][6] = resChargeInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]);
                                    rowArray++;
                                }
                            }
                            rowArray = 0;
                            resChargeInfo = "";
                            cia = new ChargeInformationAdapter(createList(listCount,dataList));
                            rv.setAdapter(cia);
                            pd.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }
    
}