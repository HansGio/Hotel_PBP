package com.pbpc_e.hotel_pbp.ui.reservation;

import com.google.gson.annotations.SerializedName;
import com.pbpc_e.hotel_pbp.ui.book.RoomDAO;

import java.util.List;

public class ReservationResponse {

    @SerializedName("reservation")
    private ReservationDAO reservation = null;

    @SerializedName("reservations")
    private List<ReservationDAO> reservations = null;

    @SerializedName("message")
    private String message;

    public ReservationDAO getReservation() {
        return reservation;
    }

    public List<ReservationDAO> getReservations() {
        return reservations;
    }

    public String getMessage() {
        return message;
    }
}
