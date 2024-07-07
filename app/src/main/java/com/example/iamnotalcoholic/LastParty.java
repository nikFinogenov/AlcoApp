package com.example.iamnotalcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LastParty extends AppCompatActivity {

    private LinearLayout drinkContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_party);

        drinkContainer = findViewById(R.id.drinkContainer);
        Button btn = findViewById(R.id.goBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });
        loadDataFromDatabase();
    }
    private void loadDataFromDatabase() {
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("Select date from drinks order by date desc limit 1", null);
        if(cursor.moveToNext()) {
            String date = cursor.getString(0);
            TextView dateTextView = findViewById(R.id.dateParty);
            dateTextView.setText(date);
            String sql = String.format("SELECT * FROM drinks where date = '%s';", date);
            cursor.close();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String type = cursor.getString(0);
                int volume = cursor.getInt(1);
                int strength = cursor.getInt(2);
                double price = cursor.getDouble(3);
                View drinkCard = LayoutInflater.from(this).inflate(R.layout.drink_card, null);
                TextView typeTextView = drinkCard.findViewById(R.id.typeTextView);
                TextView volumeTextView = drinkCard.findViewById(R.id.volumeTextView);
                TextView strengthTextView = drinkCard.findViewById(R.id.strengthTextView);
                TextView priceTextView = drinkCard.findViewById(R.id.priceTextView);
                typeTextView.setText(type);
                volumeTextView.setText("Volume: " + volume);
                strengthTextView.setText("Strength: " + strength);
                priceTextView.setText("Price: $" + price);
                drinkContainer.addView(drinkCard);
                if (drinkContainer.getChildCount() >= 1) {
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
}