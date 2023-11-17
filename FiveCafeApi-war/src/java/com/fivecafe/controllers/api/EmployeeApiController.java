package com.fivecafe.controllers.api;

import com.fivecafe.body.employee.CreEmpReq;
import com.fivecafe.body.employee.EmployeeRes;
import com.fivecafe.body.employee.UpdEmpReq;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Roles;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.RolesFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Employee.PREFIX)
public class EmployeeApiController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    RolesFacadeLocal rolesFacade = lookupRolesFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();
    
    @GetMapping(""+UrlProvider.Employee.ALL)
    public ResponseEntity<DataResponse<List<EmployeeRes>>> all() {
        List<Employees> allEmps = employeesFacade.findAll();
        
        List<EmployeeRes> data = new ArrayList<>();
        
        for (Employees emp : allEmps) {
            data.add(EmployeeRes.builder()
                    .employeeID(emp.getEmployeeID())
                    .roleID(emp.getRoleID().getRoleID())
                    .roleName(emp.getRoleID().getRoleName())
                    .name(emp.getName())
                    .phone(emp.getPhone())
                    .username(emp.getUsername())
                    .build()
            );
        }
        
        DataResponse<List<EmployeeRes>> res = new DataResponse<>();
        
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all role");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Employee.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreEmpReq reqBody, BindingResult br) 
            throws MethodArgumentNotValidException 
    {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        Roles role = rolesFacade.find(reqBody.getRoleID());
        if (role == null) {
            br.rejectValue("roleID", "error.roleID", "Role ID is not exist");
        }
        if (employeesFacade.findByUsername(reqBody.getUsername()) != null) {
            br.rejectValue("username", "error.username", "Username is current existing");
        }
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        Employees nEmp = new Employees();
        
        nEmp.setRoleID(role);
        nEmp.setName(reqBody.getName());
        nEmp.setPhone(reqBody.getPhone());
        nEmp.setUsername(reqBody.getUsername());
        nEmp.setPassword(passwordEncoder.encode(reqBody.getPassword()));
        
        employeesFacade.create(nEmp);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new employee")
                        .build()
        );
    }
    
    @PutMapping(""+UrlProvider.Employee.UPDATE)
    public ResponseEntity<StandardResponse> update(@Valid @RequestBody UpdEmpReq reqBody, BindingResult br) 
            throws MethodArgumentNotValidException 
    {
        // Validation
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        Roles role = rolesFacade.find(reqBody.getRoleID());
        if (role == null) {
            br.rejectValue("roleID", "error.roleID", "Role ID is not exist");
        }
        
        // Find emp
        Employees emp = employeesFacade.find(reqBody.getEmployeeID());
        
        // Check emp not null
        if (emp == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        } else {
            Employees empWithUsername = employeesFacade.findByUsername(reqBody.getUsername());
            if (empWithUsername != null && !reqBody.getUsername().equals(emp.getUsername())) {
                br.rejectValue("username", "error.username", "Username is current existing");
            }
        }
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Update info
        emp.setRoleID(role);
        emp.setName(reqBody.getName());
        emp.setPhone(reqBody.getPhone());
        emp.setUsername(reqBody.getUsername());
        if (reqBody.getPassword() != null && !reqBody.getPassword().isEmpty()) {
            if (reqBody.getPassword().length() >= 8 && reqBody.getPassword().length() <= 100) {
                emp.setPassword(passwordEncoder.encode(reqBody.getPassword()));
            } else {
                br.rejectValue("password", "error.password", "Password must be greater than 8 characters and less than 100 characters");
                throw new MethodArgumentNotValidException(null, br);
            }
        }
        
        employeesFacade.edit(emp);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update employee")
                        .build()
        );
    }
    
    @DeleteMapping("" + UrlProvider.Employee.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids) {
        String[] idArr = ids.split(",");
        
        for (String id : idArr) {
            try {
                Employees emp = employeesFacade.find(Integer.parseInt(id));
            
                if (emp != null) {
                    employeesFacade.remove(emp);
                }
            } catch (NumberFormatException e) {
            }
        }
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete employees")
                        .build()
        );
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

    private RolesFacadeLocal lookupRolesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RolesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/RolesFacade!com.fivecafe.session_beans.RolesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
