package center.appconseil.flutter_torch

import android.annotation.TargetApi
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager;
import android.os.Build

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterTorchPlugin(var cameraManager: CameraManager): MethodCallHandler {

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_torch")
      registrar.activity().getSystemService(Context.CAMERA_SERVICE)
      channel.setMethodCallHandler(FlutterTorchPlugin(registrar.activity().getSystemService(Context.CAMERA_SERVICE) as CameraManager))
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method) {
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

  fun hasLamp(): Boolean {
    val cameraId = cameraManager.cameraIdList[0]

    val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (cameraCharacteristics[CameraCharacteristics.FLASH_INFO_AVAILABLE] ?: false)
  }

  fun turnTorch(on: Boolean) {
    if (!hasLamp()) {
      return
    }
    val cameraId = cameraManager.cameraIdList[0]
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      turnTorchM(cameraId, on)
    }
    else {

    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  fun turnTorchM(cameraId: String, on: Boolean) {
    cameraManager.setTorchMode(cameraId, on)
  }
}
