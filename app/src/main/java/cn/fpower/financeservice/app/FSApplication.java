package cn.fpower.financeservice.app;

import android.app.Application;
import android.content.Context;

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

import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.AppUtils;
import cn.fpower.financeservice.utils.LogUtils;

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

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.setDebug(true);//先开启log日志

        if (mInstance == null) {
            mInstance = this;
        }
        mContext = getApplicationContext();
        initAppParames(this);
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

}
