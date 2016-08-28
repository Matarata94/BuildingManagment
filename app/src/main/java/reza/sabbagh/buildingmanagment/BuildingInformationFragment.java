package reza.sabbagh.buildingmanagment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class BuildingInformationFragment extends Fragment {

    private Shimmer shimmer;
    private Button ButtonRegistration;
    private Typeface bhomaFont;
    private ShimmerTextView shmtv_numberBuilding,shmtv_typeBuilding,shmtv_nameBuilding,shmtv_numberOfUnit,shmtv_Plaque,shmtv_Adrress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_building_information, container, false);

        shmtv_numberBuilding =(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_numberBuilding);
        shmtv_typeBuilding=(ShimmerTextView)rootView.findViewById(R.id.BuildingInfoFrag_shimtv_typeBuilding);
        shmtv_nameBuilding=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_nameBuilding);
        shmtv_numberOfUnit=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_numberOfUnit);
        shmtv_Plaque=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Plaque);
        shmtv_Adrress=(ShimmerTextView) rootView.findViewById(R.id.BuildingInfoFrag_shimtv_Adrress);

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

        bhomaFont = Typeface.createFromAsset(getContext().getAssets(),"DastNevis.otf");
        ButtonRegistration=(Button) rootView.findViewById(R.id.buildinginformation_btn_edit);
        shmtv_numberBuilding.setTypeface(bhomaFont);
        shmtv_typeBuilding.setTypeface(bhomaFont);
        shmtv_nameBuilding.setTypeface(bhomaFont);
        shmtv_numberOfUnit.setTypeface(bhomaFont);
        shmtv_Plaque.setTypeface(bhomaFont);
        shmtv_Adrress.setTypeface(bhomaFont);
        ButtonRegistration.setTypeface(bhomaFont);

        return rootView;
    }
}