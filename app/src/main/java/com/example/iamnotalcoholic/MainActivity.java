package com.example.iamnotalcoholic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvThisWeekOrMonth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public void showDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.delete:
                showDialog();
                return true;
            case R.id.export:
                return true;
            case R.id.edit:
                return true;
            case R.id.about:
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    public void addPageClick(View v) {
        Intent intent = new Intent(this, AddPage.class);
        startActivity(intent);
    }
    public void WeeklyReportClick(View v) {
        Intent intent = new Intent(this, WeeklyReport.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }
    public void LastPartyClick(View v) {
        Intent intent = new Intent(this, LastParty.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter_reverse, R.anim.animation_leave_reverse);
    }
}
