package com.mobile.thalyson.teste_zxing

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.zxing.Result

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_zxing.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.requestPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.util.jar.Manifest

class ZxingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,ZXingScannerView.ResultHandler {

    val REQUEST_CODE_CAMERA = 182;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val zXingScannerView = ZXingScannerView(this)

        setContentView(R.layout.content_zxing)
        askCameraPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun askCameraPermission() {
        requestPermissions(
            PermissionRequest.Builder(this, REQUEST_CODE_CAMERA, Manifest.permission.CAMERA)
                .setRationale("Permissão Necessária.")
                .setPositiveButtonText("OK")
                .setNegativeButtonText("Cancelar")
                .build())
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()

        z_xing_scenner.setResultHandler { this }
    }

    override fun handleResult(result: Result?) {
        Log.i("LOG", "Conteúdo do código lido: ${result!!.text}")
        Log.i("LOG", "Formato do código lido: ${result.barcodeFormat.name}")

        z_xing_scenner.resumeCameraPreview { this }
    }
}
