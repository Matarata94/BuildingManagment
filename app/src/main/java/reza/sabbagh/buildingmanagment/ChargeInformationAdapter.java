package reza.sabbagh.buildingmanagment;

import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChargeInformationAdapter extends RecyclerView.Adapter<ChargeInformationAdapter.ChargeViewHolder> {

    private List<ChargeInformationAdapterData> chargeInfoData;
    private Typeface iransans,bhoma;

    public ChargeInformationAdapter(List<ChargeInformationAdapterData> chargeInfoData) {
        this.chargeInfoData = chargeInfoData;
    }

    @Override
    public int getItemCount() {
        return chargeInfoData.size();
    }

    @Override
    public void onBindViewHolder(ChargeViewHolder chargeViewHolder, int i) {
        ChargeInformationAdapterData ci = chargeInfoData.get(i);
        chargeViewHolder.vBillNumber.setText(ci.bill_number);
        chargeViewHolder.vBillAmount.setText(ci.bill_amount);
        chargeViewHolder.vBillDate.setText(ci.bill_date);
        chargeViewHolder.vBillNumber.setTypeface(bhoma);
        chargeViewHolder.vBillAmount.setTypeface(iransans);
        chargeViewHolder.vBillDate.setTypeface(iransans);
    }

    @Override
    public ChargeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_layout, viewGroup, false);
        iransans = Typeface.createFromAsset(itemView.getContext().getAssets(),"iraniansans.ttf");
        bhoma = Typeface.createFromAsset(itemView.getContext().getAssets(),"BHoma.ttf");

        return new ChargeViewHolder(itemView);
    }

    public static class ChargeViewHolder extends RecyclerView.ViewHolder {
        protected TextView vBillNumber;
        protected TextView vBillAmount;
        protected TextView vBillDate;
        public static CardView vCardView;

        public ChargeViewHolder(View v) {
            super(v);
            vBillNumber =  (TextView) v.findViewById(R.id.cv_tv_username);
            vBillAmount =  (TextView) v.findViewById(R.id.cv_tv_buildingNumber);
            vBillDate = (TextView)  v.findViewById(R.id.cv_tv_phoneNumber);
            vCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

}