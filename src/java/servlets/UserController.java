/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.RoleManager;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "UserController", urlPatterns = {
    "/takeOnBook",
    "/createHistory",
    "/showBook",

})
public class UserController extends HttpServlet {
    @EJB private UserFacade userFacade;
    @EJB private BookFacade bookFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
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
        if(null == session){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        RoleManager roleManager = new RoleManager();
        if(!roleManager.isRoleUser("USER",user)){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        String path = request.getServletPath();
        switch (path) {
            case "/takeOnBook":
                List<Book> listBooks = bookFacade.findEnabledBooks();
                List<Reader> listReaders = readerFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.setAttribute("listReaders", listReaders);
                request.getRequestDispatcher("/WEB-INF/takeOnBook.jsp")
                        .forward(request, response);
                break;
            case "/createHistory":
                String bookId = request.getParameter("bookId");
                String readerId = request.getParameter("readerId");
                try{
                    Book book = bookFacade.find(Long.parseLong(bookId));
                    if(book.getQuantity()>0){
                        Reader reader = readerFacade.find(Long.parseLong(readerId));
                        book.setQuantity(book.getQuantity()-1);
                        bookFacade.edit(book);
                        History history = new History();
                        history.setBook(book);
                        history.setReader(reader);
                        history.setTakeOnDate(new Date());
                        historyFacade.create(history);
                        request.setAttribute("info", "Книга выдана");
                    }else{
                        request.setAttribute("info", "Этой книги нет в наличии");
                    }
                    
                }catch(NumberFormatException e){
                    request.setAttribute("info", "Не корректные данные");
                }
                request.getRequestDispatcher("/takeOnBook")
                        .forward(request, response);
                break;
            case "/showBook":
                bookId = request.getParameter("bookId");
                Book book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                roleManager = new RoleManager();
                String userRole = roleManager.getTopRole(user);
                request.setAttribute("userRole", userRole);
                request.getRequestDispatcher("/WEB-INF/showBook.jsp")
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
