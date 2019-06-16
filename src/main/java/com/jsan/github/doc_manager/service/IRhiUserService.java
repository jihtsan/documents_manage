package com.jsan.github.doc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsan.github.doc_manager.entity.RhiUser;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
public interface IRhiUserService extends IService<RhiUser> {

    RhiUser login(String account, String password);

    List<RhiUser> retrieveUser(String userName, String nickName);

    RhiUser getUserInfoByFullUsername(String userName);

    Set<String> getRolesByUserId(Long uid);

    Set<String> getPermsByUserId(Long uid);

    void changePwd(long userId,String password);
}
