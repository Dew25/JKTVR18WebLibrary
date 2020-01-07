/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserFacade;
import util.EncryptPass;
import util.RoleManager;

/**
 *
 * @author Melnikov
 */
@Stateless
@Path("loginControllerREST")
public class LoginControllerREST {
  @EJB private UserFacade userFacade;
  @Context private HttpServletRequest request;
  
  @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
  private EntityManager em;

  @POST
  @Path("loginRest")
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response loginRest(List<String> credentials) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    String login = credentials.get(1);
    String password = credentials.get(2);
    String token = credentials.get(0);
    HttpSession session = request.getSession(false);
    if(session != null && token != null && token.equals(session.getId())){
      jsonObjectBuilder.add("token",token);
      return Response.ok(jsonObjectBuilder.build().toString()).build();
    }
    login = login.trim();
    password = password.trim();
    if(null == login || "".equals(login) || null == password || "".equals(password)){
        jsonObjectBuilder.add("token", false);
        return Response.ok(jsonObjectBuilder.build().toString()).build();
    }
    User user = userFacade.findByLogin(login);
    if(user == null){
        jsonObjectBuilder.add("token", false);
        return Response.ok(jsonObjectBuilder.build().toString()).build();
    }
    EncryptPass ep = new EncryptPass();
    password = ep.setEncryptPass(password, user.getSalts());
    if(!password.equals(user.getPassword())){
        jsonObjectBuilder.add("token", false);
        return Response.ok(jsonObjectBuilder.build().toString()).build();
    }
    session = request.getSession(true);
    session.setAttribute("user", user);
    token = session.getId();
    jsonObjectBuilder.add("token", token);
    return Response.ok(jsonObjectBuilder.build().toString()).build();
  } 

  
}
