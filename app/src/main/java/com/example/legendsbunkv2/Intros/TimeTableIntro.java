package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class TimeTableIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Create / Modify Time Table",
                "Here you can set Time Table to use other features of the app.",
                R.drawable.timetable_main,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Add subjects to the Time Table",
                "Using the dropdown add subject to each day of the week in the order you attend.",
                R.drawable.timetable_select_subject,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("How to add Labs/Special Classes",
                "If the labs/special classes are equivalent to multiple classes then add them multiple times in the list.",
                R.drawable.post_timetable_multiple,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Want to modify Time Table?",
                "Just Click on the subject to delete it from that days list",
                R.drawable.timetable_delete_click,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Prefs.putString("TimeTableIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Prefs.putString("TimeTableIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}