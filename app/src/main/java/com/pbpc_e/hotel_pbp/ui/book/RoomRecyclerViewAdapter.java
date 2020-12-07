package com.pbpc_e.hotel_pbp.ui.book;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.ApiInterface;
import com.pbpc_e.hotel_pbp.databinding.AdapterRecyclerViewBinding;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.ui.reservation.ReservationResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<RoomDAO> dataList;
    private List<RoomDAO> filteredDataList;
    private String checkIn = "", checkOut = "", token = "";
    private int person = -1, night = -1, userId;
    ProgressDialog progressDialog;

    public RoomRecyclerViewAdapter(Context context, List<RoomDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public void setNight(int night) {
        this.night = night;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    person = -1;
                    filteredDataList = dataList;
                } else {
                    person = Integer.parseInt(charSequenceString);
                    List<RoomDAO> filteredList = new ArrayList<>();
                    for (RoomDAO room : dataList) {
                        if (room.getCapacity() >= person) {
                            filteredList.add(room);
                        }
                        filteredDataList = filteredList;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredDataList = (List<RoomDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRecyclerViewBinding adapterRecyclerViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.room_adapter_recycler_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(adapterRecyclerViewBinding);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final RoomDAO room = filteredDataList.get(position);
        holder.adapterRecyclerViewBinding.setRoom(room);

        progressDialog = new ProgressDialog(context);
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AdapterRecyclerViewBinding adapterRecyclerViewBinding;
        CardView cardRoom;

        public MyViewHolder(@NonNull AdapterRecyclerViewBinding adapterRecyclerViewBinding) {
            super(adapterRecyclerViewBinding.getRoot());
            View root = adapterRecyclerViewBinding.getRoot();
            this.adapterRecyclerViewBinding = adapterRecyclerViewBinding;

            cardRoom = root.findViewById(R.id.cv_room);
            cardRoom.setOnClickListener(this);
        }

        public void onClick(View view) {
            RoomDAO room = filteredDataList.get(getAdapterPosition());
            String msg = room.getType();
            if (night == -1 || checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(context, "Please enter your check in and check out date!", Toast.LENGTH_SHORT).show();
            } else if (person == -1) {
                Toast.makeText(context, "Please specify the number of people who are staying!", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to book this room?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<RoomResponse> add = apiService.bookRoom("Bearer " + token, String.valueOf(room.getId()));

                        progressDialog.show();
                        add.enqueue(new Callback<RoomResponse>() {
                            @Override
                            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {

                                if (response.code() == 200) {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Call<ReservationResponse> add = apiService.createReservation("Bearer " + token, room.getId(), userId, night, checkIn, checkOut, night * room.getPricePerNight());

                                    add.enqueue(new Callback<ReservationResponse>() {
                                        @Override
                                        public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                                            progressDialog.dismiss();
                                            if (response.code() == 200) {
                                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                try {
                                                    JSONObject jObjError = new JSONObject(response.errorBody().string());

                                                    Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ReservationResponse> call, Throwable t) {
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                } else {
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RoomResponse> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

}