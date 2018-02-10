package com.voley.www.voley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private Button insertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        recyclerViewMakanan = (RecyclerView) findViewById(R.id.recyclerview);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();
        mListData = new ArrayList<>();
        getData();
        insertData = (Button) findViewById(R.id.postData);
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
                iniData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        WebServices.getmInstance(MainActivity.this).addToRequestque(request);

    }

    public void iniData(String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);

            //ini toast untuk menampilkan pesan sukses dari json
            Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            // ini utk mengambil attribute array yg ada di json (yaitu attribute data)
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            //looping utk array
            for(int i=0; i<jsonArray.length(); i++){
                //get json berdasarkan banyaknya data (index i)
                JSONObject objectMakanan = jsonArray.getJSONObject(i);

                //get data berdasarkan attribte yang ada dijsonnya (harus sama)
                String nama = objectMakanan.getString("name");
                String email = objectMakanan.getString("email");
                String phone = objectMakanan.getString("phone");
                String address = objectMakanan.getString("address");
                String idCustomer = objectMakanan.getString("id");

                //add data ke modelnya
                CustomerModel customerModel = new CustomerModel();
                customerModel.setNama(nama);
                customerModel.setEmail(email);
                customerModel.setPhone(phone);
                customerModel.setAddress(address);
                customerModel.setIdCustomer(idCustomer);

                //add model ke list
                mListData.add(customerModel);

                //passing data list ke adapter
                mAdapter = new CustomerAdapter(mListData, MainActivity.this);
                mAdapter.notifyDataSetChanged();
                recyclerViewMakanan.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerViewMakanan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewMakanan.setAdapter(mAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
     /* End Get All Data Voly */
}
