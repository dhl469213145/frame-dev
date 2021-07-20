package com.framework.easyExcelDemo.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.framework.easyExcelDemo.cover.IssuingItemStatusConver;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代发-批次管理
 * 
 * @author dhl
 * @date 2021-01-11 16:03:49
 */
@Data
public class ExportVo implements Serializable {
	private static final long serialVersionUID = 1L;


	@ExcelProperty(value = "企业编码", index = 2)
	private String companyCode;

	@ExcelProperty(value = "企业名称", index = 3)
	private String companyName;

	@ExcelProperty(value = "批次号", index = 0)
	private String batchNo;

	@ExcelProperty(value = "状态", index = 9, converter = IssuingItemStatusConver.class)
	private Integer status;

	@ExcelProperty(value = "任务编号", index = 4)
	private String taskCode;

	@ExcelProperty(value = "任务名称", index = 5)
	private String taskName;

	@ExcelProperty(value = "汇总金额", index = 8)
	@NumberFormat("#.##")
	private BigDecimal summaryAmount;

	@DateTimeFormat("yyyy/MM/dd HH:mm:ss")
	@ExcelProperty(value = "复核时间", index = 11)
	private Date checkTime;

}
