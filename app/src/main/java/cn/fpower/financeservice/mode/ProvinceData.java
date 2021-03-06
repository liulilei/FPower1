package cn.fpower.financeservice.mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/8.
 */
public class ProvinceData {
    private Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> getMap() {
        return map;
    }

    /**
     * 显示中文
     **/
    public String getProvinceAddress(long pid, long cid, long did) {
        String address = "";
        if (pid > 0 && map.containsKey(pid + "")) {
            address += map.get(pid+ "");
        }
        if (cid > 0 && map.containsKey(cid + "")) {
            address = address + " " + map.get(cid+ "");
        }
        if (did > 0 && map.containsKey(did + "")) {
            address = address + " " + map.get(did+ "");
        }
        return address;
    }

    private ArrayList<ProvinceInfo.Province> options1Items = new ArrayList<ProvinceInfo.Province>();
    private ArrayList<ArrayList<ProvinceInfo.Province>> options2Items = new ArrayList<ArrayList<ProvinceInfo.Province>>();
    private ArrayList<ArrayList<ArrayList<ProvinceInfo.Province>>> options3Items = new ArrayList<ArrayList<ArrayList<ProvinceInfo.Province>>>();

    public ArrayList<ProvinceInfo.Province> getOptions1Items() {
        return options1Items;
    }

    public void setOptions1Items(ArrayList<ProvinceInfo.Province> options1Items) {
        this.options1Items = options1Items;
    }

    public ArrayList<ArrayList<ProvinceInfo.Province>> getOptions2Items() {
        return options2Items;
    }

    public void setOptions2Items(ArrayList<ArrayList<ProvinceInfo.Province>> options2Items) {
        this.options2Items = options2Items;
    }

    public ArrayList<ArrayList<ArrayList<ProvinceInfo.Province>>> getOptions3Items() {
        return options3Items;
    }

    public void setOptions3Items(ArrayList<ArrayList<ArrayList<ProvinceInfo.Province>>> options3Items) {
        this.options3Items = options3Items;
    }
}