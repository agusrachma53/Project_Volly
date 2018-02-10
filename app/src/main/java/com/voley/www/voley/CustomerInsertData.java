package com.voley.www.voley;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CustomerInsertData extends AppCompatActivity {

    private EditText name,address,country,province,city,postCode,email,username,password,phone,bankAccount;
    private String v_name,v_address,v_country,v_province,v_city,
            v_postCode,v_email,v_username,v_password,v_gender,v_phone,v_bankAccount,message;
    private Button btnVoley,batalInsert;
    private Spinner selectGender;
    private ArrayAdapter<CharSequence> adapter;
    private Context context;
    private Snackbar snackbar;
    private ConstraintLayout constraintLayout;
    private String BaseUrl = "https://ph0001.babastudio.org/afand_store/serviceforajax/m_InsertData.php";
    private Timer t = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);




        this.context = context;

        name = (EditText) findViewById(R.id.f_name);
        address = (EditText) findViewById(R.id.f_address);
        country = (EditText) findViewById(R.id.f_province);
        province = (EditText) findViewById(R.id.f_city);
        city = (EditText) findViewById(R.id.f_city);
        postCode = (EditText) findViewById(R.id.f_post_code);
        email = (EditText) findViewById(R.id.f_email);
        username = (EditText) findViewById(R.id.f_username);
        password = (EditText) findViewById(R.id.f_password);
        phone = (EditText) findViewById(R.id.f_phone);
        bankAccount = (EditText) findViewById(R.id.f_bank_account);
        btnVoley = (Button) findViewById(R.id.postInsert);
        selectGender = (Spinner) findViewById(R.id.f_ganderx);
        batalInsert = (Button) findViewById(R.id.batalInsert);

        /* Spinner Gander */
        adapter = ArrayAdapter.createFromResource(this, R.array.gd,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(adapter);



        /* Validation */
        final Form form = new Form.Builder(CustomerInsertData.this)
                .showErrors(true).build();

        /* Button Batal Post Data */

        batalInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(CustomerInsertData.this,MainActivity.class);
                startActivity(changeActivity);
            }
        });

        /* Button Post Data */

        btnVoley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    v_gender = selectGender.getSelectedItem().toString();
                    v_phone = phone.getText().toString();
                    v_bankAccount = bankAccount.getText().toString();
                    PostData(v_name,v_address,v_country,v_province,v_city,v_postCode,v_email,v_username,v_password,v_gender,v_phone,v_bankAccount);
                }
            }
        });
    }

    /* Procces Post Data */
    public void PostData(final String p_name, final String p_address, final String p_country, final String p_province,
                         final String p_city, final String p_postCode, final String p_email, final String p_username,
                         final String p_password, final String p_gender, final String p_phone, final String p_bankAccount){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            /* Response */
                            JSONObject jsonObject = new JSONObject(response);
                            String pesan = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
                            Intent changeActivity = new Intent(CustomerInsertData.this,MainActivity.class);
                            startActivity(changeActivity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", p_name);
                params.put("address", p_address);
                params.put("country", p_country);
                params.put("province", p_province);
                params.put("city", p_city);
                params.put("post_code", p_postCode);
                params.put("email", p_email);
                params.put("username", p_username);
                params.put("password", p_password);
                params.put("gender", p_gender);
                params.put("phone", p_phone);
                params.put("bank_account", p_bankAccount);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
