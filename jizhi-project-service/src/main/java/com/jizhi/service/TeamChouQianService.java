package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jizhi.constant.ChouQianCache;
import com.jizhi.dao.TeamChouQianDao;
import com.jizhi.model.TeamChouQianDetail;
@Service
public class TeamChouQianService {

	@Autowired
	private TeamChouQianDao teamChouQianDao;
	
	public String chouqian(int raceId,String phone) throws Exception{
		TeamChouQianDetail tcqd = teamChouQianDao.queryByPhoneAndRace(phone, raceId);
		if (null == tcqd) {
			throw new RuntimeException("抽签失败，非法用户操作");
		}
		if ( null != tcqd && (!StringUtils.isEmpty(tcqd.getLabel())) ) {
			throw new RuntimeException("您已经抽取过,结果为【"+tcqd.getLabel()+"】");
		}
		String label = ChouQianCache.getInstance().getLable();
		teamChouQianDao.updateLabel(label, phone, raceId);
		return label;
	}
	
	public List<TeamChouQianDetail> queryDetails(int raceId) {
		return teamChouQianDao.queryDetails(raceId);
	}
}
