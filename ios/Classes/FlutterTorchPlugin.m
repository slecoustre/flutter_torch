#import "FlutterTorchPlugin.h"
#import <flutter_torch/flutter_torch-Swift.h>

@implementation FlutterTorchPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTorchPlugin registerWithRegistrar:registrar];
}
@end
