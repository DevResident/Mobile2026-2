package com.example.clase12;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_NIGHT_MODE = "night_mode";

    private MaterialButtonToggleGroup themeModeToggle;
    private boolean settingThemeToggleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeMode = getSavedThemeMode();
        AppCompatDelegate.setDefaultNightMode(themeMode);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        themeModeToggle = findViewById(R.id.themeModeToggle);
        configureThemeToggle(themeMode);
        updateSystemBarIcons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            positionThemeToggle(systemBars.top);
            updateSystemBarIcons();
            return insets;
        });

        if (savedInstanceState == null) {
            loadFragment(new CursosFragment(), false);
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainerView2, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void configureThemeToggle(int themeMode) {
        settingThemeToggleState = true;
        themeModeToggle.check(getButtonIdForThemeMode(themeMode));
        settingThemeToggleState = false;

        themeModeToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (settingThemeToggleState || !isChecked) return;

            int selectedMode = getThemeModeForButtonId(checkedId);
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    .edit()
                    .putInt(KEY_THEME_MODE, selectedMode)
                    .apply();
            AppCompatDelegate.setDefaultNightMode(selectedMode);
        });
    }

    private void positionThemeToggle(int statusBarTop) {
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) themeModeToggle.getLayoutParams();
        int topMargin = statusBarTop + dp(8);
        if (params.topMargin != topMargin) {
            params.topMargin = topMargin;
            themeModeToggle.setLayoutParams(params);
        }
    }

    private int getSavedThemeMode() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains(KEY_THEME_MODE)) {
            int savedMode = prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_YES);
            return savedMode == AppCompatDelegate.MODE_NIGHT_NO
                    ? AppCompatDelegate.MODE_NIGHT_NO
                    : AppCompatDelegate.MODE_NIGHT_YES;
        }
        if (prefs.contains(KEY_NIGHT_MODE)) {
            return prefs.getBoolean(KEY_NIGHT_MODE, true)
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO;
        }
        return AppCompatDelegate.MODE_NIGHT_YES;
    }

    private int getButtonIdForThemeMode(int themeMode) {
        if (themeMode == AppCompatDelegate.MODE_NIGHT_NO) {
            return R.id.btnThemeLight;
        }
        if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            return R.id.btnThemeDark;
        }
        return R.id.btnThemeDark;
    }

    private int getThemeModeForButtonId(int checkedId) {
        if (checkedId == R.id.btnThemeLight) {
            return AppCompatDelegate.MODE_NIGHT_NO;
        }
        if (checkedId == R.id.btnThemeDark) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        }
        return AppCompatDelegate.MODE_NIGHT_YES;
    }

    private void updateSystemBarIcons() {
        int nightMask = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = nightMask == Configuration.UI_MODE_NIGHT_YES;
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(!isNightMode);
        controller.setAppearanceLightNavigationBars(!isNightMode);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
