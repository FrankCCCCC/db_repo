package org.elasql.cache.tpart;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasql.cache.CachedRecord;
import org.elasql.sql.PrimaryKey;

public class CacheRead {
    private final Object anchors[] = new Object[1009];
    private static Map<PrimaryKey, CachedRecord> recordCache = new ConcurrentHashMap<PrimaryKey, CachedRecord>();
	private int counter = 0;
	private int cacheMiss = 0;
    
    private Object prepareAnchor(Object o) {
		int hash = o.hashCode() % anchors.length;
		if (hash < 0) {
			hash += anchors.length;
		}
		return anchors[hash];
	}

    CacheRead(){
        // recordCache = new HashMap<PrimaryKey, CachedRecord>();
        // System.out.println("CacheRead");
    }

    /***
     * 
     * @param key: 
     * @return rec: Return 
     */
    public CachedRecord readCache(PrimaryKey key){
		CachedRecord rec = null;
		rec = recordCache.get(key);
        
        if(rec == null){
            cacheMiss += 1;
        }
        counter += 1;
		if(counter % 10000 == 0){
            System.out.println("Cache Miss: " + cacheMiss + " / " + counter + "("+ recordCache.size() +")");   
        }
        
		return rec;
    }

    public void addCache(PrimaryKey key, CachedRecord rec){
        recordCache.putIfAbsent(key, rec);
    }
}
