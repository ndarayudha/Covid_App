package com.example.appkp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appkp.R
import com.example.appkp.ui.PhotoScreenActivity
import com.example.appkp.ui.auth.presenter.LoginPresenter
import com.example.appkp.ui.auth.view.ILoginView
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginScreenActivity : AppCompatActivity(), ILoginView {


    lateinit var loginPresenter: LoginPresenter
    lateinit var preference: Preferences
    lateinit var queue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginPresenter = LoginPresenter(this@LoginScreenActivity)
        preference = Preferences(this)
        preference.setValue("firstLaunch", "first")



        btn_login.setOnClickListener {
            val email = edt_name.text.toString()
            val password = edt_password.text.toString()

            val login = loginPresenter.onLogin(email, password)

            if (login){

                // Instantiate the RequestQueue.
                queue = Volley.newRequestQueue(this@LoginScreenActivity)
                val url = Constant.LOGIN

                // Request a string response from the provided URL.
                val stringRequest =
                    object : StringRequest(Method.POST, url, Response.Listener<String> { response ->

                        try {
                            val obj = JSONObject(response)
                            if (obj.getBoolean("success")) {
                                val user = obj.getJSONObject("user")
                                // masukan kedalam sharePrefrence
                                preference = Preferences(this@LoginScreenActivity).apply {
                                    setValue("token", obj.getString("token"))
                                    setValue("name", user.getString("name"))
                                    setValue("photo", user.getString("photo"))
                                    setValue("isLoggedIn", "true")

                                    onLoginSuccess("Login Success")

                                    startActivity(Intent(this@LoginScreenActivity.applicationContext, PhotoScreenActivity::class.java))
                                    finishAffinity()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }, Response.ErrorListener {
                        it.printStackTrace()
                        onLoginError("Login Failed")
                    }) {

                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["email"] = email
                            params["password"] = password
                            return params
                        }
                    }
                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            }

        }

        tv_daftar.setOnClickListener {
            startActivity(Intent(this@LoginScreenActivity, RegisterScreenActivity::class.java))

        }
    }


    override fun onLoginSuccess(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginError(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }


}
