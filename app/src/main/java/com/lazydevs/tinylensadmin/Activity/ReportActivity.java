package com.lazydevs.tinylensadmin.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylensadmin.Adapter.ReportListAdapter;
import com.lazydevs.tinylensadmin.Model.ModelReport;
import com.lazydevs.tinylensadmin.Model.ModelUser;
import com.lazydevs.tinylensadmin.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    RecyclerView recyclerViewReportListView;
    ReportListAdapter reportListAdapter;

    ArrayList<ModelReport> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reports= new ArrayList<>();
        recyclerViewReportListView=(RecyclerView)findViewById(R.id.rv_report_list);

        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewReportListView.setLayoutManager(linearVertical);
        reportListAdapter = new ReportListAdapter(getApplicationContext(),reports);
        recyclerViewReportListView.setAdapter(reportListAdapter);

        Query query = FirebaseDatabase.getInstance().getReference().child("reports");
        query.orderByKey().addChildEventListener(new QueryForReports());
    }

    public void btn_orderlist_report(View view) {
        onBackPressed();
        finish();
        this.overridePendingTransition(0, 0);
    }

    private class QueryForReports implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelReport modelReport= dataSnapshot.getValue(ModelReport.class);

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.child(modelReport.getReporterId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    String reporterName=modelUser.getFirstName()+" "+modelUser.getLastName();
                    modelReport.setReporterName(reporterName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databaseReference.child(modelReport.getReportedId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    String reportedName=modelUser.getFirstName()+" "+modelUser.getLastName();
                    modelReport.setReportedName(reportedName);

                    reportListAdapter.setValues(modelReport);
                    reportListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
