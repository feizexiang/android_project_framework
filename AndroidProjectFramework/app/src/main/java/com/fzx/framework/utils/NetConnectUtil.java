package com.fzx.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * WIFI是否打开
 *
 * @ClassName: WifiControl
 * @author fzx
 * @date 2015-1-22 下午1:23:24
 *
 */
public class NetConnectUtil {
	/**
	 * 网络是否连接
	 *
	 * @Title: isNetworkConnected
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * WIFI是否连接
	 *
	 * @Title: isWifi
	 * @param context
	 * @return
	 */
	public static Boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return false;
		}
		if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取IP
	 *
	 * @Title: getLocalIpAddress
	 * @return
	 */
	public synchronized static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress.getAddress().length == 4) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			DLog.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 获取WIFI
	 *
	 * @Title: getWifiIpAddress
	 * @param context
	 * @return
	 */
	public static String getWifiIpAddress(Context context) {
		if (context != null) {
			// 获取wifi服务
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			// 判断wifi是否开启
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(true);
			}
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "."
					+ ((ipAddress >> 16) & 0xFF) + "."
					+ (ipAddress >> 24 & 0xFF);
		}
		return "";
	}

}
