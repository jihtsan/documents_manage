package com.jsan.github.doc_manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsan.github.doc_manager.entity.RhiActicle;
import com.jsan.github.doc_manager.entity.RhiProduct;
import com.jsan.github.doc_manager.enums.ActicleEnum;
import com.jsan.github.doc_manager.mapper.RhiActicleMapper;
import com.jsan.github.doc_manager.service.IRhiActicleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 文章PO
 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Service
public class RhiActicleServiceImpl extends ServiceImpl<RhiActicleMapper, RhiActicle> implements IRhiActicleService {

    @Value("${page.pagesize}")
    private int pageSize;

    @Override
    public PageInfo<RhiActicle> retrieveActicleList(String acticle, Integer classify, int pageno) {
        LambdaQueryWrapper<RhiActicle> queryWrapper =new QueryWrapper<RhiActicle>().lambda();
        if (!Objects.isNull(classify))queryWrapper.eq(RhiActicle::getClassify, ActicleEnum.getActicleClassify(classify));
        if (!Objects.isNull(acticle))queryWrapper.like(RhiActicle::getTitle,acticle);
        PageHelper.startPage(pageno,pageSize);
        return new PageInfo<>(list(queryWrapper.orderByDesc(RhiActicle::getSortTop,RhiActicle::getUpdateTime)));
    }

    @Override
    public void acticle2Top(Long acticleId, boolean toptype) {
        RhiActicle r = new RhiActicle();
        r.setSortTop(toptype).setId(acticleId);
        updateById( r);
    }
}
