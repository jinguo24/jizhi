package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jizhi.dao.YardDao;
import com.jizhi.model.Yard;
@Service
public class YardService {

	@Autowired
	private YardDao yardDao;
	
	public void addYard(Yard yard) {
		yardDao.addYard(yard);
	}
	
	public List<Yard> getYardList(String name,int pageIndex,int pageSize) {
		return yardDao.getYardList(name, pageIndex, pageSize);
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
