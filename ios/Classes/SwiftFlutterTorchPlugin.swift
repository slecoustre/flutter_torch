import Flutter
import UIKit
import AVFoundation

public class SwiftFlutterTorchPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_torch", binaryMessenger: registrar.messenger())
        let instance = SwiftFlutterTorchPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        print("handle \(call.method)")
        switch call.method {
        case "hasLamp":
            result(NSNumber(value: hasTorch()))
            break
        case "turnOff":
            turnOff()
            result(nil)
            break
        case "turnOn":
            turnOn()
            result(nil)
            break
        default:
            result(FlutterMethodNotImplemented);
        }
    }
    
    func hasTorch() -> Bool {
        guard let device = AVCaptureDevice.default(for: .video) else {return false}
        return device.hasFlash && device.hasTorch
    }
    
    func turnOff() {
        guard let device = AVCaptureDevice.default(for: .video) else {return}
        if (device.hasFlash && device.hasTorch) {
            try! device.lockForConfiguration()
            device.torchMode = .off
            device.unlockForConfiguration()
        }
    }
    
    func turnOn() {
        guard let device = AVCaptureDevice.default(for: .video) else {return}
        if (device.hasFlash && device.hasTorch) {
            try! device.lockForConfiguration()
            device.torchMode = .on
            device.unlockForConfiguration()
        }
    }
}
