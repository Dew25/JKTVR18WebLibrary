/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.History;
import entity.Reader;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Melnikov
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "JKTVR18WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }

        
    public Object getProfit(){
        try {
            Query query = em.createQuery("SELECT SUM(h.book.price) FROM History h");
               Object profit = query.getSingleResult();
            return profit; 
        } catch (Exception e){
            return null;
        }
        
    }

    public List<History> findByUser(User user) {
        Reader reader = user.getReader();
        return em.createQuery("SELECT h FROM History h WHERE h.reader = :reader")
                .setParameter("reader", reader)
                .getResultList();
    }
    
}
