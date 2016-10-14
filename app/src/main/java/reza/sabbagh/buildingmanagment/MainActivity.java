package reza.sabbagh.buildingmanagment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private Typeface bhomaFont;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bhomaFont = Typeface.createFromAsset(getAssets(),"DastNevis.otf");

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        addFragment(new BuildingInformationFragment(), true, R.id.container);

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        menuParams.setAnimationDuration(65);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.close);
        close.setBgResource(android.R.color.transparent);
        close.setDividerColor(android.R.color.transparent);
        close.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject building_info = new MenuObject("اطلاعات ساختمان");
        building_info.setResource(R.drawable.domain);
        building_info.setBgResource(android.R.color.transparent);
        building_info.setDividerColor(Color.parseColor("#e91e63"));
        building_info.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject users_info = new MenuObject("اطلاعات مالکین و ساکنین");
        users_info.setResource(R.drawable.humans);
        users_info.setBgResource(android.R.color.transparent);
        users_info.setDividerColor(Color.parseColor("#2196f3"));
        users_info.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject units_info = new MenuObject("اطلاعات واحدها");
        units_info.setResource(R.drawable.home);
        units_info.setBgResource(android.R.color.transparent);
        units_info.setDividerColor(Color.parseColor("#673ab7"));
        units_info.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject charge_info = new MenuObject("قبوض و هزینه ها");
        charge_info.setResource(R.drawable.charge_info);
        charge_info.setBgResource(android.R.color.transparent);
        charge_info.setDividerColor(Color.parseColor("#009688"));
        charge_info.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject charge_list = new MenuObject("لیست شارژ");
        charge_list.setResource(R.drawable.charge_list);
        charge_list.setBgResource(android.R.color.transparent);
        charge_list.setDividerColor(Color.parseColor("#cddc39"));
        charge_list.setScaleType(ImageView.ScaleType.CENTER);

        MenuObject charge_calculate = new MenuObject("محاسبه شارژ");
        charge_calculate.setResource(R.drawable.calculator);
        charge_calculate.setBgResource(android.R.color.transparent);
        charge_calculate.setDividerColor(Color.parseColor("#00bcd4"));
        charge_calculate.setScaleType(ImageView.ScaleType.CENTER);

        /*MenuObject meetings = new MenuObject("جلسات");
        meetings.setResource(R.drawable.meetings);
        meetings.setBgResource(android.R.color.transparent);
        meetings.setDividerColor(Color.parseColor("#8bc34a"));
        meetings.setScaleType(ImageView.ScaleType.CENTER);*/

        MenuObject about_us = new MenuObject("درباره ما");
        about_us.setResource(R.drawable.aboutus);
        about_us.setBgResource(android.R.color.transparent);
        about_us.setDividerColor(Color.parseColor("#ff9800"));
        about_us.setScaleType(ImageView.ScaleType.CENTER);

        menuObjects.add(close);
        menuObjects.add(building_info);
        menuObjects.add(users_info);
        menuObjects.add(units_info);
        menuObjects.add(charge_info);
        menuObjects.add(charge_list);
        menuObjects.add(charge_calculate);
        //menuObjects.add(meetings);
        menuObjects.add(about_us);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolBarTextView.setText("مدیریت مجتمع");
        mToolBarTextView.setTextColor(Color.parseColor("#ffffff"));
        mToolBarTextView.setTypeface(bhomaFont);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            System.exit(0);
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position){
            case 1:
                addFragment(new BuildingInformationFragment(), true, R.id.container);
                break;
            case 2:
                addFragment(new UsersInformationFragment(), true, R.id.container);
                break;
            case 3:
                addFragment(new UnitsInformationFragment(), true, R.id.container);
                break;
            case 4:addFragment(new BillsInformationFragment(),true,R.id.container);
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }
}

