package cn.fpower.financeservice.net;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.constants.ResultCode;
import cn.fpower.financeservice.utils.DialogUtils;
import cn.fpower.financeservice.utils.LogUtils;
import cn.fpower.financeservice.utils.NetUtil;
import cn.fpower.financeservice.utils.ToastUtils;

public class NetworkService {

    private static final String TAG = "NetworkService";

    private static HttpUtils mHttpUtils;

    public static void post(Context cont, String pUrl, IRequestListener pListener) {
        post(cont, pUrl, null, pListener);
    }

    public static void post(final Context cont, String pUrl, RequestParams params, final IRequestListener pListener) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(1000 * 10);
            mHttpUtils.configCurrentHttpCacheExpiry(0);
        }
        if (!NetUtil.isNetworkConnected(cont)) {
            ToastUtils.show(cont, R.string.check_net);
            if (null != pListener) {
                pListener.onError("请检查网络");
            }
            return;
        }
        mHttpUtils.send(HttpMethod.POST, pUrl, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.show(cont, R.string.network_error);
                        if (null != pListener) {
                            pListener.onError(msg);
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        checkResponseCode(cont, responseInfo.result, pListener, HttpMethod.POST);
                    }
                });
    }

    public static void postWithLoading(Context cont, String pUrl,
                                       final IRequestListener pListener) {
        postWithLoading(cont, pUrl, null, pListener);
    }

    public static void postWithLoading(final Context cont, String pUrl, RequestParams params, final IRequestListener pListener) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(1000 * 10);
            mHttpUtils.configCurrentHttpCacheExpiry(0);
        }

        if (!NetUtil.isNetworkConnected(cont)) {
            ToastUtils.show(cont, R.string.check_net);
            if (null != pListener) {
                pListener.onError("请检查网络");
            }
            return;
        }
        DialogUtils.showProgess(cont, R.string.loading);
        mHttpUtils.send(HttpMethod.POST, pUrl, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e(TAG, "网络错误编码:" + error.getExceptionCode());
                        DialogUtils.dismissProgessDirectly();
                        ToastUtils.show(cont, R.string.network_error);
                        if (null != pListener) {
                            pListener.onError(msg);
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtils.dismissProgessDirectly();
                        checkResponseCode(cont, responseInfo.result, pListener, HttpMethod.POST);
                    }
                });
    }

    public static void get(final Context cont, String pUrl, RequestParams params, final IRequestListener pListener) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(1000 * 10);
            mHttpUtils.configCurrentHttpCacheExpiry(0);
        }

        if (!NetUtil.isNetworkConnected(cont)) {
            ToastUtils.show(cont, R.string.check_net);
            if (null != pListener) {
                pListener.onError("请检查网络");
            }
            return;
        }
        mHttpUtils.send(HttpMethod.GET, pUrl, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtils.show(cont, R.string.network_error);
                if (null != pListener) {
                    pListener.onError(msg);
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                checkResponseCode(cont, responseInfo.result, pListener,HttpMethod.GET);
            }
        });
    }

    public static void getWithLoading(final Context cont, String pUrl, RequestParams params, final IRequestListener pListener) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(1000 * 10);
            mHttpUtils.configCurrentHttpCacheExpiry(0);
        }

        if (!NetUtil.isNetworkConnected(cont)) {
            ToastUtils.show(cont, R.string.check_net);
            if (null != pListener) {
                pListener.onError("请检查网络");
            }
            return;
        }
        DialogUtils.showProgess(cont, R.string.loading);
        mHttpUtils.send(HttpMethod.GET, pUrl, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException error, String msg) {
                LogUtils.e(TAG, "网络错误编码:" + error.getExceptionCode());
                DialogUtils.dismissProgessDirectly();
                ToastUtils.show(cont, R.string.network_error);
                if (null != pListener) {
                    pListener.onError(msg);
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                DialogUtils.dismissProgessDirectly();
                checkResponseCode(cont, responseInfo.result, pListener,HttpMethod.GET);
            }
        });
    }

    /**
     * 下载更新
     * @param cont
     * @param path url
     * @param target 目标目录
     * @param pListener
     */
    public static void downloadFile(final Context cont, String path, String target, final IDownListener pListener) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(1000 * 10);
        }

        if (!NetUtil.isNetworkConnected(cont)) {
            ToastUtils.show(cont, R.string.check_net);
            if (null != pListener) {
                pListener.onError("请检查网络");
            }
            return;
        }
        //断点
        mHttpUtils.download(path,target,true,true,new RequestCallBack<File>() {

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                if (null != pListener) {
                    pListener.onSuccess(responseInfo.result);
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                if (null != pListener) {
                    pListener.onLoading(total,current,(int)(current * 100 / total));
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtils.show(cont, R.string.network_error);
                if (null != pListener) {
                    pListener.onError(msg);
                }
            }
        });
    }

    private static void checkResponseCode(Context cont, String result, IRequestListener pListener,HttpMethod m) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = -1;
            if (jsonObject.has("code")) {
                code = jsonObject.getInt("code");
            }
            String msg = "";
            if (jsonObject.has("message")) {
                msg = jsonObject.getString("message");
            }
            switch (code) {
                case ResultCode.SUCCESS:
                    if (null != pListener) {
                        if(HttpMethod.POST==m){
                            ToastUtils.show(cont, msg);
                        }
                        pListener.onSuccess(result);
                    }
                    break;
                default:
                    break;
            }
            if (code != ResultCode.SUCCESS) {
                if (null != pListener) {
                    ToastUtils.show(cont, msg);
                    pListener.onError(result);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ToastUtils.show(cont, R.string.json_erro);
        }
    }
}
