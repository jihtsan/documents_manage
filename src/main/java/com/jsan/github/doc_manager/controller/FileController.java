package com.jsan.github.doc_manager.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.DatePrinter;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Api(tags = "犀牛-文件上传")
@Controller
@Slf4j
@RequestMapping("/file")
public class FileController extends BaseController {

    @Value("${file.path}")
    private String filePath;


    @ApiOperation(value = "文件上传", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "u_file", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel u_file(MultipartFile file) throws IOException {
        String filename = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN)+file.getOriginalFilename();
        File f = new File(filePath+ filename);
        FileWriter.create(f)
                .write(file.getBytes(),0,file.getBytes().length);
        return response(filename);
    }

    @ApiOperation(value = "文件读取", httpMethod = "get", response = String.class)
    @RequestMapping(value = "g_file/{filename}", method = RequestMethod.GET, produces = "application/json")
    public void g_file( HttpServletResponse response, @PathVariable("filename") String filename) {
        try {
            response.setHeader("Content-Type","image/jpeg");
            //得到向客户端输出二进制数据的对象
            OutputStream toClient=response.getOutputStream();
            //输出数据
            toClient.write(FileReader.create(new File(filePath+filename)).readBytes());
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
