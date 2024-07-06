package com.example.iamnotalcoholic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class WeeklyReport extends AppCompatActivity {

    private LinearLayout drinkContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);

        drinkContainer = findViewById(R.id.drinkContainer);
        Button btn = findViewById(R.id.goBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animation_enter_reverse, R.anim.animation_leave_reverse);
            }
        });

        // Load data from database
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM drinks ORDER BY date ASC;", null);
        String lastDate = "";
        while (cursor.moveToNext()) {
            String type = cursor.getString(0);
            int volume = cursor.getInt(1);
            int strength = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String date = cursor.getString(4);

            // Inflate the drink card layout
            View drinkCard = LayoutInflater.from(this).inflate(R.layout.drink_card, null);

            // Bind data to the views in the card
            TextView dateTextView = drinkCard.findViewById(R.id.dateTextView);
            View dateLine = drinkCard.findViewById(R.id.dateLine);
            TextView typeTextView = drinkCard.findViewById(R.id.typeTextView);
            TextView volumeTextView = drinkCard.findViewById(R.id.volumeTextView);
            TextView strengthTextView = drinkCard.findViewById(R.id.strengthTextView);
            TextView priceTextView = drinkCard.findViewById(R.id.priceTextView);

            // Check if the date has changed to display section headers
            if (!date.equals(lastDate)) {
                lastDate = date;
                dateTextView.setVisibility(View.VISIBLE);
                dateLine.setVisibility(View.VISIBLE);
                dateTextView.setText(date);
            } else {
                dateTextView.setVisibility(View.GONE);
                dateLine.setVisibility(View.INVISIBLE);
            }

            typeTextView.setText(type);
            volumeTextView.setText("Volume: " + volume);
            strengthTextView.setText("Strength: " + strength);
            priceTextView.setText("Price: $" + price);

            // Add the drink card to the container
            drinkContainer.addView(drinkCard);

            // Add a separator line between cards (optional)
            if (drinkContainer.getChildCount() > 1) {
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                drinkContainer.addView(divider);
            }
        }
        cursor.close();
        db.close();
    }


}
