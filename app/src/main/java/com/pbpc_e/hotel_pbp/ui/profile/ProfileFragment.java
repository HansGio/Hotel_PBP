package com.pbpc_e.hotel_pbp.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pbpc_e.hotel_pbp.R;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private int REQUEST_IMAGE_CAPTURE = 100;
    private int RESULT_OK = -1;

    TextView textName, textEmail, textPhone;
    ProgressBar progressBar;
    ImageView imageProfile;
    CardView cardCamera;

    FirebaseUser user;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        textName = root.findViewById(R.id.text_name);
        textEmail = root.findViewById(R.id.text_email);
        textPhone = root.findViewById(R.id.text_phone);
        imageProfile = root.findViewById(R.id.profile_image);
        cardCamera = root.findViewById(R.id.camera_card);
        progressBar = root.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            textEmail.setText(user.getEmail());
            textName.setText(user.getDisplayName());
            if(user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()){
                textPhone.setText(user.getPhoneNumber());
            }
            if(user.getPhotoUrl() != null){
                Glide.with(getActivity())
                        .load(user.getPhotoUrl())
                        .into(imageProfile);
            }
        }
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        progressBar.setVisibility(View.INVISIBLE);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });


        return root;
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void  takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageProfile.setImageBitmap(imageBitmap);

            progressBar.setVisibility(View.VISIBLE);
            handleUpload(imageBitmap);
        }
    }

    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Profile picture updated", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to update picture", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}