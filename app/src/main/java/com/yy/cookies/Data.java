package com.yy.cookies;


import com.yy.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Data {
	public static List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
	public static Map<String, List<Map<String, Object>>> childs = new HashMap<String, List<Map<String, Object>>>();

	public List<Map<String, Object>> initGroups() {
		groups.clear();
		Map<String, Object> group = new HashMap<String, Object>();
		group.put("name", "神经系统");
		group.put("id", 1);
		group.put("iv", R.drawable.checkbox_unselect);
		groups.add(group);
		group = new HashMap<String, Object>();
		group.put("name", "内科");
		group.put("id", 2);
		group.put("iv", R.drawable.checkbox_unselect);
		groups.add(group);
		return groups;
	}

	public Map<String, List<Map<String, Object>>> initChilds() {
		childs.clear();
		List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 11);
		map.put("name", "听觉神经");
		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 12);
		map.put("name", "视觉神经");
		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 13);
		map.put("name", "嗅觉神经");
		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 14);
		map.put("name", "感觉神经");
		child.add(map);
		
		childs.put("1", child);
///////////////////////////////////////////////////////////////
		child = new ArrayList<Map<String, Object>>();
		map = new HashMap<String, Object>();
		map.put("id", 23);
		map.put("name", "胸科");
		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 24);
		map.put("name", "口腔科");
		child.add(map);
		map.put("id", 25);
		map.put("name", "神经科");
		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 26);
		map.put("name", "妇科");
		child.add(map);
		childs.put("2", child);
		return childs;
	}

}
