package com.example.appkp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.auth.AuthResponse
import com.example.appkp.ui.PhotoScreenActivity
import com.example.appkp.ui.auth.presenter.RegisterPresenter
import com.example.appkp.ui.auth.view.IResult
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback


class RegisterScreenActivity : AppCompatActivity(), IResult {


    lateinit var registerPresenter: RegisterPresenter
    lateinit var preference: Preferences
    private val TAG = "RegiterTag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        registerPresenter = RegisterPresenter(this)
        preference = Preferences(this)


        btn_register.setOnClickListener {
            val name = edt_name.text.toString()
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            val register = registerPresenter.onRegister(email, password, name)

            if (register) {
                RetrofitBuilder(Constant.BASE_URL).api.onRegister(email, name, password)
                    .enqueue(object : Callback<AuthResponse> {

                        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                            onError("Register Failed")
                        }

                        override fun onResponse(
                            call: Call<AuthResponse>,
                            response: retrofit2.Response<AuthResponse>
                        ) {
                            try {
                                val success = response.body()?.success

                                if (success!!) {
                                    val name = response.body()?.user!!.name
                                    val token = response.body()?.token

                                    
                                    preference = Preferences(this@RegisterScreenActivity).apply {
                                        setValue("token", token!!)
                                        setValue("name", name)
                                        setValue("isLoggedIn", "true")

                                        onSuccess("Register Success")

                                        startActivity(
                                            Intent(
                                                this@RegisterScreenActivity.applicationContext,
                                                PhotoScreenActivity::class.java
                                            )
                                        )
                                        finishAffinity()
                                    }
                                } else {
                                    onError(response.body()!!.message)
                                }

                            } catch (e: JSONException) {
                                Log.d(TAG, e.printStackTrace().toString())
                            }
                        }
                    })
            }
        }

    }


    override fun onSuccess(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }

}
