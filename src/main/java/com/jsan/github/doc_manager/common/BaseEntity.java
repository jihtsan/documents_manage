package com.jsan.github.doc_manager.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
}
