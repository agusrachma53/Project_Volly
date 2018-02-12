package com.voley.www.voley;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
        holder.phone.setText(model.getPhone());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,CustomerDetail.class);
                i.putExtra("idCustomer",model.getIdCustomer());
                i.putExtra("nama",model.getNama());
                i.putExtra("phone",model.getPhone());
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
        public TextView phone;
        public ConstraintLayout constraintLayout;


        public Holder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.nameCustomer);
            phone = (TextView) itemView.findViewById(R.id.phoneCustomer);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.listCustomer_c);


        }


    }
}
