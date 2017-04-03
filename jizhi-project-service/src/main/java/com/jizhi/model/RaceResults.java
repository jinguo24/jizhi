package com.jizhi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 比赛结果，人员，分表
 * @author zhengfy1
 *
 */
public class RaceResults implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceId;//赛事编号
	private int raceScheduleTeamId;//赛程编号
	private int type=1;//类型
	private String teamId;//队编号
	private String phone;//队员电话
	private int number=0;//号码
	private String collectItems;//位置收集数据项json
	private Map<String,Map<String,String>> collectItemsMap;
	private String collectItemsLists;
	private String judgeItems;//位置评判数据项json
	private Map<String,Map<String,String>> judgeItemsMap;
	private String judgeItemsList;
	private String points;//位置综合分数
	private Map<String,String> pointsMap;
	private String pointsList;
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceScheduleTeamId() {
		return raceScheduleTeamId;
	}
	public void setRaceScheduleTeamId(int raceScheduleTeamId) {
		this.raceScheduleTeamId = raceScheduleTeamId;
		if (raceScheduleTeamId > 0 ) {
			this.tbinedex = raceScheduleTeamId%10;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap = (Map<String,Map<String,String>>) JSONArray.parse(collectItems);
			setcollectitemlist();
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<String,Map<String,String>>) JSONArray.parse(judgeItems);
			setjudgeitemslists();
		}
	}
	public Map<String,Map<String,String>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public void setCollectItemsMap(Map<String,Map<String,String>> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if ( null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
		setcollectitemlist();
	}
	private void setcollectitemlist() {
		if ( null != this.collectItemsMap) {
			List<PItems> items = new ArrayList<PItems>();
			for (Iterator<String> it = this.collectItemsMap.keySet().iterator(); it.hasNext();) {
				String position = it.next();
				PItems its = new PItems();
				its.setPosKey(position);
				List<ItemsValues> ivs = new ArrayList<ItemsValues>();
				its.setData(ivs);
				Map<String,String> vs = this.collectItemsMap.get(position);
				if ( null != vs ) {
					for (Iterator<String> vit = vs.keySet().iterator();vit.hasNext();) {
						String siid = vit.next();
						String iv = vs.get(siid);
						ItemsValues isv = new ItemsValues();
						isv.setKey(siid);
						isv.setValue(iv);
						ivs.add(isv);
					}
				}
				items.add(its);
			}
			this.collectItemsLists = JSONArray.toJSONString(items);
		}
	}
	public String getCollectItemsLists() {
		return collectItemsLists;
	}
	public void setCollectItemsLists(String collectItemsLists) {
		this.collectItemsLists = collectItemsLists;
		if (!StringUtils.isEmpty(collectItemsLists)) {
			List<JSONObject> items= (List<JSONObject>) JSONArray.parse(collectItemsLists);
			if ( null != items ) {
				Map<String,Map<String,String>> cmaps = new HashMap<String,Map<String,String>>();
				for (int i = 0 ; i < items.size() ; i ++) {
					JSONObject it = items.get(i);
					String position = (String)it.get("posKey");
					Map<String,String> vs = new HashMap<String,String>();
					cmaps.put(position, vs);
					JSONArray datas = (JSONArray) it.get("data");
					if (null != datas) {
						for (int j = 0 ; j < datas.size() ; j ++) {
							JSONObject iv = (JSONObject) datas.get(j);
							vs.put(iv.get("key").toString(), iv.get("value").toString());
						}
					}
				}
				this.collectItems = JSONObject.toJSONString(cmaps);
			}
		}
		
	}
	public Map<String,Map<String,String>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public void setJudgeItemsMap(Map<String,Map<String,String>> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
		setjudgeitemslists();
	}
	public String getJudgeItemsList() {
		return judgeItemsList;
	}
	public void setJudgeItemsList(String judgeItemsList) {
		this.judgeItemsList = judgeItemsList;
		if (!StringUtils.isEmpty(judgeItemsList)) {
			List<JSONObject> items= (List<JSONObject>) JSONArray.parse(judgeItemsList);
			if ( null != items ) {
				Map<String,Map<String,String>> cmaps = new HashMap<String,Map<String,String>>();
				for (int i = 0 ; i < items.size() ; i ++) {
					JSONObject it = items.get(i);
					String postion = (String)it.get("posKey");
					Map<String,String> vs = new HashMap<String,String>();
					cmaps.put(postion, vs);
					JSONArray datas = (JSONArray) it.get("data");
					if (null != datas) {
						for (int j = 0 ; j < datas.size() ; j ++) {
							JSONObject iv = (JSONObject) datas.get(j);
							vs.put(iv.get("key").toString(), iv.get("value").toString());
						}
					}
				}
				this.judgeItems = JSONObject.toJSONString(cmaps);
			}
		}
	}
	
	private void setjudgeitemslists() {
		if ( null != this.judgeItemsMap) {
			List<PItems> items = new ArrayList<PItems>();
			for (Iterator<String> it = this.judgeItemsMap.keySet().iterator(); it.hasNext();) {
				String position = it.next();
				PItems its = new PItems();
				its.setPosKey(position);
				List<ItemsValues> ivs = new ArrayList<ItemsValues>();
				its.setData(ivs);
				Map<String,String> vs = this.judgeItemsMap.get(position);
				if ( null != vs ) {
					for (Iterator<String> vit = vs.keySet().iterator();vit.hasNext();) {
						String siid = vit.next();
						String iv = vs.get(siid);
						ItemsValues isv = new ItemsValues();
						isv.setKey(siid);
						isv.setValue(iv);
						ivs.add(isv);
						ivs.add(isv);
					}
				}
				items.add(its);
			}
			this.judgeItemsList = JSONArray.toJSONString(items);
		}
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
		if (!StringUtils.isEmpty(points)) {
			pointsMap = (Map<String, String>) JSONObject.parse(points);
			setpointslists();
		}
	}
	public Map<String, String> getPointsMap() {
		return pointsMap;
	}
	public void setPointsMap(Map<String, String> pointsMap) {
		this.pointsMap = pointsMap;
		if ( null != pointsMap) {
			this.points = JSONObject.toJSONString(pointsMap);
		}
		setpointslists();
	}
	public String getPointsList() {
		return pointsList;
	}
	public void setPointsList(String pointsList) {
		this.pointsList = pointsList;
		if (!StringUtils.isEmpty(pointsList)) {
			List<JSONObject> items= (List<JSONObject>) JSONArray.parse(pointsList);
			if ( null != items ) {
				Map<String,String> cmaps = new HashMap<String,String>();
				for (int i = 0 ; i < items.size() ; i ++) {
					JSONObject it = items.get(i);
					String postion = (String)it.get("key");
					cmaps.put(postion, it.get("value").toString());
				}
				this.points = JSONObject.toJSONString(cmaps);
			}
		}
	}
	private void setpointslists() {
		if ( null != this.pointsMap) {
			List<ItemsValues> items = new ArrayList<ItemsValues>();
			for (Iterator<String> it = this.pointsMap.keySet().iterator(); it.hasNext();) {
				String position = it.next();
				ItemsValues iv = new ItemsValues();
				iv.setKey(position);
				iv.setValue(pointsMap.get(position));
				items.add(iv);
			}
			this.pointsList = JSONArray.toJSONString(items);
		}
	}
}
