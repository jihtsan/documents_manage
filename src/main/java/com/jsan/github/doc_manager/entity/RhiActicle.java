package com.jsan.github.doc_manager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jsan.github.doc_manager.common.BaseEntity;
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
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("rhi_acticle")//数据库表名称
public class RhiActicle {

    private static final long serialVersionUID = 1L;

    @TableId("id")//数据库主键名称
    private Long id;

    private Integer userId;

    private String title;

    private Blob htmlContent;

    private String classify;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime sendTime;

    private Boolean contentState;

    private Boolean sortTop;


}
