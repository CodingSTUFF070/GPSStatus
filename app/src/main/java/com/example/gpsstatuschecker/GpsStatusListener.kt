package com.example.gpsstatuschecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData

class GpsStatusListener(private val context: Context) : LiveData<Boolean>() {

    override fun onActive() {
        registerReceiver()
        checkGpsStatus()
    }

    override fun onInactive() {
        unRegisterReceiver()
    }

    private val gpsStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            checkGpsStatus()
        }
    }

    private fun checkGpsStatus() = if (isLocationEnable()) {
        postValue(true)
    } else {
        postValue(false)
    }

    private fun isLocationEnable() = context.getSystemService(LocationManager::class.java)
        .isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun registerReceiver() = context.registerReceiver(
        gpsStatusReceiver,
        IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
    )

    private fun unRegisterReceiver() = context.unregisterReceiver(gpsStatusReceiver)
}