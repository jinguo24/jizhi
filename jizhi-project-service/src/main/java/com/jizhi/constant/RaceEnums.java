package com.jizhi.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceEnums {

	
	public enum RaceTypes {

		ZUQIU(1,"足球"),LANQIU(2,"篮球"),YUMAOQIU(3,"羽毛球");
		
		private RaceTypes(int id, String name) {
			this.id = id;
			this.name = name;
		}
		private int id;
		private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public enum RacePositions {
		ZUQIU_QIANFENG("zuqiu_qianfeng","前锋",RaceTypes.ZUQIU.id,50.0),
		ZUQIU_ZHONGCHANG("zuqiu_zhongchang","中场",RaceTypes.ZUQIU.id,50.0),
		ZUQIU_HOUYAO("zuqiu_houyao","后腰",RaceTypes.ZUQIU.id,50.0),
		ZUQIU_HOUWEI("zuqiu_houwei","后卫",RaceTypes.ZUQIU.id,50.0),
		ZUQIU_SHOUMENYUAN("zuqiu_shoumenyuan","守门员",RaceTypes.ZUQIU.id,50.0);
		
		private RacePositions(String id, String name, int type,Double value) {
			this.id = id;
			this.name = name;
			this.type = type;
			this.defaultValue = value;
		}
		private String id;
		private String name;
		private int type;
		private Double defaultValue;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public Double getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(Double defaultValue) {
			this.defaultValue = defaultValue;
		}
		public static List<Map<String,String>> getPositions(int type) {
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for (RacePositions rp : RacePositions.values()) {
				if (rp.getType() == type) {
					Map<String,String> rps = new HashMap<String,String>();
					rps.put("id", rp.getId());
					rps.put("name", rp.getName());
					list.add(rps);
				}
			}
			return list;
		}
		public static Double getDValue(String id) {
			for (RacePositions rp : RacePositions.values()) {
				if (rp.getId().equals(id)) {
					return rp.getDefaultValue();
				}
			}
			return null;
		}
		
		
		
	}
	
}
