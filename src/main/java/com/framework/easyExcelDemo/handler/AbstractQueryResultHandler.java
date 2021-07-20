package com.framework.easyExcelDemo.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public abstract class AbstractQueryResultHandler<T> implements ResultHandler<T> {

    // 这是每一个批处理查询的数量
//    @NacosValue(value = "${excel.export.batchSize}")
    public int batchSize = 10000;

    //初始值
    public int size = 0;

    // 存储每批数据的临时容器
    public List<T> batchDatas = new ArrayList<>(batchSize);

    public void handleResult(ResultContext<? extends T> resultContext) {
        // 这里获取流式查询每次返回的单条结果
        T t = resultContext.getResultObject();
        batchDatas.add(t);
        size++;
        if (size == batchSize) {
            log.info("本批次处理数据量 :{}",size );
            handle();
        }
    }

    // 1.这个方需要子类重写此接口，处理具体业务逻辑
    public abstract void handle();

    //处理最后一批不到 batchSize(查询设定的阀值)的数据
    public void lastSDHandle() {
        log.info("最后批次处理数据量 :{}",size );
        handle();
    }

}
