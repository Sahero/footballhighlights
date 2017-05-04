/**
*Football.java
*
*@author Sagar Shrestha 
**/
package com.spring.elasticsearch.model;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class Football {
	private String id;
	private String title;
	
	@NotBlank
	private String homeTeamName;
	
	@NotNull
	@Min(0)
	private int homeTeamScore;
	
	@NotBlank
	private String awayTeamName;
	
	@NotNull
	@Min(0)
	private int awayTeamScore;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
    @Past
	private Date fixtureDate;
	
	@NotBlank
	private String videoURL;
	
	@NotBlank
	private String videoSource;
	
	private String tags;
	
	private String categories;
	private String remarks;

	public Football(){}

	public Football(String id, String title, String homeTeamName, int homeTeamScore, String awayTeamName,
			int awayTeamScore, Date fixtureDate, String videoURL, String videoSource, String tags, String categories,
			String remarks) {
		super();
		this.id = id;
		this.title = title;
		this.homeTeamName = homeTeamName;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamName = awayTeamName;
		this.awayTeamScore = awayTeamScore;
		this.fixtureDate = fixtureDate;
		this.videoURL = videoURL;
		this.videoSource = videoSource;
		this.tags = tags;
		this.categories = categories;
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public Date getFixtureDate() {
		return fixtureDate;
	}

	public void setFixtureDate(Date fixtureDate) {
		this.fixtureDate = fixtureDate;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(String videoSource) {
		this.videoSource = videoSource;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Football [id=" + id + ", title=" + title + ", homeTeamName=" + homeTeamName + ", homeTeamScore="
				+ homeTeamScore + ", awayTeamName=" + awayTeamName + ", awayTeamScore=" + awayTeamScore
				+ ", fixtureDate=" + fixtureDate + ", videoURL=" + videoURL + ", videoSource=" + videoSource + ", tags="
				+ tags + ", categories=" + categories + ", remarks=" + remarks + "]";
	}
	
	

}
