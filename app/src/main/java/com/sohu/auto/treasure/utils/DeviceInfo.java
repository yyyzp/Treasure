package com.sohu.auto.treasure.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import java.util.UUID;

public class DeviceInfo {

    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";



    /**
     * 受限于各种因素 该方案是不可靠的
     * 比如权限变化时  取到的两次可能不一致
     *
     * @param context
     * @return
     */


    /**
     * @param context
     * @return
     */

    @SuppressWarnings("MissingPermission")
    public static String getImei(Context context) {
        String imei = "";
        //读取imei
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
        }
        return imei;
    }

    @SuppressWarnings("MissingPermission")
    public static String getIMSI(Context context) {
        String imsi = "";

        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                imsi = manager.getSubscriberId();
                if (imsi == null) {
                    imsi = "";
                }
            }
        } catch (Exception e) {

        }

        return imsi;
    }


//    /**
    //  * 美团代码 后续可考察可行性
    //**/
//    static String deviceId(Context context) {
//        try {
//            String imei = null;
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//                // 1 compute IMEI
//                TelephonyManager TelephonyMgr = (TelephonyManager) context
//                        .getSystemService(Context.TELEPHONY_SERVICE);
//
//                if (TelephonyMgr != null) {
//                    imei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
//                }
//                if (!TextUtils.isEmpty(imei)) {
//                    // got imei, return it
//                    return imei.trim();
//                }
//            }
//            imei = null;
//            // 2 compute DEVICE ID
//            String devIDShort = “35”
//            + // we make this look like a valid IMEI
//                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10
//                    + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
//                    + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
//                    + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
//                    + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
//                    + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
//                    + Build.USER.length() % 10; // 13 digits
//
//            // 3 android ID - unreliable
//            String androidId = Settings.Secure.getString(
//                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
//
//            // 4 wifi manager read MAC address - requires
//            // android.permission.ACCESS_WIFI_STATE or comes as null
//            WifiManager wm = (WifiManager) context
//                    .getSystemService(Context.WIFI_SERVICE);
//            String wlanMac = null;
//            if (wm != null) {
//                try {
//                    wlanMac = wm.getConnectionInfo().getMacAddress();
//                } catch (Exception e) {
//                    UUIDHelper.getInstance().getEventLogProvider().throwableReport(e);
////                    Log.d(“UUID”, e.getMessage(), e);
//                }
//            }
//
//            // 5 Bluetooth MAC address android.permission.BLUETOOTH required, so
//            // currenty just comment out, in case we use this method later
//            String btMac = null;
//
//            /*
//             * BluetoothAdapter bluetoothAdapter = null; // Local Bluetooth
//             * adapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//             * if (bluetoothAdapter != null) { try { btMac =
//             * bluetoothAdapter.getAddress(); } catch (Exception e) {
//             * Log.d(“UUID”, e.getMessage(), e); } }
//             */
//
//            // 6 SUM THE IDs
//            String devIdLong = imei + devIDShort + androidId + wlanMac + btMac;
//            MessageDigest m = null;
//            try {
//                m = MessageDigest.getInstance(“MD5");
//            } catch (NoSuchAlgorithmException e) {
//                UUIDHelper.getInstance().getEventLogProvider().throwableReport(e);
////                Log.d(“UUID”, e.getMessage(), e);
//            }
//            m.update(devIdLong.getBytes(), 0, devIdLong.length());
//            byte md5Data[] = m.digest();
//
//            String uniqueId = “”;
//            for (int i = 0, len = md5Data.length; i < len; i++) {
//                int b = (0xFF & md5Data[i]);
//                // if it is a single digit, make sure it have 0 in front (proper
//                // padding)
//                if (b <= 0xF)
//                    uniqueId += “0”;
//                // add number to string
//                uniqueId += Integer.toHexString(b);
//            }
//            uniqueId = uniqueId.toUpperCase();
//            if (uniqueId.length() > 15) {
//                uniqueId = uniqueId.substring(0, 15);
//            }
//            return uniqueId.trim();
//        } catch (Throwable t) {
//            UUIDHelper.getInstance().getEventLogProvider().throwableReport(t);
//        }
//        return “DeviceId0";
//    }

    private static String getUUID(Context context) {
        String uniqueID;
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE);
        uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(PREF_UNIQUE_ID, uniqueID);
            editor.apply();
        }
        return uniqueID;
    }

    /**
     * Okhttp 设置header User-Agent
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * MAC地址通过非系统接口可采集(6.0以下通过系统接口采集)，采集场景仅限于处于wifi连接状态。
     * 非系统接口，其实指的就是直接读取文件的方式
     * 下面是6.0以下的方式
     *
     * @param context
     * @return
     */
    @SuppressWarnings("MissingPermission")
    private static String getMac(Context context) {
        try {
            WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
                return localWifiInfo.getMacAddress();
            }
            return "";
        } catch (Exception localException) {

        }
        return "";
    }



    public static boolean isNotificationEnable(Context context) {
        if (null == context) return false;
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }


    /**
     * 跳转设置允许安装第三方应用权限设置界面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void toInstallPermissionSettingIntent(Activity activity) {
        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        activity.startActivity(intent);
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕大小
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }



    /**
     * 根据dip返回当前设备上的px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2Px(Context context, int dip) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        float density = dm.density;
        return (int) (dip * density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
