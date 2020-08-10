package com.test.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.test.beans.ResponseHandler;
import com.test.dto.Request;
import com.test.dto.Response;

import io.swagger.annotations.Api;

/**
 * 
 * @author Assert Solutions S.A.S
 * @description Servicio de prueba 
 */
@Component
@Api(value = "Initial Proyect Camel-REST", description = "Estrucutura Basica Proyecto Rest Y Camel")
public class RestDslMainRoute extends RouteBuilder {

    @Value("${camel.component.servlet.mapping.context-path}")
    private String contextPath;

    @Autowired
    private Environment env;
    private Logger log = LoggerFactory.getLogger(RestDslMainRoute.class);

    @Override
    public void configure() throws Exception {
    // @formatter:off
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            .contextPath(contextPath.substring(0, contextPath.length() - 2))
            // turn on swagger api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title",  env.getProperty("api.title"))
            .apiProperty("api.version", env.getProperty("api.version"));
        
        rest("/example-openshift").description(env.getProperty("api.description"))
            .consumes("application/json")
            .produces("application/json")
        
         .get().description(env.getProperty("api.description.service")).outType(Response.class)
            .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
            .to("direct:update-user")
         .post().description(env.getProperty("api.description.service")).type(Request.class).description(
             env.getProperty("api.description.input.post")).outType(Response.class) 
             .responseMessage().code(200).message("All users successfully created").endResponseMessage()
             .to("direct:update-user")
         .put().description(env.getProperty("api.description.service")).type(Request.class).description(
             env.getProperty("api.description.input.post")).outType(Response.class) 
             .responseMessage().code(200).message("All users successfully created").endResponseMessage()
             .to("direct:update-user")
         .delete().description(env.getProperty("api.description.service")).type(Request.class).description(
             env.getProperty("api.description.input.post")).outType(Response.class) 
             .responseMessage().code(200).message("All users successfully created").endResponseMessage()
             .to("direct:update-user")
             ;
       
        from("direct:update-user")
            .bean(ResponseHandler.class)
            .log(LoggingLevel.INFO,log,"Body: ${body}")
            .setHeader("Content-Type", simple("application/json"))
            .setHeader("Accept", simple("application/json"))
            .log(LoggingLevel.INFO,log,"Response Body: ${body}");
        
        
        rest()
        	.tag("/get/user")
        	.description("Api que contiene los servicios para consulta de usuarios")
        	.get("/get")
        		.to("direct:listAllUsers")
        	.get("/get/{document}")
        		.param()
        			.dataType("string")
        			.name("document")
        			.type(RestParamType.path)
        		.endParam()
        		.to("direct:listUser");
        
        rest()
    		.tag("/post/user")
    		.description("Api que contiene los servicios para consulta de usuarios")
    		.post("/post")
    			.type(Request.class)
    			.to("direct:createUser");
        rest()
	        .tag("/update/user")
	        .description("Api que contiene los servicios para consulta de usuarios")
	        .put("/put/{document}")
	        	.type(Request.class)
		        .param()
					.dataType("string")
					.name("document")
					.type(RestParamType.path)
				.endParam()
				
				.to("direct:updateUser");
        rest()
	        .tag("/delete")
	        .description("Api que contiene los servicios para consulta de usuarios")
	        .delete("delete/{document}")
		        .param()
					.dataType("string")
					.name("document")
					.type(RestParamType.path)
				.endParam()
				.to("direct:deleteUser");
        
        from("direct:createUser")
        	.setProperty("serviceRest", simple("create"))
        	.process("userProcessor");
        from("direct:updateUser")
        	.setProperty("serviceRest", simple("update"))
        	.process("userProcessor");
        from("direct:deleteUser")
        	.setProperty("serviceRest", simple("delete"))
        	.process("userProcessor");
        from("direct:listUser")
        	.setProperty("serviceRest", simple("list"))
        	.process("userProcessor");
        from("direct:listAllUsers")
        	.setProperty("serviceRest", simple("listall"))
        	.process("userProcessor");
        // @formatter:on
    }

}
