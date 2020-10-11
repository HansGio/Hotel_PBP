package com.pbpc_e.hotel_pbp.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.pbpc_e.hotel_pbp.R;

public class AboutFragment extends Fragment {

    private static final String IMAGE_URL = "http://hoteltentrem.com/wp-content/uploads/2019/12/tentrem-semarang.jpg";

    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        imageView = root.findViewById(R.id.image_about);

        Glide.with(getActivity()).load(IMAGE_URL).into(imageView);

        return root;
    }
}