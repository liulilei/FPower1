package cn.fpower.financeservice.mode;

/**
 * {
 * "data": {
 * "region": "",
 * "register_source": "2",
 * "birthday": "0000-00-00",
 * "face": "0",
 * "passwd": "OTZlNzkyMTg5NjVlYjcyYzkyYTU0OWRkNWEzMzAxMTI=",
 * "sex": "0",
 * "status": "1",
 * "reg_time": "1449074097",
 * "uptime": "1449074097",
 * "group_id": "1",
 * "id": "3",
 * "addtime": "1449074097",
 * "username": "0",
 * "rank": "0",
 * "mobile": "13516599661"
 * },
 * "message": "登录成功，完善信息",
 * "code": "201"
 * }
 */
public class UserInfo extends BaseMode {
    private String region;
    private String register_source;//来源 1、IOS 2、安卓
    private String birthday;//生日
    private String face;//头像 "0",
    private String passwd;//密码 加密
    private String sex;//性别[0,人妖，1男，2女]
    private String status;//"1",
    private String reg_time;// 1449074097",
    private String uptime;// 1449074097",
    private String group_id;//"1",
    private String id;//"3",
    private String addtime;//1449074097",
    private String username;//用户名 "0",
    private String rank;//0",
    private String mobile;//手机号 13516599661"
}
