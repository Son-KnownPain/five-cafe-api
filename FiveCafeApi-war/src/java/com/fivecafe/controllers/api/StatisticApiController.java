package com.fivecafe.controllers.api;

import com.fivecafe.body.statistic.CostRes;
import com.fivecafe.body.statistic.RevenueRes;
import com.fivecafe.dto.DailyCostDTO;
import com.fivecafe.dto.DailyRevenueDTO;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.BillsFacadeLocal;
import com.fivecafe.session_beans.ImportsFacadeLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Statistic.PREFIX)
public class StatisticApiController {

    ImportsFacadeLocal importsFacade = lookupImportsFacadeLocal();

    BillsFacadeLocal billsFacade = lookupBillsFacadeLocal();
    
    @GetMapping(""+UrlProvider.Statistic.REVENUE)
    public ResponseEntity<DataResponse<List<RevenueRes>>> revenue(
            @RequestParam(value = "last-days", defaultValue = "", required = false) String lastDays,
            @RequestParam(value = "startDate", defaultValue = "", required = false) String startDateQueryParam,
            @RequestParam(value = "endDate", defaultValue = "", required = false) String endDateQueryParam
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        int lastDaysInt = 3;
        
        switch (lastDays) {
            case "30":
                lastDaysInt = 30;
                break;
            case "7":
                lastDaysInt = 7;
                break;
            default:
                lastDaysInt = 3;
        }
        
        // Litmit days range
        SimpleDateFormat queryDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // Thêm 1 ngày
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        
        Date endDate = calendar.getTime();
        String endDateString = queryDateFormatter.format(endDate);
        
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -lastDaysInt + 1);
        
        Date startDate = calendar.getTime();
        String startDateString = queryDateFormatter.format(startDate);
        
        // Query param validate
        if (
            startDateQueryParam != null && !startDateQueryParam.isEmpty() &&
            endDateQueryParam != null && !endDateQueryParam.isEmpty()
        ) {
           try {
                startDate = formatter.parse(startDateQueryParam);
                endDate = formatter.parse(endDateQueryParam);
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localStartDate = LocalDate.parse(startDateQueryParam, dtf);
                LocalDate localEndDate = LocalDate.parse(endDateQueryParam, dtf);
                
                if (startDate.before(endDate) && localStartDate.isAfter(localEndDate.minusDays(15))) {
                    // Thêm 1 ngày cho end date
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    endDate = calendar.getTime();

                    startDateString = queryDateFormatter.format(startDate);
                    endDateString = queryDateFormatter.format(endDate);
                }
            } catch (ParseException e) {
                // Catch exception here
            }
        }
                
        // Response
        DataResponse<List<RevenueRes>> res = new DataResponse<>();
        
        List<RevenueRes> data = new ArrayList<>();
        
        for (DailyRevenueDTO item : billsFacade.getDailyRevenue(startDateString, endDateString)) {
            RevenueRes itemData = new RevenueRes();
            itemData.setDate(formatter.format(item.getDate()));
            itemData.setRevenue(item.getRevenue());
            
            data.add(itemData);
        }
        
        res.setData(data);
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully get daily revenue");
        
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Statistic.COST)
    public ResponseEntity<DataResponse<List<CostRes>>> cost(
            @RequestParam(value = "last-days", defaultValue = "", required = false) String lastDays,
            @RequestParam(value = "startDate", defaultValue = "", required = false) String startDateQueryParam,
            @RequestParam(value = "endDate", defaultValue = "", required = false) String endDateQueryParam
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        int lastDaysInt = 3;
        
        switch (lastDays) {
            case "30":
                lastDaysInt = 30;
                break;
            case "7":
                lastDaysInt = 7;
                break;
            default:
                lastDaysInt = 3;
        }
        
        // Litmit days range
        SimpleDateFormat queryDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // Thêm 1 ngày
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        
        Date endDate = calendar.getTime();
        String endDateString = queryDateFormatter.format(endDate);
        
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -lastDaysInt + 1);
        
        Date startDate = calendar.getTime();
        String startDateString = queryDateFormatter.format(startDate);
        
        // Query param validate
        if (
            startDateQueryParam != null && !startDateQueryParam.isEmpty() &&
            endDateQueryParam != null && !endDateQueryParam.isEmpty()
        ) {
           try {
                startDate = formatter.parse(startDateQueryParam);
                endDate = formatter.parse(endDateQueryParam);
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localStartDate = LocalDate.parse(startDateQueryParam, dtf);
                LocalDate localEndDate = LocalDate.parse(endDateQueryParam, dtf);
                
                if (startDate.before(endDate) && localStartDate.isAfter(localEndDate.minusDays(15))) {
                    // Thêm 1 ngày cho end date
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    endDate = calendar.getTime();

                    startDateString = queryDateFormatter.format(startDate);
                    endDateString = queryDateFormatter.format(endDate);
                }
            } catch (ParseException e) {
                // Catch exception here
            }
        }
            
        System.out.println("START DATE: " + startDateString);
        System.out.println("END DATE: " + endDateString);
        
        // Response
        DataResponse<List<CostRes>> res = new DataResponse<>();
        
        List<CostRes> data = new ArrayList<>();
        
        for (DailyCostDTO item : importsFacade.getDailyCost(startDateString, endDateString)) {
            CostRes itemData = new CostRes();
            itemData.setDate(formatter.format(item.getDate()));
            itemData.setCost(item.getCost());
            
            data.add(itemData);
        }
        
        res.setData(data);
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully get daily cost");
        
        return ResponseEntity.ok(res);
    }

    private BillsFacadeLocal lookupBillsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillsFacade!com.fivecafe.session_beans.BillsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ImportsFacadeLocal lookupImportsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ImportsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ImportsFacade!com.fivecafe.session_beans.ImportsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
