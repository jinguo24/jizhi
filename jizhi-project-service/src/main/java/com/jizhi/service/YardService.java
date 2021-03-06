package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.YardDao;
import com.jizhi.model.Yard;
import com.simple.common.util.PageResult;
@Service
public class YardService {

	@Autowired
	private YardDao yardDao;
	
	public void addYard(Yard yard) {
		yardDao.addYard(yard);
	}
	
	public List<Yard> getYardList(String name,int status,int pageIndex,int pageSize) {
		return yardDao.getYardList(name,status, pageIndex, pageSize);
	}
	
	public int getYardCount(String name,int status) {
		return yardDao.getYardCount(name,status);
	}
	
	public PageResult getYardPageResult(String name,int status,int pageIndex,int pageSize) {
		List<Yard> yards = getYardList(name,status,pageIndex,pageSize);
		int count = getYardCount(name,status);
		return new PageResult(count,pageSize,pageIndex,yards);
	}
	
	public Yard getYard(int id) {
		return yardDao.getYardById(id);
	}
	
	public void updateYard(Yard yard) {
		yardDao.update(yard);
	}
	
	public void updateStatus(int status, int id) {
		yardDao.updateStatus(status, id);
	}
	
}
