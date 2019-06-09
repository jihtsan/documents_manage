package com.jsan.github.doc_manager.service.Impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.exception.BusinessError;
import com.jsan.github.doc_manager.exception.BusinessException;
import com.jsan.github.doc_manager.mapper.RhiUserMapper;
import com.jsan.github.doc_manager.service.IRhiUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Service
public class RhiUserServiceImpl extends ServiceImpl<RhiUserMapper, RhiUser> implements IRhiUserService {

    @Value("${rhino.admin.userid}")
    private long adminUserId;

    @Override
    public RhiUser login(String account, String password) {
        if (StrUtil.isAllBlank(account, password)) throw new BusinessException("用户名|密码不能为空");
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            throw BusinessError.AuthFail.exception("未知用户");
        } catch (IncorrectCredentialsException ice) {
            throw BusinessError.AuthFail.exception("密码不正确");
        } catch (LockedAccountException lae) {
            throw BusinessError.AuthFail.exception("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            throw BusinessError.AuthFail.exception("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            throw BusinessError.AuthFail.exception("用户名或密码不正确");
        }
        RhiUser user = (RhiUser) currentUser.getPrincipal();
        Console.log(JSON.toJSONString(user));
        return user;
    }


    @Override
    public List<RhiUser> retrieveUser(String userName, String nickName) {
        userName = StrUtil.nullToEmpty(userName);
        nickName = StrUtil.nullToEmpty(nickName);
        List<RhiUser> allUser = list(new QueryWrapper<RhiUser>().lambda().like(RhiUser::getUserName, userName).or().like(RhiUser::getNickName, nickName));
        return allUser;
    }

    @Override
    public RhiUser getUserInfoByFullUsername(String userName) {
        RhiUser user = getOne(new QueryWrapper<RhiUser>().lambda().eq(RhiUser::getUserName, userName));
        if (Objects.isNull(user)) throw new BusinessException("该用户不存在");
        return user;
    }

    @Override
    public Set<String> getRolesByUserId(Long uid) {
        Set<String> roles = new HashSet<>();
        if (uid == adminUserId) roles.add("admin");
        roles.add("customer");
        return roles;
    }

    @Override
    public Set<String> getPermsByUserId(Long uid) {
        return new HashSet<>();
    }
}
