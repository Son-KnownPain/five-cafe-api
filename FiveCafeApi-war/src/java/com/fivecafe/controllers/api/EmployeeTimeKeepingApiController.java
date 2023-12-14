package com.fivecafe.controllers.api;

import com.fivecafe.body.employeetimekeeping.CreateEmployeeTimeKeeping;
import com.fivecafe.body.employeetimekeeping.EmployeeTimeKeepingResponse;
import com.fivecafe.body.employeetimekeeping.UpdateAndDeleteEmployeeTimeKeeping;
import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Shifts;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.EmployeeTimeKeepingsFacadeLocal;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.ShiftsFacadeLocal;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.EmployeeTimeKeeping.PREFIX)
public class EmployeeTimeKeepingApiController {

    ShiftsFacadeLocal shiftsFacade = lookupShiftsFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    EmployeeTimeKeepingsFacadeLocal employeeTimeKeepingsFacade = lookupEmployeeTimeKeepingsFacadeLocal();

    @GetMapping("" + UrlProvider.EmployeeTimeKeeping.ALL)
    public ResponseEntity<DataResponse<List<EmployeeTimeKeepingResponse>>> all() {
        List<EmployeeTimeKeepings> allETK = employeeTimeKeepingsFacade.findAll();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        List<EmployeeTimeKeepingResponse> data = new ArrayList<>();

        for (EmployeeTimeKeepings empTK : allETK) {
            data.add(EmployeeTimeKeepingResponse.builder()
                    .timeKeepingID(empTK.getTimeKeepingID())
                    .employeeID(empTK.getEmployeeID().getEmployeeID())
                    .employeeName(empTK.getEmployeeID().getName())
                    .shiftID(empTK.getShiftID().getShiftID())
                    .shiftName(empTK.getShiftID().getName())
                    .date(fmt.format(empTK.getDate()))
                    .salary(empTK.getSalary())
                    .isPaid(empTK.getIsPaid())
                    .build()
            );
        }

        DataResponse<List<EmployeeTimeKeepingResponse>> res = new DataResponse<>();
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all employee time keeping");
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    @PostMapping("" + UrlProvider.EmployeeTimeKeeping.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateEmployeeTimeKeeping reqBody, BindingResult br)
            throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        //Emp
        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }
        //Shift
        Shifts shifts = shiftsFacade.find(reqBody.getShiftID());
        if (shifts == null) {
            br.rejectValue("shiftID", "error.shiftID", "Shift ID is not exist");
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        //TimeKeeping
        EmployeeTimeKeepings etkAdd = new EmployeeTimeKeepings();

        etkAdd.setEmployeeID(employees);
        etkAdd.setShiftID(shifts);
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        etkAdd.setDate(new Date());
        etkAdd.setSalary(shifts.getSalary());
        etkAdd.setIsPaid(false);
                
        try {
            employeeTimeKeepingsFacade.create(etkAdd);
            return ResponseEntity.ok(StandardResponse
                    .builder()
                    .success(true)
                    .status(200)
                    .message("Successfully create new employee time keeping")
                    .build());
        } catch (Exception e) {
            // Xử lý exception và trả về thông điệp lỗi cụ thể
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(StandardResponse.builder()
                            .success(false)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Error creating employee time keeping: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("" + UrlProvider.EmployeeTimeKeeping.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @Valid @RequestBody UpdateAndDeleteEmployeeTimeKeeping reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }

        Shifts shifts = shiftsFacade.find(reqBody.getShiftID());
        if (shifts == null) {
            br.rejectValue("shiftID", "error.shiftID", "Shift ID is not exist");
        }

        EmployeeTimeKeepings etkUpdate = employeeTimeKeepingsFacade.find(reqBody.getTimeKeepingID());

        if (etkUpdate == null) {
            br.rejectValue("timekeepingID", "error.timekeepingID", "The time keeping ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        etkUpdate.setEmployeeID(employees);
        etkUpdate.setShiftID(shifts);
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = fmt.parse(reqBody.getDate());
            etkUpdate.setDate(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        etkUpdate.setSalary(reqBody.getSalary());
        etkUpdate.setIsPaid(reqBody.isPaid());

        employeeTimeKeepingsFacade.edit(etkUpdate);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.EmployeeTimeKeeping.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idetk= ids.split(",");
        
        for (String id : idetk){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            EmployeeTimeKeepings employeeTimeKeepings= employeeTimeKeepingsFacade.find(idInt);
            if(employeeTimeKeepings != null){
                employeeTimeKeepingsFacade.remove(employeeTimeKeepings);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete employee time keepings")
                        .build()
        );
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

    private EmployeesFacadeLocal lookupEmployeesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeesFacade!com.fivecafe.session_beans.EmployeesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ShiftsFacadeLocal lookupShiftsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ShiftsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ShiftsFacade!com.fivecafe.session_beans.ShiftsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
