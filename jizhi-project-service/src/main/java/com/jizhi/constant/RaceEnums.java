package com.jizhi.constant;

import java.util.ArrayList;
import java.util.List;

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
		ZUQIU_QIANFENG("zuqiu_qianfeng","前锋",RaceTypes.ZUQIU.id);
		
		private RacePositions(String id, String name, int type) {
			this.id = id;
			this.name = name;
			this.type = type;
		}
		private String id;
		private String name;
		private int type;
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
		public static List<RacePositions> getPositions(int type) {
			List<RacePositions> rps = new ArrayList<RacePositions>();
			for (RacePositions rp : RacePositions.values()) {
				if (rp.getType() == type) {
					rps.add(rp);
				}
			}
			return rps;
		}
		
		
	}
	
}
