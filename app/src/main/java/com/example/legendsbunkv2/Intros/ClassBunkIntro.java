package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class ClassBunkIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("Class Bunk Calculator",
                "Don't get confused. Let me explain.",
                R.drawable.class_bunk_calc,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Any changes made here are temporary",
                "Everytime you open this option, it appears like the home screen except that the changes made here are'nt saved. " +
                        "This can be helpful to calculate your percentage if you are absent for the next class/classes.",
                R.drawable.class_bunk_calc,
                getColor(R.color.materialGrey),
                getColor(R.color.color_yellow_900),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));


    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Prefs.putString("ClassBunkIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Prefs.putString("ClassBunkIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}