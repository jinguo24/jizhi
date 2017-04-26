package com.jizhi.service;

import java.util.HashMap;
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
		if (race.getUdefined()==1) {
			return ;
		}
		Map<String,Map<String,String>> collectsMap = race.getCollectItemsMap();
		if ( null != collectsMap) {
			//此处更新球队数据是在之前的统计数据上面加上这次的数据
			//updateTeamTongji(race.getTeamOne(),race,collectsMap.get(race.getTeamOne()));
			//updateTeamTongji(race.getTeamTwo(),race,collectsMap.get(race.getTeamTwo()));
		}
	}
	
//	private void updateTeamTongji(String teamId,RaceScheduleTeam race,Map<String,String> currentmaps) {
//		//更新统计数据
//		TongjiT tongjit = tongjiTDao.getById(teamId);
//		boolean isnew = false;
//		if ( null == tongjit) {
//			tongjit = new TongjiT();
//			tongjit.setTeamId(teamId);
//			tongjit.setCounts(1);
//			isnew = true;
//		}
//		Map<String,String> oldmaps = tongjit.getCollectItemsMap();
//		if ( null == oldmaps) {
//			oldmaps = new HashMap<String,String>();
//		}
//		Map<String,String> jdmaps = tongjit.getJudgeItemsMap();
//		if ( null == jdmaps) {
//			jdmaps = new HashMap<String,String>();
//		}
//		Map<String,Integer> collectionCountsMap = tongjit.getCollectItemsCountsMap();
//		if ( null == collectionCountsMap) {
//			collectionCountsMap = new HashMap<String,Integer>();
//		}
//		if ( null != currentmaps) {
//			TongjiHelper.updateTeamTongjiBySchedule(race, tongjit, oldmaps, collectionCountsMap, jdmaps);
//			tongjit.setCollectItemsMap(oldmaps);
//			//设置评分项
//			tongjit.setJudgeItemsMap(jdmaps);
//			tongjit.setPoints(TongjiHelper.getTeamPoints(tongjit.getJudgeItemsMap()));
//			tongjit.setCollectItemsCountsMap(collectionCountsMap);
//		}
//		
//		if (isnew) {
//			tongjiTDao.addTongjiTeam(tongjit);
//		}else {
//			tongjiTDao.update(tongjit);
//		}
//	}
	
	
	public void delete(int id) {
		raceScheduleTeamDao.deleteById(id);
	}
}
