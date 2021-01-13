package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class VacationIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("This is the Vacation Planner!",
                "You will never regret your vacation when you plan it according to your attendance :)",
                R.drawable.vacation_main,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Choose the dates",
                "Set the departure and arrival for your vacation",
                R.drawable.vacation_main,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Do you have holidays during your vacation?",
                "Mark the holidays and click next.",
                R.drawable.vacation_holidays,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Mark your additional absents",
                "If there is any extra class in a subject during your vacation, mark that subject in negative(-ve) with the number of classes of that " +
                        "subject you will miss additionally along with those from the time table.",
                R.drawable.vacation_additional,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Mark your classes that are cancelled during your vacation",
                "If there is any class in a subject which is cancelled during your vacation, mark that subject in positive(+ve) with the number of classes of that " +
                        "subject that are cancelled.",
                R.drawable.vacation_additional,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Note",
                "The positive value of a subject can't exceed the max number of classes that subject is present in the time table for the duration of your" +
                        " vacation",
                R.drawable.vacation_additional,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));

        addSlide(AppIntroFragment.newInstance("Estimated attendance after vacation",
                "Enjoy your vacation in peace V",
                R.drawable.vacation_result,
                getColor(R.color.materialGrey),
                getColor(R.color.color_white_1000),
                getColor(R.color.color_white_1000),
                R.font.secular_one_cumilative,
                R.font.prompt));


    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Prefs.putString("VacationIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Prefs.putString("VacationIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}
