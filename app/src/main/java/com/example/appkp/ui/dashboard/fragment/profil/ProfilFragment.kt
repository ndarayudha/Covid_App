package com.example.appkp.ui.dashboard.fragment.profil

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.auth.FindUserResponse
import com.example.appkp.model.auth.UserPhotoResponse2
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import kotlinx.android.synthetic.main.fragment_profil.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilFragment : Fragment() {


    lateinit var preference: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preferences(view.context)
        val nama = preference.getValue("name")

        getUserInfo(nama)


//        val items = listOf("Material", "Design", "Components", "Android")
//        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_gender, items)
//        (tf_jeniskelamin.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        btn_simpanPerubahan.setOnClickListener {

            val name = tf_nama.editText?.text.toString()
            val ttl = tf_tanggal.editText?.text.toString()
            val kelamin = tf_jenisKelamin.editText?.text.toString()
            val alamat = tf_alamat.editText?.text.toString()
            val tinggiBadan = tf_tinggiBadan.editText?.text.toString()
            val beratBadan = tf_beratBadan.editText?.text.toString()

            updatePersonalInfo(
                name = name,
                ttl = ttl,
                jenisKelamin = kelamin,
                alamat = alamat,
                tinggiBadan = tinggiBadan.toInt(),
                beratBadan = beratBadan.toInt(),
                context = view.context
            )

        }
    }



    private fun setInputText(
        name: String?,
        ttl: String? = "",
        jenisKelamin: String? = "",
        alamat: String? = "",
        tinggiBadan: String?,
        beratBadan: String?
    ) {
        ti_nama.setText(name)
        ti_tanggal.setText(ttl)
        ti_jenisKelamin.setText(jenisKelamin)
        ti_alamat.setText(alamat)
        ti_tinggiBadan.setText(tinggiBadan)
        ti_beratBadan.setText(beratBadan)
    }


    private fun updatePersonalInfo(
        name: String?,
        ttl: String?,
        jenisKelamin: String?,
        alamat: String?,
        tinggiBadan: Int?,
        beratBadan: Int?,
        context: Context
    ) {
        val token = preference.getValue("token")!!


        RetrofitBuilder(Constant.BASE_URL).api.updatePersonalInfo(
            name, ttl, jenisKelamin, alamat, tinggiBadan, beratBadan, "Bearer $token"
        ).enqueue(object : Callback<UserPhotoResponse2> {
            override fun onFailure(call: Call<UserPhotoResponse2>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UserPhotoResponse2>,
                response: Response<UserPhotoResponse2>
            ) {
                try {
                    val success = response.body()?.success
                    val message = response.body()?.message
                    val pasien = response.body()?.pasien


                    if (success!!) {
                        preference.setValue("ttl", pasien!!.ttl.toString())
                        preference.setValue("jenisKelamin", pasien.jenis_kelamin.toString())
                        preference.setValue("alamat", pasien.alamat.toString())
                        preference.setValue("tinggiBadan", pasien.tinggi_badan.toString())
                        preference.setValue("beratBadan", pasien.berat_badan.toString())

                        showToast(context, message)
                    } else {
                        showToast(context, message)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }


    private fun getUserInfo(name: String?) {
        val token = preference.getValue("token")!!

        RetrofitBuilder(Constant.BASE_URL).api.getUser("Bearer $token")
            .enqueue(object : Callback<FindUserResponse> {

                override fun onFailure(call: Call<FindUserResponse>, t: Throwable) {
                    Log.d("user info error", t.message)
                }

                override fun onResponse(
                    call: Call<FindUserResponse>,
                    response: Response<FindUserResponse>
                ) {
                    try {
                        val pasien = response.body()?.user
                        val tanggalLahir = pasien?.ttl
                        val jenisKelamin = pasien?.jenis_kelamin
                        val alamatPasien = pasien?.alamat
                        val tinggiBadanPasien = pasien?.tinggi_badan.toString()
                        val beratBadanPasien = pasien?.berat_badan.toString()

                        setInputText(
                            name = name,
                            ttl = tanggalLahir,
                            jenisKelamin = jenisKelamin,
                            alamat = alamatPasien,
                            tinggiBadan = tinggiBadanPasien,
                            beratBadan = beratBadanPasien
                        )

                        progressBar.visibility = View.GONE
                        tf_nama.visibility = View.VISIBLE
                        tf_tanggal.visibility = View.VISIBLE
                        tf_jenisKelamin.visibility = View.VISIBLE
                        tf_alamat.visibility = View.VISIBLE
                        tf_beratBadan.visibility = View.VISIBLE
                        tf_tinggiBadan.visibility = View.VISIBLE
                        btn_simpanPerubahan.visibility = View.VISIBLE

                    } catch (e: Exception) {

                    }
                }

            })
    }


    private fun showToast(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}