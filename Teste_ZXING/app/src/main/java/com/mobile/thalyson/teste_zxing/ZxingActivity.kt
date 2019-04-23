package com.mobile.thalyson.teste_zxing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.widget.TextView
import com.google.zxing.Result

import kotlinx.android.synthetic.main.content_zxing.*
import me.dm7.barcodescanner.core.CameraUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.w3c.dom.Text
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.requestPermissions
import pub.devrel.easypermissions.PermissionRequest

class ZxingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,ZXingScannerView.ResultHandler {

    val REQUEST_CODE_CAMERA = 182;
    private var txtResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val zXingScannerView = ZXingScannerView(this)

        txtResult = findViewById(R.id.txtResult)

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

        z_xing_scenner.setResultHandler ( this )

        // Para dispositivos HUAWEI
        val brand = Build.MANUFACTURER
        if(brand.equals("HUAWEI", true)) {
            z_xing_scenner.setAspectTolerance(0.5F)
        }

        z_xing_scenner.startCamera()

        // Alterar cores da borda, linha e fundo
//        z_xing_scenner.setBorderColor(Color.RED)
//        z_xing_scenner.setLaserColor(Color.YELLOW)
//        z_xing_scenner.setMaskColor(Color.BLACK)

    }

    override fun onPause() {
        super.onPause()

        z_xing_scenner.stopCamera()

        val camera = CameraUtils.getCameraInstance()
        if(camera != null)
            (camera as Camera).release()
    }

    override fun handleResult(result: Result?) {
        Log.i("LOG", "Conteúdo do código lido: ${result!!.text}")
        Log.i("LOG", "Formato do código lido: ${result.barcodeFormat.name}")

        z_xing_scenner.resumeCameraPreview ( this )

        txtResult?.text = result.toString()
    }

    fun ZXingScannerView.isFlashSupported(context: Context)= context
        .packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    fun enableFlash(context: Context,
    zXing: ZXingScannerView,
    status: Boolean){
        if(zXing.isFlashSupported(context)){
            zXing.flash = status
        }
    }
}
