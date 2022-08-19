/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.logic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gugsy
 */
public class PersistenceUnit {
    
   
    private final static String PERSISTENCE_UNIT_NAME ="student.ac.zw_farmerDDS_war_1.0-SNAPSHOTPU";
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    
        
    
    }
    
    
}
