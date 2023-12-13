/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.ImportDetails;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ADMIN
 */
@Stateless
public class ImportDetailsFacade extends AbstractFacade<ImportDetails> implements ImportDetailsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImportDetailsFacade() {
        super(ImportDetails.class);
    }

    @Override
    public List<ImportDetails> findByImportID(int importID) {
        TypedQuery<ImportDetails> query = em.createNamedQuery("ImportDetails.findByImportID", ImportDetails.class);

        query.setParameter("importID", importID);

        return query.getResultList();
    }

    
}
