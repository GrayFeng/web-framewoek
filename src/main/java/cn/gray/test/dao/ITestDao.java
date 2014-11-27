package cn.gray.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.gray.common.annotation.MyBatisRepository;
import cn.gray.common.pojo.City;

/**
 * Description: ITestDao.java
 * All Rights Reserved.
 * @version 1.0  2014年7月25日 下午1:54:28  
 * @author Gray(jy.feng@zuche.com) 
 */

@Repository
@MyBatisRepository
public interface ITestDao {
	
	public List<City> getCityList();

}
