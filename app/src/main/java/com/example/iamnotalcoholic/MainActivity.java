package com.example.iamnotalcoholic;

import androidx.activity.*;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvThisWeekOrMonth;

    @Deprecated
    public void onBackPressed(View v) {
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure you use the correct layout file name here

        tvThisWeekOrMonth = findViewById(R.id.tvThisWeekOrMonth);

        tvThisWeekOrMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tvThisWeekOrMonth.getText().toString();
                if (currentText.equals("This week")) {
                    tvThisWeekOrMonth.setText("This month");
                } else {
                    tvThisWeekOrMonth.setText("This week");
                }
            }
        });

        Button btnAddNewEntry = findViewById(R.id.btnAddNewEntry);
        btnAddNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPageClick(v);
                // Handle the new entry addition here
            }
        });
        ImageButton btnWeeklyEntry = findViewById(R.id.btnEntryLeft);
        btnWeeklyEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyReportClick(v);
                // Handle the new entry addition here
            }
        });
        ImageButton btnLastEntry = findViewById(R.id.btnEntryRight);
        btnLastEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastPartyClick(v);
                // Handle the new entry addition here
            }
        });

    }
    public void addPageClick(View v) {
        Intent intent = new Intent(v.getContext(), AddPage.class);
        v.getContext().startActivity(intent);
    }
    public void WeeklyReportClick(View v) {
        Intent intent = new Intent(v.getContext(), WeeklyReport.class);
        v.getContext().startActivity(intent);
        Activity activity = (Activity) v.getContext();
        activity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }
    public void LastPartyClick(View v) {
        Intent intent = new Intent(v.getContext(), LastParty.class);
        v.getContext().startActivity(intent);
        Activity activity = (Activity) v.getContext();
        activity.overridePendingTransition(R.anim.animation_enter_reverse, R.anim.animation_leave_reverse);
    }
}
