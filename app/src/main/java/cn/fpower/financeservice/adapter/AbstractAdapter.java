package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liulilei
 *         集成BaseAdapter的基类，抽取adapter的基本代码。
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;

    /**
     * 初始化适配器
     *
     * @param Context
     * @param datas
     */
    public AbstractAdapter(Context Context, List<T> datas) {
        this.mContext = Context;
        if (datas == null) {
            this.mList = new ArrayList<T>();
        } else {
            this.mList = datas;
        }
    }

    /**
     * 添加集合数据
     *
     * @param arrayData
     */
    public void addData(List<T> arrayData) {
        if (arrayData != null) {
            mList.addAll(arrayData);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据对象
     *
     * @param data
     */
    public void addData(T data) {
        if (data != null) {
            mList.add(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据到第一个
     *
     * @param data
     */
    public void addDataFirst(T data) {
        if (data != null) {
            mList.add(0, data);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     *
     * @param data
     */
    public void remove(T data) {
        if (data != null) {
            mList.remove(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 刷新数据源
     *
     * @param arrayData
     */
    public void refresh(List<T> arrayData) {
        if (arrayData != null) {
            mList.clear();
            mList.addAll(arrayData);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
