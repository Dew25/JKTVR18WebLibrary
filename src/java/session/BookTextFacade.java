/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.BookText;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class BookTextFacade extends AbstractFacade<BookText> {

    @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookTextFacade() {
        super(BookText.class);
    }

    public BookText findByBook(Book book) {
        try {
            return (BookText) em.createQuery("SELECT bt FROM BookText bt WHERE bt.book = :book")
                    .setParameter("book", book)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
