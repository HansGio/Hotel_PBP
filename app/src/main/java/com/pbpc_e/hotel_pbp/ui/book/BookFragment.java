package com.pbpc_e.hotel_pbp.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.databinding.FragmentBookBinding;
import com.pbpc_e.hotel_pbp.ui.about.AboutViewModel;

import java.util.ArrayList;

public class BookFragment extends Fragment {

    private ArrayList<Kamar> ListKamar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FragmentBookBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false);
        View root = binding.getRoot();


        //recycler view
        recyclerView = binding.recyclerViewKamar;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListKamar = new DaftarKamar().Kamar;
        adapter = new RecyclerViewAdapter(getContext(), ListKamar);
        recyclerView.setAdapter(adapter);
        return root;
    }
}