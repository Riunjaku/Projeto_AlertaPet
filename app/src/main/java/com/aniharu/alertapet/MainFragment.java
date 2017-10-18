package com.aniharu.alertapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;


public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener
{

        GoogleMap mGoogleMap;
        MapView mMapView;
        GoogleApiClient mGoogleApiClient;
        private static final String TAG = "fudeu";
        private double longitude;
        private double latitude;
        SupportMapFragment mapFragment;


        public MainFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(this);
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.map, mapFragment)
                        .commit();
            }
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mMapView = (MapView) view.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            mMapView.getMapAsync(this);

            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity() /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .build();


        }

        @Override
        public void onStart() {
            super.onStart();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
            mMapView.onStart();

        }

        @Override
        public void onStop() {
            super.onStop();
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.stopAutoManage((getActivity()));
                mGoogleApiClient.disconnect();
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            mMapView.onResume();
            mGoogleApiClient.reconnect();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent result) {

            super.onActivityResult(requestCode, resultCode, result);

            mGoogleApiClient.connect();
        }

        @Override
        public void onClick(View view) {
            Log.v(TAG, "view click event");
        }

        @Override
        public void onConnected(@Nullable Bundle bundle) {


        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d("Dominando", "onDisconnected");
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(getActivity(), "onMarkerClick", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.mGoogleMap = googleMap;
            //desativa os controles do mapa
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            mGoogleMap.setBuildingsEnabled(false);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
            //seta o rio de janeiro como ponto inicial
            LatLng RIODEJANEIRO = new LatLng(-22.927350, -43.422644);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RIODEJANEIRO, 12));
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            //seta as bordas do rio de janeiro
            LatLngBounds RIODEJANEIROBOUNDS = new LatLngBounds(
                    new LatLng(-23.056930, -43.790686), new LatLng(-22.745730, -43.102667));
            mGoogleMap.setLatLngBoundsForCameraTarget(RIODEJANEIROBOUNDS);


        }

        @Override
        public void onMapLongClick(LatLng latLng) {

        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {

        }

}
