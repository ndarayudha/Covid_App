package com.example.appkp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.auth.AuthResponse
import com.example.appkp.ui.PhotoScreenActivity
import com.example.appkp.ui.auth.presenter.LoginPresenter
import com.example.appkp.ui.auth.view.IResult
import com.example.appkp.ui.dashboard.DashboardActivity
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenActivity : AppCompatActivity(), IResult {


    lateinit var loginPresenter: LoginPresenter
    lateinit var preference: Preferences
    private val TAG = "LoginTag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        loginPresenter = LoginPresenter(this@LoginScreenActivity)
        preference = Preferences(this)
        preference.setValue("firstLaunch", "first")



        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            val login = loginPresenter.onLogin(email, password)


            if (login) {
                RetrofitBuilder(Constant.BASE_URL).api.onLogin(email, password)
                    .enqueue(object : Callback<AuthResponse> {
                        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                            onError("Login Failed")
                        }

                        override fun onResponse(
                            call: Call<AuthResponse>,
                            response: Response<AuthResponse>
                        ) {

                            try {
                                val success = response.body()?.success

                                if (success!!) {
                                    val name = response.body()?.user!!.name
                                    val token = response.body()?.token

                                    preference = Preferences(this@LoginScreenActivity).apply {
                                        setValue("token", token!!)
                                        setValue("name", name)
                                        setValue("isLoggedIn", "true")

                                        onSuccess("Login Success")

                                        startActivity(
                                            Intent(
                                                this@LoginScreenActivity.applicationContext,
                                                DashboardActivity::class.java
                                            )
                                        )
                                        finishAffinity()
                                    }
                                } else {
                                    onError(response.body()!!.message)
                                }

                            } catch (e: NullPointerException) {
                                onError(e.message!!)
                            }


                        }

                    })
            }


            btn_daftar.setOnClickListener {
                startActivity(Intent(this, RegisterScreenActivity::class.java))
            }
        }

    }

    override fun onSuccess(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_SHORT).show()
    }



}





