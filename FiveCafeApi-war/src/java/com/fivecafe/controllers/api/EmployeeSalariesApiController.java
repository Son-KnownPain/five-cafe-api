package com.fivecafe.controllers.api;

import com.fivecafe.body.employeesalary.CreateEmployeeSalaries;
import com.fivecafe.body.employeesalary.EmployeeSalariesResponse;
import com.fivecafe.body.employeesalary.UpdateAndDeleteEmployeeSalaries;
import com.fivecafe.entities.EmployeeSalaries;
import com.fivecafe.entities.Employees;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.EmployeeSalariesFacadeLocal;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import java.text.ParseException;
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
import org.springframework.http.HttpStatus;
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

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    EmployeeSalariesFacadeLocal employeeSalariesFacade = lookupEmployeeSalariesFacadeLocal();
    

    @GetMapping("" + UrlProvider.EmployeeSalaries.ALL)
    public ResponseEntity<DataResponse<List<EmployeeSalariesResponse>>> all(HttpServletRequest request) {
        List<EmployeeSalaries> allSalary = employeeSalariesFacade.findAll();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        List<EmployeeSalariesResponse> data = new ArrayList<>();

        for (EmployeeSalaries empSalary : allSalary) {
            data.add(EmployeeSalariesResponse.builder()
                    .employeeSalaryID(empSalary.getEmployeeSalaryID())
                    .employeeID(empSalary.getEmployeeID().getEmployeeID())
                    .date(fmt.format(empSalary.getDate()))
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
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateEmployeeSalaries reqBody, BindingResult br)
            throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        //Emp
        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        //TimeKeeping
        EmployeeSalaries salaryAdd = new EmployeeSalaries();

        salaryAdd.setEmployeeID(employees);
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = fmt.parse(reqBody.getDate());
            salaryAdd.setDate(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
            employeeSalariesFacade.create(salaryAdd);
            return ResponseEntity.ok(StandardResponse
                    .builder()
                    .success(true)
                    .status(200)
                    .message("Successfully create new employee salary")
                    .build());
    }
    
    @PutMapping("" + UrlProvider.EmployeeSalaries.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @Valid @RequestBody UpdateAndDeleteEmployeeSalaries reqBody, BindingResult br
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
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = fmt.parse(reqBody.getDate());
            salaryUpdate.setDate(date);
        }catch(ParseException e){
            e.printStackTrace();
        }

        employeeSalariesFacade.edit(salaryUpdate);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
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
    
    
}
