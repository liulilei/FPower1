package cn.fpower.financeservice.utils;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;

public class LocationUtils {

    public static Location getLocation(Context context) {
        Geocoder geocoder = new Geocoder(context);
        // 用于获取Location对象，以及其他
        String serviceName = Context.LOCATION_SERVICE;
        // 实例化一个LocationManager对象
        LocationManager locationManager = (LocationManager) context
                .getSystemService(serviceName);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false); // 不要求海拔
        criteria.setBearingRequired(false); // 不要求方位
        criteria.setCostAllowed(false); // 不允许有话费
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true);

        if (provider == null) {
            return null;
        }

        // 通过最后一次的地理位置来获得Location对象
        return locationManager.getLastKnownLocation(provider);
    }

    /**
     * 获取经度
     *
     * @param context
     * @return
     */
    public static double getLongitude(Context context) {
        Location location = getLocation(context);
        if (location != null) {
            return location.getLongitude();
        } else {
            return 0;
        }
    }

    /**
     * 返回纬度
     *
     * @param context
     * @return
     */
    public static double getLatitude(Context context) {
        Location location = getLocation(context);
        if (location != null) {
            return location.getLatitude();
        } else {
            return 0;
        }
    }

    /**
     * 通过地理坐标获取城市名 其中CN分别是city和name的首字母缩写
     *
     * @param context
     */
    public static String getCNBylocation(Context context) {

        Geocoder geocoder = new Geocoder(context);
        // 用于获取Location对象，以及其他
        String serviceName = Context.LOCATION_SERVICE;
        // 实例化一个LocationManager对象
        LocationManager locationManager = (LocationManager) context
                .getSystemService(serviceName);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false); // 不要求海拔
        criteria.setBearingRequired(false); // 不要求方位
        criteria.setCostAllowed(false); // 不允许有话费
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true);

        if (provider == null) {
            return "";
        }

        // 通过最后一次的地理位置来获得Location对象
        Location location = locationManager.getLastKnownLocation(provider);

        String mcityName = "";
        List<Address> addList = null;
        double lat = 0;
        double lng = 0;
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        } else {
            return "";
        }

        try {
            addList = geocoder.getFromLocation(lat, lng, 1); // 解析经纬度
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address add = addList.get(i);
                mcityName += add.getLocality();
            }
        }
        if (mcityName.length() != 0) {
            return mcityName.substring(0, (mcityName.length() - 1));
        } else {
            return mcityName;
        }
    }
}
