package com.jizhi.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.TongjiT;

public class TongjiHelper {

	private static final double Default_jingong = 600.00;
	private static final double Default_fangshou = 600.00;
	private static final double Default_peihe = 600.00;
	public static final String key_c_t_jinqiu = "t_jinqiu";
	public static final String key_c_t_shiqiu = "t_shiqiu";
	private static final String key_c_t_kongqiulv = "t_kongqiulv";
	private static final String key_j_t_jingong= "t_jingong";
	private static final String key_j_t_fangshou = "t_fangshou";
	private static final String key_j_t_peihe = "t_peihe";
	
	public static Double getTeamJinGongByJinQiu(Double currentValue,Double oldVaule,Double lastPerValue) {
		if (currentValue >= lastPerValue) {
			return oldVaule+(currentValue-lastPerValue)*10;
		}else if (currentValue < lastPerValue)  {
			return oldVaule-(lastPerValue-currentValue)*5;
		}
		return oldVaule;
	}
	
	public static Double getTeamFangShouByShiQiu(Double currentValue,Double oldVaule,Double lastPerValue) {
		if (currentValue >= lastPerValue) {
			return oldVaule-(currentValue-lastPerValue)*5;
		}else if (currentValue < lastPerValue)  {
			return oldVaule+(lastPerValue-currentValue)*5;
		}
		return oldVaule;
	}
	
	public static Double getTeamPeiHeByKongQiuLv(Double currentValue,Double oldVaule,Double lastPerValue) {
		if (currentValue >= lastPerValue) {
			return oldVaule+(currentValue-lastPerValue)*10;
		}else if (currentValue < lastPerValue)  {
			return oldVaule-(lastPerValue-currentValue)*5;
		}
		return oldVaule;
	}
	
	/**
	 * 计算球队进攻能力，防守能力，配合能力
	 * @param collectKey
	 * @param collectValue
	 * @param oldCollectsMap
	 * @param countsMap
	 * @param judgeMap
	 */
	public static void calculateTeamJudge(String collectKey,String collectValue,Map<String,Double> oldCollectsMap,Map<String,Integer> countsMap,Map<String,Double> judgeMap) {
		if (StringUtils.isEmpty(collectValue)) {
			return;
		}
		/**
		 * 根据进球数算出攻击能力，规则：这一场的进球数大于之前统计的场均进球数，则加上（多余进球数）*10, 如果比场均小，则减去(少于进球数)*5
		 */
		if ( null == oldCollectsMap ) {
			oldCollectsMap = new HashMap<String,Double>();
		}
		if ( null == countsMap) {
			countsMap = new HashMap<String,Integer>();
		}
		if (key_c_t_jinqiu.equals(collectKey)) {
			//场均进球
			Double oldjinqiucounts = oldCollectsMap.get(collectKey);
			if ( null == oldjinqiucounts) {
				oldjinqiucounts = 0.0;
			}
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double perjinqiucounts = oldjinqiucounts/counts;
			Double cvalue = Double.parseDouble(collectValue);
			Double oldValue = judgeMap.get(key_j_t_jingong);
			if ( null == oldValue) {
				oldValue = Default_jingong;
			}
			judgeMap.put(key_j_t_jingong, getTeamJinGongByJinQiu(cvalue,oldValue,perjinqiucounts));
		}
		/*
		 * 防守能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if (key_c_t_shiqiu.equals(collectKey)) {
			Double oldshiqiucounts = oldCollectsMap.get(collectKey);
			if (null == oldshiqiucounts) {
				oldshiqiucounts = 0.0;
			}
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double pershiqiucounts = oldshiqiucounts/counts;
			Double cvalue = Double.parseDouble(collectValue);
			Double oldValue = judgeMap.get(key_j_t_fangshou);
			if ( null == oldValue) {
				oldValue = Default_fangshou;
			}
			judgeMap.put(key_j_t_fangshou, getTeamFangShouByShiQiu(cvalue,oldValue,pershiqiucounts));
		}
		
		/*
		 * 配合能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if (key_c_t_kongqiulv.equals(collectKey)) {
			Double oldpeihecounts = oldCollectsMap.get(collectKey);
			if ( null == oldpeihecounts) {
				oldpeihecounts = 0.0;
			}
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double perpeihecounts = oldpeihecounts/counts;
			Double cvalue = Double.parseDouble(collectValue);
			Double oldValue = judgeMap.get(key_j_t_peihe);
			if ( null == oldValue) {
				oldValue = Default_peihe;
			}
			judgeMap.put(key_j_t_peihe, getTeamPeiHeByKongQiuLv(cvalue,oldValue,perpeihecounts));
		}
	}
	
	/**
	 * 计算球队综合分数
	 * @param judgeMaps
	 * @return
	 */
	public static Double getTeamPoints(Map<String,Double> judgeMaps) {
		if ( null == judgeMaps) {
			return 0.00;
		}
		Double jingong = judgeMaps.get(key_j_t_jingong);
		if ( null == jingong) {
			jingong = Default_jingong;
		}
		Double fangshou = judgeMaps.get(key_j_t_fangshou);
		if ( null == fangshou) {
			fangshou = Default_fangshou;
		}
		Double peihe = judgeMaps.get(key_j_t_peihe);
		if ( null == peihe) {
			peihe = Default_peihe;
		}
		return (jingong+fangshou+peihe)/3;
	}
	
	/**
	 * @param collectMaps
	 * @param key
	 * @return
	 */
	public static Integer getTeamCollects(Map<String,String> collectMaps,String key) {
		if ( null == collectMaps) {
			return null;
		}
		String jinqiu = collectMaps.get(key);
		if (StringUtils.isEmpty(jinqiu)) {
			return null;
		}
		try {
			return Integer.parseInt(jinqiu);
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过一场赛程的结果来更新球队的统计数据
	 * @param rst
	 * @param tt
	 * @param collectionMap
	 * @param collectionCountsMap
	 * @param judgeMap
	 */
	public static void updateTeamTongjiBySchedule(RaceScheduleTeam rst,TongjiT tt,Map<String,Double> collectionMap,
			Map<String,Integer> collectionCountsMap,Map<String, Double> judgeMap) {
		if (rst.getUdefined()!=1) {
			//设置数据收集项
			if ( null != rst.getCollectItemsMap()) {
				Map<String,String> citems= rst.getCollectItemsMap().get(tt.getTeamId());
				if ( null != citems) {
					//设置数据收集项
					for (Iterator<String> it = citems.keySet().iterator();it.hasNext();) {
						String itemId = it.next();
						String svalue = citems.get(itemId);
						Double value = Double.valueOf(svalue);
						Double oldValue = collectionMap.get(itemId);
						//先计算值
						TongjiHelper.calculateTeamJudge(itemId, svalue, collectionMap, collectionCountsMap, judgeMap);
						if ( null == oldValue) {
							collectionMap.put(itemId, value);
						}else {
							collectionMap.put(itemId, oldValue+value);
						}
						Integer counts = collectionCountsMap.get(itemId);
						if (null != counts ) {
							collectionCountsMap.put(itemId, counts+1);
						}else {
							collectionCountsMap.put(itemId, 1);
						}
					}
				}
			}
			if (!StringUtils.isEmpty(rst.getSuccessTeamId())) {
				if (tt.getTeamId().equals(rst.getSuccessTeamId())) {
					int wins = tt.getWins();
					tt.setWins(wins+1);
				}else if (rst.getSuccessTeamId().equals("0")) {
					int evens = tt.getEven();
					tt.setEven(evens+1);
				}else {
					int loses = tt.getLoses();
					tt.setLoses(loses+1);
				}
			}
			int counts = tt.getCounts();
			tt.setCounts(counts+1);
		}
	}
	
}
