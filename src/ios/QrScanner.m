/********* QrScanner.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <Cordova/CDVPluginResult.h>
#import <Cordova/CDVAppDelegate.h>
#import <ScanKitFrameWork/ScanKitFrameWork.h>

@interface QrScanner : CDVPlugin {
  // Member variables go here.
  NSString* callbackId;
}

- (void)startScan:(CDVInvokedUrlCommand*)command;
@end

@implementation QrScanner

- (void)startScan:(CDVInvokedUrlCommand*)command
{
    callbackId = command.callbackId;
    HmsDefaultScanViewController *hmsDefaultScanViewController = [[HmsDefaultScanViewController alloc] init];
    hmsDefaultScanViewController.defaultScanDelegate = self;
    [self.webView addSubview:hmsDefaultScanViewController.view];
    [self.viewController addChildViewController:hmsDefaultScanViewController];
    [self.viewController didMoveToParentViewController:hmsDefaultScanViewController];
    self.viewController.navigationController.navigationBarHidden = YES;
}

- (void)defaultScanDelegateForDicResult:(NSDictionary *)resultDic{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *toastString = [NSString stringWithFormat:@"%@",[resultDic objectForKey:@"text"]];
        CDVPluginResult * pluginResult =[CDVPluginResult resultWithStatus : CDVCommandStatus_OK messageAsString : toastString];
        [self.commandDelegate sendPluginResult: pluginResult callbackId: callbackId];
    });
}

- (void)defaultScanImagePickerDelegateForImage:(UIImage *)image{
    NSDictionary *dic = [HmsBitMap bitMapForImage:image withOptions:[[HmsScanOptions alloc] initWithScanFormatType:ALL Photo:true]];
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *toastString = [NSString stringWithFormat:@"%@",[dic objectForKey:@"text"]];
        CDVPluginResult * pluginResult =[CDVPluginResult resultWithStatus : CDVCommandStatus_OK messageAsString : toastString];
        [self.commandDelegate sendPluginResult: pluginResult callbackId: callbackId];
    });
}

@end
