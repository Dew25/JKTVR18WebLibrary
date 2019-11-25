/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Image;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import session.ImageFacade;
import util.PropertiesLoader;
import util.RoleManager;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "UploadController", urlPatterns = {"/uploadController"})
@MultipartConfig
public class UploadController extends HttpServlet {
    @EJB private ImageFacade imageFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        RoleManager rm = new RoleManager();
        if(null == session){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin.jsp")
                    .forward(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        if(null == user){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin.jsp")
                    .forward(request, response);
            return;
        }
        if(!rm.isRoleUser("MANAGER", user)){
            request.setAttribute("info", "У вас нет прав!");
            request.getRequestDispatcher("/showLogin.jsp")
                    .forward(request, response);
            return;
        }
       String description = request.getParameter("description");
       if(null == description || "".equals(description)){
            request.setAttribute("info", "Заполните поле с описанием изображения");
            request.getRequestDispatcher("/showUploadFile")
                    .forward(request, response);
       }
       String imagesFolder = PropertiesLoader.getFolderPath("path");
       new File(imagesFolder).mkdirs();
       List<Part> fileParts = request.getParts()
                .stream()
                .filter( part -> "file".equals(part.getName()))
                .collect(Collectors.toList());
       
       for(Part filePart : fileParts){
            String path =  imagesFolder+File.separatorChar
                            +getFileName(filePart);
            File uploadFile = new File(path);
            try(InputStream fileContent = filePart.getInputStream()){
               Files.copy(
                       fileContent,uploadFile.toPath(), 
                       StandardCopyOption.REPLACE_EXISTING
               );
               
            }
            
            Image image = new Image(description,getFileName(filePart));
            imageFacade.create(image);
            
        }    
       request.getRequestDispatcher("/newBook").forward(request, response);
       
    }
   
    private String getFileName(final Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part
                .getHeader("content-disposition")
                .split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
