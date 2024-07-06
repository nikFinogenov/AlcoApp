package com.example.iamnotalcoholic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<ReportItem> reportItems;

    public ReportAdapter(List<ReportItem> reportItems) {
        this.reportItems = reportItems;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_row, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        ReportItem currentItem = reportItems.get(position);
        holder.textType.setText(currentItem.getType());
        holder.textVolume.setText(currentItem.getVolume());
        holder.textStrength.setText(currentItem.getStrength());
        holder.textPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return reportItems.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView textType, textVolume, textStrength, textPrice;

        public ReportViewHolder(View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textVolume = itemView.findViewById(R.id.textVolume);
            textStrength = itemView.findViewById(R.id.textStrength);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }
}
