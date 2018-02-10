package com.voley.www.voley;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jack Ma on 2/6/2018.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.Holder> {

    private List<CustomerModel> mListData;
    private Context mContext;


    public CustomerAdapter(List<CustomerModel> mListData, Context mContext){
        this.mListData = mListData;
        this.mContext = mContext;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_item_data,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final CustomerModel model = mListData.get(position);

        /* Set Data Makanan */
        holder.nama.setText(model.getNama());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.address.setText(model.getAddress());
        holder.detailCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,CustomerDetail.class);
                i.putExtra("idCustomer",model.getIdCustomer());
                i.putExtra("nama",model.getNama());
                i.putExtra("phone",model.getPhone());
                i.putExtra("address",model.getAddress());
                i.putExtra("email",model.getEmail());
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView nama;
        public TextView email;
        public TextView phone;
        public TextView address;
        public Button detailCustomer;

        public Holder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.c_name);
            email = (TextView) itemView.findViewById(R.id.c_email);
            phone = (TextView) itemView.findViewById(R.id.c_phone);
            address = (TextView) itemView.findViewById(R.id.c_address);
            detailCustomer = (Button) itemView.findViewById(R.id.detailCustomer);
        }


    }
}
