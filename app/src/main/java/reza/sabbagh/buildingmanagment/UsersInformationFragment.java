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

public class UsersInformationFragment extends Fragment{

    private FloatingActionMenu fabmenu;
    private FloatingActionButton subFabExit,subFabAdd;
    private EditText searchET;
    private Button search_btn;
    private RecyclerView rv;
    private UsersInformationAdapter uia;
    public static String resUsersInfo="",resUsersInfoDelete="";
    private int count=0,listCount,stringIndexHolder[] = new int[8],rowArray=0,selectedItemPosition=10000, selectedItemSearchPosition =10000,upDataList=0;
    private Timer tm;
    private ProgressDialog pd;
    private database db;
    private String link = FirstActivity.globalLink + "UsersInfo.php",completeProfile="",completeProfileTitle="";
    private String[][] dataList,dataListSearch;
    private Typeface iransans;
    private Bundle bundle = new Bundle();
    private MaterialDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users_information, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.usersInfoFrag_rv);
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
                        completeProfileTitle = dataListSearch[position][0] + " " + dataListSearch[position][1];
                        completeProfile = "شماره همراه: " + dataListSearch[position][2] + "\n\n" + "شماره منزل: "+ dataListSearch[position][3]
                                + "\n\n" + "ایمیل: "+ dataListSearch[position][4] + "\n\n"+ "شماره ساختمان: "+ dataListSearch[position][5]
                                + "\n\n" + "شماره واحد: "+ dataListSearch[position][6];
                        dialog = new MaterialDialog.Builder(getActivity())
                                .title(completeProfileTitle)
                                .content(completeProfile)
                                .neutralText("تایید")
                                .positiveText("ویرایش")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent in = new Intent(getContext(),RegisterUserActivity.class);
                                        if(selectedItemSearchPosition != 10000){
                                            String[] data = new String[8];
                                            for(int i=0;i < 7;i++){
                                                data[i] = dataListSearch[selectedItemSearchPosition][i];
                                            }
                                            data[8] = "edit_user";
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
                                        db.open();
                                        serverWorkingDelete(FirstActivity.globalLink + "RegisterUser.php","delete",db.queryInfo(2),dataListSearch[selectedItemSearchPosition][6],"s");
                                        db.close();
                                        upDataList = 1;
                                    }
                                })
                                .typeface(iransans,iransans)
                                .icon(ContextCompat.getDrawable(getContext(),R.drawable.aboutus))
                                .show();
                    }else if(uia.getItemCount() == listCount){
                        //normal position
                        selectedItemPosition = position;
                        completeProfileTitle = dataList[position][0] + " " + dataList[position][1];
                        completeProfile = "شماره همراه: " + dataList[position][2] + "\n\n" + "شماره منزل: "+ dataList[position][3]
                                + "\n\n" +"ایمیل: "+ dataList[position][4] + "\n\n" + "شماره ساختمان: "+ dataList[position][5]
                                + "\n\n" +"شماره واحد: "+ dataList[position][6];
                        dialog = new MaterialDialog.Builder(getActivity())
                                .title(completeProfileTitle)
                                .content(completeProfile)
                                .neutralText("تایید")
                                .positiveText("ویرایش")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent in = new Intent(getContext(),RegisterUserActivity.class);
                                        if(selectedItemPosition != 10000){
                                            String[] data = new String[8];
                                            for(int i=0;i < 7;i++){
                                                data[i] = dataList[selectedItemPosition][i];
                                            }
                                            data[8] = "edit_use";
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
                                        db.open();
                                        serverWorkingDelete(FirstActivity.globalLink + "RegisterUser.php","delete",db.queryInfo(2),dataList[selectedItemPosition][6],"n");
                                        db.close();
                                    }
                                })
                                .typeface(iransans,iransans)
                                .icon(ContextCompat.getDrawable(getContext(),R.drawable.aboutus))
                                .show();
                    }
                    }
                })
        );

        fabmenu = (FloatingActionMenu) rootView.findViewById(R.id.usersInfoFrag_fab_menu);
        subFabExit = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_exit);
        subFabAdd = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_add);
        searchET = (EditText) rootView.findViewById(R.id.fragment_usersInfo_et_search);
        search_btn = (Button) rootView.findViewById(R.id.fragment_usersInfo_btn_search);
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
                Intent in = new Intent(getContext(),RegisterUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1","add_user");
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchET.getText().length() == 0){

                }else{
                    String tempName="",tempUnitNum="";
                    int tempCounter=0;
                    if(upDataList == 1){
                        db.open();
                        serverWorking(link,"query",db.queryInfo(2));
                        db.close();
                        searchET.setText("");
                    }
                    dataListSearch = new String[80][7];
                    for(int i=0;i < listCount;i++){
                        tempName = dataList[i][0] + " "+ dataList[i][1];
                        tempUnitNum = dataList[i][6];
                        if(tempName.contains(searchET.getText().toString()) | tempUnitNum.contains(searchET.getText().toString())){
                            for (int j=0;j < 7;j++){
                                dataListSearch[tempCounter][j] = dataList[i][j];
                            }
                            tempCounter++;
                        }
                    }
                    uia = new UsersInformationAdapter(createList(tempCounter,dataListSearch));
                    rv.setAdapter(uia);
                }
            }
        });

        return rootView;
    }

    private List<UsersInformationAdapterData> createList(int size,String data[][]) {
        List<UsersInformationAdapterData> result = new ArrayList<UsersInformationAdapterData>();
        for (int i=0; i < size; i++) {
            UsersInformationAdapterData ci = new UsersInformationAdapterData();
            ci.name = UsersInformationAdapterData.NAME_PREFIX + data[i][0] + " " + data[i][1];
            ci.phone_number = UsersInformationAdapterData.PHONE_PREFIX + data[i][2];
            ci.building_number = UsersInformationAdapterData.BUILDING_NUMBER_PREFIX + data[i][5];
            result.add(ci);
        }
        return result;
    }

    private void serverWorking(String link, String requesttype, String badminusername){
        new ServerConnectorUsersInfo(link,requesttype,badminusername).execute();
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
                        }else if(resUsersInfo.equals("no information")){
                            tm.cancel();
                            pd.cancel();
                            resUsersInfo = "";
                            count = 0;
                            Toast.makeText(getContext(),"اطلاعاتی یافت نشد!",Toast.LENGTH_LONG).show();
                        }else if(resUsersInfo.contains("founded")){
                            tm.cancel();
                            count = 0;
                            listCount = Integer.parseInt(resUsersInfo.substring(resUsersInfo.indexOf("~") + 1,resUsersInfo.indexOf("!")));
                            dataList = new String[listCount][7];
                            for(int i=0;i < resUsersInfo.length();i++){
                                if(resUsersInfo.charAt(i) == '!'){
                                    stringIndexHolder[0] = i;
                                }
                                if(resUsersInfo.charAt(i) == '@'){
                                    stringIndexHolder[1] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]).equals(""))
                                        dataList[rowArray][0] = resUsersInfo.substring(stringIndexHolder[0]+1,stringIndexHolder[1]);

                                }
                                if(resUsersInfo.charAt(i) == '#'){
                                    stringIndexHolder[2] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]).equals(""))
                                        dataList[rowArray][1] = resUsersInfo.substring(stringIndexHolder[1]+1,stringIndexHolder[2]);
                                }
                                if(resUsersInfo.charAt(i) == '$'){
                                    stringIndexHolder[3] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]).equals(""))
                                        dataList[rowArray][2] = resUsersInfo.substring(stringIndexHolder[2]+1,stringIndexHolder[3]);
                                }if(resUsersInfo.charAt(i) == '%'){
                                    stringIndexHolder[4] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]).equals(""))
                                        dataList[rowArray][3] = resUsersInfo.substring(stringIndexHolder[3]+1,stringIndexHolder[4]);
                                }
                                if(resUsersInfo.charAt(i) == '^'){
                                    stringIndexHolder[5] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]).equals(""))
                                        dataList[rowArray][4] = resUsersInfo.substring(stringIndexHolder[4]+1,stringIndexHolder[5]);
                                }
                                if(resUsersInfo.charAt(i) == '&'){
                                    stringIndexHolder[6] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]).equals(""))
                                        dataList[rowArray][5] = resUsersInfo.substring(stringIndexHolder[5]+1,stringIndexHolder[6]);
                                }
                                if(resUsersInfo.charAt(i) == '*'){
                                    stringIndexHolder[7] = i;
                                    if(!resUsersInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]).equals(""))
                                        dataList[rowArray][6] = resUsersInfo.substring(stringIndexHolder[6]+1,stringIndexHolder[7]);
                                    rowArray++;
                                }
                            }
                            rowArray = 0;
                            resUsersInfo = "";
                            uia = new UsersInformationAdapter(createList(listCount,dataList));
                            rv.setAdapter(uia);
                            pd.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    private void serverWorkingDelete(String link, String requesttype, String adminusername, String unitnumber, final String sORn){
        new ServerConnectorDeleteUser(link,requesttype,adminusername,unitnumber).execute();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Deleting...");
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
                        }else if(resUsersInfoDelete.contains("delete fail")){
                            tm.cancel();
                            pd.cancel();
                            resUsersInfoDelete = "";
                            count = 0;
                            Toast.makeText(getContext(),"خطا در حذف اطلاعات. لطفا در زمان دیگری امتحان کنید!",Toast.LENGTH_LONG).show();
                        }else if(resUsersInfoDelete.contains("deleted")){
                            tm.cancel();
                            pd.cancel();
                            resUsersInfoDelete = "";
                            count = 0;
                            Toast.makeText(getContext(),"با موفقیت حذف گردید!",Toast.LENGTH_LONG).show();
                            if(sORn.equals("n")){
                                uia = new UsersInformationAdapter(createList(uia.getItemCount()-1,dataList));
                                rv.setAdapter(uia);
                            }else if(sORn.equals("s")){
                                uia = new UsersInformationAdapter(createList(uia.getItemCount()-1,dataListSearch));
                                rv.setAdapter(uia);
                            }
                        }
                    }
                });
            }
        }, 1, 1000);
    }


}
