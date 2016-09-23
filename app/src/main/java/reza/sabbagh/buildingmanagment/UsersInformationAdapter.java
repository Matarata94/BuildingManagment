package reza.sabbagh.buildingmanagment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsersInformationAdapter extends RecyclerView.Adapter<UsersInformationAdapter.UsersViewHolder> {

    private List<UsersInformationAdapterData> usersInfoData;
    private Typeface iransans,bhoma;

    public UsersInformationAdapter(List<UsersInformationAdapterData> usersInfoData) {
        this.usersInfoData = usersInfoData;
    }

    @Override
    public int getItemCount() {
        return usersInfoData.size();
    }

    @Override
    public void onBindViewHolder(UsersViewHolder usersViewHolder, int i) {
        UsersInformationAdapterData ci = usersInfoData.get(i);
        usersViewHolder.vName.setText(ci.name);
        usersViewHolder.vBuildingNumber.setText(ci.building_number);
        usersViewHolder.vPhoneNumber.setText(ci.phone_number);
        usersViewHolder.vName.setTypeface(bhoma);
        usersViewHolder.vBuildingNumber.setTypeface(iransans);
        usersViewHolder.vPhoneNumber.setTypeface(iransans);
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_layout, viewGroup, false);
        iransans = Typeface.createFromAsset(itemView.getContext().getAssets(),"iraniansans.ttf");
        bhoma = Typeface.createFromAsset(itemView.getContext().getAssets(),"BHoma.ttf");

        return new UsersViewHolder(itemView);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vBuildingNumber;
        protected TextView vPhoneNumber;
        public static CardView vCardView;

        public UsersViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.cv_tv_username);
            vBuildingNumber =  (TextView) v.findViewById(R.id.cv_tv_buildingNumber);
            vPhoneNumber = (TextView)  v.findViewById(R.id.cv_tv_phoneNumber);
            vCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

}