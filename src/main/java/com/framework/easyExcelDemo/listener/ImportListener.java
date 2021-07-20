package com.framework.easyExcelDemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.framework.common.exception.RRException;
import com.framework.easyExcelDemo.pojo.dto.UploadDTO;
import com.framework.easyExcelDemo.pojo.vo.ImportVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImportListener extends AnalysisEventListener<ImportVo> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private long importLimitSize = 300l;
    List<ImportVo> dataList = new ArrayList<>();

    private UploadDTO uploadDTO;
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
//    private ExcelService excelService;

    public ImportListener(Long importLimitSize, UploadDTO uploadDTO) {
        this.importLimitSize = importLimitSize;
        this.uploadDTO = uploadDTO;
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param excelService
     * @param importLimitSize
     */
//    public ImportListener(ExcelService excelService, Long importLimitSize, UploadDTO uploadDTO) {
//        this.excelService = excelService;
//        this.importLimitSize = importLimitSize;
//        this.uploadDTO = uploadDTO;
//    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(ImportVo data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
//        dataList.add(data);
//        countLimit++;
//        if(limitSize == countLimit) {
//            countLimit = 0;
//            log.error("上传失败！超过限定条目的数据！");
//            throw new RRException("上传失败！超过限定条目的数据！");
//        }
//        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (dataList.size() < limitSize && dataList.size() >= batchSize) {
//            importAndInCache();
//            // 存储完成清理 list
//            dataList.clear();
//        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if(context.readRowHolder().getRowIndex() - 1 > importLimitSize) {
            log.error("上传失败！超过限定条目的数据！");
            throw new RRException("上传失败！超过限定条目的数据！");
        } else {
            // 入库操作
            // importAndInCache();
            log.info("所有数据解析完成！");
        }
    }
    /**
     * 加上存储数据库
     */
//    private void importAndInCache() {
//        log.info("{}条数据，开始存储数据库！", dataList.size());
//        excelService.importAndInCache(dataList, uploadDTO);
//        log.info("存储数据库成功！");
//    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex());
            RRException rre = new RRException("模板错误！");
            rre.setMsg("模板错误！");
            throw rre;
        }
    }
}
