package org.wso2.carbon.hive.data.source.access.internal;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.hive.data.source.access.util.DataSourceAccessUtil;
import org.wso2.carbon.ndatasource.core.DataSourceService;
import org.wso2.carbon.rssmanager.core.service.RSSManagerService;

/**
 * @scr.component name="data.source.access.component" immediate="true"
 * @scr.reference name="datasources.service" interface="org.wso2.carbon.ndatasource.core.DataSourceService"
 * cardinality="1..1" policy="dynamic" bind="setDataSourceService" unbind="unsetDataSourceService"
 * @scr.reference name="rss.service" interface="org.wso2.carbon.rssmanager.core.service.RSSManagerService"
 * cardinality="1..1" policy="dynamic" bind="setRSSManagerService" unbind="unsetRSSManagerService"
 */

public class DataSourceAccessComponent {

    private static Log log = LogFactory.getLog(DataSourceAccessComponent.class);


    protected void activate(ComponentContext ctxt) {
        try {
            log.debug("Hive DataSource access bundle is activated ");
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            /* don't throw exception */
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        log.debug("Hive DataSource access bundle is deactivated ");
    }

    protected void setDataSourceService(DataSourceService dataSourceService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Carbon Data Sources Service");
        }
        DataSourceAccessUtil.setCarbonDataSourceService(dataSourceService);
    }

    protected void unsetDataSourceService(
            DataSourceService dataSourceService) {
        if (log.isDebugEnabled()) {
            log.debug("Unsetting the Carbon Data Sources Service");
        }
        DataSourceAccessUtil.setCarbonDataSourceService(null);
    }

    protected void setRSSManagerService(RSSManagerService rssManagerService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the RSS Manager Service");
        }

        DataSourceAccessUtil.setRSSManagerService(rssManagerService);
    }

    protected void unsetRSSManagerService(RSSManagerService rssManagerService) {
        if (log.isDebugEnabled()) {
            log.debug("Unetting the RSS Manager Service");
        }

        DataSourceAccessUtil.setRSSManagerService(null);
    }

    
}
