package reza.sabbagh.buildingmanagment;

/**
 * Created by Matarata on 16/8/2016.
 */

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class UnitsInformationAdapter extends RecyclerView.Adapter<UnitsInformationAdapter.UnitsViewHolder>{

    private List<UnitsInformationAdapterData> unitsInfoData;

    public UnitsInformationAdapter(List<UnitsInformationAdapterData> unitsInfoData) {
        this.unitsInfoData = unitsInfoData;
    }

    @Override
    public int getItemCount() {
        return unitsInfoData.size();
    }

    @Override
    public void onBindViewHolder(UnitsViewHolder unitsViewHolder, int i) {
        UnitsInformationAdapterData ci = unitsInfoData.get(i);
        unitsViewHolder.vName.setText(ci.unit_number);
        unitsViewHolder.vBuildingNumber.setText(ci.username);
        //unitsViewHolder.vPhoneNumber.setText(ci.phone_number);
        /*if(i%2 == 0){
            usersViewHolder.vCardView.setCardBackgroundColor(Color.parseColor("#4db6ac"));
        }else{
            usersViewHolder.vCardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }*/
    }

    @Override
    public UnitsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_layout, viewGroup, false);

        return new UnitsViewHolder(itemView);
    }

    public static class UnitsViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vBuildingNumber;
        protected TextView vPhoneNumber;
        public static CardView vCardView;

        public UnitsViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.cv_tv_username);
            vBuildingNumber =  (TextView) v.findViewById(R.id.cv_tv_buildingNumber);
            vPhoneNumber = (TextView)  v.findViewById(R.id.cv_tv_phoneNumber);
            vCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
