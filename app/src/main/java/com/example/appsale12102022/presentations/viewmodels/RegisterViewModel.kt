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
import java.io.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class RegisterViewModel(context: Context) : ViewModel() {
    private val authenticationRepository: AuthenticationRepository =
        AuthenticationRepository(context)
    private val userResource = MutableLiveData<AppResource<User>>()
    val userResourceLiveData: LiveData<AppResource<User>>
        get() = userResource
    fun getUserResource(): LiveData<AppResource<User>> {
        return userResourceLiveData
    }
    fun signUp(email: String, password: String, name: String, phone: String, address: String) {
        userResource.value = AppResource.Loading(null)
        authenticationRepository.signUp(email, password, name, phone, address)
            .enqueue(object : Callback<AppResource<UserDTO>> {
                override fun onResponse(
                    call: Call<AppResource<UserDTO>>,
                    response: Response<AppResource<UserDTO>>
                ) {
                    if (response.isSuccessful) {
                        val userDTO = response.body()!!.data
                        val user = User(
                            userDTO?.email,
                            userDTO?.name,
                            userDTO?.phone,
                            userDTO?.token
                        )
                        userResource.value = AppResource.Success(user)
                    } else {
                        response.errorBody()?.let { errorBody ->
                            try {
                                val jsonObject = JSONObject(errorBody.string())
                                val message = jsonObject.getString("message")
                                userResource.value = AppResource.Error(message)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<AppResource<UserDTO>>, t: Throwable) {
                    userResource.value = t.message?.let { AppResource.Error(it) }
                }
            })
    }
}