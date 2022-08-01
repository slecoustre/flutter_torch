package center.appconseil.flutter_torch

import android.annotation.TargetApi
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


class FlutterTorchPlugin : MethodCallHandler, FlutterPlugin {
    private val TAG = "FlutterTorchPlugin"

    private var mApplicationContext: Context? = null
    private var mMethodChannel: MethodChannel? = null
    private var cameraManager: CameraManager? = null

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        onAttachedToEngine(binding.applicationContext, binding.binaryMessenger)
    }

    private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger) {
        mApplicationContext = applicationContext
        mMethodChannel = MethodChannel(messenger, "flutter_torch")
        mMethodChannel?.setMethodCallHandler(this)
        cameraManager =
            applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        mApplicationContext = null
        mMethodChannel?.setMethodCallHandler(null)
        mMethodChannel = null
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        Log.d(TAG, call.method)
        when (call.method) {
            "hasLamp" -> result.success(hasLamp())
            "turnOn" -> {
                turnTorch(true)
                result.success(null)
            }
            "turnOff" -> {
                turnTorch(false)
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    private fun hasLamp(): Boolean {
        val camManager = cameraManager ?: return false

        val cameraId = camManager.cameraIdList[0]

        val cameraCharacteristics = camManager.getCameraCharacteristics(cameraId)

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (cameraCharacteristics[CameraCharacteristics.FLASH_INFO_AVAILABLE]
            ?: false)
    }

    private fun turnTorch(on: Boolean) {
        if (!hasLamp()) {
            return
        }
        val camManager = cameraManager ?: return
        val cameraId = camManager.cameraIdList[0]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            turnTorchM(cameraId, on)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun turnTorchM(cameraId: String, on: Boolean) {
        val camManager = cameraManager ?: return
        camManager.setTorchMode(cameraId, on)
    }
}
