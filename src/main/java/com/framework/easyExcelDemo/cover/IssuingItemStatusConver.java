package com.framework.easyExcelDemo.cover;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.framework.common.exception.RRException;
import com.framework.common.utils.Constant;

public class IssuingItemStatusConver implements Converter<Integer> {

    //在java中类型
    @Override
    public Class supportJavaTypeKey() {return Integer.class;}
    // 在excel中类型
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {return CellDataTypeEnum.STRING;}
//    //将excel的数据类型转为java数据类型
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        if (stringValue == null) {
            throw new RRException("业务状态填写为空");
        }
        return Constant.IssuingMgrStatusEnum.valueOf(stringValue).getValue();
    }

    @Override
    public CellData convertToExcelData(Integer o, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(o < 0 || Constant.IssuingMgrStatusEnum.values().length <= 0) return new CellData("");

        int statusValue = Constant.IssuingMgrStatusEnum.values()[o - 1].getValue();
        if(statusValue == Constant.IssuingMgrStatusEnum.REFUSED.getValue() || statusValue == Constant.IssuingMgrStatusEnum.FAILED.getValue())
            return new CellData("失败");
        if(statusValue == Constant.IssuingMgrStatusEnum.SUCCESS.getValue())
            return new CellData("成功");
        return new CellData("");
    }



}
