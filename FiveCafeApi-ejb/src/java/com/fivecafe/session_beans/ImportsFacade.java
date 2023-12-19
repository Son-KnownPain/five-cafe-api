package com.fivecafe.session_beans;

import com.fivecafe.dto.DailyCostDTO;
import com.fivecafe.dto.DailyRevenueDTO;
import com.fivecafe.entities.Imports;
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
public class ImportsFacade extends AbstractFacade<Imports> implements ImportsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImportsFacade() {
        super(Imports.class);
    }

    @Override
    public List<Imports> searchImportByDate(Date dateForm, Date dateTo) throws ParseException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Imports> criteriaQuery = criteriaBuilder.createQuery(Imports.class);
        Root<Imports> root = criteriaQuery.from(Imports.class);

        // Tạo biểu thức cho ngày bắt đầu và kết thúc
        Path<Date> datePath = root.get("date");
        Predicate datePredicate = criteriaBuilder.between(datePath, dateForm, dateTo);

        // Thêm biểu thức vào câu truy vấn
        criteriaQuery.where(datePredicate);

        // Thực hiện truy vấn
        List<Imports> results = em.createQuery(criteriaQuery).getResultList();

        return results;
    }
    
    @Override
    public List<DailyCostDTO> getDailyCost(String startDate, String endDate) {
        String sql = "SELECT * FROM CostByDays\n" +
                        "WHERE [Date] >= '" + startDate + "' AND [Date] <= '" + endDate + "' ";
        
        Query nativeQuery = em.createNativeQuery(sql);
        List<Object[]> resultList = nativeQuery.getResultList();

        List<DailyCostDTO> result = new ArrayList<>();
        for (Object[] row : resultList) {
            double cost = (Double) row[0];
            Date date = (Date) row[1];
            result.add(new DailyCostDTO(cost, date));
        }

        return result;
    }
}
