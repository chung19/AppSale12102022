package com.example.appsale12102022.presentations.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale12102022.common.AppConstant;
import com.example.appsale12102022.data.local.SharePrefApp;
import com.example.appsale12102022.data.model.User;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.dto.UserDTO;
import com.example.appsale12102022.data.repositories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 2/3/2023.
 */
public class RegisterViewModel extends ViewModel {
    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<AppResource<User>> userResource = new MutableLiveData<>();

    public RegisterViewModel(Context context) {
        authenticationRepository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<User>> getUserResource() {
        return userResource;
    }

    public void signUp(String email, String password, String name, String phone, String address) {
        userResource.setValue(new AppResource.Loading(null));
        authenticationRepository.signUp(email, password, name, phone, address)
                .enqueue(new Callback<AppResource<UserDTO>>() {
                    @Override
                    public void onResponse(Call<AppResource<UserDTO>> call, Response<AppResource<UserDTO>> response) {
                        if (response.isSuccessful()) {
                            UserDTO userDTO = response.body().data;
                            User user = new User(userDTO.getEmail(), userDTO.getName(), userDTO.getPhone(), userDTO.getToken());
                            userResource.setValue(new AppResource.Success<>(user));
                        } else {
                            if (response.errorBody() == null) return;
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                userResource.setValue(new AppResource.Error<>(message));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResource<UserDTO>> call, Throwable t) {
                        userResource.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }
}
