package com.jsan.github.doc_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jsan.github.doc_manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RhiUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userName;

    @JsonIgnore
    private String password;

    private String nickName;

    private String email;

    private String userFace;

    private String createTime;

    private String updateTime;

}
