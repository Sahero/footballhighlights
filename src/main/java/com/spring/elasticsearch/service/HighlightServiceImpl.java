/**
*HighlightServiceImpl.java
*
*@author Sagar Shrestha 
**/
package com.spring.elasticsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.elasticsearch.DAO.ElasticSearchDAO;
import com.spring.elasticsearch.model.Football;
import com.spring.elasticsearch.model.Search;

@Service
public class HighlightServiceImpl implements HighlightService {
	
	@Autowired 
	private ElasticSearchDAO elasticSearchDAO;

	@Override
	@Transactional
	public List<Football> getAllHighlights() {
		return elasticSearchDAO.getAllHighlights();
	}

	@Override
	@Transactional
	public void saveHighlight(Football theFootball) {
		elasticSearchDAO.saveHighlight(theFootball);

	}

	@Override
	@Transactional
	public Football getHighlight(String id) {
		return elasticSearchDAO.getHighlight(id);
	}

	@Override
	@Transactional
	public void deleteHighlight(String id) {
		elasticSearchDAO.deleteHighlight(id);

	}

	@Override
	@Transactional
	public List<Football> getHighlights(Search theSearch) {
		return elasticSearchDAO.getHighlights(theSearch);
	}

	@Override
	@Transactional
	public List<Football> getAllHighlights(int pageNum) {
		return elasticSearchDAO.getAllHighlights(pageNum);
	}

	@Override
	@Transactional
	public long getTotalNumberOfRecords() {		
		return elasticSearchDAO.getTotalNumberOfRecords();
	}

}
