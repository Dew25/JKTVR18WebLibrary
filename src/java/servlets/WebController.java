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
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.EncryptPass;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "WebController", urlPatterns = {
    "/newBook",
    "/addBook",
    "/newReader",
    "/addReader",
    "/takeOnBook",
    "/createHistory",
    "/returnBook",
    "/returnOnBook", 
    "/showListAllBooks",
    "/changeActiveBook",
})
public class WebController extends HttpServlet {
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
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
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
                year = request.getParameter("year");
                String login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                if(!password1.equals(password2)){
                    request.setAttribute("info", "Некорректные данные");
                    request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                    break;
                }
                Reader reader=null;
                try{
                    reader = new Reader(null,name, lastname, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
                    readerFacade.create(reader);
                    EncryptPass ep = new EncryptPass();
                    String salts = ep.createSalts();
                    String encryptPassword = ep.setEncryptPass(password1, salts);
                    User user = new User(login,encryptPassword,salts,reader);
                    userFacade.create(user);
                    request.setAttribute("reader", reader);
                    request.setAttribute("info", "Читатель "+reader.getName()+" "+reader.getLastname()+" добавлен");
                }catch(NumberFormatException e){
                    readerFacade.remove(reader);
                    request.setAttribute("info", "Некорректные данные");
                }
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
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
                        reader = readerFacade.find(Long.parseLong(readerId));
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
            case "/showListAllBooks":
                List<Book> listAllBooks = bookFacade.findAll();
                request.setAttribute("listAllBooks", listAllBooks);
                request.getRequestDispatcher("/WEB-INF/listAllBooks.jsp")
                        .forward(request, response);
                break;
            case "/changeActiveBook":
                bookId = request.getParameter("bookId");
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
