package com.example.ifoundit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;

import com.naver.maps.map.LocationSource;

//커스텀 위치 소스
public class GpsOnlyLocationSource extends MainActivity implements LocationSource, LocationListener {
    @NonNull
    private final Context context;
    @Nullable
    private final LocationManager locationManager;
    @Nullable
    private LocationSource.OnLocationChangedListener listener;

    public GpsOnlyLocationSource(@NonNull Context context) {
        this.context = context;
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void activate(@NonNull LocationSource.OnLocationChangedListener listener) {
        if (locationManager == null) {
            return;
        }

        if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            //권한 요청 로직 생략
            return;
        }

        this.listener = listener;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);
    }

    @Override
    public void deactivate() {
        if (locationManager == null) {
            return;
        }

        listener = null;
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
