package com.pbpc_e.hotel_pbp.ui.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pbpc_e.hotel_pbp.databinding.AdapterRecyclerViewBinding;
import com.pbpc_e.hotel_pbp.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Kamar> result = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<Kamar> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRecyclerViewBinding adapterRecyclerViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.adapter_recycler_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(adapterRecyclerViewBinding);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Kamar kmr = result.get(position);
        holder.adapterRecyclerViewBinding.setKmr(kmr);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AdapterRecyclerViewBinding adapterRecyclerViewBinding;
        CardView listKamar;

        public MyViewHolder(@NonNull AdapterRecyclerViewBinding adapterRecyclerViewBinding){
            super(adapterRecyclerViewBinding.getRoot());
            View root = adapterRecyclerViewBinding.getRoot();
            this.adapterRecyclerViewBinding = adapterRecyclerViewBinding;

            listKamar = root.findViewById(R.id.listKamar);
            listKamar.setOnClickListener(this);
        }
        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }

}