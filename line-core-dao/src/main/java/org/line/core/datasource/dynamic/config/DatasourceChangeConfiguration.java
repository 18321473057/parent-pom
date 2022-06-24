package org.line.core.datasource.dynamic.config;

import com.alibaba.druid.pool.DruidDataSource;
;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.line.core.datasource.dynamic.DatasourceChangeAspect;
import org.line.core.datasource.dynamic.DynamicDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangcs
 * @Date: 2020/10/23 14:46
 * @Description: DatasourceChangeAspect 切面也必须加入spring
 */
@Configuration
@EnableConfigurationProperties(DatasourceProperties.class)
@ConditionalOnProperty(prefix = "line.dynamic.datasource", name = "enable")
public class DatasourceChangeConfiguration {

    @Autowired
    private DatasourceProperties datasourceProperties;

    Log logger = LogFactory.getLog(DatasourceChangeConfiguration.class);

    /**
     * 声明多数据源切换切面
     * */
    @Bean
    public DatasourceChangeAspect datasourceChangeAspect() {
        DatasourceChangeAspect changeAspect = new DatasourceChangeAspect();
        return changeAspect;
    }


    /**
     * 创建动态切换的数据源
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        List<DruidProperties> datasourceListProperties = datasourceProperties.getDatasourceList();
        Map<Object,Object> targetDataSources = new HashMap();
        Object defaultDataSource = null;
        for (DruidProperties druidProperties : datasourceListProperties) {
            String datasourceName = druidProperties.getName();
            DataSource druidDatasource = createDruidDatasource(druidProperties);
            targetDataSources.put(datasourceName, druidDatasource);
            if (datasourceProperties.getDefaultDatasourceName().equals(druidProperties.getName())) {
                defaultDataSource = druidDatasource;
            }
        }
        DynamicDatasource datasource = new DynamicDatasource();
        datasource.setTargetDataSources(targetDataSources);
        datasource.setDefaultTargetDataSource(defaultDataSource == null ? datasourceProperties.getDefaultDatasourceName() : defaultDataSource);
        return datasource;
    }

    /**
     * 根据给定的配置创建数据源
     */
    private DataSource createDruidDatasource(DruidProperties druidProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setName(druidProperties.getName());
        datasource.setUrl(druidProperties.getUrl());
        datasource.setUsername(druidProperties.getUsername());
        datasource.setPassword(druidProperties.getPassword());
        datasource.setDriverClassName(druidProperties.getDriverClassName());
        //启动池时创建的初始连接数，初始化发生在显示调用init方法或者第一次getConnection时。  默认 0
        datasource.setInitialSize(druidProperties.getInitialSize());
        //最小连接数   0
        datasource.setMinIdle(druidProperties.getMinIdle());
        //最大连接数  8
        datasource.setMaxActive(druidProperties.getMaxActive());
        // 没有可用连接时，最大等待时间（毫秒) ,-1:无限期等待  默认 : 无限期
        datasource.setMaxWait(druidProperties.getMaxWait());
        datasource.setValidationQueryTimeout(druidProperties.getValidationQueryTimeout());

        /**
         * 检测连接是否有效的SQL,必须是一个查询语句，常用SELECT ‘X’。
         *  若validationQuery为null，则testOnBorrow、testOnReturn、testWhileIdle等配置不起作用。
         */
        datasource.setValidationQuery(druidProperties.getValidationQuery());
        datasource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        datasource.setTestOnReturn(druidProperties.isTestOnReturn());
        datasource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());

        /**
         * 有两个含义：一个是desdtroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接；
         * 另一个是testWhileIdle的判断依据，详细看testWhileIdle属性的说明，单位毫秒
         * 默认 60000
         * */
        datasource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        /**
         * 建议配置为true，不影响性能，并且保证安全性。
         * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
         * 默认 false
         * */
        datasource.setTestWhileIdle(druidProperties.isTestWhileIdle());

        //连接保持空闲而不被驱逐的最小时间（毫秒)
        datasource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());

        try {
            datasource.setFilters(druidProperties.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return datasource;
    }
}
