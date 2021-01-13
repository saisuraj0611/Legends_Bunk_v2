package com.example.legendsbunkv2.Intros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class HomeFragmentIntro extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


                addSlide(AppIntroFragment.newInstance("Welcome to the Smartest Attendance Manager",
                        "Click next to understand the basic functionality of the app",
                        R.drawable.main_fresh_install,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Adding Subjects",
                        "Click the + button",
                        R.drawable.add_subject,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Adding Subjects",
                        "Name your subject and click ADD",
                        R.drawable.add_subject_dialog,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Posting Present",
                        "Click the Tick to post present for the corresponding subject & the total presents for that subject are shown below it.",
                        R.drawable.present_button_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Posting Absent",
                        "Click the X to post absents for the corresponding subject & the total absents for that subject are shown below it.",
                        R.drawable.absent_button_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Posting Cancelled",
                        "Click the Blocked button to post cancelled for that subject.",
                        R.drawable.cancelled_button_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Posting Cancelled",
                        "The cancel is equivalent to not posting attendance as it dose'nt effect the percentage but can be used to keep track of the cancelled classes.",
                        R.drawable.cancelled_button_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Individual subject status",
                        "Shows the no. of classes that can be bunked or to be attended based on the threshold attendance set.",
                        R.drawable.status_subject_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Want to change the Threshold?",
                        "The default threshold is 75% but can be set to any value between 1-99% from the Navigation Drawer.",
                        R.drawable.navigation_drawer_threshold,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Delete subjects",
                        "Click the delete icon beside the + button",
                        R.drawable.delete_subject_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Delete subjects",
                        "Choose the subjects to be deleted and click Delete. Remember this cant be undone!",
                        R.drawable.delete_subject_main_dialog,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_red_900),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Explore the Features :)",
                        "Click the navigation drawer icon or Swipe from the left to access other amazing features!",
                        R.drawable.navigation_drawer_icon,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Explore the Features :)",
                        "Make sure to check the Vacation Planner feature!",
                        R.drawable.navigation_drawer,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));

                addSlide(AppIntroFragment.newInstance("Need help regarding a feature?",
                        "Click the icon in the toolbar to get details of that feature.",
                        R.drawable.help_home,
                        getColor(R.color.materialGrey),
                        getColor(R.color.color_white_1000),
                        getColor(R.color.color_white_1000),
                        R.font.secular_one_cumilative,
                        R.font.prompt));




    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Prefs.remove("HomeFragmentIntro");
        Prefs.putString("HomeFragmentIntro","disabled");

        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Prefs.remove("HomeFragmentIntro");
        Prefs.putString("HomeFragmentIntro","disabled");
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);
    }
}
