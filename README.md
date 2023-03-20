# Cordova-plugin-qrscanner-hms

基于[华为HMS-scankit](https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/service-introduction-0000001050041994)开发,提供便捷的条形码和二维码扫描，助你快速构建应用内的扫码功能。可以实现远距离码或小型码的检测和自动放大，同时针对常见复杂扫码场景（如反光、暗光、污损、模糊、柱面）做了针对性识别优化，提升扫码成功率与用户体验。

目前仅支持默认模式。之前使用的是[cordova-plugin-qrscanner](https://github.com/bitpay/cordova-plugin-qrscanner) 无奈识别率实在是低，偶然看到华为有这个库试用之后发现识别率很高，所以做成了cordova插件分享出来！

## 支持平台

- Android
- Ios

## 功能

- 相机扫码
- 相册照片选择


## 安装

```shell
cordova plugin add cordova-plugin-qrscanner-hms
```

## 使用

### 扫描

```javascript
cordova.plugins.QrScanner.startScan(
  (codeStr) => {
    // success
  },
  (error) => {
    // error
  }
);
```

