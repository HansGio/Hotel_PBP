package com.pbpc_e.hotel_pbp.ui.home;

import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.ui.about.AboutFragment;
import com.pbpc_e.hotel_pbp.ui.book.BookFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageSlider imageSlider;
    CardView cardViewBook, cardViewAbout, cardViewProfile;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewAbout = root.findViewById(R.id.cardview_about);
        cardViewBook = root.findViewById(R.id.cardview_book);
        cardViewProfile = root.findViewById(R.id.cardview_profile);

        cardViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_about);
            }
        });
        cardViewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_book);
            }
        });
        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_profile);
            }
        });

        imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://hoteltentrem.com/wp-content/uploads/2020/01/tentrem-hotel-yogyakarta-gallery-pool-jpg.jpg", "", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://hoteltentrem.com/wp-content/uploads/2020/01/tentrem-hotel-yogyakarta-gallery-Room-Deluxe-King.jpg", "", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://hoteltentrem.com/wp-content/uploads/2020/01/art-gallery.jpg", "", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        return root;
    }
}