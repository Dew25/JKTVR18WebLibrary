/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookImage;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class BookImageFacade extends AbstractFacade<BookImage> {

    @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookImageFacade() {
        super(BookImage.class);
    }
    
}
