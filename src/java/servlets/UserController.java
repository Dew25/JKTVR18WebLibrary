/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import entity.Book;
import entity.BookImage;
import entity.BookText;
import entity.Comment;
import entity.History;
import entity.Image;
import entity.Reader;
import entity.Text;
import entity.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myclasses.BooksData;
import session.BookFacade;
import session.BookImageFacade;
import session.BookTextFacade;
import session.CommentFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.EncryptPass;
import util.PropertiesLoader;
import util.RoleManager;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "UserController", urlPatterns = {
    "/takeOnBook",
    "/createHistory",
    "/showBook",
    "/addComment",
    "/showUserProfile",
    "/changeReader",
    "/changeComment",

})
public class UserController extends HttpServlet {
    @EJB private UserFacade userFacade;
    @EJB private BookFacade bookFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private BookImageFacade bookImageFacade;
    @EJB private BookTextFacade bookTextFacade;
    @EJB private CommentFacade commentFacade;
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
        String json = null;
        EncryptPass ep = new EncryptPass();
        HttpSession session = request.getSession(false);
        if(null == session){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        RoleManager roleManager = new RoleManager();
        String userRole = roleManager.getTopRole(user);
        request.setAttribute("user", user);
        request.setAttribute("userRole", userRole);
        if(!roleManager.isRoleUser("USER",user)){
            request.setAttribute("info", "У вас нет прав");
            request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
            return;
        }
        String path = request.getServletPath();
        switch (path) {
           
            case "/createHistory":
                String bookId = request.getParameter("bookId");
                
                try{
                    Book book = bookFacade.find(Long.parseLong(bookId));
                    History history = new History();
                    history.setBook(book);
                    //уменьшить деньги покупателя
                    Reader reader = user.getReader();
                    if(reader.getCash()-book.getPrice()<=0){
                        request.setAttribute("info", "У покупателя нет денег!");
                        request.getRequestDispatcher("/showListAllBooks")
                        .forward(request, response);
                        break;
                    }
                    reader.setCash(reader.getCash()-book.getPrice());
                    readerFacade.edit(reader);
                    history.setReader(reader);
                    history.setTakeOnDate(new Date());
                    historyFacade.create(history);
                    request.setAttribute("info", "Книга куплена");
                }catch(NumberFormatException e){
                    request.setAttribute("info", "Не корректные данные");
                }
                request.getRequestDispatcher("/showListAllBooks")
                        .forward(request, response);
                break;
            case "/showBook":
                bookId = request.getParameter("bookId");
                Book book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                List<Comment> comments = commentFacade.findByBook(book);
                request.setAttribute("comments", comments);
                Image image = bookImageFacade.findImageByBook(book);
                if(image == null){
                    request.setAttribute("info", "Не найдена обложка книги");
                }
                request.setAttribute("image", image);
                request.setAttribute("userRole", userRole);
                request.getRequestDispatcher("/WEB-INF/showBook.jsp")
                        .forward(request, response);
                break;
            case "/addComment":
                JsonReader data = new JsonReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                CommentEdit commentEdit = new Gson().fromJson(data, CommentEdit.class);
                book = bookFacade.find(Long.parseLong(commentEdit.bookId));
                Comment comment = new Comment();
                comment.setBook(book);
                comment.setUser(user);
                comment.setCommentText(commentEdit.commentText);
                comment.setCreateDate(new Date());
                comment.setAvalable(true);
                commentFacade.create(comment);
                
                json = new Gson().toJson(comment);
                break;
            case "/changeComment":
                String commentId = request.getParameter("commentId");
                String commentText = request.getParameter("commentText");
                comment = commentFacade.find(Long.parseLong(commentId));
                Date date=new Date();
                comment.setLastEditDate(date);
                comment.setCommentText(commentText);
                commentFacade.edit(comment);
                SimpleDateFormat formatText = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String editDate = formatText.format(comment.getLastEditDate());
                String[] sentToClient = {comment.getCommentText(),editDate};
                json = new Gson().toJson(sentToClient);
                break;
            case "/showUserProfile":
                request.setAttribute("user", user);
               // List<History> listHistories = historyFacade.findByUser(user);
                 
               // String fileFolder = PropertiesLoader.getFolderPath("file");
                 /* 
                  * алгоритм получения данных для заполнения Map:
                  * в цикле перебора listHistories получить history
                    * из history получить book
                    * по book найти bookText
                    * из bookText получить text
                    * из text получить имя файла в fileName
                    * добавить в Map fileName и book.getTitle()
                 */
                String fileName;
                List<BooksData> listBooksData = new ArrayList<>();
                List<History> histories = historyFacade.findByUser(user);
                for (int i = 0; i < histories.size(); i++) {
                  book = histories.get(i).getBook();
                  BooksData bd = new BooksData();
                  bd.setBook(book);
                  BookImage bookImage = bookImageFacade.findByBook(book);
                  bd.setImage(bookImage.getImage());
                  BookText bookText = bookTextFacade.findByBook(book);
                  bd.setText(bookText.getText());
                  listBooksData.add(bd);
                }
                request.setAttribute("listBooksData", listBooksData);
                request.getRequestDispatcher("/WEB-INF/showUserProfile.jsp")
                        .forward(request, response);
                break;
            case "/changeReader":
                String name = request.getParameter("name");
                String lastname = request.getParameter("lastname");
                String cash = request.getParameter("cash");
                String day = request.getParameter("day");
                String month = request.getParameter("month");
                String year = request.getParameter("year");
                String login = request.getParameter("login");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
               
                
                if("".equals(password1) || !password1.equals(password2)){
                    request.setAttribute("info", "Несовпадают пароли");
                    request.getRequestDispatcher("/showUserProfile")
                        .forward(request, response);
                    break;
                }
                Reader reader=null;
                try{
                    reader = readerFacade.find(user.getReader().getId());
                    reader.setName(name);
                    reader.setLastname(lastname);
                    reader.setCash(Integer.parseInt(cash));
                    reader.setDay(Integer.parseInt(day));
                    reader.setMonth(Integer.parseInt(month));
                    reader.setYear(Integer.parseInt(year));
                    user.setLogin(login);
                    String salts = ep.createSalts();
                    String encryptPassword = ep.setEncryptPass(password1, salts);
                    user.setPassword(encryptPassword);
                    user.setSalts(salts);
                    readerFacade.edit(reader);
                    user.setReader(reader);
                    userFacade.edit(user);
                    session.setAttribute("user", user);
                    request.setAttribute("info", "Профиль читателя "+reader.getName()+" "+reader.getLastname()+" изменен");
                }catch(NumberFormatException e){
                    readerFacade.remove(reader);
                    request.setAttribute("info", "Некорректные данные");
                }
                request.getRequestDispatcher("/index")
                        .forward(request, response);
                break;    
        }
        if(json != null){
          try (PrintWriter out = response.getWriter()) {
                  out.println(json);
          }
          json = null;
        }
    }
    public class CommentEdit{
      public String bookId;
      public String commentText;
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
