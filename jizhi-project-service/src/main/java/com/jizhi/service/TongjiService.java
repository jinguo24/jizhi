package com.jizhi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
		}
		tongjit = new TongjiT();
		tongjit.setTeamId(teamId);
		updateTeamTonji(tongjit,type);
		if (isNew) {
			tongjitDao.addTongjiTeam(tongjit);
		}else {
			tongjitDao.update(tongjit);
		}
	}
	
	private void updateTeamTonji(TongjiT tt,int type) {
		List<RaceScheduleTeam> rsts = raceScheduleTeamDao.query(0, tt.getTeamId(), type,999, 0, 10000);
		if ( null != rsts) {
			//球队位置数据项统计
			Map<String,Double> collectionMap = new HashMap<String,Double>();
			Map<String,Integer> collectionCountsMap = new HashMap<String,Integer>();
			//球队位置评判统计
			Map<String, Double> judgeMap = new HashMap<String,Double>();
			//总分数
			Double points = 0.00;
			for (int i = 0 ; i < rsts.size() ; i ++) {
				RaceScheduleTeam rst = rsts.get(i);
				TongjiHelper.updateTeamTongjiBySchedule(rst, tt, collectionMap, collectionCountsMap, judgeMap);
			}
			tt.setCollectItemsMap(collectionMap);
			tt.setJudgeItemsMap(judgeMap);
			tt.setPoints(Math.floor(TongjiHelper.getTeamPoints(judgeMap)));
			tt.setCollectItemsCountsMap(collectionCountsMap);
			//tt.setJudgeItemsCountsMap(judgeCountsMap);
		}
	}
	
	public void updateTeamRaceTongji(int raceId) {
		Race race = raceDao.queryById(raceId);
		List<RaceScheduleTeam> rsts = raceScheduleTeamDao.query(raceId, null, race.getType(), 999, 0, 100);
		//赢的场次
		Map<String,Integer> wins = new HashMap<String,Integer>();
		//输的场次
		Map<String,Integer> loses = new HashMap<String,Integer>();
		//平的场次
		Map<String,Integer> evens = new HashMap<String,Integer>();
		//进球次数
		Map<String,Integer> jinqius = new HashMap<String,Integer>();
		//失球数
		Map<String,Integer> shiqius = new HashMap<String,Integer>();
		//总场次
		Map<String,Integer> countsMap = new HashMap<String,Integer>();
		//场次
		List<String> teamIds = new ArrayList<String>();
		if ( null != rsts) {
			//按队伍统计每个数据项的集合
			for (int i = 0 ; i < rsts.size(); i ++) {
				RaceScheduleTeam rst = rsts.get(i);
				if (rst.getUdefined()==1) {
					continue;
				}
				String teamOneId = rst.getTeamOne();
				String teamTwoId = rst.getTeamTwo();
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
				}else if ("0".equals(succTeamId)) {
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
				//设置进球数，失球数
				Map<String,Map<String,String>> collectMaps = rst.getCollectItemsMap();
				if ( null != collectMaps) {
					setTeamJinqiuAndShiqiu(teamOneId,TongjiHelper.key_c_t_jinqiu,collectMaps.get(teamOneId),jinqius);
					setTeamJinqiuAndShiqiu(teamOneId,TongjiHelper.key_c_t_shiqiu,collectMaps.get(teamOneId),shiqius);
					setTeamJinqiuAndShiqiu(teamTwoId,TongjiHelper.key_c_t_jinqiu,collectMaps.get(teamTwoId),jinqius);
					setTeamJinqiuAndShiqiu(teamTwoId,TongjiHelper.key_c_t_shiqiu,collectMaps.get(teamTwoId),shiqius);
				}
				//设置场次
				if (rst.getStatus()==3) {
					if(!countsMap.containsKey(teamOneId)) {
						countsMap.put(teamOneId, 1);
					}else {
						countsMap.put(teamOneId, countsMap.get(teamOneId)+1);
					}
					if(!countsMap.containsKey(teamTwoId)) {
						countsMap.put(teamTwoId, 1);
					}else {
						countsMap.put(teamTwoId, countsMap.get(teamTwoId)+1);
					}
				}
				if (!teamIds.contains(teamOneId)) {
					teamIds.add(teamOneId);
				}
				if (!teamIds.contains(teamTwoId)) {
					teamIds.add(teamTwoId);
				}
				
			}
			
			for (int j = 0 ; j < teamIds.size(); j ++) {
				TongjiTRace ttr = new TongjiTRace();
				String teamId = teamIds.get(j);
				ttr.setRaceId(raceId);
				ttr.setTeamId(teamId);
				ttr.setWins(null==wins.get(teamId)?0:wins.get(teamId));
				ttr.setLoses(null==loses.get(teamId)?0:loses.get(teamId));
				ttr.setEvens(null==evens.get(teamId)?0:evens.get(teamId));
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
				
				ttr.setJinqius(null ==jinqius.get(teamId)?0:jinqius.get(teamId));
				ttr.setShiqius(null ==shiqius.get(teamId)?0:shiqius.get(teamId));
				ttr.setJingshengqiu(ttr.getJinqius()-ttr.getShiqius());
				ttr.setCounts(null==countsMap.get(teamId)?0:countsMap.get(teamId));
				TongjiTRace ttre = tongjiTRaceDao.getByTeamAndRace(teamId, raceId);
				if ( null == ttre) {
					tongjiTRaceDao.addTongjiTeamRace(ttr);
				}else {
					ttr.setId(ttre.getId());
					tongjiTRaceDao.update(ttr);
				}
			}
		}
	}
	
	private void setTeamJinqiuAndShiqiu(String teamId,String key,Map<String,String> collectMaps,Map<String,Integer> maps) {
		if (null != collectMaps) {
			Integer teamcount = TongjiHelper.getTeamCollects(collectMaps, key);
			if (null != teamcount) {
				if (!maps.containsKey(teamId)) {
					maps.put(teamId, teamcount);
				}else {
					maps.put(teamId, maps.get(teamId)+teamcount);
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
	
	public List<TongjiTRace> getTongjiTRaces(int raceId) {
		return tongjiTRaceDao.getByRaceId(raceId);
	}
}
