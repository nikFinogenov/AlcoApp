package com.example.iamnotalcoholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class WeeklyReport extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;
    private List<ReportItem> reportItems;

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        reportItems = generateReportItems(); // Здесь вы должны генерировать данные для отчета
        reportAdapter = new ReportAdapter(reportItems);
        recyclerView.setAdapter(reportAdapter);

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
    }
    private List<ReportItem> generateReportItems() {
        List<ReportItem> items = new ArrayList<>();
        // Добавьте вашу логику для генерации данных для отчета (тип, объем, крепкость, цена)
        // Пример:
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        items.add(new ReportItem("Вино", "200 мл", "12%", "500 руб"));
        items.add(new ReportItem("Пиво", "500 мл", "5%", "300 руб"));
        // Добавьте остальные элементы
        return items;
    }
}