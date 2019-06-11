package com.jsan.github.doc_manager.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ActicleEnum {
    COMPANY(1),
    INDUSTRY(2);

    @EnumValue
    private final int value;

    ActicleEnum(Integer value){
        this.value = value;
    }


    public static ActicleEnum getActicleClassify(int classify){
        for (ActicleEnum value : ActicleEnum.values()) {
            if (value.value==classify) return value;
        }
        return null;
    }

}
