package com.jsan.github.doc_manager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jsan.github.doc_manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("rhi_product_dimension")//数据库表名称
public class RhiProductDimension {

    private static final long serialVersionUID = 1L;

    @TableId("id")//数据库主键名称
    private Long id;

    private String dimensionName;

    private LocalDateTime createTime;


}
