package com.framework.easyExcelDemo.handler;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

public class ExportHandler<T> extends AbstractQueryResultHandler<T> {
    private ExcelWriter excelWriter;
    private WriteSheet writeSheet;



    public ExportHandler() {

    }

    public ExportHandler(ExcelWriter excelWriter, WriteSheet writeSheet) {
        this.excelWriter = excelWriter;
        this.writeSheet = writeSheet;
    }

    public ExportHandler(ExcelWriter excelWriter, WriteSheet writeSheet, int batchSize) {
        this.excelWriter = excelWriter;
        this.writeSheet = writeSheet;
        super.batchSize = batchSize;
    }

    @Override
    public void handle() {
        try {
            excelWriter.write(batchDatas, writeSheet);
        } finally {
            size = 0;
            batchDatas.clear();
        }

    }
}
