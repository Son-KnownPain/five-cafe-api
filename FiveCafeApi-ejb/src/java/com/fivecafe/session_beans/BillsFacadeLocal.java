/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.dto.DailyRevenueDTO;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.entities.Bills;
import com.fivecafe.entities.Employees;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface BillsFacadeLocal {

    void create(Bills bills);

    void edit(Bills bills);

    void remove(Bills bills);

    Bills find(Object id);

    List<Bills> findAll();

    List<Bills> findRange(int[] range);

    int count();

    public List<Bills> getBillByDaterange(Date dateForm, Date dateTo) throws ParseException;

    public List<Bills> findByEmployeeID(Employees empID);

    public List<DailyRevenueDTO> getDailyRevenue(String startDate, String endDate);

    public boolean hasBillNotServedByCardCode(String cardCode, BillStatuses billStatusNotServed);

    public List<Bills> getNotServedBillsByEmp(Employees emp, BillStatuses billStatusNotServed);
    
}
