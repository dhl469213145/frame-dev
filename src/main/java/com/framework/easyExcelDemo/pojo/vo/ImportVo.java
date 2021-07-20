package com.framework.easyExcelDemo.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 导入实体类
 * 
 * @author dhl
 * @date 2021-01-11 16:03:49
 */
@Data
public class ImportVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 出款订单号
	 */
	@NotBlank(message = "出款订单号不能为空")
	// BN + yyyyMMddHHmmss + 4位数字
	@Pattern(regexp = "^BN" +
			"((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})" +		// 年
			"(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))" +// 月日
			"([0-1]?[0-9]|2[0-3])" +	// 时
			"([0-5][0-9])" +			// 分
//			"([0-5][0-9])" +			// 秒
			"\\d{4}$",message = "出款订单号不正确")
	@ExcelProperty(value = "出款订单号", index = 0)
	private String orderCode;
	/**
	 * 交易金额
	 */
	@NotNull(message = "交易金额不能为空")
	@Digits(integer=16,fraction=2, message = "交易金额的值超出了允许范围(只允许在16位整数和2位小数范围内)")
	@ExcelProperty(value = "出款金额", index = 1)
	private BigDecimal tradingAmount;

	/**
	 * 收款户名
	 */
	@NotBlank(message = "收款户名不能为空")
	@ExcelProperty(value = "收款户名", index = 2)
	private String collectionBankUsername;
	/**
	 * 收款账号
	 */
	@NotBlank(message = "收款账号不能为空")
	@Size(min=12, max=19, message = "收款账号字符个数必须在12和19之间")
	@ExcelProperty(value = "收款账号", index = 3)
	private String collectionBankAccount;
	/**
	 * 身份证id
	 */
	@NotBlank(message = "身份证id不能为空")
	@Size(min=18, max=18, message = "身份证长度必须为18位")
	@ExcelProperty(value = "身份证号码", index = 4)
	private String cardId;
	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$",message = "电话号码不正确")
	@ExcelProperty(value = "手机号码", index = 5)
	private String mobile;

	/**
	 * 推广业绩说明
	 */
	@NotBlank(message = "推广业绩说明不能为空")
	@Size(min=1, max=170, message = "推广业绩说明长度不能超过170个字符")
	@ExcelProperty(value = "业绩(出款)说明", index = 6)
	private String paidInstructions;

}
