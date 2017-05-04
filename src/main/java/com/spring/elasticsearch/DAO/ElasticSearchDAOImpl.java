/**
 *ElasticSearchDAOImpl.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.DAO;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.all.AllField;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.spring.elasticsearch.config.AppConfig;
import com.spring.elasticsearch.config.BeanConfig;
import com.spring.elasticsearch.model.Football;
import com.spring.elasticsearch.model.Search;

//@PropertySource("classpath:application.properties")
//@Repository
//@WebAppConfiguration
public class ElasticSearchDAOImpl implements ElasticSearchDAO{

	private TransportClient transportClient;
	private String index;
	private String type;
	private String id;
	private String host1;
	private String host2;
	private int port;
	private int numVideos;
	private int start;
	private int end;
	private long totalNumberOfRecords;
	
	//number.of.videos.in.a.page

	@Autowired
	private Environment environment;

	public ElasticSearchDAOImpl() {

	}


	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost1() {
		return host1;
	}

	public void setHost1(String host1) {
		this.host1 = host1;
	}

	public String getHost2() {
		return host2;
	}

	public void setHost2(String host2) {
		this.host2 = host2;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public int getNumVideos() {
		return numVideos;
	}


	public void setNumVideos(int numVideos) {
		this.numVideos = numVideos;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void init(){
		setIndex(environment.getProperty("elasticsearch.index"));
		setType(environment.getProperty("elasticsearch.type"));
		setHost1(environment.getProperty("elasticsearch.host1"));
		setHost2(environment.getProperty("elasticsearch.host2"));
		setPort(Integer.parseInt(environment.getProperty("elasticsearch.port")));
		setNumVideos(Integer.parseInt(environment.getProperty("number.of.videos.in.a.page")));
	}

	@SuppressWarnings("resource")
	public void connect() {
		init();
		//System.out.println(environment.getProperty("elasticsearch.host1"));

		try {
			transportClient = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(getHost1()), getPort()))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(getHost2()), getPort()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		


	}

	public void disconnect() {
		transportClient.close();
	}

	public List<Football> getAllHighlights(){
		setId(null);
		connect();
		//LocalDateTime currentTime = LocalDateTime.now();
		//System.out.println("Current DateTime: " + currentTime);
		SearchResponse response = transportClient.prepareSearch(getIndex())//, "index")
				.setTypes(getType())//, "type")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				//.setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
				//.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
				//.setFrom(start).setSize(end).setExplain(true)
				.addSort("fixtureDate", SortOrder.DESC)
				.get();
		setTotalNumberOfRecords(response.getHits().getTotalHits());
		//System.out.println(response.toString());
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		//System.out.println(searchHits.size());
		List<Football> results = new ArrayList<Football>();


		//System.out.println(searchHits.toString());
		searchHits.forEach( hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Football.class)));


		for(int i=0;i<results.size();i++){
			results.get(i).setId(searchHits.get(i).getId());
			results.get(i).setTitle(results.get(i).getHomeTeamName()+" VS "+results.get(i).getAwayTeamName()+" "
					+ results.get(i).getHomeTeamScore()+"-"+results.get(i).getAwayTeamScore());
			results.get(i).setTags(results.get(i).getTags().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			results.get(i).setCategories(results.get(i).getCategories().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		}
		//currentTime = LocalDateTime.now();
		//System.out.println("Current DateTime2: " + currentTime);
		disconnect();
		return results;
	}



	@Override
	public List<Football> getHighlights(Search theSearch) {
		setId(null);
		connect();
		//LocalDateTime currentTime = LocalDateTime.now();
		//System.out.println("Current DateTime: " + currentTime);
		SearchResponse response = transportClient.prepareSearch(getIndex())//, "index")
				.setTypes(getType())//, "type")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.queryStringQuery(theSearch.getUsingAll()))
				//.setQuery(QueryBuilders.multiMatchQuery(theSearch.getUsingAll(),"homeTeamName"))
				//.setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
				//.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
				//.setFrom(start).setSize(end).setExplain(true)
				.addSort("fixtureDate", SortOrder.DESC)
				.get();
		setTotalNumberOfRecords(response.getHits().getTotalHits());
		//System.out.println(response.toString());
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		//System.out.println(searchHits.size());
		List<Football> results = new ArrayList<Football>();


		//System.out.println(searchHits.toString());
		searchHits.forEach( hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Football.class)));


		for(int i=0;i<results.size();i++){
			results.get(i).setId(searchHits.get(i).getId());
			results.get(i).setTitle(results.get(i).getHomeTeamName()+" VS "+results.get(i).getAwayTeamName()+" "
					+ results.get(i).getHomeTeamScore()+"-"+results.get(i).getAwayTeamScore());
			results.get(i).setTags(results.get(i).getTags().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			results.get(i).setCategories(results.get(i).getCategories().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		}
		//currentTime = LocalDateTime.now();
		//System.out.println("Current DateTime2: " + currentTime);
		disconnect();
		return results;
	}
	
	public List<Football> getAllHighlights(int pageNum){
		setId(null);
		connect();
		int end = (pageNum * getNumVideos());
		int start = (end - getNumVideos())  + 1 ;
		 ;
		//System.out.println(start + " --"+end + "--"+pageNum);
	
		SearchResponse response = transportClient.prepareSearch(getIndex())//, "index")
				.setTypes(getType())//, "type")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				//.setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
				//.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
				.setFrom(start).setSize(end)
				.addSort("fixtureDate", SortOrder.DESC)
				.get();
		//System.out.println(response.getHits().getTotalHits());
		//System.out.println(response.getHits().getHits());
		setTotalNumberOfRecords(response.getHits().getTotalHits());
		
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		//System.out.println(searchHits.size());
		List<Football> results = new ArrayList<Football>();


		//System.out.println(searchHits.toString());
		searchHits.forEach( hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Football.class)));


		for(int i=0;i<results.size();i++){
			results.get(i).setId(searchHits.get(i).getId());
			results.get(i).setTitle(results.get(i).getHomeTeamName()+" VS "+results.get(i).getAwayTeamName()+" "
					+ results.get(i).getHomeTeamScore()+"-"+results.get(i).getAwayTeamScore());
			results.get(i).setTags(results.get(i).getTags().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			results.get(i).setCategories(results.get(i).getCategories().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		}
		//currentTime = LocalDateTime.now();
		//System.out.println("Current DateTime2: " + currentTime);
		disconnect();
		return results;
	}

	@Override
	public void saveHighlight(Football theFootball) {
		Map<String, Object> jsonHighlight = new HashMap<String, Object>();
		jsonHighlight.put("homeTeamName",theFootball.getHomeTeamName());
		jsonHighlight.put("homeTeamScore",theFootball.getHomeTeamScore());
		jsonHighlight.put("awayTeamName",theFootball.getAwayTeamName());
		jsonHighlight.put("awayTeamScore",theFootball.getAwayTeamScore());
		jsonHighlight.put("fixtureDate",theFootball.getFixtureDate());
		jsonHighlight.put("videoURL",theFootball.getVideoURL());
		jsonHighlight.put("videoSource",theFootball.getVideoSource());
		//jsonHighlight.put("tags",{theFootball.getTags().split(",")});
		String a[] = {theFootball.getHomeTeamName(),theFootball.getAwayTeamName()};
		jsonHighlight.put("tags",a);
		jsonHighlight.put("categories",theFootball.getCategories().split(","));
		jsonHighlight.put("remarks",theFootball.getRemarks());

		connect();
		IndexResponse response = null;
		
		if(getId()==null){
			response = transportClient.prepareIndex(getIndex(), getType())
					.setSource(jsonHighlight)
					.get();
		}
		else{
			response = transportClient.prepareIndex(getIndex(), getType(),getId())
					.setSource(jsonHighlight)
					.get();	
		}
		disconnect();
	}

	@Override
	public Football getHighlight(String id) {
		connect();
		setId(id);
		GetResponse response = transportClient.prepareGet(getIndex(),getType(),id).get();
		//System.out.println(id + "--"+response.getSourceAsString());
		Football theFootball = JSON.parseObject(response.getSourceAsString(),Football.class);
		theFootball.setTags(theFootball.getTags().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		theFootball.setCategories(theFootball.getCategories().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
		disconnect();
		return theFootball;
	}

	@Override
	public void deleteHighlight(String id) {
		connect();
		setId(id);
		DeleteResponse response = transportClient.prepareDelete(getIndex(),getType(),id).get();
		System.out.println(response.toString());
		disconnect();
	}

	public static void main(String args[]){

		ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

		ElasticSearchDAO elasticSearchDAO = (ElasticSearchDAO) context.getBean("elasticSearchDAO");
		List<Football> football=(List<Football>) elasticSearchDAO.getAllHighlights(2);
		
		for(int i=0;i<football.size();i++){
			System.out.println(football.get(i).getId());
			System.out.println(football.get(i).getTitle());
			System.out.println(football.get(i).getFixtureDate());
			System.out.println(football.get(i).getTags());
		}
		/*final CountResponse response = _client.prepareCount(INDEX)
	            .execute()
	            .actionGet();*/
System.out.println(10/10);

	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getEnd() {
		return end;
	}


	public void setEnd(int end) {
		this.end = end;
	}


	@Override
	public long getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}


	public void setTotalNumberOfRecords(long totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}



}
