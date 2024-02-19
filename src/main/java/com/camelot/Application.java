package com.camelot;

import com.camelot.bean.TableInfo;
import com.camelot.builder.*;

import java.util.List;

/**
 * @author Lord Camelot
 * @date 2024/2/8
 * @description
 */
public class Application {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList =  BuildTable.getTables();

        BuildBase.execute();

        for (TableInfo tableInfo : tableInfoList) {
            BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
            BuildMapper.execute(tableInfo);
            BuildMapperXml.execute(tableInfo);
            BuildService.execute(tableInfo);
            BuildServiceImpl.execute(tableInfo);
            BuildController.execute(tableInfo);
        }
    }
}
