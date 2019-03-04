package cn.edu.qfnu.rumo.context.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * MyBatis数据源配置
 *
 * @author ZhouYuqing
 */
//@Configuration
//@AutoConfigureAfter({DataSourceConfiguration.class})
public class RumoMybatisConfiguration  {

//    private static Log logger = LogFactory.getLog(MybatisConfiguration.class);
//
//    @Resource(name = "masterDataSource")
//    private DataSource masterDataSource;
//    @Resource(name = "slaveDataSource")
//    private DataSource slaveDataSource;
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        return super.sqlSessionFactory(roundRobinDataSouceProxy());
//    }
//
//    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
//        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
//        Map<Object, Object> targetDataResources = new ClassLoaderRepository.SoftHashMap();
//        targetDataResources.put(DbContextHolder.DbType.MASTER, masterDataSource);
//        targetDataResources.put(DbContextHolder.DbType.SLAVE, slaveDataSource);
//        proxy.setDefaultTargetDataSource(masterDataSource);//默认源
//        proxy.setTargetDataSources(targetDataResources);
//        return proxy;
//    }
}
