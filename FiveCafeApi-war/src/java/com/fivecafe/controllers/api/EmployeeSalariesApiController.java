package com.fivecafe.controllers.api;

import com.fivecafe.body.employeesalary.AddDetailItemReq;
import com.fivecafe.body.employeesalary.AddESDetailsReq;
import com.fivecafe.body.employeesalary.CreateEmployeeSalaryReq;
import com.fivecafe.body.employeesalary.EmployeeSalariesResponse;
import com.fivecafe.body.employeesalary.EmployeeSalaryDetailResponse;
import com.fivecafe.body.employeesalary.UpdateDetailItemReq;
import com.fivecafe.body.employeesalary.UpdateSalaryReq;
import com.fivecafe.entities.EmployeeSalaries;
import com.fivecafe.entities.EmployeeSalaryDetails;
import com.fivecafe.entities.EmployeeSalaryDetailsPK;
import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Employees;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.EmployeeSalariesFacadeLocal;
import com.fivecafe.session_beans.EmployeeSalaryDetailsFacadeLocal;
import com.fivecafe.session_beans.EmployeeTimeKeepingsFacadeLocal;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.EmployeeSalaries.PREFIX + "")
public class EmployeeSalariesApiController {

    EmployeeSalaryDetailsFacadeLocal employeeSalaryDetailsFacade = lookupEmployeeSalaryDetailsFacadeLocal();

    EmployeeTimeKeepingsFacadeLocal employeeTimeKeepingsFacade = lookupEmployeeTimeKeepingsFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    EmployeeSalariesFacadeLocal employeeSalariesFacade = lookupEmployeeSalariesFacadeLocal();
    

    @GetMapping("" + UrlProvider.EmployeeSalaries.ALL)
    public ResponseEntity<DataResponse<List<EmployeeSalariesResponse>>> all(HttpServletRequest request) {
        List<EmployeeSalaries> allSalary = employeeSalariesFacade.findAll();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        List<EmployeeSalariesResponse> data = new ArrayList<>();

        for (EmployeeSalaries empSalary : allSalary) {
            List<EmployeeSalaryDetailResponse> details = new ArrayList<>();
            for (EmployeeSalaryDetails detail : employeeSalaryDetailsFacade.findByEmpSalaryID(empSalary.getEmployeeSalaryID())) {
                EmployeeSalaryDetailResponse detailResponse = new EmployeeSalaryDetailResponse();
                detailResponse.setEmployeeTimekeepingID(detail.getEmployeeTimeKeepings().getTimeKeepingID());
                detailResponse.setShiftName(detail.getEmployeeTimeKeepings().getShiftID().getName());
                detailResponse.setSalary(detail.getEmployeeTimeKeepings().getSalary());
                detailResponse.setTimeKeepingDate(fmt.format(detail.getEmployeeTimeKeepings().getDate()));
                detailResponse.setBonus((int) (detail.getBonus() * 100));
                detailResponse.setDeduction(detail.getDeduction());
                
                details.add(detailResponse);
            }
            
            data.add(EmployeeSalariesResponse.builder()
                    .employeeSalaryID(empSalary.getEmployeeSalaryID())
                    .employeeID(empSalary.getEmployeeID().getEmployeeID())
                    .employeeName(empSalary.getEmployeeID().getName())
                    .date(fmt.format(empSalary.getDate()))
                    .details(details)
                    .build()
            );
        }

        DataResponse<List<EmployeeSalariesResponse>> res = new DataResponse<>();
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all employee salary");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping("" + UrlProvider.EmployeeSalaries.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateEmployeeSalaryReq reqBody, BindingResult br)
            throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }
        
        // Validate details
        for (CreateEmployeeSalaryReq.CreateEmployeeSalaryDetailReq detail : reqBody.getDetails()) {
            EmployeeTimeKeepings timeKeepings = employeeTimeKeepingsFacade.find(detail.getEtkID());
            if (timeKeepings == null) {
                br.rejectValue("forError", "error.forError", "Employee timekeeping ID you provided is not exist");
                break;
            }
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        EmployeeSalaries salary = new EmployeeSalaries();

        salary.setEmployeeID(employees);
        salary.setDate(new Date());
            
        employeeSalariesFacade.create(salary);
        
        // Handle details
        try {
            for (CreateEmployeeSalaryReq.CreateEmployeeSalaryDetailReq detail : reqBody.getDetails()) {
                EmployeeTimeKeepings timeKeepings = employeeTimeKeepingsFacade.find(detail.getEtkID());
                
                EmployeeSalaryDetails salaryDetailRecord = new EmployeeSalaryDetails();
                
                EmployeeSalaryDetailsPK PK = new EmployeeSalaryDetailsPK();
                PK.setEmployeeSalaryID(salary.getEmployeeSalaryID());
                PK.setTimeKeepingID(timeKeepings.getTimeKeepingID());
                
                salaryDetailRecord.setEmployeeSalaryDetailsPK(PK);
                salaryDetailRecord.setEmployeeSalaries(salary);
                salaryDetailRecord.setEmployeeTimeKeepings(timeKeepings);
                
                salaryDetailRecord.setBonus((double) detail.getBonus() / 100);
                salaryDetailRecord.setDeduction(detail.getDeduction());
                
                employeeSalaryDetailsFacade.create(salaryDetailRecord);
                
                timeKeepings.setIsPaid(true);
                employeeTimeKeepingsFacade.edit(timeKeepings);
            }
        } catch (Exception e) {
            employeeSalariesFacade.remove(salary);
        }
        
        return ResponseEntity.ok(StandardResponse
                .builder()
                .success(true)
                .status(200)
                .message("Successfully create new employee salary")
                .build()
        );
    }
    
    @PostMapping("" + UrlProvider.EmployeeSalaries.ADD_DETAILS)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody AddESDetailsReq reqBody, BindingResult br)
            throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        EmployeeSalaries salary = employeeSalariesFacade.find(reqBody.getEmployeeSalaryID());
        if (salary == null) {
            br.rejectValue("employeeSalaryID", "error.employeeSalaryID", "Employee Salary ID ID you provided is not exist");
        }
        
        // Validate details
        for (AddESDetailsReq.AddEmployeeSalaryDetailReq detail : reqBody.getDetails()) {
            EmployeeTimeKeepings timeKeepings = employeeTimeKeepingsFacade.find(detail.getEtkID());
            
            EmployeeSalaryDetailsPK PK = new EmployeeSalaryDetailsPK();
            PK.setEmployeeSalaryID(reqBody.getEmployeeSalaryID());
            PK.setTimeKeepingID(detail.getEtkID());
            
            EmployeeSalaryDetails detailItem = employeeSalaryDetailsFacade.find(PK);
            
            if (timeKeepings == null) {
                br.rejectValue("forError", "error.forError", "Employee timekeeping ID you provided is not exist");
                break;
            }
            if (detailItem != null) {
                br.rejectValue("forError", "error.forError", "Employee Salary Detail is currently existing");
                break;
            }
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        // Handle details
        try {
            for (AddESDetailsReq.AddEmployeeSalaryDetailReq detail : reqBody.getDetails()) {
                EmployeeTimeKeepings timeKeepings = employeeTimeKeepingsFacade.find(detail.getEtkID());
                
                EmployeeSalaryDetails salaryDetailRecord = new EmployeeSalaryDetails();
                
                EmployeeSalaryDetailsPK PK = new EmployeeSalaryDetailsPK();
                PK.setEmployeeSalaryID(salary.getEmployeeSalaryID());
                PK.setTimeKeepingID(timeKeepings.getTimeKeepingID());
                
                salaryDetailRecord.setEmployeeSalaryDetailsPK(PK);
                salaryDetailRecord.setEmployeeSalaries(salary);
                salaryDetailRecord.setEmployeeTimeKeepings(timeKeepings);
                
                salaryDetailRecord.setBonus((double) detail.getBonus() / 100);
                salaryDetailRecord.setDeduction(detail.getDeduction());
                
                employeeSalaryDetailsFacade.create(salaryDetailRecord);
                
                timeKeepings.setIsPaid(true);
                employeeTimeKeepingsFacade.edit(timeKeepings);
            }
        } catch (Exception e) {
            employeeSalariesFacade.remove(salary);
        }
        
        return ResponseEntity.ok(StandardResponse
                .builder()
                .success(true)
                .status(200)
                .message("Successfully add new employee salary detail")
                .build()
        );
    }
    
    @PutMapping("" + UrlProvider.EmployeeSalaries.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @Valid @RequestBody UpdateSalaryReq reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }

        EmployeeSalaries salaryUpdate = employeeSalariesFacade.find(reqBody.getEmployeeSalaryID());
        if (salaryUpdate == null) {
            br.rejectValue("employeeSalaryID", "error.employeeSalaryID", "The employee salary ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        salaryUpdate.setEmployeeID(employees);

        employeeSalariesFacade.edit(salaryUpdate);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update employee salary")
                        .build()
        );
    }
    
    @PutMapping(""+UrlProvider.EmployeeSalaries.UPDATE_DETAIL_ITEM)
    public ResponseEntity<?> updateDetailItem(@Valid @RequestBody UpdateDetailItemReq reqBody, BindingResult br) throws MethodArgumentNotValidException {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Validate
        EmployeeSalaries salary = employeeSalariesFacade.find(reqBody.getEmployeeSalaryID());
        if (salary == null) {
            br.rejectValue("employeeSalaryID", "error.employeeSalaryID", "Employee Salary ID you provided is not exist");
        }
        
        EmployeeTimeKeepings etk = employeeTimeKeepingsFacade.find(reqBody.getEtkID());
        if (etk == null) {
            br.rejectValue("etkID", "error.etkID", "Time Keeping ID you provided is not exist");
        }

        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Valid
        EmployeeSalaryDetailsPK PK = new EmployeeSalaryDetailsPK();
        PK.setEmployeeSalaryID(salary.getEmployeeSalaryID());
        PK.setTimeKeepingID(etk.getTimeKeepingID());
                
        EmployeeSalaryDetails salaryDetailRecord = employeeSalaryDetailsFacade.find(PK);

        salaryDetailRecord.setBonus(reqBody.getBonus() / 100);
        salaryDetailRecord.setDeduction(reqBody.getDeduction());

        employeeSalaryDetailsFacade.edit(salaryDetailRecord);
        
        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update employee salary detail data")
                .build();
        
        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping(""+UrlProvider.EmployeeSalaries.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idsalary= ids.split(",");
        
        for (String id : idsalary){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            EmployeeSalaries employeeSalaries= employeeSalariesFacade.find(idInt);
            if(employeeSalaries != null){
                List<EmployeeSalaryDetails> details = employeeSalaryDetailsFacade.findByEmpSalaryID(employeeSalaries.getEmployeeSalaryID());
                for (EmployeeSalaryDetails detail : details) {
                    EmployeeTimeKeepings etk = employeeTimeKeepingsFacade.find(detail.getEmployeeTimeKeepings().getTimeKeepingID());
                    
                    etk.setIsPaid(false);
                    
                    employeeSalaryDetailsFacade.remove(detail);
                    
                    employeeTimeKeepingsFacade.edit(etk);
                }
                
                employeeSalariesFacade.remove(employeeSalaries);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete employee salary")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.EmployeeSalaries.DELETE_DETAIL_ITEM)
    public ResponseEntity<?> deleteDetails(@RequestParam("salaryID") int salaryID, @RequestParam("etkID") int etkID){
        EmployeeSalaryDetailsPK PK = new EmployeeSalaryDetailsPK();
        PK.setEmployeeSalaryID(salaryID);
        PK.setTimeKeepingID(etkID);
                
        EmployeeSalaryDetails salaryDetailRecord = employeeSalaryDetailsFacade.find(PK);
        
        if (salaryDetailRecord == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found employee salary detail with employee salary id and etk id you provided");

            return ResponseEntity.badRequest().body(res);
        }
        
        EmployeeTimeKeepings etk = employeeTimeKeepingsFacade.find(salaryDetailRecord.getEmployeeTimeKeepings().getTimeKeepingID());
                    
        etk.setIsPaid(false);

        employeeSalaryDetailsFacade.remove(salaryDetailRecord);

        employeeTimeKeepingsFacade.edit(etk);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete employee salary detail")
                        .build()
        );
    }
    
    private EmployeeSalariesFacadeLocal lookupEmployeeSalariesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeeSalariesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeeSalariesFacade!com.fivecafe.session_beans.EmployeeSalariesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private EmployeesFacadeLocal lookupEmployeesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeesFacade!com.fivecafe.session_beans.EmployeesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private EmployeeTimeKeepingsFacadeLocal lookupEmployeeTimeKeepingsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeeTimeKeepingsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeeTimeKeepingsFacade!com.fivecafe.session_beans.EmployeeTimeKeepingsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private EmployeeSalaryDetailsFacadeLocal lookupEmployeeSalaryDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeeSalaryDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeeSalaryDetailsFacade!com.fivecafe.session_beans.EmployeeSalaryDetailsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
