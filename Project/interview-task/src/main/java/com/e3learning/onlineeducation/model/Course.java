/**
 * 
 * @author abdelrahman
 * Date 06/03/2015
 * Course entity represents the Courses table in the database
 */

package com.e3learning.onlineeducation.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="courses")
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2887294283690413191L;



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(unique=true)
	@Size(min=2, max=255)
	private String title;
	
	@Size(max=1024)
	private String description;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade=CascadeType.ALL)
	private java.util.List<Training> training = new ArrayList<Training>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public java.util.List<Training> getTraining() {
		return training;
	}

	public void setTraining(java.util.List<Training> training) {
		this.training = training;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
