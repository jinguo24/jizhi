package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.Yard;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class YardDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addYard(Yard yard) {
		this.sqlSession.insert("yard.insert", yard);
	}
	
	public void update(Yard yard) {
		this.sqlSession.update("yard.update",yard);
	}
	
	public List<Yard> getYardList(String name,int status,int pageIndex,int pageSize) {
		Map param = new HashMap();
		param.put("name", name);
		param.put("status", status);
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("yard.query",param);
	}
	
	public int getYardCount(String name,int status) {
		Map param = new HashMap();
		param.put("name", name);
		param.put("status", status);
		return this.sqlSession.selectOne("yard.queryCount",param);
	}
	
	public Yard getYardById(int id) {
		Map param = new HashMap();
		param.put("id", id);
		return this.sqlSession.selectOne("yard.queryById",param);
	}
	
	public void updateStatus(int status,int id) {
		Map param = new HashMap();
		param.put("id", id);
		param.put("status", status);
		this.sqlSession.update("yard.updateStatus",param);
	}
	
}
