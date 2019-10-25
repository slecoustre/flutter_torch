import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTorch {
  static const MethodChannel _channel = const MethodChannel('flutter_torch');

  static Future<bool> get hasLamp async =>
      await _channel.invokeMethod('hasLamp');

  static Future turnOn() => _channel.invokeMethod('turnOn');

  static Future turnOff() => _channel.invokeMethod('turnOff');
}
