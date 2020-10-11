package com.pbpc_e.hotel_pbp.ui.book;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.pbpc_e.hotel_pbp.HomeActivity;
import com.pbpc_e.hotel_pbp.LoginActivity;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.databinding.FragmentBookBinding;
import com.pbpc_e.hotel_pbp.ui.about.AboutViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookFragment extends Fragment {

    private ArrayList<Kamar> ListKamar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button button3;
    ImageView imageView;
    TextView tv1, tv2, tv3;
    ConstraintLayout date;
    FragmentBookBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false);
        View root = binding.getRoot();

        button3 = root.findViewById(R.id.button3);
        tv1 = root.findViewById(R.id.textView);
        tv2 = root.findViewById(R.id.textView2);
        tv3 = root.findViewById(R.id.textView3);
        imageView = root.findViewById(R.id.imageView);
        date = root.findViewById(R.id.date);

        //CalendarConstraints
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        //MaterialDatePicker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT CHECK IN DATE");
        builder.setCalendarConstraints(constraintsBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Long startDate = selection.first;
                Long endDate = selection.second;

                long diff = endDate - startDate;
                long daysDiff = TimeUnit.MILLISECONDS.toDays(diff);
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                String startDateString = formatter.format(new Date(startDate));
                String endDateString = formatter.format(new Date(endDate));

                tv1.setText(startDateString);
                tv2.setText(endDateString);
                tv3.setText(daysDiff + " night(s)");
            }
        });

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