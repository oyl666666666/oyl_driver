package com.chengxusheji;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理器
 */
//@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerResolver {
    
	
	/**
     * @Descript 统一处理文件过大问题.
     */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        //log.error("上传文件过大 ex={}", e);
        //return retMsgCode("上传文件过大", 9999）;
        return "upload_error";
    }
	
	@ExceptionHandler(MultipartException.class)
	@ResponseBody
    public Map<String,Object> handleMultipartException(MultipartException e) {
        //log.error("上传文件过大 ex={}", e);
        //return retMsgCode("上传文件过大", 9999）;
		String message = "上传文件过大！";
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("message", message);
		resMap.put("success",false); 
        return resMap;
    }
 
}
