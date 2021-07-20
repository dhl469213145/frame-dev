package com.framework.easyExcelDemo.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 代发上传dto
 * 
 * @author dhl
 * @date 2021-01-11 16:03:49
 */
@Data
public class UploadDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "上传文件")
	@ApiModelProperty("上传文件不能为空!")
	private MultipartFile file;

	@ApiModelProperty("任务id")
//	@NotNull(message = "任务id不能为空!")
	private Long taskId;

	@ApiModelProperty("任务code")
	@NotBlank(message = "任务code不能为空!")
	private String taskCode;

	@ApiModelProperty("任务名称")
	@NotBlank(message = "任务名称不能为空!")
	private String taskName;

	@ApiModelProperty("批次描述")
	private String batchDescribe;

	private String batchCode;

	private Long createId;

}
