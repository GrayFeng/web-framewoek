package cn.gray.test.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

/**
 * Description: TestRedisController.java
 * All Rights Reserved.
 * @version 1.0  2014年9月18日 上午9:37:52  
 * @author Gray(jy.feng@zuche.com) 
 */

@Controller
@RequestMapping("/redis")
public class TestRedisController {
	
	@RequestMapping("set-{value}")
	@ResponseBody
	public String set(@PathVariable String value){
		Jedis jedis = new Jedis("192.168.118.128");
		jedis.set("testKey", value);
		jedis.close();
		return "ok";
	}
	
	@RequestMapping("get")
	@ResponseBody
	public String get(){
		Jedis jedis = new Jedis("192.168.118.128");
		String value = jedis.get("testKey");
		jedis.close();
		return value;
	}

}
