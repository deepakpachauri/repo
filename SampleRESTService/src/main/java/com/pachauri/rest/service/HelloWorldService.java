package com.pachauri.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {
	
	@GET
	public Response getMessage(){
		return Response.status(200).entity("Hello Jersey").build();
	}

}
