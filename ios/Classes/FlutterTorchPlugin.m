#import "FlutterTorchPlugin.h"
#if __has_include(<flutter_torch/flutter_torch-Swift.h>)
#import <flutter_torch/flutter_torch-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "lutter_torch-Swift.h"
#endif

@implementation FlutterTorchPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTorchPlugin registerWithRegistrar:registrar];
}
@end
