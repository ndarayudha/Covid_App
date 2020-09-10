package com.example.appkp.ui.dashboard.fragment



import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.appkp.R


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import java.util.jar.Manifest


class LocationFragment : Fragment(), OnMapReadyCallback {


    private lateinit var map: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val location1 = LatLng(-8.294535, 114.307428)
        val location2 = LatLng(-8.458892, 114.259420)
        val zoom = 15f

        map.addMarker(MarkerOptions().position(location1).title("Lokasi 1"))
        map.addMarker(MarkerOptions().position(location2).title("Lokasi 2"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, zoom))
    }
}
