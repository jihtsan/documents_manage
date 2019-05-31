package com.jsan.github.doc_manager.config;

import com.jsan.github.doc_manager.common.CustomerRealm;
import com.jsan.github.doc_manager.service.IRhiUserService;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Configuration
@Log4j2
public class ShiroConfig {
    private static final String SHIRO_AUTH_REDIS_CACHE_KEY = "XN:PO:SHIRO:AUTH";
    private static final String SHIRO_SESSION_REDIS_CACHE_KEY = "XN:PO:SHIRO:SESSION";

    @Bean
    public Realm realm(IRhiUserService userService) {
        return new CustomerRealm(userService);
    }

    @Bean
    public DefaultSecurityManager securityManager(Realm realm, CacheManager cacheManager, SessionManager sessionManager) {
        var sm = new DefaultWebSecurityManager(realm);
        // TODO 添加分布式缓存实现以及分布式session实现
        sm.setCacheManager(cacheManager);
        sm.setSessionManager(sessionManager);
        return sm;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        log.info("注入Shiro的WebFilter --> shiroFilter[{}]", ShiroFilterFactoryBean.class);
        var shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/**", "anon");
        //配置一般跳转,如果没有接口权限会直接跳转到403
        filterChainDefinitionMap.put("/index/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/index/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index/loginhtml");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CacheManager cacheManager(@Autowired @Qualifier("objRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        return new AbstractCacheManager() {

            @Override
            protected Cache createCache(String name) throws CacheException {
                BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SHIRO_AUTH_REDIS_CACHE_KEY + ":" + name);
                return new RedisCacheAdapter(ops);
            }

            /**
             * 所有的key均使用tostring方法
             * 因为SimplePrincipalCollection作为key时，redis直接序列化改对象将导致每次的对象都不同，key也不同，所以清除缓存会失败
             * @param <K>
             * @param <V>
             */
            class RedisCacheAdapter<K, V> implements Cache<K, V> {
                private BoundHashOperations<String, Object, Object> ops;

                RedisCacheAdapter(BoundHashOperations<String, Object, Object> ops) {
                    this.ops = ops;
                }

                @Override
                public V get(K k) throws CacheException {
                    long t = ops.getExpire();
                    return (V) ops.get(k.toString());
                }

                @Override
                public V put(K k, V v) throws CacheException {
                    ops.put(k.toString(), v);
                    return v;
                }

                @Override
                public V remove(K k) throws CacheException {
                    V v = (V) ops.get(k.toString());
                    ops.delete(k.toString());
                    return v;
                }

                @Override
                public void clear() throws CacheException {
                    ops.delete(keys());
                }

                @Override
                public int size() {
                    return ops.size().intValue();
                }

                @Override
                public Set<K> keys() {
                    return (Set<K>) ops.keys();
                }

                @Override
                public Collection<V> values() {
                    return (Collection<V>) ops.values();
                }
            }
        };
    }

    @Bean
    public SessionManager sessionManager(@Autowired @Qualifier("objRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager() {
            {
                this.sessionDAO = new RedisSessionDAO(redisTemplate);
            }

            class RedisSessionDAO extends AbstractSessionDAO {

                private final BoundHashOperations<String, Object, Object> ops;

                RedisSessionDAO(RedisTemplate<String, Object> redisTemplate) {
                    this.ops = redisTemplate.boundHashOps(SHIRO_SESSION_REDIS_CACHE_KEY);
                }

                @Override
                protected Serializable doCreate(Session session) {
                    Serializable sessionId = generateSessionId(session);
                    assignSessionId(session, sessionId);
                    ops.put(sessionId, session);
                    return sessionId;
                }

                @Override
                protected Session doReadSession(Serializable sessionId) {
                    return (Session) ops.get(sessionId);
                }

                @Override
                public void update(Session session) throws UnknownSessionException {
                    ops.put(session.getId(), session);
                }

                @Override
                public void delete(Session session) {
                    ops.delete(session.getId());
                }

                @Override
                public Collection<Session> getActiveSessions() {
                    return ops.values().stream().map(it -> (Session) it).collect(Collectors.toList());
                }
            }
        };
        // session 有效时间1分钟
        sessionManager.setGlobalSessionTimeout(TimeUnit.MINUTES.toMillis(1));
        return sessionManager;
    }
}