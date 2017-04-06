package com.jizhi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class TongjiHelper {

	private static final double Default_jingong = 600.00;
	private static final double Default_fangshou = 600.00;
	private static final double Default_peihe = 600.00;
	
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
		if ("t_jinqiu".equals(collectKey)) {
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
			Double oldValue = judgeMap.get("t_jingong");
			if ( null == oldValue) {
				oldValue = Default_jingong;
			}
			judgeMap.put("t_jingong", getTeamJinGongByJinQiu(cvalue,oldValue,perjinqiucounts));
		}
		/*
		 * 防守能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if ("t_shiqiu".equals(collectKey)) {
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
			Double oldValue = judgeMap.get("t_fangshou");
			if ( null == oldValue) {
				oldValue = Default_fangshou;
			}
			judgeMap.put("t_fangshou", getTeamFangShouByShiQiu(cvalue,oldValue,pershiqiucounts));
		}
		
		/*
		 * 配合能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if ("t_kongqiulv".equals(collectKey)) {
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
			Double oldValue = judgeMap.get("t_peihe");
			if ( null == oldValue) {
				oldValue = Default_peihe;
			}
			judgeMap.put("t_peihe", getTeamPeiHeByKongQiuLv(cvalue,oldValue,perpeihecounts));
		}
	}
	
	public static Double getTeamPoints(Map<String,Double> judgeMaps) {
		if ( null == judgeMaps) {
			return 0.00;
		}
		Double jingong = judgeMaps.get("t_jingong");
		if ( null == jingong) {
			jingong = Default_jingong;
		}
		Double fangshou = judgeMaps.get("t_fangshou");
		if ( null == fangshou) {
			fangshou = Default_fangshou;
		}
		Double peihe = judgeMaps.get("t_peihe");
		if ( null == peihe) {
			peihe = Default_peihe;
		}
		return (jingong+fangshou+peihe)/3;
	}
	
}
