package com.pachauri.rest.service;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class HelloWorldServiceTest extends JerseyTest {

	public HelloWorldServiceTest() {
		super("com.pachauri.rest");
	}
	
	@Test
    public void test() {
		WebResource webResource = resource();
        String responseMsg = webResource.path("hello").get(String.class);
        Assert.assertEquals("Hello Jersey", responseMsg);
    }

}
