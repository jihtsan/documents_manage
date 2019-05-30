package com.jsan.github.doc_manager.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.exception.BusinessException;
import com.jsan.github.doc_manager.mapper.RhiUserMapper;
import com.jsan.github.doc_manager.service.IRhiUserService;
import com.jsan.github.doc_manager.utils.CryptoUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Service
public class RhiUserServiceImpl extends ServiceImpl<RhiUserMapper, RhiUser> implements IRhiUserService {

    @Override
    public RhiUser login(String tokenId, String account, String password) {
       if (StrUtil.isAllBlank(account,password)) throw new BusinessException("用户名|密码不能为空");

        RhiUser user = checkUser(account, password);


        return null;
    }

    /**
     * 检查用户状态
     */
    private RhiUser checkUser(String account, String password) {
        RhiUser user =  getOne(new QueryWrapper<RhiUser>().lambda().eq(RhiUser::getUserName,account).eq(RhiUser::getPassword, CryptoUtils.md5(password)));
        return user;
    }
}
