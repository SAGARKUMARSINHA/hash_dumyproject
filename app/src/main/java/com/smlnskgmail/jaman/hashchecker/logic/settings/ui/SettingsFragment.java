package com.smlnskgmail.jaman.hashchecker.logic.settings.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragmentCompat;

import com.smlnskgmail.jaman.hashchecker.BuildConfig;
import com.smlnskgmail.jaman.hashchecker.R;
import com.smlnskgmail.jaman.hashchecker.components.dialogs.system.AppSnackbar;
import com.smlnskgmail.jaman.hashchecker.components.states.AppBackClickTarget;
import com.smlnskgmail.jaman.hashchecker.components.vibrator.Vibrator;
import com.smlnskgmail.jaman.hashchecker.logic.database.DatabaseExporter;
import com.smlnskgmail.jaman.hashchecker.logic.database.HelperFactory;
import com.smlnskgmail.jaman.hashchecker.logic.settings.SettingsHelper;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.languages.Language;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.languages.LanguagesBottomSheet;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.themes.Theme;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.themes.ThemesBottomSheet;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.weblinks.WebLink;
import com.smlnskgmail.jaman.hashchecker.logic.settings.ui.lists.weblinks.WebLinksBottomSheet;
import com.smlnskgmail.jaman.hashchecker.tools.LogTool;
import com.smlnskgmail.jaman.hashchecker.tools.UITools;
import com.smlnskgmail.jaman.hashchecker.tools.WebTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SettingsFragment extends PreferenceFragmentCompat implements AppBackClickTarget {

    private ActionBar actionBar;
    private FragmentManager fragmentManager;
    private Context context;

    @SuppressLint("ResourceType")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        fragmentManager = getActivity().getSupportFragmentManager();
        context = getContext();

        initializeActionBar();
        initializeFileManagerSwitcher();
        initializeLanguageSettings();
        initializeThemesSettings();
        initializePrivacyPolicy();
        initializeUserDataExport();
        initializeAuthorLinks();
        initializeRateButton();
        initializeHelpWithTranslationButton();
        initializeAppVersionInfo();
    }

    private void initializeActionBar() {
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(context, R.drawable.ic_arrow_back));
    }

    private void initializeLanguageSettings() {
        findPreference(getString(R.string.key_language))
                .setOnPreferenceClickListener(preference -> {
            LanguagesBottomSheet languagesBottomSheet = new LanguagesBottomSheet();
            languagesBottomSheet.setList(Arrays.asList(Language.values()));
            languagesBottomSheet.show(fragmentManager);
            return false;
        });
    }

    private void initializeThemesSettings() {
        findPreference(getString(R.string.key_theme))
                .setOnPreferenceClickListener(preference -> {
            ThemesBottomSheet themesBottomSheet = new ThemesBottomSheet();
            themesBottomSheet.setList(Arrays.asList(Theme.values()));
            themesBottomSheet.show(fragmentManager);
            return false;
        });
    }

    private void initializePrivacyPolicy() {
        findPreference(getString(R.string.key_privacy_policy))
                .setOnPreferenceClickListener(preference -> {
            WebTools.openWebLink(context, context.getString(R.string.web_link_privacy_policy));
            return false;
        });
    }

    private void initializeUserDataExport() {
        findPreference(getString(R.string.key_export_user_data))
                .setOnPreferenceClickListener(preference -> {
            saveUserData();
            return false;
        });
    }

    private void saveUserData() {
        if (HelperFactory.getHelper().isHistoryItemsListIsEmpty()) {
            try {
                Intent saveFileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                saveFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                saveFileIntent.setType("application/zip");
                saveFileIntent.putExtra(Intent.EXTRA_TITLE, DatabaseExporter.EXPORT_FILE);
                startActivityForResult(
                        saveFileIntent,
                        SettingsHelper.FILE_CREATE
                );
            } catch (ActivityNotFoundException e) {
                LogTool.e(e);
                showSnackbar(
                        getString(R.string.message_error_start_file_selector)
                );
            }
        } else {
            showSnackbar(
                    getString(R.string.history_empty_view_message)
            );
        }
    }

    private void showSnackbar(@NonNull String message) {
        new AppSnackbar(
                context,
                getView(),
                message,
                new Vibrator(context),
                UITools.getAccentColor(context)
        ).show();
    }

    private void initializeAuthorLinks() {
        findPreference(getString(R.string.key_author))
                .setOnPreferenceClickListener(preference -> {
            WebLinksBottomSheet webLinksBottomSheet = new WebLinksBottomSheet();
            webLinksBottomSheet.setList(WebLink.getAuthorLinks());
            webLinksBottomSheet.show(fragmentManager);
            return false;
        });
    }

    private void initializeHelpWithTranslationButton() {
        findPreference(getString(R.string.key_help_with_translation))
                .setOnPreferenceClickListener(preference -> {
            WebTools.openWebLink(
                    context,
                    context.getString(R.string.web_link_help_with_translation)
            );
            return false;
        });
    }

    private void initializeRateButton() {
        findPreference(getString(R.string.key_rate_app))
                .setOnPreferenceClickListener(preference -> {
            openGooglePlay();
            return false;
        });
    }

    private void openGooglePlay() {
        final String appPackageName = context.getPackageName();
        Uri link;
        try {
            link = Uri.parse("market://details?id=" + appPackageName);
            context.startActivity(new Intent(Intent.ACTION_VIEW, link));
        } catch (ActivityNotFoundException e) {
            try {
                link = Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
                context.startActivity(new Intent(Intent.ACTION_VIEW, link));
            } catch (ActivityNotFoundException e2) {
                LogTool.e(e2);
                showSnackbar(
                        getString(R.string.message_error_start_google_play)
                );
            }
        }
    }

    private void initializeAppVersionInfo() {
        findPreference(getString(R.string.key_version)).setSummary(
                String.format("%s (%s)",
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        );
    }

    private void initializeFileManagerSwitcher() {
        findPreference(getString(R.string.key_inner_file_manager))
                .setOnPreferenceChangeListener((preference, o) -> {
            SettingsHelper.setRefreshSelectedFileStatus(context, true);
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        view.setBackgroundColor(UITools.getCommonBackgroundColor(context));
        setDividerHeight(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == SettingsHelper.FILE_CREATE) {
                if (resultCode == Activity.RESULT_OK) {
                    copyUserDataToUserFolder(data.getData());
                }
            }
        }
    }

    private void copyUserDataToUserFolder(@Nullable Uri uri) {
        if (uri != null) {
            try {
                DatabaseExporter.exportDatabase(context);
                ParcelFileDescriptor descriptor = context.getApplicationContext().getContentResolver()
                        .openFileDescriptor(uri, "w");
                if (descriptor != null) {
                    FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
                    copyFile(new File(DatabaseExporter.getUserDataZip(context)), outputStream);
                }
            } catch (IOException e) {
                LogTool.e(e);
            }
        }
    }

    private void copyFile(@NonNull File source, @NonNull FileOutputStream outputStream)
            throws IOException {
        try (InputStream inputStream = new FileInputStream(source)) {
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UITools.setActionBarTitle(
                actionBar,
                R.string.menu_title_settings
        );
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void appBackClick() {
        UITools.removeFragment(fragmentManager, this);
    }

}
