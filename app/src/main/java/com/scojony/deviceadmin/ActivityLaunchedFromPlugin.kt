package com.scojony.deviceadmin

import android.app.Activity
import android.os.Bundle
import com.rosan.dhizuku.api.Dhizuku;

class ActivityLaunchedFromPlugin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launched_from_background)
        Dhizuku.init()
    }

}