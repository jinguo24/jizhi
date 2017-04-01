package com.jizhi.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChouQianCache {

	private ChouQianCache() {
		
	}
	
	private static final ChouQianCache instance = new ChouQianCache();
	
	public static ChouQianCache getInstance() {
		return instance;
	}
	
	private static final String[] labes = new String[]{"A","B","C","D","E","F","G","H","I","J","K"};
	private String[] raceLabes = null;
	public void init(int lableGroupCounts,int teamCounts,Map<String,Integer> existsLabelCounts) {
		if (null == existsLabelCounts) {
			existsLabelCounts = new HashMap<String,Integer>();
		}
		List<String> labelList = new ArrayList<String>();
		int maxSize = teamCounts/lableGroupCounts;
		for (int i = 0 ; i < lableGroupCounts; i++) {
			Integer c = existsLabelCounts.get(labes[i]);
			if ( null == c || c == 0 ) {
				for (int j = 0 ; j < maxSize; j ++) {
					labelList.add(labes[i]);
				}
			}else if(c < maxSize ){
				for (int j = c ; j < maxSize; j ++) {
					labelList.add(labes[i]);
				}
			}
		}
		raceLabes = new String[labelList.size()];
		for (int i = 0 ; i < labelList.size() ; i ++) {
			raceLabes[i] = labelList.get(i);
		}
	}
	
	public String getLable() {
		synchronized(instance) {
			int randomNumber=(int)(Math.random()*raceLabes.length);
			while (raceLabes[randomNumber] == null) {
				randomNumber=(int)(Math.random()*raceLabes.length);
			}
			String label = raceLabes[randomNumber];
			raceLabes[randomNumber] = null;
			return label;
		}
	}
	
	public static void main(String[] args) {
		Map<String,Integer> c = new HashMap<String,Integer>();
		c.put("A", 2);
		c.put("B", 3);
		ChouQianCache.getInstance().init(2, 8, c);
		String[] ss = ChouQianCache.getInstance().raceLabes;
		for (String s : ss) {
			System.err.println(s);
		}
	}
}
