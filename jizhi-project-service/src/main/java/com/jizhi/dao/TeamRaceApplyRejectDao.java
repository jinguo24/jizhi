package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.TeamRaceApplyReject;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamRaceApplyRejectDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeamRaceApply(TeamRaceApplyReject teamRaceApplyReject) {
		this.sqlSession.insert("teamRaceApplyReject.insert", teamRaceApplyReject);
	}
	
	public void updateStatus(TeamRaceApplyReject teamRaceApply) {
		this.sqlSession.update("teamRaceApplyReject.updateStatus",teamRaceApply);
	}
	
	public List<TeamRaceApplyReject> getTeamRaceApplyList(Integer raceId,String raceName,int status,int type,String phone,int pageIndex,int pageSize) {
		Map param = new HashMap();
		param.put("raceName", raceName);
		param.put("leaderPhone", phone);
		param.put("status", status);
		param.put("type", type);
		if (null == raceId ) {
			param.put("raceId", 0);
		}else {
			param.put("raceId", raceId);
		}
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("teamRaceApplyReject.query",param);
	}
	
	public Integer getTeamRaceApplyCount(Integer raceId,String raceName,int status,int type,String phone) {
		Map param = new HashMap();
		param.put("raceName", raceName);
		param.put("leaderPhone", phone);
		param.put("status", status);
		param.put("type", type);
		if (null == raceId ) {
			param.put("raceId", 0);
		}else {
			param.put("raceId", raceId);
		}
		return this.sqlSession.selectOne("teamRaceApplyReject.queryCount",param);
	}
	
	public TeamRaceApplyReject getTeamRaceApplyById(String id) {
		return this.sqlSession.selectOne("teamRaceApplyReject.queryById",id);
	}
	
	public TeamRaceApplyReject getByRaceAndTeam(int raceId,String teamName) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("teamName", teamName);
		return this.sqlSession.selectOne("teamRaceApplyReject.queryByRaceAndTeam",param);
	}
	
	public List<TeamRaceApplyReject> queryListByIds(List<String> ids) {
		Map param = new HashMap();
		param.put("ids", ids);
		return this.sqlSession.selectList("teamRaceApplyReject.queryByIds",param);
	}
	
	public void deleteById(String id) {
		this.sqlSession.delete("teamRaceApplyReject.delete",id);
	}
}
