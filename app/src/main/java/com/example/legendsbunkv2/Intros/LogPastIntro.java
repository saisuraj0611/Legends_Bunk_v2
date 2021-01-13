package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class LogPastIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("Forgot to post your attendance?",
                "Don't worry, follow the next steps to post previous attendance.",
                R.drawable.logpast_main,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Select date",
                "Choose your day to log your attendance.",
                R.drawable.logpast_date_selector,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Post your attendance",
                "Use the buttons to post attendance for corresponding subjects.",
                R.drawable.logpast_subjecy_buttons,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Traverse through the dates",
                "Choose the arrow buttons to travers through the dates with ease and post attendance for multiple days.",
                R.drawable.logpast_date_arrows,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Prefs.putString("LogPastFragmentIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Prefs.putString("LogPastFragmentIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}
