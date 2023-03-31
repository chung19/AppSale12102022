package com.example.appsale12102022.presentations.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsale12102022.common.AppConstant
import com.example.appsale12102022.data.local.SharePrefApp
import com.example.appsale12102022.data.model.User
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.data.remote.dto.UserDTO
import com.example.appsale12102022.data.repositories.AuthenticationRepository
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by pphat on 2/1/2023.
 */
class LoginViewModel(private val context: Context) : ViewModel() {
    private val authenticationRepository: AuthenticationRepository = AuthenticationRepository(context)
    val userResource = MutableLiveData<AppResource<User>>()

    fun getUserResource(): LiveData<AppResource<User>> {
        return userResource
    }

    fun signIn(email: String?, password: String?) {
        userResource.value = AppResource.Loading(null)
        authenticationRepository.signIn(email!!, password!!)
            .enqueue(object : Callback<AppResource<UserDTO>> {
                override fun onResponse(call: Call<AppResource<UserDTO>>, response: Response<AppResource<UserDTO>>) {
                    if (response.isSuccessful) {
                        val userDTO = response.body()!!.data
                        val user = User(userDTO!!.email, userDTO.name, userDTO.phone, userDTO.token)
                        userResource.value = AppResource.Success(user)

                        if (!user.token!!.isEmpty()) {
                            SharePrefApp.getInstance(context).saveDataString(AppConstant.KEY_TOKEN, user.token!!)
                        }
                    } else {
                        if (response.errorBody() == null) return
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            val message = jsonObject.getString("message")
                            userResource.value = AppResource.Error(message)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<AppResource<UserDTO>>, t: Throwable) {
                    userResource.value = AppResource.Error(t.message!!)
                }
            })
    }
}
