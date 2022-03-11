package com.xiayule.xiayucommom;

import android.graphics.Color;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiayule.commonlibrary.base.BaseActivity;
import com.xiayule.commonlibrary.utlis.KLog;
import com.xiayule.commonlibrary.utlis.StatusBarUtils;
import com.xiayule.xiayucommom.ui.dashboard.DashboardFragment;
import com.xiayule.xiayucommom.ui.home.HomeFragment;
import com.xiayule.xiayucommom.ui.notifications.NotificationsFragment;
import com.xiayule.xiayucommom.utils.BottomNavigationViewHelper;

import java.util.Objects;

public class MainActivity2 extends BaseActivity {

    String[] name = new String[]{
            "首页",
            "二页",
            "三页",
    };

    int[] icon = new int[]{
            R.drawable.icon_home,
            R.drawable.icon_collect,
            R.drawable.icon_my,
    };

    int[] itemId = new int[]{
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
    };

    private Fragment[] fragments = new Fragment[]{
            new HomeFragment(),
            new DashboardFragment(),
            new NotificationsFragment(),
    };

    String normalUrl = "http://www.abc.szzfyjzx.icu/icon6.png";
    String pressUrl = "http://www.abc.szzfyjzx.icu/icon7.png";

    String[] url = new String[]{pressUrl, normalUrl};

    BottomNavigationView navView;

    @Override
    public void initParam() {
        // 去除标题栏
//        Objects.requireNonNull(this.getSupportActionBar()).hide();

//        StatusBarUtils.getInstance().transparencyBar(this, Color.TRANSPARENT);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView() {
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        addView(navView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        /// 设置标题栏 样式中设置了无标题栏的时候 会造成闪退 或者在 initParam中 去除标题栏
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void addView(BottomNavigationView navView) {
        for (int i = 0; i < name.length; i++) {
            navView.getMenu().add(0, itemId[i], i, name[i]);
//            navView.getMenu().findItem(itemId[i]).setIcon(icon[i]);
            navView.getMenu().findItem(itemId[i]);
        }
    }


    @Override
    public void initData() {
        // 可以模拟请求成功后加载网络图标
        BottomNavigationViewHelper.replaceItemImage(navView, url);
    }

}