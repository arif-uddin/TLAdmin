package com.lazydevs.tinylensadmin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylensadmin.R;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderStatus,orderId,orderDate,productType,productQuantity,orderDescription,buyerName,photoBy;
    ImageView orderedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderStatus=(TextView)findViewById(R.id.tv_order_status);
        orderedPhoto=(ImageView)findViewById(R.id.iv_ordered_photo);
        orderId=(TextView) findViewById(R.id.tv_orderid);
        orderDate=(TextView) findViewById(R.id.tv_order_date);
        productType=(TextView) findViewById(R.id.tv_product_type);
        productQuantity=(TextView) findViewById(R.id.tv_product_quantity);
        orderDescription=(TextView) findViewById(R.id.tv_order_description);
        buyerName=(TextView) findViewById(R.id.tv_buyer_name);
        photoBy=(TextView) findViewById(R.id.tv_photo_owner_name);

        orderStatus.setText("Order Status: "+" "+getIntent().getExtras().getString("order_status"));

        Glide
                .with(getApplicationContext())
                .load(getIntent().getExtras().getString("ordered_image_url"))
                .into(orderedPhoto);
        orderId.setText("Order ID: "+" "+getIntent().getExtras().getString("order_id"));
        orderDate.setText("Order Date: "+" "+getIntent().getExtras().getString("order_date"));
        productType.setText("Product Type: "+" "+getIntent().getExtras().getString("order_product_type"));
        productQuantity.setText("Quantity: "+" "+getIntent().getExtras().getString("order_product_quantity"));
        orderDescription.setText(getIntent().getExtras().getString("order_description"));
        buyerName.setText("Order by: "+getIntent().getExtras().getString("buyer_name"));
        photoBy.setText("Photo by: "+getIntent().getExtras().getString("owner_name"));
    }

    public void btn_back(View view) {
        Intent intent=new Intent(OrderDetailActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
