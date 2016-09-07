package reza.sabbagh.buildingmanagment;

/**
 * Created by Matarata on 16/8/2016.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class UnitsInformationAdapter extends RecyclerView.Adapter<UnitsInformationAdapter.UnitsViewHolder>{

    private List<UnitsInformationAdapterData> unitsInfoData;
    private Typeface iransans,bhoma;

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
        unitsViewHolder.vResidentName.setText(ci.resident_name);
        unitsViewHolder.vUnitNumber.setText(ci.unit_number);
        unitsViewHolder.vResidenceDate.setText(ci.residence_date);
    }

    @Override
    public UnitsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_layout, viewGroup, false);
        iransans = Typeface.createFromAsset(itemView.getContext().getAssets(),"iraniansans.ttf");
        bhoma = Typeface.createFromAsset(itemView.getContext().getAssets(),"BHoma.ttf");

        return new UnitsViewHolder(itemView);
    }

    public static class UnitsViewHolder extends RecyclerView.ViewHolder {
        protected TextView vResidentName;
        protected TextView vUnitNumber;
        protected TextView vResidenceDate;
        public static CardView vCardView;

        public UnitsViewHolder(View v) {
            super(v);
            vResidentName =  (TextView) v.findViewById(R.id.cv_tv_username);
            vUnitNumber =  (TextView) v.findViewById(R.id.cv_tv_buildingNumber);
            vResidenceDate = (TextView)  v.findViewById(R.id.cv_tv_phoneNumber);
            vCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
