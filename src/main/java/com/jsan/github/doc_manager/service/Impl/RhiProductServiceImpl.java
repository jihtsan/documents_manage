package com.jsan.github.doc_manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsan.github.doc_manager.entity.RhiProduct;
import com.jsan.github.doc_manager.mapper.RhiProductMapper;
import com.jsan.github.doc_manager.service.IRhiProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Service
public class RhiProductServiceImpl extends ServiceImpl<RhiProductMapper, RhiProduct> implements IRhiProductService {

    @Value("${page.pagesize}")
    private int pageSize;

    @Override
    public PageInfo<RhiProduct> retrieveProductList(String productName, Long dimensionId, int pageNo) {
        LambdaQueryWrapper<RhiProduct> queryWrapper =new QueryWrapper<RhiProduct>().lambda();
        if (!Objects.isNull(dimensionId))queryWrapper.eq(RhiProduct::getDimensionId,dimensionId);
        PageHelper.startPage(pageNo,pageSize);
        return new PageInfo<>(list(queryWrapper.like(RhiProduct::getProductName,productName)));
    }

    @Override
    public void product2Top(long productId, boolean topTips) {
        updateById((RhiProduct) new RhiProduct().setSortTop(topTips).setId(productId));
    }
}
