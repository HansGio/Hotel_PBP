package com.pbpc_e.hotel_pbp.ui.reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.ApiInterface;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.ui.book.RoomResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ReservationFragment extends Fragment {

    private static final String USER_PREF_NAME = "User";

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;

    double total;
    int userId = 0;
    String token = "";

    public ReservationFragment() {
        // Required empty public constructor
    }

    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);
        progressDialog = new ProgressDialog(getContext());
        recyclerView = v.findViewById(R.id.recycler_view_reserve);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);

        swipeRefresh.setRefreshing(true);

        loadPreferences();
        loadReservation();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadReservation();
            }
        });
        return v;
    }

    public void loadReservation() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ReservationResponse> call = apiService.getUserReservation("Bearer " + token, userId);

        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.code() == 200) {
                    generateDataList(response.body().getReservations());
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                swipeRefresh.setRefreshing(false);
//                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<ReservationDAO> reservations) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ReservationAdapter(getContext(), reservations);
        adapter.setToken(token);
        recyclerView.setAdapter(adapter);
    }

    private void loadPreferences() {
        preferences = getActivity().getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        token = preferences.getString("token", "");
        userId = preferences.getInt("id", 0);
    }
}