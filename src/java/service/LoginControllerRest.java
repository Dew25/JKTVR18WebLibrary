/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
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

/**
 *
 * @author jvm
 */
@Stateless
@Path("loginControllerRest")
public class LoginControllerRest{

  @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
  private EntityManager em;
  @Context private HttpServletRequest request;
  @EJB private UserFacade userFacade;
  
  @POST
  @Path("loginRest")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response loginRest(List<String> credentials) {
    String login = credentials.get(1);
    String password = credentials.get(2);
    String token = credentials.get(0);
    HttpSession session = request.getSession(false);
    if(session != null && token != null && token.equals(session.getId())){
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      jsonObjectBuilder.add("token",token);
      return Response.ok(jsonObjectBuilder.build().toString()).build();
    }
    login = login.trim();
    password = password.trim();

    if(null == login || "".equals(login) || null == password || "".equals(password)){
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    User user = userFacade.findByLogin(login);
    if(user == null){
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    EncryptPass ep = new EncryptPass();
    password = ep.setEncryptPass(password, user.getSalts());
    if(!password.equals(user.getPassword())){
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    session = request.getSession(true);
    session.setAttribute("user", user);
    token = session.getId();
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("token",token);
    JsonObject jsonObject = jsonObjectBuilder.build();
    String jsonToken = jsonObject.toString();
    return Response.ok(jsonToken).build();
        
    }
  
}
