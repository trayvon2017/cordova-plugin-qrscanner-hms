package com.tryvon2017.cordova;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PermissionHelper;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import org.json.JSONArray;
import org.json.JSONException;


public class QrScanner extends CordovaPlugin {
    private final int PERMISSION_REQUEST_CODE = 2017; // 用于检查权限
    private final int REQUEST_CODE_SCAN = 2018; // 用于接收扫码结果
    private final int SCAN_RESULT_OK = -1;
    private final int PERMISSIONS_LENGTH = 2;

    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private CallbackContext scanCallBack;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("startScan")) {
            scanCallBack = callbackContext;
            if(!hasPermisssion()){
                requestPermissions(PERMISSION_REQUEST_CODE);
            } else {
                scan(scanCallBack);
            }
            return true;
        }
        return false;
    }

    private void scan(CallbackContext callbackContext) {
        cordova.setActivityResultCallback(this);
        ScanUtil.startScan(cordova.getActivity(), REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
    }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SCAN_RESULT_OK && requestCode == REQUEST_CODE_SCAN && data != null) {
            HmsScan hmsScan = data.getParcelableExtra(ScanUtil.RESULT);
            String codeResult = hmsScan.getOriginalValue();
            scanCallBack.success(codeResult);
        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == PERMISSIONS_LENGTH && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            scan(scanCallBack);
        } else {
            scanCallBack.error("permission denied");
        }
    }

    /**
     * 检查权限
     * @return
     */
    public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

}
