package com.github.oogasawa.utility.collections;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UniquedList {
	
	ArrayList<String> entity = new ArrayList<String>();

	public void add(String s) {
		if (!has(s))
			entity.add(s);
	}
	
	public void addAll(ArrayList<String> list) {
		for (String s : list) {
			add(s);
		}
	}
	
	public boolean has(String s) {
		for (String item : entity) {
			if (item.equals(s))
				return true;
		}
		return false;
	}


	public String toString() {
		return entity.stream()
			.collect(Collectors.joining("\n"));
	}
}
