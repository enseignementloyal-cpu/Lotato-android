package com.lotato.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lotato.pro.R;
import com.lotato.pro.api.ApiService;
import com.lotato.pro.models.AppSession;
import com.lotato.pro.print.SunmiPrintHelper;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHome, navHistory, navReports, navWinners;
    private TextView tvAgentName;
    private int activeTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppSession session = AppSession.getInstance(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        ApiService.getInstance().setAuthToken(session.getToken());
        SunmiPrintHelper.getInstance().initPrinter(this);

        tvAgentName = findViewById(R.id.tvAgentName);
        tvAgentName.setText(session.getAgentName());

        navHome = findViewById(R.id.navHome);
        navHistory = findViewById(R.id.navHistory);
        navReports = findViewById(R.id.navReports);
        navWinners = findViewById(R.id.navWinners);

        navHome.setOnClickListener(v -> selectTab(0));
        navHistory.setOnClickListener(v -> selectTab(1));
        navReports.setOnClickListener(v -> selectTab(2));
        navWinners.setOnClickListener(v -> selectTab(3));

        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());

        selectTab(0);
    }

    private void selectTab(int tab) {
        activeTab = tab;
        updateNavColors();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        switch (tab) {
            case 1: fragment = new HistoryFragment(); break;
            case 2: fragment = new ReportsFragment(); break;
            case 3: fragment = new WinnersFragment(); break;
            default: fragment = new DrawSelectionFragment(); break;
        }

        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    private void updateNavColors() {
        int active = getResources().getColor(R.color.secondary);
        int inactive = getResources().getColor(R.color.text_dim);

        getNavIcon(navHome).setTextColor(activeTab == 0 ? active : inactive);
        getNavLabel(navHome).setTextColor(activeTab == 0 ? active : inactive);
        getNavIcon(navHistory).setTextColor(activeTab == 1 ? active : inactive);
        getNavLabel(navHistory).setTextColor(activeTab == 1 ? active : inactive);
        getNavIcon(navReports).setTextColor(activeTab == 2 ? active : inactive);
        getNavLabel(navReports).setTextColor(activeTab == 2 ? active : inactive);
        getNavIcon(navWinners).setTextColor(activeTab == 3 ? active : inactive);
        getNavLabel(navWinners).setTextColor(activeTab == 3 ? active : inactive);
    }

    private TextView getNavIcon(LinearLayout nav) {
        return (TextView) nav.getChildAt(0);
    }

    private TextView getNavLabel(LinearLayout nav) {
        return (TextView) nav.getChildAt(1);
    }

    private void logout() {
        SunmiPrintHelper.getInstance().destroyService(this);
        AppSession.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SunmiPrintHelper.getInstance().destroyService(this);
    }
}
