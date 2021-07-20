package com.framework.easyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.framework.common.exception.RRException;
import com.framework.easyExcelDemo.handler.ExportHandler;
import com.framework.easyExcelDemo.listener.ImportListener;
import com.framework.easyExcelDemo.pojo.dto.UploadDTO;
import com.framework.easyExcelDemo.pojo.vo.ExportVo;
import com.framework.easyExcelDemo.pojo.vo.ImportVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class ExcelDemo {

    @Autowired
    private ExcelDao excelDao;

    public void read(UploadDTO uploadDTO) {
        String traceId = String.valueOf(new Random().nextLong());
        try {
            /**
             *   文件流
             *   上传文件流对应的实体类--实体类内部可以做响应的格式校验
             *   监听类---一行行的读出数据，可以在这里对数据进行相关校验后再进行入库操作
             *           构造方法依据需求可以自定义，这里设置了入库操作需要的dao，限制导入文件数据行数的数量，入参对象
             */
            EasyExcel.read(uploadDTO.getFile().getInputStream(), ImportVo.class, new ImportListener(100l, uploadDTO)).sheet().doRead();
        } catch (DataAccessException dae) {
            if (dae.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.error("{},代发上传错误....{}", traceId, dae.getMessage());
                throw new RRException("代发上传错误!数据库操作异常!订单号重复!");
            }
            log.error("{},代发上传错误....{}", traceId, dae.getMessage());
            throw new RRException("代发上传错误!数据库操作异常!");
        }catch (ExcelAnalysisException eae) {
            log.error("{},代发上传错误....{}", traceId, eae.getMessage());
            throw new RRException(eae.getCause().getMessage());
        } catch (Exception e) {
            log.error("{},代发上传错误....{}", traceId, e.getMessage());
            throw new RRException(e.getMessage());
        }
    }


    public void export(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params){
        String traceId = String.valueOf(new Random().nextLong());
        log.info("{},导出代发批次记录start....", traceId);
        try{
            this.export(response, params);
            log.info("{},导出代发批次记录end....", traceId);
        } catch (DataAccessException dae) {
            log.error("{},导出代发批次记录错误....{}", traceId, dae.getMessage());
//            throw new RRException("导出代发批次记录错误!数据库操作异常！");
        } catch (Exception e) {
            log.error("{},导出代发批次记录错误....{}", traceId, e.getMessage());
            throw new RRException("导出代发批次记录错误....");
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        try{
            String fileName = "导出demo";
            String sheetName = "sheet1";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), ExportVo.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();

            QueryWrapper<ExportVo> queryWrapper = new QueryWrapper<ExportVo>().eq("enable", 1);

            ExportHandler<ExportVo> handler = new ExportHandler(excelWriter, writeSheet);
            excelDao.findBystream(queryWrapper, handler);

            handler.lastSDHandle();
            excelWriter.finish();
        } catch (Exception e) {
            log.error("退款-下载文件失败！{}", e.getMessage());
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

//    @Select("select id, batch_no, company_code, company_name, status, task_id, task_code, task_name, summary_number, summary_amount, commit_Time, channel_Id, channel_Type, check_Time," +
//            " check_reason, paid_time, batch_describe, create_time from b_issuing_mgr ${ew.customSqlSegment}")
//    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 3000)
//    @ResultType(BIssuingMgrVo.class)
//    void findBystream(@Param(Constants.WRAPPER) QueryWrapper<BIssuingMgrVo> wrapper, ResultHandler<BIssuingMgrVo> handler);


   /*       官方demo
   @Test
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        // 写法2
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(), writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }*/
}
