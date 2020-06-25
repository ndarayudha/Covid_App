package com.example.appkp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appkp.R
import com.example.appkp.ui.auth.presenter.LoginPresenter
import com.example.appkp.ui.auth.presenter.RegisterPresenter
import com.example.appkp.ui.auth.view.ILoginView
import com.example.appkp.ui.auth.view.IRegisterView
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edt_email
import kotlinx.android.synthetic.main.activity_register.edt_password
import org.json.JSONException
import org.json.JSONObject

class RegisterScreenActivity : AppCompatActivity(), IRegisterView {

    lateinit var registerPresenter: RegisterPresenter
    lateinit var preference: Preferences
    lateinit var queue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        registerPresenter = RegisterPresenter(this)
        preference = Preferences(this)


        btn_register.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            val name = edt_name.text.toString()


            if (registerPresenter.onRegister(email, password, name)){

                // Instantiate the RequestQueue.
                queue = Volley.newRequestQueue(this@RegisterScreenActivity)
                val url = Constant.REGISTER

                // Request a string response from the provided URL.
                val stringRequest =
                    object : StringRequest(Method.POST, url, Response.Listener<String> { response ->

                        try {
                            val obj = JSONObject(response)
                            if (obj.getBoolean("success")) {
                                val user = obj.getJSONObject("user")
                                // masukan kedalam sharePrefrence
                                preference = Preferences(this@RegisterScreenActivity).apply {
                                    setValue("token", obj.getString("token"))
                                    setValue("name", user.getString("name"))
                                    setValue("photo", user.getString("photo"))


                                    Toast.makeText(this@RegisterScreenActivity, "Register suceess", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }, Response.ErrorListener {
                        it.printStackTrace()
                    }) {

                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["email"] = email
                            params["password"] = password
                            params["name"] = name
                            return params
                        }
                    }
                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            }

        }

    }

    override fun onRegisterSuccess(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterError(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }


}
