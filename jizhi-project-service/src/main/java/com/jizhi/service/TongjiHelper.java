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
	public static void calculateTeamJudge(String collectKey,String collectValue,Map<String,String> oldCollectsMap,Map<String,Integer> countsMap,Map<String,String> judgeMap) {
		if (StringUtils.isEmpty(collectValue)) {
			return;
		}
		/**
		 * 根据进球数算出攻击能力，规则：这一场的进球数大于之前统计的场均进球数，则加上（多余进球数）*10, 如果比场均小，则减去(少于进球数)*5
		 */
		if ( null == oldCollectsMap ) {
			oldCollectsMap = new HashMap<String,String>();
		}
		if ( null == countsMap) {
			countsMap = new HashMap<String,Integer>();
		}
		if (key_c_t_jinqiu.equals(collectKey)) {
			//场均进球
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double oldjinqiucounts = getDouble(oldCollectsMap.get(collectKey)) ;
			Double perjinqiucounts = oldjinqiucounts/counts;
			Double cvalue = getDouble(collectValue);
			Double oldValue = getDouble(judgeMap.get(key_j_t_jingong));
			if ( null == oldValue  || oldValue==0) {
				oldValue = Default_jingong;
			}
			judgeMap.put(key_j_t_jingong, String.valueOf(getTeamJinGongByJinQiu(cvalue,oldValue,perjinqiucounts)));
		}
		/*
		 * 防守能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if (key_c_t_shiqiu.equals(collectKey)) {
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double oldshiqiucounts = getDouble(oldCollectsMap.get(collectKey));
			Double pershiqiucounts = oldshiqiucounts/counts;
			Double cvalue = getDouble(collectValue);
			Double oldValue = getDouble(judgeMap.get(key_j_t_fangshou));
			if ( null == oldValue  || oldValue==0) {
				oldValue = Default_fangshou;
			}
			judgeMap.put(key_j_t_fangshou, String.valueOf(getTeamFangShouByShiQiu(cvalue,oldValue,pershiqiucounts)));
		}
		
		/*
		 * 配合能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if (key_c_t_kongqiulv.equals(collectKey)) {
			Integer counts = countsMap.get(collectKey);
			if ( null == counts) {
				counts = 1;
			}
			Double oldpeihecounts = getDouble(oldCollectsMap.get(collectKey));
			Double perpeihecounts = oldpeihecounts/counts;
			Double cvalue = getDouble(collectValue);
			Double oldValue = getDouble(judgeMap.get(key_j_t_peihe));
			if ( null == oldValue || oldValue==0) {
				oldValue = Default_peihe;
			}
			judgeMap.put(key_j_t_peihe, String.valueOf(getTeamPeiHeByKongQiuLv(cvalue,oldValue,perpeihecounts)));
		}
	}
	
	private static Double getDouble(String value) {
		if (StringUtils.isEmpty(value)) {
			return 0.00;
		}
		try {
			return Double.parseDouble(value);
		}catch(Exception e) {
			return 0.00;
		}
	}
	
	/**
	 * 计算球队综合分数
	 * @param judgeMaps
	 * @return
	 */
	public static Double getTeamPoints(Map<String,String> judgeMaps) {
		if ( null == judgeMaps) {
			return 0.00;
		}
		String jingongstr = judgeMaps.get(key_j_t_jingong);
		Double jingong = null;
		if ( null == jingongstr) {
			jingong = Default_jingong;
		}else {
			jingong = getDouble(jingongstr);
		}
		String fangshoustr = judgeMaps.get(key_j_t_fangshou);
		Double fangshou = null;
		if ( null == fangshoustr) {
			fangshou = Default_fangshou;
		}else {
			fangshou = getDouble(fangshoustr);
		}
		String peihestr = judgeMaps.get(key_j_t_peihe);
		Double peihe = null;
		if ( null == peihestr) {
			peihe = Default_peihe;
		}else {
			peihe = getDouble(peihestr);
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
	 * 算法：每场比赛的结果跟之前的结果来对比，场均，进行加减分
	 * @param rst
	 * @param tt
	 * @param collectionMap
	 * @param collectionCountsMap
	 * @param judgeMap
	 */
//	public static void updateTeamTongjiBySchedule(RaceScheduleTeam rst,TongjiT tt,Map<String,String> collectionMap,
//			Map<String,Integer> collectionCountsMap,Map<String, String> judgeMap) {
//		if (rst.getUdefined()!=1) {
//			//设置数据收集项
//			if ( null != rst.getCollectItemsMap()) {
//				Map<String,String> citems= rst.getCollectItemsMap().get(tt.getTeamId());
//				if ( null != citems) {
//					//设置数据收集项
//					for (Iterator<String> it = citems.keySet().iterator();it.hasNext();) {
//						String itemId = it.next();
//						String svalue = citems.get(itemId);
//						Double value = getDouble(svalue);
//						Double oldValue = getDouble(collectionMap.get(itemId));
//						//先计算值
//						TongjiHelper.calculateTeamJudge(itemId, svalue, collectionMap, collectionCountsMap, judgeMap);
//						if ( null == oldValue) {
//							collectionMap.put(itemId, String.valueOf(value));
//						}else {
//							collectionMap.put(itemId, String.valueOf(oldValue+value));
//						}
//						Integer counts = collectionCountsMap.get(itemId);
//						if (null != counts ) {
//							collectionCountsMap.put(itemId, counts+1);
//						}else {
//							collectionCountsMap.put(itemId, 1);
//						}
//					}
//				}
//			}
//			if (!StringUtils.isEmpty(rst.getSuccessTeamId())) {
//				if (tt.getTeamId().equals(rst.getSuccessTeamId())) {
//					int wins = tt.getWins();
//					tt.setWins(wins+1);
//				}else if (rst.getSuccessTeamId().equals("0")) {
//					int evens = tt.getEven();
//					tt.setEven(evens+1);
//				}else {
//					int loses = tt.getLoses();
//					tt.setLoses(loses+1);
//				}
//			}
//			int counts = tt.getCounts();
//			tt.setCounts(counts+1);
//		}
//	}
	
	/**
	 * 通过一场赛程的结果来更新球队的统计数据
	 * @param rst
	 * @param tt
	 * @param collectionMap
	 * @param collectionCountsMap
	 * @param judgeMap
	 */
	public static void updateTeamTongjiSimpleBySchedule(RaceScheduleTeam rst,TongjiT tt,Map<String,String> collectionMap,
			Map<String,Integer> collectionCountsMap) {
		if (rst.getUdefined()!=1) {
			//设置数据收集项
			if ( null != rst.getCollectItemsMap()) {
				Map<String,String> citems= rst.getCollectItemsMap().get(tt.getTeamId());
				if ( null != citems) {
					//设置数据收集项
					for (Iterator<String> it = citems.keySet().iterator();it.hasNext();) {
						String itemId = it.next();
						String svalue = citems.get(itemId);
						Double value = getDouble(svalue);
						Double oldValue = getDouble(collectionMap.get(itemId));
						//先计算值
						//去掉此项 TongjiHelper.calculateTeamJudge(itemId, svalue, collectionMap, collectionCountsMap, judgeMap);
						if ( null == oldValue) {
							collectionMap.put(itemId, String.valueOf(value));
						}else {
							collectionMap.put(itemId, String.valueOf(oldValue+value));
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
	
	/**
	 * 计算球队进攻能力，防守能力，配合能力
	 * @param collectKey
	 * @param collectValue
	 * @param oldCollectsMap
	 * @param countsMap
	 * @param judgeMap
	 */
	public static void calculateTeamJudge(Map<String,String> collectsMap,Map<String,Integer> countsMap,Map<String,String> judgeMap) {
		if ( null == collectsMap || null == countsMap) {
			return ;
		}
		//进攻能力，总进球数/场数 *200
		String jinqiu = collectsMap.get(key_c_t_jinqiu);
		Integer counts = countsMap.get(key_c_t_jinqiu);
		if ( null == counts) {
			counts = 1;
		}
		if (!StringUtils.isEmpty(jinqiu)) {
			//场均进球
			
			Double jinqiucounts = getDouble(jinqiu) ;
			Double perjinqiucounts = jinqiucounts/counts;
			Double jingongnengli = (Default_jingong-200)+perjinqiucounts*80.9;
			if (jingongnengli>999) {
				jingongnengli = 999.0;
			}
			if (jingongnengli < 200) {
				jingongnengli = 200.0;
			}
			judgeMap.put(key_j_t_jingong, String.valueOf(jingongnengli));
		}else {
			judgeMap.put(key_j_t_jingong, String.valueOf(Default_jingong-200));
		}
		/*
		 * 防守能力，999-场均失球*80
		 */
		String fangshou = collectsMap.get(key_c_t_shiqiu);
		Integer fscounts = countsMap.get(key_c_t_shiqiu);
		if ( null == fscounts) {
			fscounts = 1;
		}
		if (!StringUtils.isEmpty(fangshou)) {
			Double oldshiqiucounts = getDouble(fangshou);
			Double pershiqiucounts = oldshiqiucounts/fscounts;
			Double fangshounengli = 999-pershiqiucounts*80;
			if (fangshounengli<200) {
				fangshounengli = 200.0;
			}
			judgeMap.put(key_j_t_fangshou, String.valueOf(fangshounengli));
		}else {
			judgeMap.put(key_j_t_fangshou, String.valueOf(Default_fangshou));
		}
		
		/*
		 * 配合能力，控球率*15
		 */
		String kongqiu = collectsMap.get(key_c_t_kongqiulv);
		Integer kqcounts = countsMap.get(key_c_t_kongqiulv);
		if ( null == kqcounts) {
			kqcounts = 1;
		}
		if (!StringUtils.isEmpty(kongqiu)) {
			Double oldpeihecounts = getDouble(kongqiu);
			Double perpeihecounts = oldpeihecounts/counts;
			Double peihenengli = perpeihecounts*15;
			if (peihenengli < 200) {
				peihenengli = 200.0;
			}
			judgeMap.put(key_j_t_peihe, String.valueOf(peihenengli));
		}else {
			judgeMap.put(key_j_t_peihe, String.valueOf(Default_peihe));
		}
	}
	
}
