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

import com.aniharu.alertapet.Classes.Marcadores;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener
{

        private static final String TAG = "fudeu";
        //Variaveis globais
        GoogleMap mGoogleMap;
        MapView mMapView;
        GoogleApiClient mGoogleApiClient;
        SupportMapFragment mapFragment;
        Marcadores marcadores;
        Marcadores[] x = {};

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

            mMapView = view.findViewById(R.id.map);
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
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(getActivity(), "onMarkerClick", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

            this.mGoogleMap = googleMap;

            //Configuração do mapa
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            mGoogleMap.setBuildingsEnabled(false);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            //Restrição do mapa
            LatLng RIODEJANEIRO = new LatLng(-22.927350, -43.422644);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RIODEJANEIRO, 12));
            LatLngBounds RIODEJANEIROBOUNDS = new LatLngBounds(
                    new LatLng(-23.056930, -43.790686), new LatLng(-22.745730, -43.102667));
            mGoogleMap.setLatLngBoundsForCameraTarget(RIODEJANEIROBOUNDS);

            //Customizações
            CustomInfoWindow adapter = new CustomInfoWindow(MainFragment.this);
            mGoogleMap.setInfoWindowAdapter(adapter);

            //Marcador de teste
            //Receber o JSON do firebase com todos os marcadores, transformar num array de marcadores
            //e colocar o array no lugar do X
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            //Query select * from users where email = vEmail
            Query query = mDatabase.child("markers");

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        marcadores = messageSnapshot.getValue(Marcadores.class);
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Marcadores marcadores = new Marcadores();
            marcadores.setDescricao("teste dos cxx");
            marcadores.setImagemUrl("http://placehold.it/120x120&text=image1");
            marcadores.setLat(-22.87396);
            marcadores.setLng(-43.4313797);

            Marcadores marcadores2 = new Marcadores();
            marcadores2.setDescricao("teste dos marcadores");
            marcadores2.setImagemUrl("http://placehold.it/120x120&text=image4");
            marcadores2.setLat(-22.873994);
            marcadores2.setLng(-43.4632977);

            Marcadores[] x = {marcadores, marcadores2};
            adcionarMarcadores(x);

        }

        public void adcionarMarcadores(Marcadores[] marcadores)
        {

            for (Marcadores marker:marcadores) {

                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.position(new LatLng(marker.getLat(), marker.getLng()))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker))
                        .snippet(marker.descricao)
                        .title(marker.imagemUrl);

                mGoogleMap.addMarker(markerOpt);

            }

        }


        //Metodos nunca usados
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
