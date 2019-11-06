/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
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
@WebServlet(name = "AdminController", urlPatterns = {
    "/newBook",
    "/addBook",
    "/returnBook",
    "/returnOnBook", 
    "/changeActiveBook",
    "/editBook",
    "/changeBook",
    
})
public class AdminController extends HttpServlet {
    @EJB private BookFacade bookFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private UserFacade userFacade;
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
        if(null == user){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        RoleManager roleManager = new RoleManager();
        if(!roleManager.isRoleUser("ADMIN",user)){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        String path = request.getServletPath();

        switch (path) {
            case "/newBook":
                request.getRequestDispatcher("/WEB-INF/newBook.jsp")
                        .forward(request, response);
                break;
            case "/addBook":
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String year = request.getParameter("year");
                String quantity = request.getParameter("quantity");
                try{
                    Book book = new Book(title, author, Integer.parseInt(year), Integer.parseInt(quantity));
                    bookFacade.create(book);
                    request.setAttribute("book", book);
                    request.setAttribute("info", "Книга "+book.getTitle()+" добавлена!");
                    
                }catch(NumberFormatException e){
                    request.setAttribute("info", "Некорректные данные");
                }
                request.getRequestDispatcher("/index")
                        .forward(request, response);
                break;
            case "/returnBook":
                List<History> listHistories = historyFacade.findNotReturnBook();
                request.setAttribute("listHistories", listHistories);
                request.getRequestDispatcher("/WEB-INF/returnBook.jsp")
                        .forward(request, response);
                break;
            case "/returnOnBook":
                String historiId = request.getParameter("historyId");
                History history  = historyFacade.find(Long.parseLong(historiId));
                Book book = history.getBook();
                book.setQuantity(book.getQuantity()+1);
                bookFacade.edit(book);
                history.setReturnDate(new Date());
                historyFacade.edit(history);
                request.setAttribute("info", "Книга возвращена!");
                request.getRequestDispatcher("/returnBook")
                        .forward(request, response);
                break;
            
            case "/changeActiveBook":
                String bookId = request.getParameter("bookId");
                String active = request.getParameter("active");
                book = bookFacade.find(Long.parseLong(bookId));
                if("true".equals(active)){
                    book.setActive(false);
                }else{
                    book.setActive(true);
                }
                bookFacade.edit(book);
                request.getRequestDispatcher("/showListAllBooks")
                        .forward(request, response);
                break;
            case "/showBook":
                bookId = request.getParameter("bookId");
                book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/showBook.jsp")
                        .forward(request, response);
                break;
            case "/editBook":
                bookId = request.getParameter("bookId");
                book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/editBook.jsp")
                        .forward(request, response);
                break;
            case "/changeBook":
                bookId = request.getParameter("bookId");
                title = request.getParameter("title");
                author = request.getParameter("author");
                year = request.getParameter("year");
                quantity = request.getParameter("quantity");
                String activeOn = request.getParameter("active");
                boolean activeBook;
                if("on".equals(activeOn)){
                    activeBook = true;
                }else{
                    activeBook = false;
                }
                book = bookFacade.find(Long.parseLong(bookId));
                book.setActive(activeBook);
                book.setAuthor(author);
                book.setQuantity(Integer.parseInt(quantity));
                book.setTitle(title);
                book.setYear(Integer.parseInt(year));
                bookFacade.edit(book);
                request.setAttribute("book", book);
                request.setAttribute("info", "Книга отредактирована");
                 request.getRequestDispatcher("/WEB-INF/editBook.jsp")
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
