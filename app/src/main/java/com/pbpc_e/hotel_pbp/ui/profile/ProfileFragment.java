package com.pbpc_e.hotel_pbp.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pbpc_e.hotel_pbp.ApiClient;
import com.pbpc_e.hotel_pbp.ApiInterface;
import com.pbpc_e.hotel_pbp.MenuActivity;
import com.pbpc_e.hotel_pbp.R;
import com.pbpc_e.hotel_pbp.RegisterActivity;
import com.pbpc_e.hotel_pbp.UserResponse;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private int REQUEST_IMAGE_CAPTURE = 100;
    private int RESULT_OK = -1;
    private static final String USER_PREF_NAME = "User";

    SharedPreferences preferences;
    TextInputLayout inputLayoutEmail, inputLayoutName, inputLayoutPhone;
    TextInputEditText inputEmail, inputName, inputPhone;
    Button btnSave;
    ProgressBar progressBar;
    ImageView imageProfile;
    CardView cardCamera;
    String token, imageString = "";

    FirebaseUser user;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSave = root.findViewById(R.id.button_save);
        inputLayoutName = root.findViewById(R.id.input_layout_name);
        inputLayoutEmail = root.findViewById(R.id.input_layout_email);
        inputLayoutName = root.findViewById(R.id.input_layout_name);
        inputLayoutPhone = root.findViewById(R.id.input_layout_phone);
        inputEmail = root.findViewById(R.id.input_email);
        inputName = root.findViewById(R.id.input_name);
        inputPhone = root.findViewById(R.id.input_phone);
        imageProfile = root.findViewById(R.id.profile_image);
        cardCamera = root.findViewById(R.id.camera_card);
        progressBar = root.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        loadPreferences();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (!token.isEmpty()) {
            loadUser();
        }
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });


        return root;
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    private void takePictureIntent() {
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

            handleUpload(imageBitmap);
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void updateUser() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.update("Bearer " + token, inputName.getText().toString(), inputEmail.getText().toString(), inputPhone.getText().toString(), imageString);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                inputLayoutEmail.setError(null);
                inputLayoutName.setError(null);
                inputLayoutPhone.setError(null);

                progressBar.setVisibility(View.INVISIBLE);
                if(response.code() == 200) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        if(jObjError.get("message") instanceof JSONObject){
                            if (jObjError.getJSONObject("message").has("email")) {
                                inputLayoutEmail.setError(jObjError.getJSONObject("message").getJSONArray("email").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("name")) {
                                inputLayoutName.setError(jObjError.getJSONObject("message").getJSONArray("name").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("phone")) {
                                inputLayoutPhone.setError(jObjError.getJSONObject("message").getJSONArray("phone").get(0).toString());
                            }
                        } else
                            Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void loadUser() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.details("Bearer " + token);

        call.enqueue(new Callback<UserResponse>() {
            private static final String TAG = "MenuActivity";

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.code() == 200) {
                    if (response.body().getUser() != null) {
                        String userEmail = response.body().getUser().getEmail();
                        String displayName = response.body().getUser().getName();
                        String phone = response.body().getUser().getPhone();

                        inputEmail.setText(userEmail);
                        inputName.setText(displayName);
                        inputPhone.setText(phone);

                        String url = ApiClient.BASE_URL + "img/" + response.body().getUser().getImage();
                        Log.d("URL: ", url);

                        if (!response.body().getUser().getImage().isEmpty()) {
                            Glide.with(getContext())
                                    .load(url)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                                    .into(imageProfile);
                        }
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void loadPreferences() {
        preferences = getActivity().getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        token = preferences.getString("token", "");
    }
}