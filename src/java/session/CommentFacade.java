/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class CommentFacade extends AbstractFacade<Comment> {

  @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public CommentFacade() {
    super(Comment.class);
  }

  public List<Comment> findByBook(Book book) {
    try {
      return em.createQuery("SELECT comment FROM Comment comment WHERE comment.book = :book AND comment.avalable = true")
              .setParameter("book", book)
              .getResultList();
    } catch (Exception e) {
      return null;
    }
  }
  
}
