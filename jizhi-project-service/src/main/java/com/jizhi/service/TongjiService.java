package com.jizhi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.constant.RaceEnums.RacePositions;
import com.jizhi.dao.RaceDao;
import com.jizhi.dao.RaceResultsDao;
import com.jizhi.dao.RaceScheduleTeamDao;
import com.jizhi.dao.TongjiPDao;
import com.jizhi.dao.TongjiTDao;
import com.jizhi.dao.TongjiTRaceDao;
import com.jizhi.dao.UserDao;
import com.jizhi.model.Race;
import com.jizhi.model.RaceCollectItem;
import com.jizhi.model.RaceResults;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.TongjiP;
import com.jizhi.model.TongjiT;
import com.jizhi.model.TongjiTRace;
import com.jizhi.model.User;
@Service
public class TongjiService {

	@Autowired
	private TongjiPDao tongjipDao;
	@Autowired
	private RaceResultsDao raceResultsDao;
	@Autowired
	private RaceScheduleTeamDao raceScheduleTeamDao;
	@Autowired
	private TongjiTDao tongjitDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RaceDao raceDao;
	@Autowired
	private TongjiTRaceDao tongjiTRaceDao;
	@Autowired
	private ConstantsService constantService;
	
	
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
		Map<String,Map<String,Double>> positionsCollectionMap = new HashMap<String,Map<String,Double>>();
		Map<String,Map<String,Integer>> positionsCollectionCountsMap = new HashMap<String,Map<String,Integer>>();
		//球队位置评判统计
		Map<String,Map<String,Double>> positionsJudgeMap = new HashMap<String,Map<String,Double>>();
		Map<String,Map<String,Integer>> positionsJudgeCountsMap = new HashMap<String,Map<String,Integer>>();
		//总评判项
		Map<String,Double> allJudgeMap = new HashMap<String,Double>();
		Map<String,Integer> allJudgeCountsMap = new HashMap<String,Integer>();
		//位置比赛总场数
		Map<String,Integer> countsMap = new HashMap<String,Integer>();
		//总分数
		Map<String,Double> points = new HashMap<String,Double>();
		//查询用户所有的统计数据，根据type分类，然后把统计项和评判项的对应值相加
		for (int i = 0 ;i < 10 ; i ++) {
			List<RaceResults> rrlist = raceResultsDao.queryByPhone(i, tj.getPhone(),tj.getType());
			if ( null != rrlist ) {
				for (int j = 0 ; j < rrlist.size(); j ++) {
					RaceResults rr = rrlist.get(j);
					List<String> positions = new ArrayList<>();
					//设置数据收集项
					Map<String,Map<String,String>> pitems= rr.getCollectItemsMap();
					if (null != pitems ) {
						for (Iterator<String> pit = pitems.keySet().iterator();pit.hasNext();) {
							String position = pit.next();
							Map<String,String> citems = pitems.get(position);
							if ( null != citems) {
								Map<String,Double> collectitems;
								Map<String,Integer> collectcounts;
								if (positionsCollectionMap.containsKey(position)) {
									collectitems = (Map<String,Double>) positionsCollectionMap.get(position);
								}else {
									collectitems = new HashMap<String,Double>();
									positionsCollectionMap.put(position, collectitems);
								}
								if (positionsCollectionCountsMap.containsKey(position)) {
									collectcounts = (Map<String, Integer>) positionsCollectionCountsMap.get(position);
								}else {
									collectcounts = new HashMap<String,Integer>();
									positionsCollectionCountsMap.put(position, collectcounts);
								}
								
								//设置数据收集项
								for (Iterator<String> it = citems.keySet().iterator();it.hasNext();) {
									String itemId = it.next();
									String sv = citems.get(itemId);
									try {
										Double value = Double.parseDouble(sv);
										Double oldValue = collectitems.get(itemId);
										if ( null == oldValue) {
											collectitems.put(itemId, value);
										}else {
											collectitems.put(itemId, oldValue+value);
										}
										Integer c = collectcounts.get(itemId);
										if ( null != c) {
										    collectcounts.put(itemId, c+1);
										}else {
											collectcounts.put(itemId, 1);
										}
									}catch(Exception e) {
									}
								}
							}
							if (!positions.contains(position)) {
								positions.add(position);
							}
						}
					}
					
					//设置评判项
					Map<String,Map<String,String>> pjitems=rr.getJudgeItemsMap();
					if (null != pjitems) {
						for (Iterator<String> pji = pjitems.keySet().iterator();pji.hasNext();) {
							String pjposition = pji.next();
							Map<String,String> jitems= pjitems.get(pjposition);
							if ( null != jitems) {
								Map<String,Double> judgeitems;
								Map<String,Integer> judgecounts;
								if (positionsJudgeMap.containsKey(pjposition)) {
									judgeitems = (Map<String, Double>) positionsJudgeMap.get(pjposition);
								}else {
									judgeitems = new HashMap<String,Double>();
									positionsJudgeMap.put(pjposition, judgeitems);
								}
								if (positionsJudgeCountsMap.containsKey(pjposition)) {
									judgecounts = (Map<String, Integer>) positionsJudgeCountsMap.get(pjposition);
								}else {
									judgecounts = new HashMap<String,Integer>();
									positionsJudgeCountsMap.put(pjposition, judgecounts);
								}
								//设置数据收集项
								for (Iterator<String> it = jitems.keySet().iterator();it.hasNext();) {
									String itemId = it.next();
									String psv = jitems.get(itemId);
									try {
										Double value = Double.parseDouble(psv);
										Double oldValue = judgeitems.get(itemId);
										if ( null == oldValue) {
											judgeitems.put(itemId, value);
										}else {
											judgeitems.put(itemId, oldValue+value);
										}
										Integer c = judgecounts.get(itemId);
										if ( null != c) {
											judgecounts.put(itemId, c+1);
										}else {
											judgecounts.put(itemId, 1);
										}
										
										if (allJudgeMap.containsKey(itemId)) {
											allJudgeMap.put(itemId, allJudgeMap.get(itemId)+value);
										}else {
											allJudgeMap.put(itemId, value);
										}
										if (allJudgeCountsMap.containsKey(itemId)) {
											allJudgeCountsMap.put(itemId, allJudgeCountsMap.get(itemId)+1);
										}else {
											allJudgeCountsMap.put(itemId,1);
										}
									}catch(Exception e) {
									}
								}
							}
							if (!positions.contains(pjposition)) {
								positions.add(pjposition);
							}
						}
					}
					
					for (int k = 0 ;k < positions.size();k++) {
						//设置总数
						String pkposiont = positions.get(k);
						if (countsMap.containsKey(pkposiont)) {
							int counts = countsMap.get(pkposiont)+1;
							countsMap.put(pkposiont, counts);
						}else {
							countsMap.put(pkposiont, 1);
						}
					}
					
					//设置分数
					Map<String,String> ptmaps = rr.getPointsMap();
					if ( null != ptmaps) {
						for (Iterator<String> ptit = ptmaps.keySet().iterator();ptit.hasNext();) {
							String pposition = ptit.next();
							Double value = Double.parseDouble(ptmaps.get(pposition));
							if (points.containsKey(pposition)) {
								Double oldvalue = points.get(pposition);
								points.put(pposition, oldvalue+value);
							}else {
								points.put(pposition, value);
							}
						}
					}
				}
			}
		}
		tj.setCollectItemsMap(positionsCollectionMap);
		//用统计项总分数除以统计项次数
		Map<String,Double> perjpoints = new HashMap<String,Double>();
		for (Iterator<String> jit = allJudgeMap.keySet().iterator();jit.hasNext();) {
			String jposition = jit.next();
			Double alljps = allJudgeMap.get(jposition);
			Integer acounts = allJudgeCountsMap.get(jposition);
			if (null != alljps && null != acounts) {
				perjpoints.put(jposition, alljps/acounts);
			}
		}
		tj.setAllJudgeItemsMap(perjpoints);
		//tj.setJudgeItemsMap(positionsJudgeMap);
		//tj.setRaceCountsMap(countsMap);
		//tj.setCollectCountsMap(positionsCollectionCountsMap);
		//tj.setJudgeCountsMap(positionsJudgeCountsMap);
		//用分数总数除以次数,擅长位置如果没有分数，则为位置默认分数
		Map<String,Double> perpoints = new HashMap<String,Double>();
		for (Iterator<String> poit = points.keySet().iterator();poit.hasNext();) {
			String poposition = poit.next();
			Double popoints = points.get(poposition);
			Integer count = countsMap.get(poposition);
			if ( null != popoints && null != count && count > 0 ) {
				perpoints.put(poposition, popoints/count);
			}
		}
		//擅长位置
		User user = userDao.getUserByPhone(tj.getPhone());
		List<String> vplist = user.getPositionsList();
		if (null != vplist) {
			for (int h = 0 ; h < vplist.size() ; h ++ ) {
				String pt = vplist.get(h);
				if (!perpoints.containsKey(pt)) {
					Double v = RacePositions.getDValue(pt);
					if ( null != v ) {
						perpoints.put(pt, v);	
					}
				}
			}
		}
		tj.setPointsMap(perpoints);
	}
	
	public void updateTeamTonji(String teamId,int type) {
		TongjiT tongjit =tongjitDao.getById(teamId);
		boolean isNew = false;
		if ( null == tongjit) {
			isNew = true;
			tongjit = new TongjiT();
			tongjit.setTeamId(teamId);
		}else {
			tongjit.clearItems();
		}
		updateTeamTonji(tongjit,type);
		if (isNew) {
			tongjitDao.addTongjiTeam(tongjit);
		}else {
			tongjitDao.update(tongjit);
		}
	}
	
	private void updateTeamTonji(TongjiT tt,int type) {
		List<TongjiTRace> ttrlist = tongjiTRaceDao.getByTeam(tt.getTeamId());
		if ( null != ttrlist) {
			
		}
	}
	
//	private void updateTeamTonji(TongjiT tt,int type) {
//		List<RaceScheduleTeam> rsts = raceScheduleTeamDao.query(0, tt.getTeamId(), type,999, 0, 10000);
//		if ( null != rsts) {
//			//球队位置数据项统计
//			Map<Integer,Double> collectionMap = new HashMap<Integer,Double>();
//			Map<Integer,Integer> collectionCountsMap = new HashMap<Integer,Integer>();
//			//球队位置评判统计
//			Map<Integer,Double> judgeMap = new HashMap<Integer,Double>();
//			Map<Integer,Integer> judgeCountsMap = new HashMap<Integer,Integer>();
//			//总分数
//			Double points = 0.00;
//			//胜
//			int wins = 0;
//			int loses = 0;
//			int evens = 0;
//			for (int i = 0 ; i < rsts.size() ; i ++) {
//				RaceScheduleTeam rst = rsts.get(i);
//				if (rst.getUdefined()==1) {
//					continue;
//				}
//				//设置数据收集项
//				if ( null != rst.getCollectItemsMap()) {
//					Map<String,String> citems= rst.getCollectItemsMap().get(tt.getTeamId());
//					if ( null != citems) {
//						//设置数据收集项
//						for (Iterator<String> it = citems.keySet().iterator();it.hasNext();) {
//							String itemids = it.next();
//							Integer itemId = Integer.parseInt(itemids);
//							String svalue = citems.get(itemids);
//							Double value = Double.valueOf(svalue);
//							Double oldValue = collectionMap.get(itemId);
//							if ( null == oldValue) {
//								collectionMap.put(itemId, value);
//							}else {
//								collectionMap.put(itemId, oldValue+value);
//							}
//							Integer counts = collectionCountsMap.get(itemId);
//							if (null != counts ) {
//								collectionCountsMap.put(itemId, counts+1);
//							}else {
//								collectionCountsMap.put(itemId, 1);
//							}
//						}
//					}
//				}
//				//设置评判项
//				if (null != rst.getJudgeItemsMap()) {
//					Map<String,String> jitems= rst.getJudgeItemsMap().get(tt.getTeamId());
//					if ( null != jitems) {
//						//设置数据收集项
//						for (Iterator<String> it = jitems.keySet().iterator();it.hasNext();) {
//							String itemids = it.next();
//							Integer itemId = Integer.parseInt(itemids);
//							String svalue = jitems.get(itemids);
//							Double value = Double.valueOf(svalue);
//							Double oldValue = judgeMap.get(itemId);
//							if ( null == oldValue) {
//								judgeMap.put(itemId, value);
//							}else {
//								judgeMap.put(itemId, oldValue+value);
//							}
//							Integer counts = judgeCountsMap.get(itemId);
//							if (null != counts ) {
//								judgeCountsMap.put(itemId, counts+1);
//							}else {
//								judgeCountsMap.put(itemId, 1);
//							}
//						}
//					}
//				}
//				//TODO 总分数
//				if (tt.getTeamId().equals(rst.getTeamOne())) {
//					//points = points + rst.getTeamOnePoints();
//				}else if (tt.getTeamId().equals(rst.getTeamTwo())) {
//					//points = points + rst.getTeamTwoPoints();
//				}
//				
//				if (tt.getTeamId().equals(rst.getSuccessTeamId())) {
//					wins++;
//				}else if (rst.getSuccessTeamId().equals("0")) {
//					evens++;
//				}else {
//					loses++;
//				}
//			}
//			
//			tt.setCollectItemsMap(collectionMap);
//			tt.setJudgeItemsMap(judgeMap);
//			tt.setPoints(points);
//			tt.setCounts(rsts.size());
//			tt.setWins(wins);
//			tt.setLoses(loses);
//			tt.setEven(evens);
//			tt.setCollectItemsCountsMap(collectionCountsMap);
//			tt.setJudgeItemsCountsMap(judgeCountsMap);
//		}
//	}
	
	public void updateTeamRaceTongji(int raceId) {
		Race race = raceDao.queryById(raceId);
		List<RaceScheduleTeam> rsts = raceScheduleTeamDao.query(raceId, null, race.getType(), 0, 1, 100);
		//球队数据统计
		Map<String,Map<String,Double>> teamCollectsMap = new HashMap<String,Map<String,Double>>();
		//赢的场次
		Map<String,Integer> wins = new HashMap<String,Integer>();
		//输的场次
		Map<String,Integer> loses = new HashMap<String,Integer>();
		//平的场次
		Map<String,Integer> evens = new HashMap<String,Integer>();
		if ( null != rsts) {
			Map<String,RaceCollectItem> rcmap = new HashMap<String,RaceCollectItem>();
			//查询出来所有的数据项
			List<RaceCollectItem> rcis = constantService.queryRaceCollectItemList(2, race.getType(), 1, null);
			if ( null != rcis) {
				for (int i = 0 ; i < rcis.size(); i ++) {
					RaceCollectItem rci = rcis.get(i);
					rcmap.put(rci.getId(), rci);
				}
			}
			//按队伍统计每个数据项的集合
			for (int i = 0 ; i < rsts.size(); i ++) {
				RaceScheduleTeam rst = rsts.get(i);
				String teamOneId = rst.getTeamOne();
				String teamTwoId = rst.getTeamTwo();
				//数据收集项
				Map<String, Map<String, String>> rmap = rst.getCollectItemsMap();
				if (null != rmap) {
					//队伍1
					Map<String,String> teamOneCollections = rmap.get(teamOneId);
					Map<String,Double> teamOneMap = teamCollectsMap.get(teamOneId);
					setTeamCollections(teamOneCollections,teamOneMap);
					
					//队伍2
					Map<String,String> teamTwoCollections = rmap.get(teamTwoId);
					Map<String,Double> teamTwoMap = teamCollectsMap.get(teamTwoId);
					setTeamCollections(teamTwoCollections,teamTwoMap);
				}
				//统计胜负平
				String succTeamId = rst.getSuccessTeamId();
				if (teamOneId.equals(succTeamId)) {
					 if (wins.containsKey(teamOneId)) {
						 wins.put(teamOneId, wins.get(teamOneId)+1);
					 }else {
						 wins.put(teamOneId, 1);
					 }
					 if (loses.containsKey(teamTwoId)) {
						 loses.put(teamTwoId, loses.get(teamTwoId)+1);
					 }else {
						 loses.put(teamTwoId, 1);
					 }
				}else if (teamTwoId.equals(succTeamId)) {
					if (wins.containsKey(teamTwoId)) {
						 wins.put(teamTwoId, wins.get(teamTwoId)+1);
					 }else {
						 wins.put(teamTwoId, 1);
					 }
					 if (loses.containsKey(teamOneId)) {
						 loses.put(teamOneId, loses.get(teamOneId)+1);
					 }else {
						 loses.put(teamOneId, 1);
					 }
				}else {
					if (evens.containsKey(teamTwoId)) {
						evens.put(teamTwoId, evens.get(teamTwoId)+1);
					 }else {
						 evens.put(teamTwoId, 1);
					 }
					 if (evens.containsKey(teamOneId)) {
						 evens.put(teamOneId, evens.get(teamOneId)+1);
					 }else {
						 evens.put(teamOneId, 1);
					 }
				}
				
			}
			
			//添加或者更新数据
			for (Iterator<String> tit = teamCollectsMap.keySet().iterator();tit.hasNext();) {
				TongjiTRace ttr = new TongjiTRace();
				String teamId = tit.next();
				ttr.setRaceId(raceId);
				ttr.setTeamId(teamId);
				Map<String,Double> tclls = teamCollectsMap.get(teamId);
				ttr.setCollectItemsMap(tclls);
				if ( null != tclls) {
					Double points = 0.00;
//					for (Iterator<String> tck = tclls.keySet().iterator();tck.hasNext();) {
//						String key = tck.next();
//						Double value = tclls.get(key);
//						RaceCollectItem rci = rcmap.get(key);
//						points = points + rci.getWeight()*value;
//					}
					Integer win = wins.get(teamId);
					int iwin = 0;
					if ( null != win) {
						iwin = win.intValue();
					}
					Integer even = evens.get(teamId);
					int ieven = 0;
					if ( null != even) {
						ieven = even.intValue();
					}
					ttr.setPoints(3*iwin+1*ieven);
				}
				
				ttr.setWins(wins.get(teamId));
				ttr.setLoses(loses.get(teamId));
				ttr.setEvens(evens.get(teamId));
				TongjiTRace ttre = tongjiTRaceDao.getByTeamAndRace(teamId, raceId);
				if ( null == ttre) {
					tongjiTRaceDao.addTongjiTeamRace(ttre);
				}else {
					tongjiTRaceDao.update(ttre);
				}
			}
		}
	}
	
	private void setTeamCollections(Map<String,String> teamRaceCollections,Map<String,Double> teamCollectionsMap) {
		if (null == teamCollectionsMap) {
			teamCollectionsMap = new HashMap<String,Double>();
		}
		if ( null != teamRaceCollections) {
			for (Iterator<String> oit = teamRaceCollections.keySet().iterator();oit.hasNext();) {
				String iname = oit.next();
				String ivalue = teamRaceCollections.get(iname);
				if (teamCollectionsMap.containsKey(iname)) {
					try {
						Double v = teamCollectionsMap.get(iname)+Double.parseDouble(ivalue);
						teamCollectionsMap.put(iname, v);
					}catch(Exception e) {
					}
				}else {
					try {
						teamCollectionsMap.put(iname, Double.parseDouble(ivalue));
					}catch(Exception e) {
					}
				}
				
			}
		}
	}
	
}
