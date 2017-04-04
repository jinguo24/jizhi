package com.jizhi.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceScheduleTeamDao;
import com.jizhi.dao.TongjiTDao;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.TongjiT;
import com.simple.common.util.PageResult;
@Service
public class RaceScheduleTeamService {

	@Autowired
	private RaceScheduleTeamDao raceScheduleTeamDao;
	@Autowired
	private TongjiTDao tongjiTDao;
	
	public List<RaceScheduleTeam> queryList(int raceId,String teamId,int type,int status, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceScheduleTeamDao.query(raceId,teamId,type,status,(pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int raceId,String teamId,int type,int status) {
		return raceScheduleTeamDao.queryCount(raceId,teamId,type,status);
	}
	
	public PageResult getRacePageResult(int raceId,String teamId,int type,int status,int pageIndex,int pageSize) {
		List<RaceScheduleTeam> races = queryList(raceId,teamId,type,status,pageIndex,pageSize);
		int count = queryCount(raceId,teamId,type,status);
		return new PageResult(count,pageSize,pageIndex,races);
	}
	
	public void addRacePageResult(RaceScheduleTeam race) {
		raceScheduleTeamDao.addRaceScheduleTeam(race);
	}
	
	public RaceScheduleTeam queryById(int id) {
		return raceScheduleTeamDao.queryTeamById(id);
	}
	
	public void updateRaceScheduleTeam(RaceScheduleTeam race) {
		raceScheduleTeamDao.updateRaceScheduleTeam(race);
	}
	
	public void updateRaceScheduleTeamStatus(int raceId,int status) {
		raceScheduleTeamDao.updateStatus(raceId, status);
	}
	
	public void updateRaceScheduleTeamResults(RaceScheduleTeam race) {
		raceScheduleTeamDao.updateRaceScheduleTeamResults(race);
		Map<String,Map<String,String>> collectsMap = race.getCollectItemsMap();
		if ( null != collectsMap) {
			updateTeamTongji(race.getTeamOne(),collectsMap.get(race.getTeamOne()));
			updateTeamTongji(race.getTeamTwo(),collectsMap.get(race.getTeamTwo()));
		}
	}
	
	private void updateTeamTongji(String teamId,Map<String,String> cmaps) {
		//更新统计数据
		TongjiT tongjit = tongjiTDao.getById(teamId);
		boolean isnew = false;
		if ( null == tongjit) {
			tongjit = new TongjiT();
			tongjit.setTeamId(teamId);
			tongjit.setCounts(1);
			isnew = true;
		}
		Map<String,Double> csmaps = tongjit.getCollectItemsMap();
		if ( null == csmaps) {
			csmaps = new HashMap<String,Double>();
		}
		Map<String,Double> jdmaps = tongjit.getJudgeItemsMap();
		if ( null == jdmaps) {
			jdmaps = new HashMap<String,Double>();
		}
		if ( null != cmaps) {
			for (Iterator<String> it = cmaps.keySet().iterator();it.hasNext();) {
				String ikey = it.next();
				String iv = cmaps.get(ikey);
				if (csmaps.containsKey(ikey)) {
					try {
						csmaps.put(ikey, csmaps.get(ikey)+Double.parseDouble(iv));
					}catch(Exception e) {
					}
				}else {
					try {
						csmaps.put(ikey, Double.parseDouble(iv));
					}catch(Exception e) {
					}
				}
				TongjiHelper.calculateTeamJudge(ikey,iv,tongjit.getCollectItemsMap(),tongjit.getCounts(),jdmaps);
			}
			tongjit.setCollectItemsMap(csmaps);
			//设置评分项
			tongjit.setJudgeItemsMap(jdmaps);
			tongjit.setPoints(TongjiHelper.getTeamPoints(tongjit));
		}
		if (isnew) {
			tongjiTDao.addTongjiTeam(tongjit);
		}else {
			tongjiTDao.update(tongjit);
		}
	}
	
	
	public void delete(int id) {
		raceScheduleTeamDao.deleteById(id);
	}
}
