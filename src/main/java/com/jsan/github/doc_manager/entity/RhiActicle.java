package com.jsan.github.doc_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jsan.github.doc_manager.common.BaseEntity;
import com.jsan.github.doc_manager.enums.ActicleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章PO
 *
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RhiActicle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String title;

    private Blob htmlContent;

    private ActicleEnum classify;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime sendTime;

    private Boolean contentState;

    private Boolean sortTop;


}