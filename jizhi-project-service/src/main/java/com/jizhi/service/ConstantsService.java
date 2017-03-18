package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceCollectItemDao;
import com.jizhi.dao.RaceJudgeItemDao;
import com.jizhi.model.RaceCollectItem;
import com.jizhi.model.RaceJudgeItem;
@Service
public class ConstantsService {

	@Autowired
	private RaceCollectItemDao raceCollectItemDao;
	@Autowired
	private RaceJudgeItemDao raceJudgeItemDao;
	
	public List<RaceCollectItem> queryRaceCollectItemList(int scale,int type,int status) {
		return raceCollectItemDao.queryList(scale, type, status);
	}
	
	public List<RaceJudgeItem> queryRaceJudgeItemList(int scale,int type,int status) {
		return raceJudgeItemDao.queryList(scale, type, status);
	}
	
}
