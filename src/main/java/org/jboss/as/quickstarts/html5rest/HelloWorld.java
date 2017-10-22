/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.html5rest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled And notice the @PathParam which expects the URL to contain /json/David or /xml/Mary
 *
 * @author bsutter@redhat.com
 */

@Path("/")
public class HelloWorld {
    @Inject
    HelloService helloService;

    @Context
    private HttpServletRequest request;

    @POST
    @Path("/json/{name}")
    @Produces("application/json")
    public String getHelloWorldJSON(@PathParam("name") String name) {
        System.out.println("name: " + name);
        String previousName = "Unknown";
        try{
            //java.net.InetAddress ip = InetAddress.getLocalHost();
            String jbossNodeName = System.getProperty("jboss.node.name");
            String serverIpAndPort =  jbossNodeName + ":" + java.net.InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort();
            //HttpSession sess = request.getSession(true);
            //System.out.println("sessionid: " + sess.getId());
            //previousName = sess.getAttribute("USER")!=null?(String) sess.getAttribute("USER"):"Unknown";
            //System.out.println("previousName: " + previousName);
            //sess.setAttribute("USER", name);
            //return "{\"result\":\"" + helloService.createHelloMessage(name) + "\",\"server\":\"" + serverIpAndPort + "\",\"previousName\":\"" + previousName + "\"}";
            return "{\"result\":\"" + helloService.createHelloMessage(name) + "\",\"server\":\"" + serverIpAndPort + "\"}";
        } catch(java.net.UnknownHostException exp){
            return "{\"result\":\"" + helloService.createHelloMessage(name) + "\",\"server\":\"Unknown\"}";
        }
    }

    @POST
    @Path("/xml/{name}")
    @Produces("application/xml")
    public String getHelloWorldXML(@PathParam("name") String name) {
        System.out.println("name: " + name);
        try{
          return "<xml><result>" + helloService.createHelloMessage(name) + "</result><server>" + java.net.InetAddress.getLocalHost().getHostName() + "</server></xml>";
        } catch(java.net.UnknownHostException exp){
          return "<xml><result>" + helloService.createHelloMessage(name) + "</result><server>Unknown</server></xml>";
        }
    }

}
