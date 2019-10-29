/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.EncryptPass;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "LoginController", urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/newReader",
    "/addReader",
    "/showListAllBooks",
    
})
public class LoginController extends HttpServlet {
    @EJB private UserFacade userFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private BookFacade bookFacade;
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
        EncryptPass ep = new EncryptPass();
        HttpSession session = null;
        String path = request.getServletPath();
        switch (path) {
            case "/showLogin":
                request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                login = login.trim();
                password = password.trim();
                if(null == login || "".equals(login) || null == password || "".equals(password)){
                    request.setAttribute("info", "Заполните все поля");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                }
                User user = userFacade.findByLogin(login);
                if(user == null){
                    request.setAttribute("info", "Неправильные данные");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                }
                
                password = ep.setEncryptPass(password, user.getSalts());
                if(!password.equals(user.getPassword())){
                    request.setAttribute("info", "Неправильные данные");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                }
                session = request.getSession(true);
                session.setAttribute("user", user);
                request.setAttribute("info", "Пользователь " + user.getLogin() + " aвторизован");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/logout":
                session = request.getSession(false);
                if(session != null){
                    session.invalidate();
                    request.setAttribute("info", "Вы вышли из системы");
                }
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/newReader":
                request.getRequestDispatcher("/WEB-INF/newReader.jsp")
                        .forward(request, response);
                break;
            case "/addReader":
                String name = request.getParameter("name");
                String lastname = request.getParameter("lastname");
                String day = request.getParameter("day");
                String month = request.getParameter("month");
                String year = request.getParameter("year");
                login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                request.setAttribute("name", name);
                request.setAttribute("lastname", lastname);
                request.setAttribute("day", day);
                request.setAttribute("month", month);
                request.setAttribute("year", year);
                request.setAttribute("login", login);
                
                if(!password1.equals(password2)){
                    request.setAttribute("info", "Несовпадают пароли");
                    request.getRequestDispatcher("/newReader")
                        .forward(request, response);
                    break;
                }
                Reader reader=null;
                try{
                    reader = new Reader(null,name, lastname, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
                    readerFacade.create(reader);
                    ep = new EncryptPass();
                    String salts = ep.createSalts();
                    String encryptPassword = ep.setEncryptPass(password1, salts);
                    user = new User(login,encryptPassword,salts,reader);
                    try {
                        userFacade.create(user);
                    } catch (Exception e) {
                        readerFacade.remove(reader);
                        request.setAttribute("info", "Такой пользователь уже существует");
                        request.getRequestDispatcher("/newReader")
                        .forward(request, response);
                        break;
                    }
                    request.setAttribute("info", "Читатель "+reader.getName()+" "+reader.getLastname()+" добавлен");
                }catch(NumberFormatException e){
                    readerFacade.remove(reader);
                    request.setAttribute("info", "Некорректные данные");
                }
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
            case "/showListAllBooks":
                List<Book> listAllBooks = bookFacade.findAll();
                request.setAttribute("listAllBooks", listAllBooks);
                request.getRequestDispatcher("/WEB-INF/listAllBooks.jsp")
                        .forward(request, response);
                break;    
        }
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
