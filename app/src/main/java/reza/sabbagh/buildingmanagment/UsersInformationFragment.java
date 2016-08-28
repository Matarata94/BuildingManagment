package reza.sabbagh.buildingmanagment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersInformationFragment extends Fragment{

    private FloatingActionMenu fabmenu;
    private FloatingActionButton subFabExit,subFabRemove,subFabEdit,subFabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users_information, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.usersInfoFrag_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        UsersInformationAdapter uia = new UsersInformationAdapter(createList(10));
        rv.setAdapter(uia);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }
                })
        );

        fabmenu = (FloatingActionMenu) rootView.findViewById(R.id.usersInfoFrag_fab_menu);
        subFabExit = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_exit);
        subFabRemove = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_remove);
        subFabEdit = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_edit);
        subFabAdd = (FloatingActionButton) rootView.findViewById(R.id.usersInfoFrag_fab_add);
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
                startActivity(in);
            }
        });
        subFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(),RegisterUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1","edit");
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        subFabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }

    private List<UsersInformationAdapterData> createList(int size) {
        List<UsersInformationAdapterData> result = new ArrayList<UsersInformationAdapterData>();
        for (int i=1; i <= size; i++) {
            UsersInformationAdapterData ci = new UsersInformationAdapterData();
            ci.name = UsersInformationAdapterData.NAME_PREFIX + GetRandomName();
            ci.building_number = UsersInformationAdapterData.BUILDING_NUMBER_PREFIX + i;
            ci.phone_number = UsersInformationAdapterData.PHONE_PREFIX + i;
            result.add(ci);
        }
        return result;
    }

    //Begin of Temperary methods to fill card views. Should be replace with correct data query!
    private String[] Names = {
            "رضا صباق",
            "مصطفی علی مردانی",
            "حسن باقرنژاد",
            "مهدی خوشباطن",
            "حسین دست گرد",
            "رضا قدیمی",
            "محسن قنبرزاده",
            "علی حاجیلو",
            "حسن شاهد",
            "علی محمدزاده"};

    public String GetRandomName() {
        Random random = new Random();
        int p = random.nextInt(Names.length);
        return Names[p];
    }
    //End of Temperary methods to fill card views.
}
