package com.fivecafe.controllers.api;

import com.fivecafe.body.employee.CreEmpReq;
import com.fivecafe.body.employee.EmpInfoRes;
import com.fivecafe.body.employee.EmpLoginCredentials;
import com.fivecafe.body.employee.EmployeeRes;
import com.fivecafe.body.employee.UpdEmpReq;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Roles;
import com.fivecafe.enums.RequestAttributeKeys;
import com.fivecafe.enums.TokenNames;
import com.fivecafe.exceptions.UnauthorizedException;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.services.UserTokenService;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.RolesFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Employee.PREFIX)
public class EmployeeApiController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserTokenService userTokenService;

    RolesFacadeLocal rolesFacade = lookupRolesFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();
    
    @GetMapping(""+UrlProvider.Employee.ALL)
    public ResponseEntity<DataResponse<List<EmployeeRes>>> all(HttpServletRequest request) {
        List<Employees> allEmps = employeesFacade.findAll();
        
        List<EmployeeRes> data = new ArrayList<>();
        
        for (Employees emp : allEmps) {
            data.add(EmployeeRes.builder()
                    .employeeID(emp.getEmployeeID())
                    .roleID(emp.getRoleID().getRoleID())
                    .roleName(emp.getRoleID().getRoleName())
                    .name(emp.getName())
                    .image(FileSupport.perfectImg(request, "employee", emp.getImage()))
                    .phone(emp.getPhone())
                    .username(emp.getUsername())
                    .build()
            );
        }
        
        DataResponse<List<EmployeeRes>> res = new DataResponse<>();
        
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all employee");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Employee.SEARCH)
    public ResponseEntity<DataResponse<List<EmployeeRes>>> search(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "roleID", defaultValue = "") String roleID,
            HttpServletRequest request
    ) {
        Roles role = null;
        if (roleID.length() > 0) {
            role = rolesFacade.find(roleID);
        }
        
        List<Employees> allEmps = employeesFacade.searchEmployees(keyword, role);
        
        List<EmployeeRes> data = new ArrayList<>();
        
        for (Employees emp : allEmps) {
            data.add(EmployeeRes.builder()
                    .employeeID(emp.getEmployeeID())
                    .roleID(emp.getRoleID().getRoleID())
                    .roleName(emp.getRoleID().getRoleName())
                    .name(emp.getName())
                    .image(FileSupport.perfectImg(request, "employee", emp.getImage()))
                    .phone(emp.getPhone())
                    .username(emp.getUsername())
                    .build()
            );
        }
        
        DataResponse<List<EmployeeRes>> res = new DataResponse<>();
        
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching employee");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(value = ""+UrlProvider.Employee.STORE)
    public ResponseEntity<StandardResponse> store(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "data", required = false) @Valid CreEmpReq reqBody,
            BindingResult br,
            HttpSession session
    ) 
            throws MethodArgumentNotValidException 
    {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        // Validation for iamge
        if (image == null || image.isEmpty()) {
            br.rejectValue("image", "error.image", "Image is required");
            throw new MethodArgumentNotValidException(null, br);
        }
        // -----------------
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
        try {
            /**
             * Handle image
             */
            byte[] imageBytes = image.getBytes();
            String originFileName = image.getOriginalFilename();
            String newImageFileName = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "employee", imageBytes, originFileName);
            nEmp.setImage(newImageFileName);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // END-----------
        
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
    
    @PostMapping(""+UrlProvider.Employee.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "data", required = false) @Valid UpdEmpReq reqBody,
            BindingResult br,
            HttpSession session
    ) 
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
        if (image != null && !image.isEmpty()) {
            try {
               FileSupport.deleteFile(session.getServletContext().getRealPath("/"), "employee", emp.getImage());
                
               byte[] imageBytes = image.getBytes();
               String originFileName = image.getOriginalFilename();
               String newImageFileName = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "employee", imageBytes, originFileName);
               emp.setImage(newImageFileName);
           } catch (IOException ex) {
               Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
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
    public ResponseEntity<?> delete(@RequestParam("ids") String ids, HttpSession session) {
        String[] idArr = ids.split(",");
        
        for (String id : idArr) {
            try {
                Employees emp = employeesFacade.find(Integer.parseInt(id));
            
                if (emp != null) {
                    try {
                        FileSupport.deleteFile(session.getServletContext().getRealPath("/"), "employee", emp.getImage());

                    } catch (IOException ex) {
                        Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
    
    @PostMapping(""+UrlProvider.Employee.LOGIN)
    public ResponseEntity<StandardResponse> login(@Valid @RequestBody EmpLoginCredentials loginCredentials, BindingResult br, HttpServletResponse response) 
            throws MethodArgumentNotValidException, UnauthorizedException 
    {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();
        
        Employees emp = employeesFacade.findByUsername(username);
        
        if (emp == null) {
            throw new UnauthorizedException();
        }
        
        String encodedPassword = emp.getPassword();
        
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UnauthorizedException();
        }
        
        // INSERT JWT INTO COOKIE =======
        String accessToken = userTokenService.createAccessToken(emp.getEmployeeID() + "");
        String refreshToken = userTokenService.createRefreshToken(emp.getEmployeeID() + "");

        // Add AT and RT to cookie and set httpOnly to true
        Cookie accessTokenCookie = new Cookie(TokenNames.ACCESS_TOKEN.toString(), accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(60 * 60 * 24 * 365 * 60);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie(TokenNames.REFRESH_TOKEN.toString(), refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 365 * 60);
        refreshTokenCookie.setPath("/");

        // Add cookies to response
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        // ==============================
        
        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully login to application");
        
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Employee.INFO)
    public ResponseEntity<?> info(HttpServletRequest request) {
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
            
        DataResponse<EmpInfoRes> res = new DataResponse<>();
            
        try {
            int userIDInt = Integer.parseInt(userID);
            
            Employees employee = employeesFacade.find(userIDInt);
            
            EmpInfoRes info = EmpInfoRes.builder()
                    .roleName(employee.getRoleID().getRoleName())
                    .name(employee.getName())
                    .image(FileSupport.perfectImg(request, "employee", employee.getImage()))
                    .username(employee.getUsername())
                    .phone(employee.getPhone())
                    .build();
            
            res.setStatus(200);
            res.setSuccess(true);
            res.setMessage("Succsesfully get your info");
            res.setData(info);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Employee.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie aTCookie = new Cookie(TokenNames.ACCESS_TOKEN.toString(), "");
        Cookie rTCookie = new Cookie(TokenNames.REFRESH_TOKEN.toString(), "");
        
        aTCookie.setMaxAge(0);
        aTCookie.setPath("/");
        
        rTCookie.setMaxAge(0);
        rTCookie.setPath("/");
        
        response.addCookie(aTCookie);
        response.addCookie(rTCookie);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .status(200)
                        .success(true)
                        .message("Successfully logout")
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
