package com.example.meetdoc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    LinearLayout home,chat,schedule,profile;
    private ImageView homeIcon, chatIcon, scheduleIcon, profileIcon;
    private TextView homeText, chatText, scheduleText, profileText;

    private final int SELECTED_COLOR = Color.parseColor("#00BCD4"); // Purple
    private final int DEFAULT_COLOR = Color.parseColor("#757575");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        home = findViewById(R.id.home);
        chat = findViewById(R.id.chat);
        schedule = findViewById(R.id.schedule);
        profile = findViewById(R.id.profile);
        homeIcon = home.findViewById(R.id.home_icon);
        chatIcon = chat.findViewById(R.id.chat_icon);
        scheduleIcon = schedule.findViewById(R.id.schedule_icon);
        profileIcon = profile.findViewById(R.id.profile_icon);

        // Initialize TextViews
        homeText = home.findViewById(R.id.home_text);
        chatText = chat.findViewById(R.id.chat_text);
        scheduleText = schedule.findViewById(R.id.schedule_text);
        profileText = profile.findViewById(R.id.profile_text);


        // Set Default Selected Tab
        setSelectedTab(home, homeIcon, homeText);
        loadFragment(new Home());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(home, homeIcon, homeText);
                loadFragment(new Home());
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(chat, chatIcon, chatText);
                loadFragment(new BookSlot());
            }
        });


        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PatientAppointment.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTab(profile, profileIcon, profileText);
                loadFragment(new Schedule());
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void setSelectedTab(LinearLayout selectedTab, ImageView selectedIcon, TextView selectedText) {
        resetAllTabs();
        selectedIcon.setColorFilter(SELECTED_COLOR);
        selectedText.setTextColor(SELECTED_COLOR);
    }

    private void resetAllTabs() {
        homeIcon.setColorFilter(DEFAULT_COLOR);
        chatIcon.setColorFilter(DEFAULT_COLOR);
        scheduleIcon.setColorFilter(DEFAULT_COLOR);
        profileIcon.setColorFilter(DEFAULT_COLOR);

        homeText.setTextColor(DEFAULT_COLOR);
        chatText.setTextColor(DEFAULT_COLOR);
        scheduleText.setTextColor(DEFAULT_COLOR);
        profileText.setTextColor(DEFAULT_COLOR);
    }
}