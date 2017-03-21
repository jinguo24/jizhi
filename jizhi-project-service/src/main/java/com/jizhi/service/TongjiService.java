package com.jizhi.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceResultsDao;
import com.jizhi.dao.TongjiPDao;
import com.jizhi.model.RaceResults;
import com.jizhi.model.TongjiP;
@Service
public class TongjiService {

	@Autowired
	private TongjiPDao tongjipDao;
	@Autowired
	private RaceResultsDao raceResultsDao;
	
	public void addTongjiPerson(TongjiP tongjip) {
		tongjipDao.addTongjiPerson(tongjip);
	}
	
	public void update(TongjiP tongjip) {
		tongjipDao.update(tongjip);
	}
	
	public TongjiP getByPhoneAndType(String phone,int type) {
		return tongjipDao.getByPhoneAndType(phone, type);
	}
	
	public List<TongjiP> getByPhone(String phone) {
		return tongjipDao.getByPhone(phone);
	}
	
	public void updatePersonTonji(String phone,int type) {
		TongjiP tj = tongjipDao.getByPhoneAndType(phone, type);
		boolean isNew = false;
		if ( null == tj) {
			isNew = true;
			tj = new TongjiP();
			tj.setPhone(phone);
			tj.setType(type);
		}else {
			tj.clearItems();
		}
		setTonji(tj);
		if (isNew) {
			tongjipDao.addTongjiPerson(tj);
		}else {
			tongjipDao.update(tj);
		}
	}
	
	private void setTonji(TongjiP tj) {
		//球队位置数据项统计
		Map<Integer,Map<Integer,Double>> positionsCollectionMap = new HashMap<Integer,Map<Integer,Double>>();
		//球队位置评判统计
		Map<Integer,Map<Integer,Double>> positionsJudgeMap = new HashMap<Integer,Map<Integer,Double>>();
		//总数
		Map<Integer,Integer> countsMap = new HashMap<Integer,Integer>();
		//总分数
		Double points = 0.00;
		//查询用户所有的统计数据，根据type分类，然后把统计项和评判项的对应值相加
		for (int i = 0 ;i < 10 ; i ++) {
			List<RaceResults> rrlist = raceResultsDao.queryByPhone(i, tj.getPhone(),tj.getType());
			if ( null != rrlist ) {
				for (int j = 0 ; j < rrlist.size(); j ++) {
					RaceResults rr = rrlist.get(j);
					//设置数据收集项
					Map<Integer,Double> citems= rr.getCollectItemsMap();
					if ( null != citems) {
						Map<Integer,Double> collectitems;
						if (positionsCollectionMap.containsKey(rr.getPosition())) {
							collectitems = (Map<Integer, Double>) positionsCollectionMap.get(rr.getPosition());
						}else {
							collectitems = new HashMap<Integer,Double>();
							positionsCollectionMap.put(rr.getPosition(), collectitems);
						}
						//设置数据收集项
						for (Iterator<Integer> it = citems.keySet().iterator();it.hasNext();) {
							Integer itemId = it.next();
							Double value = citems.get(itemId);
							Double oldValue = collectitems.get(itemId);
							if ( null == oldValue) {
								collectitems.put(itemId, value);
							}else {
								collectitems.put(itemId, oldValue+value);
							}
						}
					}
					//设置评判项
					Map<Integer,Double> jitems= rr.getJudgeItemsMap();
					if ( null != jitems) {
						Map<Integer,Double> judgeitems;
						if (positionsJudgeMap.containsKey(rr.getPosition())) {
							judgeitems = (Map<Integer, Double>) positionsJudgeMap.get(rr.getPosition());
						}else {
							judgeitems = new HashMap<Integer,Double>();
							positionsJudgeMap.put(rr.getPosition(), judgeitems);
						}
						//设置数据收集项
						for (Iterator<Integer> it = jitems.keySet().iterator();it.hasNext();) {
							Integer itemId = it.next();
							Double value = citems.get(itemId);
							Double oldValue = judgeitems.get(itemId);
							if ( null == oldValue) {
								judgeitems.put(itemId, value);
							}else {
								judgeitems.put(itemId, oldValue+value);
							}
						}
					}
					
					//设置总数
					if (countsMap.containsKey(rr.getPosition())) {
						int counts = countsMap.get(rr.getPosition())+1;
						countsMap.put(rr.getPosition(), counts);
					}else {
						countsMap.put(rr.getPosition(), 1);
					}
					//总分数
					points = points + rr.getPoints();
				}
			}
		}
		tj.setCollectItemsMap(positionsCollectionMap);
		tj.setJudgeItemsMap(positionsJudgeMap);
		tj.setRaceCountsMap(countsMap);
		tj.setPoints(points);
	}
	
	
	public void updateTeamTonji() {
		
	}
	
}
