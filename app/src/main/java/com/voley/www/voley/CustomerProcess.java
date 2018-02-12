package com.voley.www.voley;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ASUS Notebook on 12/02/2018.
 */

public class CustomerProcess {

    public List<CustomerModel> mListData;
    public CustomerAdapter mAdapter;
    public Context mCtx;
    public RecyclerView recyclerViewCustomer;


    public List<CustomerModel> getAllCustomer(String response) {

        mListData = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);

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

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mListData;

    };

}
