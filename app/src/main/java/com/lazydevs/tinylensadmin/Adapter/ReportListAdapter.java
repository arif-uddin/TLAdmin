package com.lazydevs.tinylensadmin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylensadmin.Model.ModelReport;
import com.lazydevs.tinylensadmin.R;

import java.util.ArrayList;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ViewHolder> {


    private final Context context;
    ArrayList<ModelReport> reports;

    public ReportListAdapter(Context context, ArrayList<ModelReport> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide
                .with(context)
                .load(reports.get(i).getImageUrl())
                .into(viewHolder.imageView);
        viewHolder.reportedId.setText(reports.get(i).getReportedName());
        viewHolder.reporterId.setText(reports.get(i).getReporterName());

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reporterId,reportedId;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reporterId=itemView.findViewById(R.id.tv_reported_by);
            reportedId=itemView.findViewById(R.id.tv_report_for);
            imageView=itemView.findViewById(R.id.iv_report_image);
        }
    }

    public void setValues(ModelReport report){
        reports.add(0,report);
        notifyDataSetChanged();
    }
}
