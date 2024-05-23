package com.github.oogasawa.utility.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

//import com.github.oogasawa.utility.files.FileIO;

public class DuplicableKeyMap<K,V> implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5657223371908240636L;
	TreeMap<K,List<V>> entity = new TreeMap<K, List<V>>();

	
	/** Returns a list of values corresponding to keys.
	 * 
	 *  @param key A key.
	 *  @return A list of values corresponding to the key. If the key does not exist, return null. 
	 */
	public List<V> getValueList(K key) {
		return entity.get(key);
	}


	/** Returns one value corresponding to key.
	 *  <p>
	 *  If multiple values correspond to a key, return any one of the corresponding values.
	 *  It is not specified which value is returned.
	 *  </p>
	 *  
	 *  @param key A key.
	 *  @return A value corresponding to the key. 
	 *  @exception RuntimeException If the key does not exist.
	 * 
	 */
	public V get(K key) {
		V value = null;
		try {
			value = entity.get(key).get(0);
		}
		catch (Exception e) {
			throw new RuntimeException("Key not found: " + key);
		}
		return value;
	}

	
	public void put(K key, V value) {
		
		if (entity.containsKey(key)) {
			entity.get(key).add(value);
		}
		else {
			ArrayList<V> valList = new ArrayList<V>();
			valList.add(value);
			entity.put(key, valList);
		}
		
	}

	/** Puts key-value pairs in the map, however, if the exact same key-value pair is already in the map, it is not put.
	 *
	 * <h4>Boundary condition</h4>
	 * <ul>
	 * <li>If key is null, it throws a NullPointerException.</li>
	 * <li>If value is null, it throws a NullPointerException.</li>
	 * </ul>
	 * 
	 * @param key A key.
	 * @param value A value.
	 * @exception NullPointerException If key or value is null.
	 */
	public void putUniquePair(K key, V value) {

		if (key == null) {
			throw new NullPointerException("Key is null.");
		}
		if (value == null) {
			throw new NullPointerException("Value is null.");
		}
		
		if (entity.containsKey(key)) {
			List<V> valList = entity.get(key);
			if (!valList.contains(value)) {
				valList.add(value);
			}
		}
		else {
			ArrayList<V> valList = new ArrayList<V>();
			valList.add(value);
			entity.put(key, valList);
		}
		
	}


	
	public Set<Map.Entry<K, V>> entrySet() {
		return entity.entrySet().stream()
				.flatMap((Map.Entry<K, List<V>> entry) -> entry.getValue().stream()
						.map((V value) -> new AbstractMap.SimpleEntry<K, V>(entry.getKey(), value)))
				.collect(Collectors.toSet());

	}

	// public void save(String filename, String delim) throws IOException {
	// 	PrintWriter pw = FileIO.getPrintWriter(filename);

	// 	Set<K> keys = entity.keySet();
	// 	for (K k : keys) {
	// 		String str = ListUtil.join(delim, entity.get(k));
	// 		pw.println(k + "\t" + str);
	// 	}
	// 	pw.close();
	// }


	// public void save(String filename) throws IOException {
	// 	PrintWriter pw = FileIO.getPrintWriter(filename);

	// 	Set<K> keys = entity.keySet();
	// 	for (K k : keys) {
	// 		for (V v : entity.get(k)) {
	// 			pw.println(k + "\t" + v);				
	// 		}
	// 	}		
	// 	pw.close();
	// }


	public void clear() {
		entity.clear();
	}

	public void close() {
		entity.clear();
	}

	
	public boolean containsKey(K key) {
		return entity.containsKey(key);
	}


	@SuppressWarnings("unchecked")
	public boolean containsValue(V val) {
		Set<?> s = keySet();
		Iterator<?> sIter = s.iterator();
		K key = null;
		List<V> valList = null;
		while (sIter.hasNext()) {
			key = (K) sIter.next();
			valList = entity.get(key);
			for (int i=0; i<valList.size(); i++) {
				if (valList.get(i).equals(val))
					return true;
			}
		}
		return false;
	}



	public boolean isEmpty() {
		return entity.isEmpty();
	}

	public Set<?> keySet() {
		return entity.keySet();
	}

	public Object remove(K key) {
		return entity.remove(key);
	}

	public int size() {
		return entity.size();
	}

/*
	@SuppressWarnings("unchecked")
	public int numOfValueColumns() {
		Set<?> ks = keySet();
		Iterator<?> iter = ks.iterator();
		K        key  = (K) iter.next();
		String   val  = entity.get(key).get(0).toString();
		ArrayList<String> cols = StringUtil.splitByTab(val);
		
		return cols.size();
	}
*/
	
	public void print() {
		Set<K> keys = entity.keySet();
		for (K k : keys) {
			for (V v : entity.get(k)) {
				System.out.println(k + "\t" + v);				
			}
		}		
	}
	/*
	public <V> Collection<V> values() {
		ArrayList<V> valList = new ArrayList<V>();
		Iterator<K>  iter    = keySet().iterator();
		K key = null;
		while (iter.hasNext()) {
			key = iter.next();
			valList.addAll(entity.get(key));
		}
		return valList;
	}
	*/
	

}
