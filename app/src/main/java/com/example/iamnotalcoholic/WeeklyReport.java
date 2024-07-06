package com.example.iamnotalcoholic;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeeklyReport extends AppCompatActivity {

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);


        Button btn = findViewById(R.id.goBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
                Activity activity = unwrap(v.getContext());
                activity.overridePendingTransition(R.anim.animation_enter_reverse, R.anim.animation_leave_reverse);
            }
        });
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM drinks;", null);
        while (query.moveToNext()) {
            String type = query.getString(0);
            int val = query.getInt(1);
            int pr = query.getInt(2);
            int pro = query.getInt(3);
            String dat = query.getString(4);
            System.out.println(type + " " + val + " " + pr + " " + pro + " " + dat);
        }
        query.close();
    }
}