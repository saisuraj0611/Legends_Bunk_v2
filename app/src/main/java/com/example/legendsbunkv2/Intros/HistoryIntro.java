package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class HistoryIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Select the date range to view attendance",
                "By default the initial date is set to the date on which the first attendance was posted.",
                R.drawable.history_date_select,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));


        addSlide(AppIntroFragment.newInstance("View History",
                "",
                R.drawable.history_list,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Delete posted attendance",
                "If you have posted additional attendance or wrong attendance then you can click on the attendance to delete it.",
                R.drawable.history_delete_dialog,
                getColor(R.color.materialGrey),
                getColor(R.color.color_red_900),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Prefs.putString("HistoryIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Prefs.putString("HistoryIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}