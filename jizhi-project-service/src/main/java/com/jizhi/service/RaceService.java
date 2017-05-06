package com.jizhi.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.PersonRaceApplyNoTeamDao;
import com.jizhi.dao.RaceDao;
import com.jizhi.dao.RacePersonApplyNoTeamDao;
import com.jizhi.model.PersonRaceApplyNoTeam;
import com.jizhi.model.Race;
import com.jizhi.model.RacePersonApplyNoTeam;
import com.simple.common.util.PageResult;
@Service
public class RaceService {

	@Autowired
	private RaceDao raceDao;
	@Autowired
	private RacePersonApplyNoTeamDao racePersonDao;
	@Autowired
	private PersonRaceApplyNoTeamDao personRaceDao;
	
	public List<Race> queryRaceList(String name,int type,int status, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceDao.queryList(name,type, status,(pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(String name,int type,int status) {
		return raceDao.queryCount(name, type, status);
	}
	
	public PageResult getRacePageResult(String name,int type,int status,int pageIndex,int pageSize) {
		List<Race> races = queryRaceList(name,type,status,pageIndex,pageSize);
		int count = queryCount(name,type,status);
		return new PageResult(count,pageSize,pageIndex,races);
	}
	
	public void addRace(Race race) {
		race.setStatus(1);
		raceDao.addRace(race);
	}
	
	public Race queryById(int id) {
		return raceDao.queryById(id);
	}
	
	public void updateRace(Race race) {
		raceDao.updateRace(race);
	}
	
	public void updateStatus(int raceId,int status) {
		raceDao.updateStatus(raceId, status);
	}
	
	public void addRacePersonApplyNoTeam(String phone,int raceId,String name,String remark,String raceName) {
		RacePersonApplyNoTeam rpa = new RacePersonApplyNoTeam();
		rpa.setCreateTime(new Date());
		rpa.setName(StringUtils.trimToEmpty(name));
		rpa.setPhone(phone);
		rpa.setRaceId(raceId);
		rpa.setRemark(StringUtils.trimToEmpty(remark));
		racePersonDao.addRacePersonApplyNoTeam(rpa);
		
		PersonRaceApplyNoTeam pra = new PersonRaceApplyNoTeam();
		pra.setCreateTime(rpa.getCreateTime());
		pra.setPhone(phone);
		pra.setRaceId(raceId);
		pra.setRaceName(raceName);
		pra.setRemark(StringUtils.trimToEmpty(remark));
		personRaceDao.addPersonRaceApplyNoTeam(pra);
	}
	
	public RacePersonApplyNoTeam getPersonByRaceAndPhone(int raceId,String phone) {
		return racePersonDao.getByRaceAndPhone(raceId, phone);
	}
	
	public List<RacePersonApplyNoTeam> queryRacePersonList(int raceId,String remark,int pageIndex,int pageSize) {
		if (pageIndex<1) {
			pageIndex=1;
		}
		return racePersonDao.queryList(raceId, remark, (pageIndex-1)*pageSize, pageSize);
	}
	
	public void deletePerson(String phone,int raceId,int id) {
		racePersonDao.delete(raceId, id);
		personRaceDao.delete(phone, id);
	}
	
	public List<PersonRaceApplyNoTeam> queryPersonRaceList(String phone,String remark,int pageIndex,int pageSize) {
		if (pageIndex<1) {
			pageIndex=1;
		}
		return personRaceDao.queryList(phone, remark, (pageIndex-1)*pageSize, pageSize);
	}
	
}
