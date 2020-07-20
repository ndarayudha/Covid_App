package com.example.appkp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.UserPhotoResponse
import com.example.appkp.ui.auth.view.IResult
import com.example.appkp.ui.dashboard.DashboardActivity
import com.example.appkp.util.Preferences
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_photo_screen.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream

class PhotoScreenActivity : AppCompatActivity(), IResult {


    private var bitmap: Bitmap? = null
    private var statusAdd: Boolean = false
    lateinit var preferences: Preferences


    companion object {
        const val GALLERY_ADD_PROFILE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_screen)


        preferences = Preferences(this)


        if (preferences.getValue("inDashboard").equals("true")) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finishAffinity()
        }


        initBtn()

        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_simpan_lanjutkan.visibility = View.INVISIBLE
                iv_add.setImageResource(R.drawable.plus)
                iv_profile.setImageResource(R.drawable.userimage)
            } else {
                Intent(Intent.ACTION_PICK).also {
                    it.type = "image/*"
                    startActivityForResult(it, GALLERY_ADD_PROFILE)
                }
            }
        }
    }


    private fun initBtn() {
        btn_lanjutkan.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finishAffinity()
        }

        btn_simpan_lanjutkan.setOnClickListener {
            saveUserPhoto2()
            startActivity(Intent(this, DashboardActivity::class.java))
            onSuccess("Upload photo success")
            finishAffinity()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_ADD_PROFILE && resultCode == Activity.RESULT_OK) {

            statusAdd = true

            data?.let {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it.data)

                iv_profile.setImageBitmap(bitmap)
            }

            btn_simpan_lanjutkan.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.x)
        }
    }


    private fun saveUserPhoto2() {
        val token = preferences.getValue("token")

        RetrofitBuilder.api.savePhoto(bitmapToString(bitmap), "Bearer $token")
            .enqueue(object : Callback<UserPhotoResponse> {

                override fun onFailure(call: Call<UserPhotoResponse>, t: Throwable) {
                    onError("Upload Failed")
                }

                override fun onResponse(
                    call: Call<UserPhotoResponse>,
                    response: retrofit2.Response<UserPhotoResponse>
                ) {
                    try {
                        val success = response.body()?.success
                        val photo = response.body()?.photo

                        if (success!!) {
                            preferences.setValue("photo", photo!!)
                            onSuccess("Success")

                        } else {
                            onError(response.body()!!.message)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
    }


    private fun bitmapToString(bitmap: Bitmap?): String {
        if (bitmap != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val array: ByteArray = byteArrayOutputStream.toByteArray()


            return Base64.encodeToString(array, Base64.DEFAULT)
        }

        return ""
    }


    override fun onSuccess(message: String) {
        Toasty.success(this, message, Toasty.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toasty.error(this, message, Toasty.LENGTH_SHORT).show()
    }


}
