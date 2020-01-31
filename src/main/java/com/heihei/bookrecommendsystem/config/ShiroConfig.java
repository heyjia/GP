package com.heihei.bookrecommendsystem.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.heihei.bookrecommendsystem.entity.PrivilegeDO;
import com.heihei.bookrecommendsystem.service.PrivilegeService;
import com.heihei.bookrecommendsystem.shiro.MyShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ShiroConfig
 * @Description shiro配置类
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Configuration
public class ShiroConfig {
    Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    PrivilegeService privilegeService;
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(getCache());
        return securityManager;
    }
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm shiroRealm = new MyShiroRealm();
        return shiroRealm;
    }
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
    /*
     * @Description  配置缓存，使用shiro的内置缓存Ehcache
     * @Author CHENZEJIA
     * @Date 2019/12/17
     * @Param []
     * @return org.apache.shiro.cache.ehcache.EhCacheManager
     **/
    @Bean
    public EhCacheManager getCache(){
        return new EhCacheManager();
    }
    /*
     * @Description shiro的过滤器，配置拦截的规则：对登录页面和注册页面开放，放开静态资源以及对于显示页面需要求有Select权限
     * @Author CHENZEJIA
     * @Date 2019/12/17
     * @Param [securityManager]
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        logger.info("进入shiro配置方法");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterChainDefinitionMap = new HashMap<>();

        shiroFilterFactoryBean.setLoginUrl("/login/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthorized");
        filterChainDefinitionMap.put("/**","anon");
//        filterChainDefinitionMap.put("/login/test","anon");
//        filterChainDefinitionMap.put("/demo/**","anon");
//        filterChainDefinitionMap.put("/images/**","anon");
//        filterChainDefinitionMap.put("/unAuthorized","anon");
//        filterChainDefinitionMap.put("/login/doLogin","anon");
        //查询数据库中权限的配置
        List<PrivilegeDO> privileges = privilegeService.listPrivilege();
        for (PrivilegeDO privilege : privileges) {
            String url = privilege.getUrl();
            String prvgName = "perms[" + privilege.getName() + "]";
            logger.info(url);
            logger.info(prvgName);
            filterChainDefinitionMap.put(url,prvgName);
        }
//        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
}
