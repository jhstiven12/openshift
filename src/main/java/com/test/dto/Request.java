package com.test.dto;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect
@JsonSerialize
@ApiModel(description = "Request DTO Object")
public class Request {
    
    @JsonProperty("name")
    @ApiModelProperty(dataType = "String")
    @Size(min = 10, max = 45)
    private String name;
    

    @JsonProperty("document")
    @ApiModelProperty(dataType = "String")
    @Size(min = 10, max = 45)
    private String document;


	


	public Request() {
		super();
	}

	public Request(String name, String document) {
		super();
		this.name = name;
		this.document = document;
	}
	


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDocument() {
		return document;
	}


	public void setDocument(String document) {
		this.document = document;
	}
    
    
}
