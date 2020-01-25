package com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.themes;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashchecker.R;
import com.smlnskgmail.jaman.hashchecker.components.bottomsheets.lists.ListItemTarget;

public enum Theme implements ListItemTarget {

    LIGHT(
            R.string.title_theme_light,
            R.style.AppThemeLight
    ),
    DARK(
            R.string.title_theme_dark,
            R.style.AppThemeDark
    );

    private final int titleResId;
    private final int themeResId;

    Theme(int titleResId, int themeResId) {
        this.titleResId = titleResId;
        this.themeResId = themeResId;
    }

    @Override
    public String getTitle(@NonNull Context context) {
        return context.getString(titleResId);
    }

    @Override
    public int getPrimaryIconResId() {
        return R.drawable.ic_settings_theme;
    }

    @Override
    public int getAdditionalIconResId() {
        return R.drawable.ic_done;
    }

    public int getThemeResId() {
        return themeResId;
    }
}
