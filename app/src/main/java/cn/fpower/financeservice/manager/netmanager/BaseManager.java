package cn.fpower.financeservice.manager.netmanager;

import android.content.Context;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import cn.fpower.financeservice.net.IRequestListener;
import cn.fpower.financeservice.net.NetworkService;


/**
 * Created by ll on 2015/9/16.
 */
public class BaseManager {

    private Gson gson = new Gson();

    public void getJsonStringFromNetHasDialog(Context context, String netApi, RequestParams params, final ManagerStringListener listener) {
        NetworkService.postWithLoading(context, netApi, params, new IRequestListener() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }

            @Override
            public boolean onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
                return false;
            }
        });
    }

    public void getJsonStringFromNetNoDialog(Context context, String netApi, RequestParams params, final ManagerStringListener listener) {
        NetworkService.post(context, netApi, params, new IRequestListener() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }

            @Override
            public boolean onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
                return false;
            }
        });
    }

    public void getDataFromNetHasDialog(Context context, String netApi, RequestParams params, final Class clazz, final ManagerDataListener listener) {
        NetworkService.postWithLoading(context, netApi, params, new IRequestListener() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    listener.onSuccess(gson.fromJson(result, clazz));
                }
            }

            @Override
            public boolean onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
                return false;
            }
        });
    }

    public void getDataFromNetNoDialog(Context context, String netApi, RequestParams params, final Class clazz, final ManagerDataListener listener) {
        NetworkService.post(context, netApi, params, new IRequestListener() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    listener.onSuccess(gson.fromJson(result, clazz));
                }
            }

            @Override
            public boolean onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
                return false;
            }
        });
    }

}
