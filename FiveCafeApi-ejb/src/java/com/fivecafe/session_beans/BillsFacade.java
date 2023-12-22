package com.fivecafe.session_beans;

import com.fivecafe.dto.DailyRevenueDTO;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.entities.Bills;
import com.fivecafe.entities.Employees;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class BillsFacade extends AbstractFacade<Bills> implements BillsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillsFacade() {
        super(Bills.class);
    }
    
    @Override
    public List<Bills> getBillByDaterange(Date dateForm, Date dateTo) throws ParseException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bills> cq = cb.createQuery(Bills.class);
        Root<Bills> root = cq.from(Bills.class);
        Path<Date> datePath = root.get("createdDate");
        Predicate datePredicate = cb.between(datePath, dateForm, dateTo);
        
        cq.where(datePredicate);
        
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<Bills> findByEmployeeID(Employees emp) {
        Query query = em.createNamedQuery("Bills.findByEmployeeID", Bills.class);
        query.setParameter("employeeID", emp);
        
        return query.getResultList();
    }
    
    @Override
    public List<DailyRevenueDTO> getDailyRevenue(String startDate, String endDate) {
        String sql = "SELECT \n" +
                "	SUM(BD.UnitPrice * BD.Quantity) AS [Revenue], \n" +
                "	CONVERT(DATE, B.CreatedDate) AS [Date]\n" +
                "FROM Bills AS B\n" +
                "	JOIN BillDetails AS BD \n" +
                "ON BD.BillID = B.BillID\n" +
                "WHERE B.CreatedDate >= '" + startDate + "' AND B.CreatedDate <= '" + endDate + "'\n" + 
                "GROUP BY CONVERT(DATE, B.CreatedDate)";
        
        Query nativeQuery = em.createNativeQuery(sql);
        List<Object[]> resultList = nativeQuery.getResultList();

        List<DailyRevenueDTO> result = new ArrayList<>();
        for (Object[] row : resultList) {
            double revenue = (Double) row[0];
            Date date = (Date) row[1];
            result.add(new DailyRevenueDTO(revenue, date));
        }

        return result;
    }

    @Override
    public boolean hasBillNotServedByCardCode(String cardCode, BillStatuses billStatusNotServed) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        
        Root<Bills> billRoot = criteriaQuery.from(Bills.class);
        
        Predicate cardCodePredicate = criteriaBuilder.equal(billRoot.get("cardCode"), cardCode);
        
        Predicate billStatusPredicate = criteriaBuilder.equal(billRoot.get("billStatusID"), billStatusNotServed);
        
        Predicate combinedPredicate = criteriaBuilder.and(cardCodePredicate, billStatusPredicate);
        
        criteriaQuery.select(criteriaBuilder.count(billRoot)).where(combinedPredicate);
        
        Long count = em.createQuery(criteriaQuery).getSingleResult();
        
        return count > 0;
    }
    
    @Override
    public List<Bills> getNotServedBillsByEmp(Employees emp, BillStatuses billStatusNotServed) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Bills> criteriaQuery = criteriaBuilder.createQuery(Bills.class);
        
        Root<Bills> billRoot = criteriaQuery.from(Bills.class);
        
        Predicate empPredicate = criteriaBuilder.equal(billRoot.get("employeeID"), emp);
        
        Predicate billStatusPredicate = criteriaBuilder.equal(billRoot.get("billStatusID"), billStatusNotServed);
        
        Predicate combinedPredicate = criteriaBuilder.and(empPredicate, billStatusPredicate);
        
        criteriaQuery.where(combinedPredicate);
        
        return em.createQuery(criteriaQuery).getResultList();
    }
}
