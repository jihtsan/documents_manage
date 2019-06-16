package com.jsan.github.doc_manager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jsan.github.doc_manager.entity.RhiProduct;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
public interface IRhiProductService extends IService<RhiProduct> {

    PageInfo<RhiProduct> retrieveProductList(String productName, Long dimensionId, int pageNo);

    void product2Top(long productId, boolean topTips);
}
