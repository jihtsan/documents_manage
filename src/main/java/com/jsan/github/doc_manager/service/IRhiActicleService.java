package com.jsan.github.doc_manager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jsan.github.doc_manager.entity.RhiActicle;

/**
 * <p>
 * 文章PO
 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
public interface IRhiActicleService extends IService<RhiActicle> {


    PageInfo<RhiActicle> retrieveActicleList(String acticle, Integer classify, int pageno);

    void acticle2Top(Long acticleId,boolean toptype);
}
