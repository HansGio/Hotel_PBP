package com.pbpc_e.hotel_pbp.ui.book;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.ApiInterface;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.databinding.FragmentBookBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class BookFragment extends Fragment {

    private static final String USER_PREF_NAME = "User";

    private ArrayList<RoomDAO> rooms;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SharedPreferences preferences;
    Button btnSearch;
    ImageView imageView;
    TextInputEditText inputPerson;
    TextView tv1, tv2, tv3;
    ConstraintLayout date;
    FragmentBookBinding binding;
    String token, startDateString = "", endDateString = "";
    long daysDiff = -1;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefresh;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false);
        View root = binding.getRoot();

        btnSearch = root.findViewById(R.id.button_search);
        tv1 = root.findViewById(R.id.textView);
        tv2 = root.findViewById(R.id.textView2);
        tv3 = root.findViewById(R.id.textView3);
        inputPerson = root.findViewById(R.id.input_person);
        imageView = root.findViewById(R.id.imageView);
        date = root.findViewById(R.id.date);
        progressDialog = new ProgressDialog(getContext());
        swipeRefresh = root.findViewById(R.id.swipeRefresh);

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
                daysDiff = TimeUnit.MILLISECONDS.toDays(diff);
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                startDateString = formatter.format(new Date(startDate));
                endDateString = formatter.format(new Date(endDate));

                tv1.setText(startDateString);
                adapter.setCheckIn(startDateString);
                tv2.setText(endDateString);
                adapter.setCheckOut(endDateString);
                tv3.setText(daysDiff + " night(s)");
                adapter.setNight((int) daysDiff);
            }
        });
        swipeRefresh.setRefreshing(true);
        loadPreferences();
        loadRoom();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRoom();
            }
        });
        return root;
    }

    public void loadRoom() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RoomResponse> call = apiService.getAvailableRooms("Bearer " + token);

        call.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.code() == 200) {
                    generateDataList(response.body().getRooms());

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                swipeRefresh.setRefreshing(false);
//                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<RoomDAO> rooms) {

        recyclerView = binding.recyclerViewKamar;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerViewAdapter(getContext(), rooms);
        adapter.setToken(token);
        if (!inputPerson.getText().toString().isEmpty())
            adapter.getFilter().filter(inputPerson.getText().toString());
        recyclerView.setAdapter(adapter);

        if(!startDateString.isEmpty() && !endDateString.isEmpty() && daysDiff != -1){
            adapter.setCheckIn(startDateString);
            adapter.setCheckOut(endDateString);
            adapter.setNight((int) daysDiff);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getFilter().filter(inputPerson.getText().toString());
                inputPerson.clearFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });
    }

    private void loadPreferences() {
        preferences = getActivity().getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        token = preferences.getString("token", "");
    }
}