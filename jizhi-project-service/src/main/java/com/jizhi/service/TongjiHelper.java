package com.jizhi.service;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.jizhi.model.TongjiT;

public class TongjiHelper {

	
	public static void calculateTeamJudge(String collectKey,String collectValue,Map<String,Double> oldCollectsMap,int counts,Map<String,Double> judgeMap) {
		if (StringUtils.isEmpty(collectValue)) {
			return;
		}
		/**
		 * 根据进球数算出攻击能力，规则：这一场的进球数大于之前统计的场均进球数，则加上（多余进球数）*10, 如果比场均小，则减去(少于进球数)*5
		 */
		if ("t_jinqiu".equals(collectKey)) {
			if (null != oldCollectsMap && oldCollectsMap.containsKey(collectKey)) {
				Double oldvalue = judgeMap.get("t_jingong");
				//如果没有进攻能力值，则表示是第一场
				if ( null == oldvalue) {
					judgeMap.put("t_jingong", Math.floor(600+Double.parseDouble(collectValue)*10));
				}else {
					//场均进球
					Double oldjinqiucounts = oldCollectsMap.get(collectKey);
					Double perjinqiucounts = oldjinqiucounts/counts;
					Double cvalue = Double.parseDouble(collectValue);
					if (cvalue >= perjinqiucounts) {
						judgeMap.put("t_jingong", Math.floor(oldvalue+(cvalue-perjinqiucounts)*10));
					}else if (cvalue < perjinqiucounts)  {
						judgeMap.put("t_jingong", Math.floor(oldvalue-(perjinqiucounts-cvalue)*5));
					}
				}
			}
		}
		
		/*
		 * 防守能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if ("t_shiqiu".equals(collectKey)) {
			if (null != oldCollectsMap && oldCollectsMap.containsKey(collectKey)) {
				Double oldvalue = judgeMap.get("t_fangshou");
				//如果没有进攻能力值，则表示是第一场
				if ( null == oldvalue) {
					judgeMap.put("t_fangshou", Math.floor(600-Double.parseDouble(collectValue)*5));
				}else {
					//场均进球
					Double oldshiqiucounts = oldCollectsMap.get(collectKey);
					Double pershiqiucounts = oldshiqiucounts/counts;
					Double cvalue = Double.parseDouble(collectValue);
					if (cvalue >= pershiqiucounts) {
						judgeMap.put("t_fangshou", Math.floor(oldvalue-(cvalue-pershiqiucounts)*5));
					}else if (cvalue < pershiqiucounts)  {
						judgeMap.put("t_fangshou", Math.floor(oldvalue+(pershiqiucounts-cvalue)*5));
					}
				}
			}
		}
		
		/*
		 * 配合能力，规则跟攻击能力相反
		 * 规则：这一场的失球数大于之前统计的场均失球数，则减去（多余失球数）*5, 如果比场均小，则加上(少于失球数)*5
		 */
		if ("t_kongqiulv".equals(collectKey)) {
			if (null != oldCollectsMap && oldCollectsMap.containsKey(collectKey)) {
				Double oldvalue = judgeMap.get("t_peihe");
				//如果没有进攻能力值，则表示是第一场
				if ( null == oldvalue) {
					judgeMap.put("t_peihe", Math.floor(600+Double.parseDouble(collectValue)*10));
				}else {
					//场均进球
					Double oldpeihecounts = oldCollectsMap.get(collectKey);
					Double perpeihecounts = oldpeihecounts/counts;
					Double cvalue = Double.parseDouble(collectValue);
					if (cvalue >= perpeihecounts) {
						judgeMap.put("t_peihe", Math.floor(oldvalue+(cvalue-perpeihecounts)*10));
					}else if (cvalue < perpeihecounts)  {
						judgeMap.put("t_peihe", Math.floor(oldvalue-(perpeihecounts-cvalue)*5));
					}
				}
			}
		}
	}
	
	public static Double getTeamPoints(TongjiT tongjit) {
		Map<String,Double> judgeMaps = tongjit.getJudgeItemsMap();
		if ( null == judgeMaps) {
			return 0.00;
		}
		Double jingong = judgeMaps.get("t_jingong");
		Double fangshou = judgeMaps.get("t_fangshou");
		Double peihe = judgeMaps.get("t_peihe");
		return (jingong+fangshou+peihe)/3;
	}
	
}
