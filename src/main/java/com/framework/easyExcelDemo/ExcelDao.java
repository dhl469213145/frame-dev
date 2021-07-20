package com.framework.easyExcelDemo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.easyExcelDemo.pojo.vo.ExportVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExcelDao extends BaseMapper<ExportVo> {

    @Select("select id, batch_no, company_code, company_name, status, task_id, task_code, task_name, summary_number, summary_amount, commit_Time, channel_Id, channel_Type, check_Time," +
            " check_reason, paid_time, batch_describe, create_time from b_issuing_mgr ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 3000)
    @ResultType(ExportVo.class)
    void findBystream(@Param("ew") QueryWrapper<ExportVo> wrapper, ResultHandler<ExportVo> handler);
}
