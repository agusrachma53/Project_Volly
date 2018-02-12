package com.voley.www.voley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomerDetail extends AppCompatActivity {

    private String urlData = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_getCustomer.php?idUser=";

    private RecyclerView recyclerViewMakanan;
    private CustomerAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private CustomerModel mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view = getSupportActionBar().getCustomView();

        Intent i = (Intent) getIntent();
        final String idCustomer = i.getExtras().getString("idCustomer");
        String name = i.getExtras().getString("nama");
        String phone = i.getExtras().getString("phone");
        String address = i.getExtras().getString("address");
        String email = i.getExtras().getString("email");

        TextView idCustomer2 = (TextView) findViewById(R.id.id_user);
        TextView nameCustomer = (TextView) findViewById(R.id.c_name);
        TextView phoneCustomer = (TextView) findViewById(R.id.c_phone);
        TextView addressCustomer = (TextView) findViewById(R.id.c_address);
        TextView emailCustomer = (TextView) findViewById(R.id.c_email);
        ImageButton backTOHome = (ImageButton) findViewById(R.id.backbtn);
        ImageButton updateData = (ImageButton) findViewById(R.id.editbtn);

        idCustomer2.setText(idCustomer);
        nameCustomer.setText(name);
        phoneCustomer.setText(phone);
        addressCustomer.setText(address);
        emailCustomer.setText(email);

        backTOHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(CustomerDetail.this,CustomerUpdateData.class);
                changeActivity.putExtra("idCustomer",idCustomer);
                startActivity(changeActivity);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
