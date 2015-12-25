package cn.fpower.financeservice.mode;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cn.fpower.financeservice.utils.StringUtils;

/**
 * Created by Administrator on 2015/12/8.
 */
public class ProvinceInfo extends BaseEntity {

    public ArrayList<Province> province;

    public class Province {
        public String code;
        public String name;

        public Province() {
        }

        public Province(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getPickerViewText() {
            //这里还可以判断文字超长截断再提供显示
            if (!StringUtils.isEmpty(name) && name.length() > 5) {
                return name.substring(0, 5) + "..";
            } else {
                return name;
            }
        }
    }

    public TreeMap<String, ArrayList<Map<String, String>>> city;

    public TreeMap<String, ArrayList<Map<String, String>>> district;


    //    private String code;
//    private String name;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
//    public String getPickerViewText() {
//        //这里还可以判断文字超长截断再提供显示
//        if (name.length() <= 6)
//            return name;
//        return name.substring(0, 5) + "...";
//    }
}
