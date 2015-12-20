package cn.fpower.financeservice.app;

import android.app.Application;
import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.ProvinceInfo;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.AppUtils;
import cn.fpower.financeservice.utils.LogUtils;
import cn.fpower.financeservice.utils.StringUtils;

/*
 * application
 */
public class FSApplication extends Application {

    //应用版本号
    public static int APP_VERSION_CODE;

    //应用版本名
    public static String APP_VERSION_NAME;

    //手机的imei码
    public static String DEVICE_IMEI;

    //手机型号
    public static String MOBILE_TYPE;

    //手机系统版本号
    public static String SYSTEM_TYPE;

    //渠道号
    public static String CHANNEL_NAME;

    private static FSApplication mInstance;

    public static Context mContext;

    private ImageLoader mImgLoader;

    //用户信息
    private UserInfo userInfo;

    //声明AMapLocationClient类对象
    public AMapLocationClient locationClient = null;

    //声明mLocationOption对象
    public AMapLocationClientOption locationOption = null;

    private boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.setDebug(true);//先开启log日志

        if (mInstance == null) {
            mInstance = this;
        }
        mContext = getApplicationContext();
        initAppParames(this);
        initDate();
    }


    private static ProvinceData provinceData;

    public static ProvinceData getProvinceData() {
        return provinceData;
    }

    private void initDate() {
        provinceData=new ProvinceData();
        Gson gson = new Gson();
        String city = StringUtils.getJsonFromAssets(this, "city.txt");
        String province = StringUtils.getJsonFromAssets(this, "provice.txt");
        String distric = StringUtils.getJsonFromAssets(this, "distric.txt");
        ProvinceInfo provinceInfo = gson.fromJson(province, ProvinceInfo.class);
        ProvinceInfo cityInfo = gson.fromJson(city, ProvinceInfo.class);
        ProvinceInfo districInfo = gson.fromJson(distric, ProvinceInfo.class);
        ArrayList<ProvinceInfo.Province> provinceList = provinceInfo.province;
        TreeMap<String, ArrayList<Map<String, String>>> cityMap = cityInfo.city;
        TreeMap<String, ArrayList<Map<String, String>>> districtMap = districInfo.district;
        provinceData.setOptions1Items(provinceList);
        ArrayList<ProvinceInfo.Province> cityList_01;
        ArrayList<ArrayList<ProvinceInfo.Province>> districList_01;
        ArrayList<ProvinceInfo.Province> districList_02;
        ArrayList<Map<String, String>> cityList;
        ArrayList<Map<String, String>> districList;
        for (ProvinceInfo.Province pro : provinceList) {
            cityList_01 = new ArrayList<ProvinceInfo.Province>();
            districList_01 = new ArrayList<ArrayList<ProvinceInfo.Province>>();
            cityList = cityMap.get(pro.code);
            provinceData.getMap().put(pro.code,pro.name);
            for (Map<String, String> mapCity : cityList) {
                for (String k : mapCity.keySet()) {
                    districList_02 = new ArrayList<ProvinceInfo.Province>();
                    cityList_01.add(new ProvinceInfo().new Province(k, mapCity.get(k)));
                    provinceData.getMap().put(k, mapCity.get(k));
                    districList = districtMap.get(k);
                    for (Map<String, String> districMap : districList) {
                        for (String k1 : districMap.keySet()) {
                            districList_02.add(new ProvinceInfo().new Province(k1, districMap.get(k1)));
                            provinceData.getMap().put(k1, districMap.get(k1));
                        }
                    }
                    districList_01.add(districList_02);
                }
            }
            provinceData.getOptions3Items().add(districList_01);
            provinceData.getOptions2Items().add(cityList_01);
        }
    }

    private void initAppParames(Context context) {
        APP_VERSION_CODE = AppUtils.getAppVersionCode(context);
        APP_VERSION_NAME = AppUtils.getAppVersionName(context);
        DEVICE_IMEI = AppUtils.getDeviceImei(context);
        CHANNEL_NAME = AppUtils.getAppMetaData(context, "UMENG_CHANNEL");
        MOBILE_TYPE = AppUtils.getDeviceName();
        SYSTEM_TYPE = AppUtils.getDeviceVersion();
    }

    public static synchronized FSApplication getInstance() {
        return mInstance;
    }

    /**
     * 提供ImageLoader的获取方法，ImageLoader本身是一个单例。
     *
     * @return
     */
    public ImageLoader getImgLoader() {
        if (mImgLoader == null) {

            mImgLoader = ImageLoader.getInstance();
            mImgLoader.init(initImgloadConf());
        }
        return mImgLoader;
    }

    /**
     * 配置ImageLoader的config。
     *
     * @return ImageLoaderConfiguration
     */
    private ImageLoaderConfiguration initImgloadConf() {

        File cacheDir = new File(Constants.IMG_LOAD_CACHE_PATH);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(1024, 800)
                        // max width, max height，即保存的每个缓存文件的最大长宽
                        // .diskCacheExtraOptions(480, 800,null) // Can slow
                        // ImageLoader, use it carefully (Better don't use
                        // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                        // 线程池内加载的数量,最好是1-5
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()

                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(20)
                        //
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                        // 缓存的文件数量
                .diskCache(new UnlimitedDiscCache(cacheDir))
                        // 自定义缓存路径

                        // 将缓存下来的文件以什么方式命名
                        // 里面可以调用的方法有
                        // 1.new Md5FileNameGenerator() //使用MD5对UIL进行加密命名
                        // 2.new HashCodeFileNameGenerator()//使用HASHCODE对UIL进行加密命名
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(this, 20 * 1000, 30 * 1000)) // connectTimeout
                        // (20
                        // s),
                        // readTimeout
                        // (30
                        // s)超时时间
                .imageDecoder(new BaseImageDecoder(false)) // default
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建

        return config;

    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public AMapLocationClient getAMapLocationClient() {
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getApplicationContext());
        }
        locationClient.setLocationOption(getAMapLocationClientOption());
        return locationClient;
    }

    public AMapLocationClientOption getAMapLocationClientOption() {
        //初始化定位参数
        locationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
        return locationOption;
    }

}
