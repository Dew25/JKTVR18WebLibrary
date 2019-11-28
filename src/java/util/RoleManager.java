/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Roles;
import entity.User;
import entity.UserRoles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.RolesFacade;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
public class RoleManager {
    private RolesFacade rolesFacade;
    private UserRolesFacade userRolesFacade;
    private Roles roleAdmin;
    private Roles roleManager;
    private Roles roleUser;

    public RoleManager() {
        Context context;
        try {
            context = new InitialContext();
            rolesFacade = (RolesFacade) context.lookup("java:module/RolesFacade");
            userRolesFacade = (UserRolesFacade) context.lookup("java:module/UserRolesFacade");
            roleAdmin = rolesFacade.findRoleByName("ADMIN");
            roleManager = rolesFacade.findRoleByName("MANAGER");
            roleUser = rolesFacade.findRoleByName("USER");
        } catch (NamingException ex) {
            Logger.getLogger(RoleManager.class.getName()).log(Level.SEVERE, "Не найден бин", ex);
        }
        
    }
    public boolean isRoleUser(String roleName,User user){
        roleName = roleName.toUpperCase();
        Roles role = rolesFacade.findRoleByName(roleName);
        List<Roles> listRoles = userRolesFacade.findByUser(user);
        return listRoles.contains(role);
    }

    public String getTopRole(User user) {
        try {
            List<Roles> listRoles = userRolesFacade.findByUser(user);
            if(listRoles.contains(roleAdmin)){
                return "ADMIN";
            }
            if(listRoles.contains(roleManager)){
                return "MANAGER";
            }
            if(listRoles.contains(roleUser)){
                return "USER";
            }
            return null;
        } catch (Exception e) {
           return null; 
        }
    }
    public void setRoleUser(Roles role,User user){
       userRolesFacade.remove(user);
       UserRoles ur = new UserRoles();
       ur.setUser(user);
       if(role.equals(roleAdmin)){
           ur.setRole(roleAdmin);
           userRolesFacade.create(ur);
           ur.setRole(roleManager);
           userRolesFacade.create(ur);
           ur.setRole(roleUser);
           userRolesFacade.create(ur);
       }
       if(role.equals(roleManager)){
           ur.setRole(roleManager);
           userRolesFacade.create(ur);
           ur.setRole(roleUser);
           userRolesFacade.create(ur);
       }
       if(role.equals(roleUser)){
           ur.setRole(roleUser);
           userRolesFacade.create(ur);
       }
    }
        
    
}
