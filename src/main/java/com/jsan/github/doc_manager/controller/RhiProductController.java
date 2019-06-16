package com.jsan.github.doc_manager.controller;


import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.RhiProduct;
import com.jsan.github.doc_manager.entity.RhiProductDimension;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.service.IRhiProductDimensionService;
import com.jsan.github.doc_manager.service.IRhiProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Controller
@RequestMapping("/product")
public class RhiProductController extends BaseController {
    @Autowired
    IRhiProductService productService;

    @Autowired
    IRhiProductDimensionService productDimensionService;

    @ApiOperation(value = "产品创建", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "cu_product", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel CreateOrUpdateProduct(@RequestBody RhiProduct product) {
        productService.saveOrUpdate(product);
        return response("创建成功");
    }

    @ApiOperation(value = "产品列表", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_product", method = RequestMethod.GET, produces = "application/json")
    public ResponseModel retrieveProductList(String productName, Long dimensionId, int pageNo) {
        return response(productService.retrieveProductList(productName, dimensionId, pageNo));
    }

    @ApiOperation(value = "产品详情", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_get_product", method = RequestMethod.GET, produces = "application/json")
    public ResponseModel retrieveProductOne(Long productId) {
        return response(productService.getById(productId));
    }


    @ApiOperation(value = "产品2top", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "product2top", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel product2Top(Long productId, boolean topTips) {
        productService.product2Top(productId, topTips);
        return responseSuccess();
    }

    @ApiOperation(value = "产品删除", httpMethod = "Post", response = String.class)
    @RequestMapping(value = "d_product", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel d_product(Long productId) {
        productService.removeById(productId);
        return responseSuccess();
    }


    @ApiOperation(value = "产品维度列表", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_product_dimension", method = RequestMethod.GET, produces = "application/json")
    public ResponseModel retrieveProductDimensionList() {
        return response(productDimensionService.list());
    }


    @ApiOperation(value = "产品维度增加", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "c_product_dimension", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel retrieveProductDimensionList(@RequestBody RhiProductDimension productDimension) {
        return response(productDimensionService.save(productDimension));
    }

    @ApiOperation(value = "产品维度删除", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "d_product_dimension", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel deleteProductDimensionList(long id) {
        return response(productDimensionService.removeById(id));
    }

}
