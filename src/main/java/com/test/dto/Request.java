package com.test.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonAutoDetect
@JsonSerialize
@ApiModel(description = "Request de Ejemplo para servicios de prueba Openshift")
public class Request {
	
	@JsonProperty("id")
    @ApiModelProperty(dataType = "String")
    private String id;
	
    @JsonProperty("name")
    @ApiModelProperty(dataType = "String")
    private String name;
    
    @JsonProperty("duration")
    @ApiModelProperty(dataType = "String")
    private String duration;
    
    @JsonProperty("dateInit")
    @ApiModelProperty(dataType = "String")
    private String dateInit;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDateInit() {
		return dateInit;
	}

	public void setDateInit(String dateInit) {
		this.dateInit = dateInit;
	}

}
