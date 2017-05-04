/**
 *ElasticSearchService.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.service;

import java.util.List;

import com.spring.elasticsearch.model.Football;
import com.spring.elasticsearch.model.Search;

public interface HighlightService {
	public List<Football> getAllHighlights();
	
	public List<Football> getAllHighlights(int pageNum);

	public List<Football> getHighlights(Search theSearch);

	public void saveHighlight(Football theFootball);

	public Football getHighlight(String id);

	public void deleteHighlight(String id);
	
	public long getTotalNumberOfRecords();
}
