/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.faces.util.Json;
import entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserFacade;
import util.EncryptPass;
import util.RoleManager;

/**
 *
 * @author jvm
 */
@Stateless
@Path("loginController")

public class LoginControllerREST extends AbstractFacade<User> {
    @EJB UserFacade userFacade;
    @Context HttpServletRequest request;
    EncryptPass ep = new EncryptPass();
    RoleManager roleManager = new RoleManager();
    @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
    private EntityManager em;

    public LoginControllerREST() {
        super(User.class);
    }
    @POST
   @Path("loginRest")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response login(List<String> credentials) {
        String login = credentials.get(0);
        String password = credentials.get(1);
        String token = credentials.get(2);
        if(token.equals(request.getSession().getId())){
            return Response.ok(Json.encode(token)).build();
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
                
        password = ep.setEncryptPass(password, user.getSalts());
        if(!password.equals(user.getPassword())){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        token = session.getId();
        token = Json.encode(token);
        return Response.ok(token).build();
        
    }
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
