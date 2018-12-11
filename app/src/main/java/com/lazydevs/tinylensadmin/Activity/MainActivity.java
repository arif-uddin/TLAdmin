package com.lazydevs.tinylensadmin.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylensadmin.Adapter.OrderListAdapter;
import com.lazydevs.tinylensadmin.Model.ModelOrder;
import com.lazydevs.tinylensadmin.Model.ModelUser;
import com.lazydevs.tinylensadmin.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    RecyclerView recyclerVieworderListView;
    OrderListAdapter orderListAdapter;

    ArrayList<ModelOrder> orders;
    ArrayList<ModelUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orders = new ArrayList<>();
        users = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("orders");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerVieworderListView=findViewById(R.id.rv_order_list);

        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerVieworderListView.setLayoutManager(linearVertical);
        orderListAdapter = new OrderListAdapter(getApplicationContext(),orders);
        recyclerVieworderListView.setAdapter(orderListAdapter);

//        Query query = FirebaseDatabase.getInstance().getReference().child("orders");
//        query.orderByKey().addChildEventListener(new QueryForOrders());
    }

    @Override
    protected void onResume() {
        super.onResume();
        orders.clear();
        Query query = FirebaseDatabase.getInstance().getReference().child("orders");
        query.orderByKey().addChildEventListener(new QueryForOrders());
    }

    public void btn_order_list(View view) {

    }

    public void btn_report_order_list(View view) {
        Intent intent=new Intent(MainActivity.this,ReportActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private class QueryForOrders implements ChildEventListener {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final ModelOrder modelOrder = dataSnapshot.getValue(ModelOrder.class);

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.child(modelOrder.getBuyerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    String buyerName = modelUser.getFirstName()+" "+modelUser.getLastName();
                    modelOrder.setBuyerName(buyerName);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            databaseReference.child(modelOrder.getPhotoOwnerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    String ownerName = modelUser.getFirstName()+" "+modelUser.getLastName();
                    modelOrder.setOwnerName(ownerName);

                    orderListAdapter.setValues(modelOrder);
                    orderListAdapter.notifyDataSetChanged();
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
