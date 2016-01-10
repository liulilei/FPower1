package cn.fpower.financeservice.mode;

import java.util.ArrayList;

import cn.fpower.financeservice.utils.TimeUtils;

/**
 *
 */
public class PickData {

    private final static String YERA = "年";
    private static String[] mouths = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private int codeData;
    private String nameData;
    //第一项
    ArrayList<PickDataInfo> options1Items = new ArrayList<PickDataInfo>();
    //第二项
    ArrayList<ArrayList<PickDataInfo>> options2Items = new ArrayList<ArrayList<PickDataInfo>>();

    //月份位置
    public int position = 0;

    //开始时间
    private String start = TimeUtils.getMonthStart();
    //结束时间
    private String end = TimeUtils.getMonthEnd();

    public String getStart() {
        return start;
    }

    public void setStartAndEnd(int year, int mouth) {
        if (mouth <= 12) {
            this.start = TimeUtils.getMouthStart(year, mouth);
            this.end = TimeUtils.getMouthEnd(year, mouth);
        } else if (mouth == 13) {
            this.start = TimeUtils.getMouthStart(year, 1);
            this.end = TimeUtils.getMouthEnd(year, 6);
        } else if (mouth == 14) {
            this.start = TimeUtils.getMouthStart(year, 6);
            this.end = TimeUtils.getMouthEnd(year, 12);
        } else {
            this.start = TimeUtils.getMouthStart(year, 1);
            this.end = TimeUtils.getMouthEnd(year, 12);
        }

    }

    public String getEnd() {
        return end;
    }

    //今年月份
    private ArrayList<PickDataInfo> monthItems_now = new ArrayList<PickDataInfo>();
    //去年月份
    private static ArrayList<PickDataInfo> monthItems = new ArrayList<PickDataInfo>();

    static {
        monthItems.add(new PickDataInfo(1, "1月"));
        monthItems.add(new PickDataInfo(2, "2月"));
        monthItems.add(new PickDataInfo(3, "3月"));
        monthItems.add(new PickDataInfo(4, "4月"));
        monthItems.add(new PickDataInfo(5, "5月"));
        monthItems.add(new PickDataInfo(6, "6月"));
        monthItems.add(new PickDataInfo(7, "7月"));
        monthItems.add(new PickDataInfo(8, "8月"));
        monthItems.add(new PickDataInfo(9, "9月"));
        monthItems.add(new PickDataInfo(10, "10月"));
        monthItems.add(new PickDataInfo(11, "11月"));
        monthItems.add(new PickDataInfo(12, "12月"));
        monthItems.add(new PickDataInfo(13, "上半年"));
        monthItems.add(new PickDataInfo(14, "下半年"));
        monthItems.add(new PickDataInfo(15, "全年"));
    }

    public PickData() {
        int year = TimeUtils.getYear();
        int mouth = TimeUtils.getMonth();
        position = mouth - 1;
        for (int i = 0; i < 10; i++) {
            codeData = year - i;
            nameData = codeData + YERA;
            options1Items.add(new PickDataInfo(codeData, nameData));
            if (i == 0) {
                monthItems_now.clear();
                for (int j = 0; j < mouth; j++) {
                    monthItems_now.add(monthItems.get(j));
                }
                monthItems_now.add(monthItems.get(12));
                if (mouth > 6) {
                    monthItems_now.add(monthItems.get(13));
                }
                monthItems_now.add(monthItems.get(14));
                options2Items.add(monthItems_now);
            } else {
                options2Items.add(monthItems);
            }
        }
    }

    public ArrayList<PickDataInfo> getOptions1Items() {
        return options1Items;
    }

    public ArrayList<ArrayList<PickDataInfo>> getOptions2Items() {
        return options2Items;
    }

    public static class PickDataInfo {
        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PickDataInfo(int code, String name) {
            this.code = code;
            this.name = name;
        }

        //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
        public String getPickerViewText() {
            //这里还可以判断文字超长截断再提供显示
            return name;
        }
    }
}
