package com.jsan.github.doc_manager.utils;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_FORMAT;

/**
 * @author jihaotian
 * @version 1.0
 * @date 2019-06-25 16:29
 */

@Component
public class QiNiuFileUploadUtils {

    private static String accessKey;
    private static String secretKey;
    private static String bucket;

    @Value("${Qiniu.accessKey}")
    public  void setAccessKey(String accessKey) {
        QiNiuFileUploadUtils.accessKey = accessKey;
    }

    @Value("${Qiniu.secretKey}")
    public  void setSecretKey(String secretKey) {
        QiNiuFileUploadUtils.secretKey = secretKey;
    }

    @Value("${Qiniu.bucket}")
    public void setBucket(String bucket) {
        QiNiuFileUploadUtils.bucket = bucket;
    }

    public static Auth QiNiuAuth = null;

    public static Auth getAuth() {
        if (QiNiuAuth == null) {
            QiNiuAuth = Auth.create(accessKey, secretKey);
        }
        return QiNiuAuth;
    }

    public static String upLoadFile(MultipartFile multipartFile) {
        getAuth();
        String fileName = DateUtil.format(new Date(), PURE_DATETIME_FORMAT) + multipartFile.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        final File file;
        try {
            file = File.createTempFile(System.currentTimeMillis() + "", prefix);
            multipartFile.transferTo(file);
            fileName = upLoadFile(file, fileName);
            deleteFile(file);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String upLoadFile(File file, String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = QiNiuAuth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) file.delete();
        }
    }
}
