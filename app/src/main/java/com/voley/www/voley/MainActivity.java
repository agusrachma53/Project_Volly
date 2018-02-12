package com.voley.www.voley;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String urlData = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_getCustomer.php";
    private RecyclerView recyclerViewMakanan;
    private CustomerAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private List<CustomerModel> mListData;
    private FloatingActionButton insertData;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_main);
        View view = getSupportActionBar().getCustomView();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        recyclerViewMakanan = (RecyclerView) findViewById(R.id.recyclerview);
        mListData = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(MainActivity.this);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();



        getData();

        insertData = (FloatingActionButton) findViewById(R.id.addCustomerbtn);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postData = new Intent(MainActivity.this, CustomerInsertData.class);
                startActivity(postData);
            }
        });

    }

    public void getData(){

        final StringRequest request = new StringRequest(Request.Method.GET, urlData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                CustomerProcess customerProcess = new CustomerProcess();

                //passing data list ke adapter
                mAdapter = new CustomerAdapter(customerProcess.getAllCustomer(response),MainActivity.this);
                mAdapter.notifyDataSetChanged();
                recyclerViewMakanan.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerViewMakanan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewMakanan.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                builder.setTitle("Information");
                builder.setCancelable(true);
                builder.setMessage("Internet not available, Cross check your internet connectivity and try again");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       mProgressDialog.show();
                       getData();
                    }
                });
                builder.setNegativeButton("Cloase Aplication", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog showAlert = builder.create();
                showAlert.setCanceledOnTouchOutside(false);
                showAlert.show();
            }
        });

        WebServices.getmInstance(MainActivity.this).addToRequestque(request);

    }

    @Override
    public void onBackPressed() {
        builder.setTitle("Information");
        builder.setCancelable(true);
        builder.setMessage("Close This Aplication ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getData();
            }
        });
        AlertDialog xxx = builder.create();
        xxx.setCanceledOnTouchOutside(false);
        xxx.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


    /* End Get All Data Voly */
}
