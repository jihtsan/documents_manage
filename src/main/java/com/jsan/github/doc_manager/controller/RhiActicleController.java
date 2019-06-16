package com.jsan.github.doc_manager.controller;


import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.RhiActicle;
import com.jsan.github.doc_manager.entity.RhiProduct;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.service.IRhiActicleService;
import com.jsan.github.doc_manager.service.IRhiProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 文章PO
 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Controller
@RequestMapping("/rhi-acticle")
public class RhiActicleController extends BaseController {

    @Autowired
    IRhiActicleService acticleService;


    @ApiOperation(value = "新闻创建", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "cu_acticle", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel CreateOrUpdateActicle(@RequestBody RhiActicle acticle) {
        acticle.setUpdateTime( LocalDateTime.now());
        acticleService.saveOrUpdate(acticle);
        return response("创建成功");
    }

    @ApiOperation(value = "新闻列表", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_acticle", method = RequestMethod.GET, produces = "application/json")
    public ResponseModel retrieveActicleList(String acticleName, Integer classify, int pageNo) {
        return response(acticleService.retrieveActicleList(acticleName, classify, pageNo));
    }

    @ApiOperation(value = "新闻详情", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_get_acticle", method = RequestMethod.GET, produces = "application/json")
    public ResponseModel retrieveActicleOne(Long acticle) {
        return response(acticleService.getById(acticle));
    }


    @ApiOperation(value = "新闻2top", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "acticle2top", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel acticle2Top(Long acticleId, boolean topTips) {
        acticleService.acticle2Top(acticleId, topTips);
        return responseSuccess();
    }

    @ApiOperation(value = "删除新闻", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "d_acticle", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel acticle2Top(Long acticle) {
        acticleService.removeById(acticle);
        return responseSuccess();
    }


}

