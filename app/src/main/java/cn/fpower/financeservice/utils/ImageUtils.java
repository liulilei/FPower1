package cn.fpower.financeservice.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import cn.fpower.financeservice.app.FSApplication;

public class ImageUtils {

    public static Bitmap getBitmap(String url) {
        return FSApplication.getInstance().getImgLoader().loadImageSync(url);
    }

    /**
     * 加载圆形图片
     *
     * @param url       图片的url地址
     * @param imageView 要设置图片的控件
     * @param rId       配置默认图片的R文件地址
     */
    public static void displayImageRoundImg(int rId, String url, ImageView imageView) {
        displayImage(url, imageView, getRoundImgOptions(rId));
    }

    /**
     * 加载一般图片
     *
     * @param url       图片的url地址
     * @param imageView 要设置图片的控件
     * @param rId       配置默认图片的R文件地址
     */
    public static void displayNormalImg(int rId, String url, ImageView imageView) {
        displayImage(url, imageView, getNormalOptions(rId));
    }

    /**
     * 加载一般图片不保存在本地缓存在内存
     *
     * @param url       图片的url地址
     * @param imageView 要设置图片的控件
     * @param rId       配置默认图片的R文件地址
     */
    public static void displayNormalImgLocation(int rId, String url, ImageView imageView) {
        displayImage(url, imageView, getNormalOptionsLocation(rId));
    }

    public static void displayImage(String url, ImageView imageView, DisplayImageOptions options) {

        FSApplication.getInstance().getImgLoader().displayImage(url, imageView, options);
    }

    public static DisplayImageOptions getRoundImgOptions(int rId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(rId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(rId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(rId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                        // .imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                        // .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .displayer(new RoundedBitmapDisplayer(10))// 加载圆形图片
                .build();// 构建完成

        return options;
    }

    public static DisplayImageOptions getNormalOptions(int rId) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(rId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(rId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(rId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                        // .imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                        // .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();// 构建完成

        return options;
    }

    public static DisplayImageOptions getNormalOptionsLocation(int rId) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(rId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(rId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(rId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                        // .imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                        // .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();// 构建完成

        return options;
    }
}
