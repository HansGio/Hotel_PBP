package com.pbpc_e.hotel_pbp.ui.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.databinding.FragmentBookBinding;

import java.util.ArrayList;

public class BookViewModel extends AppCompatActivity {
    private ArrayList<Kamar> ListKamar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FragmentBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_book);
        binding.recyclerViewKamar.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewKamar.setHasFixedSize(true);
        //get data mahasiswa
        ListKamar = new DaftarKamar().Kamar;

        //recycler view
        recyclerView = findViewById(R.id.recycler_view_kamar);
        adapter = new RecyclerViewAdapter(BookViewModel.this, ListKamar);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}