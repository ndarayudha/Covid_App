package com.example.appkp.ui.dashboard.fragment


import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.appkp.R
import com.example.appkp.ui.dashboard.DashboardActivity
import com.example.appkp.util.PermissionManager


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import java.util.jar.Manifest


class LocationFragment : Fragment(), OnMapReadyCallback {


    private lateinit var map: GoogleMap
    private val TAG = LocationFragment::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1

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


//        // check permission
//        PermissionManager.check(context, )
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val poliwangi = LatLng(-8.294535, 114.307428)
        map.addMarker(MarkerOptions().position(poliwangi).title("Poliwangi"))
        map.moveCamera(CameraUpdateFactory.newLatLng(poliwangi))


//        setMapLongClick(map)
//        setPoiClick(map)
//        setMapStyle(map)

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.map_options, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        // Change the map type based on the user's selection.
//        R.id.normal_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_NORMAL
//            true
//        }
//        R.id.hybrid_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_HYBRID
//            true
//        }
//        R.id.satellite_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
//            true
//        }
//        R.id.terrain_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

//    private fun setMapLongClick(map: GoogleMap) {
//        map.setOnMapLongClickListener { latLng ->
//            val snippet = String.format(
//                Locale.getDefault(),
//                "Lat: %1$.5f, Long: %2$.5f",
//                latLng.latitude,
//                latLng.longitude
//            )
//            map.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(getString(R.string.dropped_pin))
//                    .snippet(snippet)
//            )
//        }
//    }
//
//    private fun setPoiClick(map: GoogleMap) {
//        map.setOnPoiClickListener { poi ->
//            val poiMarker = map.addMarker(
//                MarkerOptions()
//                    .position(poi.latLng)
//                    .title(poi.name)
//            )
//            poiMarker.showInfoWindow()
//        }
//    }

//    private fun setMapStyle(map: GoogleMap) {
//        try {
//            // Customize the styling of the base map using a JSON object defined
//            // in a raw resource file.
//            val success = map.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    this.context,
//                    R.raw.map_style_default
//                )
//            )
//
//            if (!success) {
//                Log.e(TAG, "Style parsing failed.")
//            }
//        } catch (e: Resources.NotFoundException) {
//            Log.e(TAG, "Can't find style. Error: ", e)
//        }
//    }




}
