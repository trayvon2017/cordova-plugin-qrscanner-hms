/*
 * @Description: 
 * @Date: 2023-03-20 10:52:36
 * @LastEditors: cfb
 * @LastEditTime: 2023-03-20 11:18:24
 * @FilePath: /cordova-plugin-qrscanner-hms/www/QrScanner.js
 */
var exec = require('cordova/exec');

/**
 * 开始扫码
 * @param {*} success success callback
 * @param {*} error error callback
 * @param {*} arg0 预留参数 暂时未启用
 */
exports.startScan = function (success, error, arg0) {
    exec(success, error, 'QrScanner', 'startScan', [arg0]);
};
