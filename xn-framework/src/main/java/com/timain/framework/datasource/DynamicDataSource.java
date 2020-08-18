package com.timain.framework.datasource;

import com.timain.common.config.datasource.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * @author yyf1172719533
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }
}
