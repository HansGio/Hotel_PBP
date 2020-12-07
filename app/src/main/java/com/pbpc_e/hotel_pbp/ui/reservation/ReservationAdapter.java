package com.pbpc_e.hotel_pbp.ui.reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.ApiInterface;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.ui.book.RoomResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private List<ReservationDAO> reservations;
    private String token;
    private Context context;

    public void setToken(String token) {
        this.token = token;
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCheckIn, textViewCheckOut, textViewNight, textViewPerson, textViewType, textViewCapacity, textViewRate, textViewTotal;
        public ImageView imageViewRoom;
        public Button buttonCancel;
        private ProgressDialog progressDialog;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCheckIn = itemView.findViewById(R.id.tvCheckIn);
            textViewCheckOut = itemView.findViewById(R.id.tvCheckOut);
            textViewNight = itemView.findViewById(R.id.tvNight);
            textViewPerson = itemView.findViewById(R.id.tvPerson);
            textViewType = itemView.findViewById(R.id.tvRoomType);
            textViewCapacity = itemView.findViewById(R.id.tvCapacity);
            textViewRate = itemView.findViewById(R.id.tvRate);
            textViewTotal = itemView.findViewById(R.id.tvTotal);
            imageViewRoom = itemView.findViewById(R.id.ivRoomPhoto);
            buttonCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    public ReservationAdapter(Context context, List<ReservationDAO> reservations) {
        this.reservations = reservations;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_adapter, parent, false);
        return new ReservationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ReservationDAO reservation = reservations.get(position);

        holder.progressDialog = new ProgressDialog(context);
        holder.textViewCheckIn.setText(reservation.getCheckIn());
        holder.textViewCheckOut.setText(reservation.getCheckOut());
        holder.textViewNight.setText(String.valueOf(reservation.getNights()) + " Nights");
        holder.textViewPerson.setText(String.valueOf(reservation.getPersons()) + " Persons");
        holder.textViewTotal.setText("IDR " + String.format("%.2f", reservation.getTotal()));
        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to cancel booking this room?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        holder.progressDialog.show();
                        Call<ReservationResponse> call = apiService.deleteReservation("Bearer " + token, reservation.getId());

                        call.enqueue(new Callback<ReservationResponse>() {
                            @Override
                            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                                if (response.code() == 200) {
                                    Call<RoomResponse> call2 = apiService.cancelRoom("Bearer " + token, reservation.getRoom_id());

                                    call2.enqueue(new Callback<RoomResponse>() {
                                        @Override
                                        public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                                            holder.progressDialog.dismiss();
                                            if (response.code() == 200) {
//                                                reservations.remove(position);
//                                                notifyItemRemoved(position);
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
                                        public void onFailure(Call<RoomResponse> call, Throwable t) {
                                            holder.progressDialog.dismiss();
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    holder.progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                                holder.progressDialog.dismiss();
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
        });

        Call<RoomResponse> call = apiService.getRoomById("Bearer " + token, reservation.getRoom_id());

        call.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                if (response.code() == 200) {
                    holder.textViewType.setText(response.body().getRoom().getType());
                    holder.textViewCapacity.setText(String.valueOf(response.body().getRoom().getCapacity()) + " Persons");
                    holder.textViewRate.setText("IDR " + String.format("%.2f", response.body().getRoom().getPricePerNight()) + " / Night");

                    String url = ApiClient.BASE_URL + "img/" + response.body().getRoom().getImage();
                    if ( response.body().getRoom().getImage() != null) {
                        try{
                            byte[] imageByteArray = Base64.decode(response.body().getRoom().getImage(), Base64.DEFAULT);
                            Glide.with(context)
                                    .load(imageByteArray)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.itemdefault)
                                    .into(holder.imageViewRoom);
                        }catch (Exception e){

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
