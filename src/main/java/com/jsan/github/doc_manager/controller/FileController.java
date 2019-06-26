package com.jsan.github.doc_manager.controller;


import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.utils.QiNiuFileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Api(tags = "犀牛-文件上传")
@Controller
@Slf4j
@RequestMapping("/file")
public class FileController extends BaseController {

    @Value("${Qiniu.url}")
    private String qiNiuUrl;


    @ApiOperation(value = "文件上传", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "u_file", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel u_file(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
            Iterator iter = mr.getFileMap().values().iterator();
            if (iter.hasNext()) {
                MultipartFile file = (MultipartFile) iter.next();
                String fileName = QiNiuFileUploadUtils.upLoadFile(file);
                return response(qiNiuUrl + fileName);
            }
        }
        return responseERROR();
    }
}
