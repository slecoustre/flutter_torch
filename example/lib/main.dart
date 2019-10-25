import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_torch/flutter_torch.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _hasTorch = false;

  @override
  void initState() {
    super.initState();
    initTorchState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initTorchState() async {
    bool hasTorch = false;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      hasTorch = await FlutterTorch.hasLamp;
    } on PlatformException {
      hasTorch = false;
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _hasTorch = hasTorch;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Has torch: $_hasTorch\n'),
        ),
      ),
    );
  }
}
