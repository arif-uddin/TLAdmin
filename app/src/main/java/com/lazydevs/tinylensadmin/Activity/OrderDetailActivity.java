package com.lazydevs.tinylensadmin.Activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylensadmin.Adapter.OrderListAdapter;
import com.lazydevs.tinylensadmin.Model.ModelEarn;
import com.lazydevs.tinylensadmin.R;

import java.text.DateFormat;
import java.util.Date;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderStatus,orderId,orderDate,productType,productQuantity,orderDescription,buyerName,photoBy,tvPayment;
    ImageView orderedPhoto;
    Button btnReceive,btnReject,btnProcessing,btnDelivered;
    String OrderId;
    String ProfitBalance;
    private Handler mWaitHandler = new Handler();
    FirebaseAuth firebaseAuth;
    String PhotoOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        orderStatus=(TextView)findViewById(R.id.tv_order_status);
        orderedPhoto=(ImageView)findViewById(R.id.iv_ordered_photo);
        orderId=(TextView) findViewById(R.id.tv_orderid);
        orderDate=(TextView) findViewById(R.id.tv_order_date);
        productType=(TextView) findViewById(R.id.tv_product_type);
        productQuantity=(TextView) findViewById(R.id.tv_product_quantity);
        orderDescription=(TextView) findViewById(R.id.tv_order_description);
        buyerName=(TextView) findViewById(R.id.tv_buyer_name);
        photoBy=(TextView) findViewById(R.id.tv_photo_owner_name);
        tvPayment=(TextView)findViewById(R.id.tv_payment);

        btnReject=(Button)findViewById(R.id.btn_reject);
        btnReceive=(Button)findViewById(R.id.btn_receive);
        btnProcessing=(Button)findViewById(R.id.btn_processing);
        btnDelivered=(Button)findViewById(R.id.btn_delivered);

        orderStatus.setText("Order Status: "+" "+getIntent().getExtras().getString("order_status"));
        ProfitBalance=getIntent().getExtras().getString("profit");
        PhotoOwner=getIntent().getExtras().getString("photoOwnerId");

        Glide
                .with(getApplicationContext())
                .load(getIntent().getExtras().getString("ordered_image_url"))
                .into(orderedPhoto);

        OrderId=getIntent().getExtras().getString("order_id");
        orderId.setText("Order ID: "+" "+OrderId);
        orderDate.setText("Order Date: "+" "+getIntent().getExtras().getString("order_date"));
        productType.setText("Product Type: "+" "+getIntent().getExtras().getString("order_product_type"));
        productQuantity.setText("Quantity: "+" "+getIntent().getExtras().getString("order_product_quantity"));
        orderDescription.setText(getIntent().getExtras().getString("order_description"));
        buyerName.setText("Order by: "+getIntent().getExtras().getString("buyer_name"));
        photoBy.setText("Photo by: "+getIntent().getExtras().getString("owner_name"));

        String TvPayment=getIntent().getExtras().getString("payment");

        if (TvPayment==null)
        {
            tvPayment.setVisibility(TextView.GONE);
        }
        else
        {
            tvPayment.setText("Payment Received!!\nTransaction Id: "+TvPayment);
        }



        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query orderReject= FirebaseDatabase.getInstance().getReference().child("orders");
                orderReject.orderByChild("orderId").equalTo(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
                        databaseReference.child(OrderId).child("orderStatus").setValue("Rejected");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(OrderDetailActivity.this, "Order Rejected", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query orderReject= FirebaseDatabase.getInstance().getReference().child("orders");
                orderReject.orderByChild("orderId").equalTo(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
                        databaseReference.child(OrderId).child("orderStatus").setValue("Received");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(OrderDetailActivity.this, "Order Received", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });

        btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query orderReject= FirebaseDatabase.getInstance().getReference().child("orders");
                orderReject.orderByChild("orderId").equalTo(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
                        databaseReference.child(OrderId).child("orderStatus").setValue("Under Process");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(OrderDetailActivity.this, "Order is added to process", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query orderReject= FirebaseDatabase.getInstance().getReference().child("orders");
                orderReject.orderByChild("orderId").equalTo(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
                        databaseReference.child(OrderId).child("orderStatus").setValue("Delivered");
                        databaseReference.child(OrderId).child("deliveryDate").setValue(DateFormat.getDateTimeInstance().format(new Date()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                ModelEarn modelEarn= new ModelEarn(DateFormat.getDateTimeInstance().format(new Date()),ProfitBalance);
                databaseReference.child("profit").child(PhotoOwner).setValue(modelEarn);


                Toast.makeText(OrderDetailActivity.this, "Product is Delivered", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });
    }

    private String getUID() {
        FirebaseUser currentFirebaseUser = firebaseAuth.getCurrentUser();
        return currentFirebaseUser.getUid();
    }
    public void btn_back(View view) {
        onBackPressed();
        finish();

    }
}
