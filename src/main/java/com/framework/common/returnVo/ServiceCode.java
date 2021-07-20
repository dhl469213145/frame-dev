package com.framework.common.returnVo;


/**
 * 自定义Code
 */
public enum ServiceCode {
    //RPC层调用错误码
    DB_RETURN(200, "服务返回正常"),
    DB_PARAM_NULL(300, "参数为空"),
    DB_PARAM_ERROR(301, "传入参数有误"),
    DB_SERVICE_OK(20100, "服务正常"),
    DB_SERVICE_UNKNOWN_ERROR(20101, "未知异常"),
    DB_SERVICE_AGENT_ERROR(20102, "DBServiceAgent异常"),
    DB_SERVICE_NETWORK_ERROR(20103, "网络异常"),
    DB_SERVICE_DBDAO_ERROR(20104, "返回数据库的具体异常信息"),
    DB_SERVICE_INVALID_FUNCTION(20105, "方法名不存在"),
    DB_SERVICE_INVALID_PARAMETER(20106, "方法参数错误"),
    DB_SERVICE_FUNCTION_NO_ACCESS(20107, "对此方法无访问权限"),
    DB_SERVICE_SPEAKER_NOT_EXISTED(20108, "数据库中没有要查询数据"),
    DB_SERVICE_SPEAKER_HAVE_NOT_VP(20109, "speaker中没有对应的voiceprint"),
    DB_SERVICE_DATA_OVERVALUED(20110, "返回数据比预期的多"),
    DB_SERVICE_SERVICE_ERROR(20111, "系统异常"),
    DB_SERVICE_OPTIMISATIC_LOCK(20112, "网络超时，请重试"),
    DB_SERVICE_CARD_NOT_EXIST(20113, "卡号不存在"),
    DB_SERVICE_PASSWORD_ERROR(20114, "密码错误"),
    ALIYUN_SMS_SENDING_FAILED(30003, "阿里云短信发送失败"),
    DB_SERVICE_ACCOUNT_NOT_EXIST(20115, "账户不存在"),
    DB_SERVICE_CARD_PAST_DUE(20116, "卡号已过期"),
    DB_SERVICE_CARD_ERROR(20117, "异常卡"),
    DB_SERVICE_RECHARGE_FAILURE(20118, "充值失败"),
    DB_SERVICE_CARD_ISUSED(20119, "该充值卡已被使用"),
    DB_SERVICE_ACCOUNT_ERROR(20120, "账户异常"),
    DB_SERVICE_BALANCE_NOT_ENOUGH(20121, "账户余额不足"),
    DB_SERVICE_DEDUCTION_FAILURE(20122, "抵扣失败"),
    DB_SERVICE_INVALID_ACCOUNT(20123, "无效的账户"),
    DB_SERVICE_CREATE_FAILURE(20124, "初始化账户信息失败"),
    DB_SERVICE_CARDNO_OR_PWD_ERROR(20125, "卡号或密码错误"),
    DB_SERVICE_CUSTOMER_ID_CANNOT_BLANK(20126, "用户id不能为空"),
    DB_SERVICE_USER_NOT_EXIST(20127, "用户不存在"),
    DB_SERVICE_CHECKED_IN(20128, "今日已签到"),
    ALIYUN_SMS_ERROR_VERIFY_CODE(30004, "短信验证码错误或已失效"),
    MOBILE_EXIST(30006, "手机号码已经存在"),
    OBILE_NOT_EXIST(30008, "自定义异常"),
    LOGIN_PASSWORD_ERROR(30010, "密码错误"),
    MOBILE_NOT_EXIST(30007, "手机号码不存在"),
    DB_DATA_NOT_EXISTS(30011, "数据不存在"),
    RECORD_EXISTS(30012, "用户与淘宝订单关系记录已存在"),
    FILE_ERROR(30013, "文件错误"),
    SAVE_SING_NIN(30015, "保存签到记录失败"),
    UPDATE_WEALTH(30014, "修改账户余额失败"),
    DB_SERVICE_INIT_CUSTOMER_INFO_ERROR(30016, "初始化用户信息失败"),
    DB_SERVICE_COUNT_ERROR(30017, "当天抽奖次数已用完"),
    DB_SERVICE_CUSTOMER_WEALTH_NOT_EXISTS(30018, "用户账户信息不存在"),
    DB_SERVICE_CAPTCHA_ERROR(30019, "验证码错误"),

    TAKE_COUNT(40001, "认购数量不能为零"),
    PERSONAL_ADDRESS_INFORMATION(40002, "证书邮寄地址相关信息不能为空"),
    ENTERPRISE_ADDRESS_INFORMATION(40003, "企业认种邮寄地址相关信息不能为空"),
    ENTERPRISE_INFORMATION_SERVERERROR(40004, "企业信息保存失败"),
    ENTERPRISE_INFORMATION_UPDATEREADY(40005, "企业信息更新失败"),
    ORDER_NON_EXISTENT(40006, "订单信息不存在"),
    EXCEL_DATE_ISNULL(40007, "导出数据为空"),
    REDIS_JSON_ANALYSIS_DEFEATED(40008, "数据转换失败"),

    DB_SERVICE_CANT_DELETE(20122, "该分类被使用，暂时不能删除"),


    DB_SAVE_ERROR(50001, "数据保存异常,请稍后重试"),

    ;


    private String msg;
    int code;

    private ServiceCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

}
