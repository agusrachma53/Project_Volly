package com.voley.www.voley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pchmn.androidverify.Form;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerUpdateData extends AppCompatActivity {

    private EditText name,address,country,province,city,postCode,email,username,password,phone,bankAccount;
    private String v_idCustomer,v_name,v_address,v_country,v_province,v_city,
            v_postCode,v_email,v_username,v_password,v_gender,v_phone,v_bankAccount,message;
    private Button updateData,batalInsert;
    private ArrayAdapter<CharSequence> adapter;
    private Context context;
    private Snackbar snackbar;
    private ProgressDialog mProgressDialog;
    private ConstraintLayout constraintLayout;
    private Spinner gender;
    private String BaseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_getCustomer.php?idCustomer=";
    private String BaseUrlUpdate = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_updateCustomer.php?idCustomer=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        /* Get Id Customer */
        Intent i = getIntent();
        v_idCustomer = i.getExtras().getString("idCustomer");

        name = (EditText) findViewById(R.id.u_name);
        address = (EditText) findViewById(R.id.u_address);
        country = (EditText) findViewById(R.id.u_country);
        province = (EditText) findViewById(R.id.u_province);
        city = (EditText) findViewById(R.id.u_city);
        postCode = (EditText) findViewById(R.id.u_post_code);
        email = (EditText) findViewById(R.id.u_email);
        username = (EditText) findViewById(R.id.u_username);
        password = (EditText) findViewById(R.id.u_password);
        gender = (Spinner) findViewById(R.id.u_ganderx);
        phone = (EditText) findViewById(R.id.u_phone);
        bankAccount = (EditText) findViewById(R.id.u_bank_account);

        /* Loading */
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();

        /* Get Data Volly */
        getDetailDataVolly(v_idCustomer);

        updateData = (Button) findViewById(R.id.updateData);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
                UpdateCustomer(v_idCustomer);
            }
        });

    }

    public void getDetailDataVolly(String idCustomer){

        final StringRequest request = new StringRequest(Request.Method.GET, BaseUrl + idCustomer, new Response.Listener<String>() {
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

        WebServices.getmInstance(CustomerUpdateData.this).addToRequestque(request);

    }

    public void iniData(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);

            // ini utk mengambil attribute array yg ada di json (yaitu attribute data)
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            /* Looping Array */
            for (int i =  0; i < jsonArray.length(); i++){

                JSONObject getDataCustomer = jsonArray.getJSONObject(i);

                /* get data berdasarkan attribte yang ada dijsonnya (harus sama) */
                v_name = getDataCustomer.getString("name");
                v_address = getDataCustomer.getString("address");
                v_country = getDataCustomer.getString("country");
                v_province = getDataCustomer.getString("province");
                v_city = getDataCustomer.getString("city");
                v_postCode = getDataCustomer.getString("post_code");
                v_email = getDataCustomer.getString("email");
                v_username = getDataCustomer.getString("username");
                v_password = getDataCustomer.getString("password");
                v_gender = getDataCustomer.getString("gender");
                v_phone = getDataCustomer.getString("phone");
                v_bankAccount = getDataCustomer.getString("bank_account");

                /* Selected Gender */
                adapter = ArrayAdapter.createFromResource(this, R.array.gd,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                gender.setAdapter(adapter);

                /* Filtering Selected Gender */
                int setSelectedGender = 0;
                for(int e = 0; e < adapter.getCount(); e++){
                    setSelectedGender = adapter.getPosition(v_gender);
                }


                /* Set Text Data To EditText */
                name.setText(v_name);
                address.setText(v_address);
                country.setText(v_country);
                province.setText(v_province);
                city.setText(v_city);
                postCode.setText(v_postCode);
                email.setText(v_email);
                username.setText(v_username);
                password.setText(v_password);
                gender.setSelection(setSelectedGender);
                phone.setText(v_phone);
                bankAccount.setText(v_bankAccount);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void UpdateCustomer(String idCustomer){

        /* Validation */
        final Form form = new Form.Builder(CustomerUpdateData.this).showErrors(true).build();
        if(form.isValid()){
            /* get Value From Edit Text */
            v_name = name.getText().toString();
            v_address = address.getText().toString();
            v_country = country.getText().toString();
            v_province = province.getText().toString();
            v_city = city.getText().toString();
            v_postCode = postCode.getText().toString();
            v_email = email.getText().toString();
            v_username = username.getText().toString();
            v_password = password.getText().toString();
            v_gender = gender.getSelectedItem().toString();
            v_phone = phone.getText().toString();
            v_bankAccount = bankAccount.getText().toString();

            Log.d("v_name",""+v_name);

            RequestQueue queue = Volley.newRequestQueue(this);



            StringRequest updateRequest = new StringRequest(Request.Method.POST, BaseUrlUpdate + idCustomer, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    mProgressDialog.dismiss();
                    /* Responese */
                    try {
                        JSONObject  jsonObject = new JSONObject(response);
                        String pesan = jsonObject.getString("message");
                        Log.d("responya",""+response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response", String.valueOf(error));
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("name", v_name);
                    params.put("address", v_address);
                    params.put("country", v_country);
                    params.put("province", v_province);
                    params.put("city", v_city);
                    params.put("post_code", v_postCode);
                    params.put("email", v_email);
                    params.put("username", v_username);
                    params.put("password", v_password);
                    params.put("gender",v_gender);
                    params.put("phone", v_phone);
                    params.put("bank_account", v_bankAccount);
                    return params;
                }
            };
            WebServices.getmInstance(CustomerUpdateData.this).addToRequestque(updateRequest);

        }



    }


}
