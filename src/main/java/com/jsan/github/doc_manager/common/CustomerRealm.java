package com.jsan.github.doc_manager.common;

import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.service.IRhiUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class CustomerRealm extends AuthorizingRealm {

    private final IRhiUserService userService;

    public CustomerRealm(IRhiUserService userService) {
        this.userService = userService;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("--------权限配置-------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        RhiUser user = (RhiUser) principals.getPrimaryPrincipal();
        try {
            //注入角色(查询所有的角色注入控制器）
            authorizationInfo.addRoles(userService.getRolesByUserId(user.getId()));
            //注入角色所有权限（查询用户所有的权限注入控制器）
            authorizationInfo.addStringPermissions(userService.getPermsByUserId(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号
        String username = (String) token.getPrincipal();
        //这里需注意。看别人的教程有人是这样写的String password = (String) token.getCredentials();
        //项目运行的时候报错，发现密码不正确。后来进源码查看发现将密码注入后。Shiro会进行转义将字符串转换成字符数组。
        //源码：this(username, password != null ? password.toCharArray() : null, false, null);
        //不晓得是否是因为版本的原因，建议使用的时候下载源码进行查看
        String password = new String((char[]) token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        RhiUser user = userService.getUserInfoByFullUsername(username);
        if (null == user) {
            throw new UnknownAccountException();
        } else {
            if (password.equals(user.getPassword())) {
                SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
                return authorizationInfo;
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }
}
