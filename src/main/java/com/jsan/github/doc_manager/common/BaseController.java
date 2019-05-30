package com.jsan.github.doc_manager.common;


import com.alibaba.fastjson.JSONException;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.exception.AbstractException;
import com.jsan.github.doc_manager.exception.BusinessException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Controller基类
 *
 * @author jinjian
 */
@RestControllerAdvice
public abstract class BaseController {

    protected static final String CODE = "responseCode";
    protected static final String MSG = "responseMsg";
    protected static final String CONTENT = "content";
    protected static final String COUNT = "count";
    protected static final String SUCCESS_CODE = "00";
    protected static final String ERROR_CODE = "01";
    protected static final String SUCCESS_MSG = "操作成功";

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    protected static final ResponseModel responseModel = new ResponseModel();

    public BaseController() {
    }

    /**
     * 拦截异常
     *
     * @param e 异常
     * @return 异常响应json
     * @throws IOException
     */
//    @ExceptionHandler()
    public ResponseModel exception(Exception e) throws IOException {
        if (e instanceof AbstractException) {
            responseModel.setMessage("数据异常");
        } else if (e instanceof BusinessException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus(((BusinessException) e).getCode());
        } else if (e instanceof MissingServletRequestParameterException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("401");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("402");
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("403");
        } else if (e instanceof NullPointerException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("404");
        } else if (e instanceof JSONException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("405");
        } else if (e instanceof IOException) {
            responseModel.setMessage(e.getMessage())
                    .setHttpStatus("406");
        } else {
            responseModel.setMessage("系统异常")
                    .setHttpStatus("99999");
            System.out.println(e);
        }
        return responseModel;
    }


    public ResponseModel response(Object o) {
        responseModel.setHttpStatus(SUCCESS_CODE)
                .setMessage(SUCCESS_MSG)
                .setData(o);

        return responseModel;
    }

    public  ResponseModel response(String code, String message) {
        responseModel.setHttpStatus(code)
                .setMessage(message);
        return responseModel;
    }

    public ResponseModel responseSuccess() {
        responseModel.setHttpStatus(SUCCESS_CODE)
                .setMessage(SUCCESS_MSG);
        return responseModel;
    }

    public ResponseModel responseERROR() {
        responseModel.setHttpStatus(ERROR_CODE)
                .setMessage(SUCCESS_MSG);
        return responseModel;
    }

    public String getTokenId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return request.getSession().getId();
    }


    public String base64(String password) {
        return new String(Base64.encodeBase64(password.getBytes()));
    }

//    String getUserId(HttpServletRequest request) {
//        String token = getTokenId(request).trim();
//        String uid = stringRedisTemplate.opsForValue().get(token);
//        if (StringUtils.isEmpty(uid)) {
//            throw new BusinessException(ExceptionType.TOKEN_ID_EXPIRE);
//        }
//        return uid;
//    }

    /**
     * 将流返回前端生成Excel
     */
    public void responseExcelFile(HttpServletResponse response, InputStream inputStream, String fileName) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        os.close();
        os.flush();
        inputStream.close();
    }

}
