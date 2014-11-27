package cn.gray.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gray.common.Constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ResultUtil.class);
	
	public static String getJsonString(Object result){
		String jsonStr = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue
				,SerializerFeature.DisableCircularReferenceDetect);
		logger.info("api-send:" + jsonStr);
		if(Constant.IS_DECRYPTION){
			jsonStr = AESEncrypter.encrypt(jsonStr);
		}
		return jsonStr;
	}
}
