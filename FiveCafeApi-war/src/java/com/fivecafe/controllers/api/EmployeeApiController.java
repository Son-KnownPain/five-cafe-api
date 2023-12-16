package com.fivecafe.controllers.api;

import com.fivecafe.body.bills.BillDetailsResponse;
import com.fivecafe.body.bills.BillResponse;
import com.fivecafe.body.employee.AddProOfBillReq;
import com.fivecafe.body.employee.CreEmpReq;
import com.fivecafe.body.employee.CreateOutboundReq;
import com.fivecafe.body.employee.EmpInfoRes;
import com.fivecafe.body.employee.EmpLoginCredentials;
import com.fivecafe.body.employee.EmployeeRes;
import com.fivecafe.body.employee.UpdEmpReq;
import com.fivecafe.body.employee.OrderingReq;
import com.fivecafe.body.employee.UpdateMyBillReq;
import com.fivecafe.body.employee.UpdateProOfBillReq;
import com.fivecafe.body.employeesalary.EmployeeSalariesResponse;
import com.fivecafe.body.employeesalary.EmployeeSalaryDetailResponse;
import com.fivecafe.body.employeetimekeeping.EmployeeTimeKeepingResponse;
import com.fivecafe.body.outbound.OutboundDetailResponse;
import com.fivecafe.body.outbound.OutboundResponse;
import com.fivecafe.entities.BillDetails;
import com.fivecafe.entities.BillDetailsPK;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.entities.Bills;
import com.fivecafe.entities.EmployeeSalaries;
import com.fivecafe.entities.EmployeeSalaryDetails;
import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.OutboundDetails;
import com.fivecafe.entities.OutboundDetailsPK;
import com.fivecafe.entities.Outbounds;
import com.fivecafe.entities.Products;
import com.fivecafe.entities.Roles;
import com.fivecafe.enums.RequestAttributeKeys;
import com.fivecafe.enums.TokenNames;
import com.fivecafe.exceptions.UnauthorizedException;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.services.UserTokenService;
import com.fivecafe.session_beans.BillDetailsFacadeLocal;
import com.fivecafe.session_beans.BillStatusesFacadeLocal;
import com.fivecafe.session_beans.BillsFacadeLocal;
import com.fivecafe.session_beans.EmployeeSalariesFacadeLocal;
import com.fivecafe.session_beans.EmployeeSalaryDetailsFacadeLocal;
import com.fivecafe.session_beans.EmployeeTimeKeepingsFacadeLocal;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import com.fivecafe.session_beans.OutboundDetailsFacadeLocal;
import com.fivecafe.session_beans.OutboundsFacadeLocal;
import com.fivecafe.session_beans.ProductsFacadeLocal;
import com.fivecafe.session_beans.RolesFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Employee.PREFIX)
public class EmployeeApiController {

    EmployeeSalaryDetailsFacadeLocal employeeSalaryDetailsFacade = lookupEmployeeSalaryDetailsFacadeLocal();

    EmployeeSalariesFacadeLocal employeeSalariesFacade = lookupEmployeeSalariesFacadeLocal();

    EmployeeTimeKeepingsFacadeLocal employeeTimeKeepingsFacade = lookupEmployeeTimeKeepingsFacadeLocal();

    OutboundDetailsFacadeLocal outboundDetailsFacade = lookupOutboundDetailsFacadeLocal();

    OutboundsFacadeLocal outboundsFacade = lookupOutboundsFacadeLocal();

    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    BillDetailsFacadeLocal billDetailsFacade = lookupBillDetailsFacadeLocal();

    BillsFacadeLocal billsFacade = lookupBillsFacadeLocal();

    BillStatusesFacadeLocal billStatusesFacade = lookupBillStatusesFacadeLocal();

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();
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
    
    // EMPLOYEE INTERACTIVE
    
    // ---- Ordering: /api/employee/ordering
    @PostMapping(""+UrlProvider.Employee.ORDERING)
    public ResponseEntity<?> ordering(
            @Valid @RequestBody OrderingReq reqBody, BindingResult br,
            HttpServletRequest request
    )
            throws MethodArgumentNotValidException
    {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate details
        for (OrderingReq.OrderingDetail detail : reqBody.getDetails()) {
            Products product = productsFacade.find(detail.getProductID());
            if (product == null) {
                br.rejectValue("forError", "error.forError", "Product ID you provided is not exist");
                break;
            }

        }
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Employees employees = employeesFacade.find(employeeIDInt);
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }

        BillStatuses billStatuses = billStatusesFacade.find(reqBody.getBillStatusID());
        if (billStatuses == null) {
            br.rejectValue("billStatusID", "error.billStatusID", "billStatus ID is not exist");
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        // All params is valid, insert record right now

        Bills billRecord = new Bills();
        billRecord.setEmployeeID(employees);
        billRecord.setBillStatusID(billStatuses);
        billRecord.setCreatedDate(new Date());
        billRecord.setCardCode(reqBody.getCardCode());

        billsFacade.create(billRecord);

        // Create detail
        try {
            for (OrderingReq.OrderingDetail detail : reqBody.getDetails()) {
                Products products = productsFacade.find(detail.getProductID());

                BillDetails billDetailRecord = new BillDetails();

                BillDetailsPK idPK = new BillDetailsPK();
                idPK.setBillID(billRecord.getBillID());
                idPK.setProductID(detail.getProductID());

                billDetailRecord.setBillDetailsPK(idPK);
                billDetailRecord.setBills(billRecord);
                billDetailRecord.setProducts(products);
                billDetailRecord.setUnitPrice(products.getPrice());
                billDetailRecord.setQuantity(detail.getQuantity());

                billDetailsFacade.create(billDetailRecord);
            }
        } catch (Exception e) {
            billsFacade.remove(billRecord);
        }

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully create new bill and bill details")
                .build();

        return ResponseEntity.ok(res);
    }
        
    // ---- Ordering: /api/employee/create-outbound
    @PostMapping(""+UrlProvider.Employee.CREATE_OUTBOUND)
    public ResponseEntity<?> createOutbound(
            @Valid @RequestBody CreateOutboundReq reqBody, BindingResult br,
            HttpServletRequest request
    ) 
            throws MethodArgumentNotValidException
    {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        Employees employees = employeesFacade.find(employeeIDInt);
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "That employee ID does not exist");
        }

        for (CreateOutboundReq.CreateOutboundDetailReq detail : reqBody.getDetails()) {
            Materials materials = materialsFacade.find(detail.getMaterialID());

            if (materials.getQuantityInStock() < detail.getQuantity()) {
                br.rejectValue("forError", "error.forError", "Not enough materials, please add more materials");
                break;
            }
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        for (CreateOutboundReq.CreateOutboundDetailReq detail : reqBody.getDetails()) {
            Materials materials = materialsFacade.find(detail.getMaterialID());
            if (materials == null) {
                br.rejectValue("materialID", "error.materialID", "That material ID does not exist");
                break;
            }
        }

        // After valid
        Outbounds obNew = new Outbounds();
        obNew.setDate(new Date());
        obNew.setEmployeeID(employees);

        outboundsFacade.create(obNew);

        //Details
        try {
            for (CreateOutboundReq.CreateOutboundDetailReq detail : reqBody.getDetails()) {
                Materials materials = materialsFacade.find(detail.getMaterialID());

                OutboundDetails outboundDetailNew = new OutboundDetails();

                OutboundDetailsPK idPK = new OutboundDetailsPK();
                idPK.setOutboundID(obNew.getOutboundID());
                idPK.setMaterialID(detail.getMaterialID());

                outboundDetailNew.setOutboundDetailsPK(idPK);
                outboundDetailNew.setOutbounds(obNew);
                outboundDetailNew.setMaterials(materials);
                outboundDetailNew.setQuantity(detail.getQuantity());

                outboundDetailsFacade.create(outboundDetailNew);
                materials.setQuantityInStock(materials.getQuantityInStock() - detail.getQuantity());
                materialsFacade.edit(materials);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        }

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new outbound")
                        .build()
        );
    }

    @GetMapping(""+UrlProvider.Employee.ALL_MY_BILLS)
    public ResponseEntity<?> allMyBills(HttpServletRequest request) {
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        List<Bills> allBills = billsFacade.findByEmployeeID(emp);
        DataResponse<List<BillResponse>> res = new DataResponse<>();
        List<BillResponse> data = new ArrayList<>();
        for (Bills billItem : allBills) {
            // Handle bills details
            List<BillDetailsResponse> listDetail = new ArrayList<>();

            for (BillDetails billDetail : billDetailsFacade.findByBillID(billItem.getBillID())) {
                listDetail.add(
                        BillDetailsResponse.builder()
                                .productID(billDetail.getProducts().getProductID())
                                .name(billDetail.getProducts().getName())
                                .image(FileSupport.perfectImg(request, "products", billDetail.getProducts().getImage()))
                                .unitPrice(billDetail.getUnitPrice())
                                .quantity(billDetail.getQuantity())
                                .build()
                );
            }

            // Add item
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            data.add(
                    BillResponse.builder()
                            .billID(billItem.getBillID())
                            .employeeID(billItem.getEmployeeID().getEmployeeID())
                            .employeeName(billItem.getEmployeeID().getName())
                            .billStatusID(billItem.getBillStatusID().getBillStatusID())
                            .billStatusValue(billItem.getBillStatusID().getBillStatusValue())
                            .createDate(formatter.format(billItem.getCreatedDate()))
                            .cardCode(billItem.getCardCode())
                            .details(listDetail)
                            .build()
            );
        }

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all my bills");
        res.setData(data);

        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Employee.ALL_MY_ETK)
    public ResponseEntity<?> allMyETK(HttpServletRequest request) {
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        List<EmployeeTimeKeepings> allETK = employeeTimeKeepingsFacade.findByEmployeeID(emp);
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
        res.setMessage("Successfully get all my employee time keepings");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping("" + UrlProvider.Employee.ALL_MY_SALARIES)
    public ResponseEntity<?> allMySalaries(HttpServletRequest request) {
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        List<EmployeeSalaries> allSalary = employeeSalariesFacade.findByEmployeeID(emp);
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
    
    @PutMapping(""+UrlProvider.Employee.UPDATE_MY_BILL)
    public ResponseEntity<?> updateMyBill(@Valid @RequestBody UpdateMyBillReq reqBody, BindingResult br, HttpServletRequest request) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        BillStatuses billStatuses = billStatusesFacade.find(reqBody.getBillStatusID());
        if (billStatuses == null) {
            br.rejectValue("billStatusID", "error.billStatusID", "billStatus ID is not exist");
        }
        
        Bills bill = billsFacade.find(reqBody.getBillID());
        if (bill == null) {
            br.rejectValue("billID", "error.billID", "Bill ID is not exist");
        }
        
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        
        if (bill.getEmployeeID().getEmployeeID() != employeeIDInt) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(403);
            res.setMessage("You are not permit");
        }
        
        bill.setBillStatusID(billStatuses);
        bill.setCardCode(reqBody.getCardCode());
        
        billsFacade.edit(bill);
        
        StandardResponse res = new StandardResponse();
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully update bill");

        return ResponseEntity.ok(res);
    }
    
    @PutMapping(""+UrlProvider.Employee.UPDATE_PRO_OF_BILL)
    public ResponseEntity<?> updateProOfBill(@Valid @RequestBody UpdateProOfBillReq reqBody, BindingResult br, HttpServletRequest request) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate
        Bills bills = billsFacade.find(reqBody.getBillID());
        if (bills == null) {
            br.rejectValue("billID", "error.billID", "Bill ID you provided is not exist");
        }

        Products products = productsFacade.find(reqBody.getProductID());
        if (products == null) {
            br.rejectValue("productID", "error.productID", "Product ID you provided is not exist");
        }
        
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        if (bills.getEmployeeID().getEmployeeID() != employeeIDInt) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(403);
            res.setMessage("You are not permit");
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Valid
        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(reqBody.getBillID());
        idPK.setProductID(reqBody.getProductID());

        BillDetails billDetail = billDetailsFacade.find(idPK);

        if (billDetail != null) {
            billDetail.setQuantity(reqBody.getQuantity());

            billDetailsFacade.edit(billDetail);
        }

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update bill product item data")
                .build();

        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping("" + UrlProvider.Employee.DELETE_PRO_OF_BILL)
    public ResponseEntity<?> deleteProOfBill(@RequestParam("productID") int productID, @RequestParam("billID") int billID, HttpServletRequest request) {

        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(billID);
        idPK.setProductID(productID);

        BillDetails billDetail = billDetailsFacade.find(idPK);
        
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        
        if (billDetail.getBills().getEmployeeID().getEmployeeID() != employeeIDInt) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(403);
            res.setMessage("You are not permit");
        }

        if (billDetail == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found bill detail with bill id and product id you provided");

            return ResponseEntity.ok(res);
        }

        billDetailsFacade.remove(billDetail);

        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully delete bill detail");

        return ResponseEntity.ok(res);
    }
    
    @PostMapping("" + UrlProvider.Employee.ADD_PRO_OF_BILL)
    public ResponseEntity<?> storeProItem(@Valid @RequestBody AddProOfBillReq reqBody, BindingResult br, HttpServletRequest request) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate
        Bills bills = billsFacade.find(reqBody.getBillID());
        if (bills == null) {
            br.rejectValue("billID", "error.billID", "Bill ID you provided is not exist");
        }

        Products products = productsFacade.find(reqBody.getProductID());
        if (products == null) {
            br.rejectValue("productID", "error.productID", "Product ID you provided is not exist");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        
        if (bills.getEmployeeID().getEmployeeID() != employeeIDInt) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(403);
            res.setMessage("You are not permit");
        }

        // Valid
        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(reqBody.getBillID());
        idPK.setProductID(reqBody.getProductID());

        BillDetails newbillDetail = new BillDetails();

        newbillDetail.setBillDetailsPK(idPK);
        newbillDetail.setBills(bills);
        newbillDetail.setProducts(products);
        newbillDetail.setUnitPrice(products.getPrice());
        newbillDetail.setQuantity(reqBody.getQuantity());
        billDetailsFacade.create(newbillDetail);

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update bill, product item data")
                .build();

        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Employee.ALL_MY_OUTBOUNDS)
    public ResponseEntity<?> allMyOutbounds(HttpServletRequest request) {
        String userID = (String) request.getAttribute(RequestAttributeKeys.USER_ID.toString());
        int employeeIDInt = 0;
        try {
            employeeIDInt = Integer.parseInt(userID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Employees emp = employeesFacade.find(employeeIDInt);
        if (emp == null) {
            StandardResponse res = new StandardResponse();
            res.setSuccess(false);
            res.setStatus(401);
            res.setMessage("Cannot found your information");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        
        List<Outbounds> allOut = outboundsFacade.findByEmployeeID(emp);

        //format date
        SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy");

        DataResponse<List<OutboundResponse>> responses = new DataResponse<>();
        List<OutboundResponse> dataOut = new ArrayList<>();

        for (Outbounds outboundItem : allOut) {
            List<OutboundDetailResponse> listOutboundDetail = new ArrayList<>();
            for (OutboundDetails outboundDetailItem : outboundDetailsFacade.findByOutboundID(outboundItem.getOutboundID())) {
                listOutboundDetail.add(
                        OutboundDetailResponse.builder()
                                .materialID(outboundDetailItem.getMaterials().getMaterialID())
                                .materialName(outboundDetailItem.getMaterials().getName())
                                .unit(outboundDetailItem.getMaterials().getUnit())
                                .materialImage(FileSupport.perfectImg(request, "material", outboundDetailItem.getMaterials().getImage()))
                                .quantity(outboundDetailItem.getQuantity())
                                .build()
                );
            }

            //add item
            dataOut.add(
                    OutboundResponse.builder()
                            .outboundID(outboundItem.getOutboundID())
                            .employeeID(outboundItem.getEmployeeID().getEmployeeID())
                            .name(outboundItem.getEmployeeID().getName())
                            .date(formDate.format(outboundItem.getDate()))
                            .details(listOutboundDetail)
                            .build()
            );
        }

        responses.setSuccess(true);
        responses.setStatus(200);
        responses.setMessage("Successfully get all my outbounds");
        responses.setData(dataOut);

        return ResponseEntity.ok(responses);
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

    private ProductsFacadeLocal lookupProductsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProductsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ProductsFacade!com.fivecafe.session_beans.ProductsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BillStatusesFacadeLocal lookupBillStatusesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillStatusesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillStatusesFacade!com.fivecafe.session_beans.BillStatusesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    private BillDetailsFacadeLocal lookupBillDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillDetailsFacade!com.fivecafe.session_beans.BillDetailsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MaterialsFacadeLocal lookupMaterialsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MaterialsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/MaterialsFacade!com.fivecafe.session_beans.MaterialsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OutboundsFacadeLocal lookupOutboundsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OutboundsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/OutboundsFacade!com.fivecafe.session_beans.OutboundsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OutboundDetailsFacadeLocal lookupOutboundDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OutboundDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/OutboundDetailsFacade!com.fivecafe.session_beans.OutboundDetailsFacadeLocal");
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

    private EmployeeSalariesFacadeLocal lookupEmployeeSalariesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeeSalariesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeeSalariesFacade!com.fivecafe.session_beans.EmployeeSalariesFacadeLocal");
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
