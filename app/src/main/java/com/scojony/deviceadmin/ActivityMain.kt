package com.scojony.deviceadmin

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.rosan.dhizuku.api.Dhizuku
import com.rosan.dhizuku.api.Dhizuku.isPermissionGranted
import com.rosan.dhizuku.api.Dhizuku.requestPermission
import com.rosan.dhizuku.api.Dhizuku.setDelegatedScopes
import com.rosan.dhizuku.api.DhizukuRequestPermissionListener
import com.scojony.deviceadmin.tasker.playstatechanged.PlayState
import com.scojony.deviceadmin.tasker.playstatechanged.PlayStateChangedActivity
import com.scojony.deviceadmin.tasker.togglingcondition.TogglingConditionRunner
import com.tasker.deviceadmin.R
import com.tasker.deviceadmin.databinding.ActivityMainBinding

private fun Any.getSystemService(devicePolicyService: String): Any {

}

class ActivityMain(var DevicePolicyManager: Any, var EditText: Any) : Activity() {
    private lateinit var binding: ActivityMainBinding
    val REQUIRED_DELEGATED_SCOPES: Array<String> = arrayOf("DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL","DevicePolicyManager.DELEGATION_PACKAGE_ACCESS")
    private fun toast(dhizukuInitFailed: Int) {
        TODO("Not yet implemented")
    }
    override fun  onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        ((DevicePolicyManager).getSystemService(DEVICE_POLICY_SERVICE)).also {
            var devicePolicyManager = it
        };
        setContentView(R.layout.main_activity);
        if (!Dhizuku.init(this)) {
            toast(R.string.dhizuku_init_failed);
            finish();
            return;
        }
        if (Dhizuku.getVersionCode() < 5) {
            toast(R.string.dhizuku_version_too_older);
            finish();
            return;
        }
        if (!isPermissionGranted())
            requestPermission(/* listener = */ new DhizukuRequestPermissionListener() {
                @RequiresApi(Build.VERSION_CODES.O)
                @Override
                fun <grantResult> onRequestPermission(int: grantResult) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            setDelegatedScopes()
                        }; else {
                            toast(R.string.dhizuku_permission_denied);
                            finish();
                        }
                    }
                }
            })

