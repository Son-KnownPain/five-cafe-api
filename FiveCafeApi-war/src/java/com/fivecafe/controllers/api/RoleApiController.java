package com.fivecafe.controllers.api;

import com.fivecafe.body.role.CreateOrUpdateRoleReq;
import com.fivecafe.body.role.RoleRes;
import com.fivecafe.entities.Roles;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.RolesFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Role.PREFIX)
public class RoleApiController {

    RolesFacadeLocal rolesFacade = lookupRolesFacadeLocal();
    
    @GetMapping(""+UrlProvider.Role.ALL)
    public ResponseEntity<DataResponse<List<RoleRes>>> all() {
        List<Roles> allRoles = rolesFacade.findAll();
        
        List<RoleRes> data = new ArrayList<>();
        
        for (Roles role : allRoles) {
            data.add(RoleRes.builder().roleID(role.getRoleID()).roleName(role.getRoleName()).build());
        }
        
        DataResponse<List<RoleRes>> res = new DataResponse<>();
        
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all role");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Role.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateOrUpdateRoleReq reqBody, BindingResult br) 
            throws MethodArgumentNotValidException 
    {
        if (rolesFacade.find(reqBody.getRoleID()) != null) {
            br.rejectValue("roleID", "error.roleID", "That Role ID is current existing");
        }
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        Roles roleNew = new Roles();
        
        roleNew.setRoleID(reqBody.getRoleID());
        roleNew.setRoleName(reqBody.getRoleName());
        
        rolesFacade.create(roleNew);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new role")
                        .build()
        );
    }
    
    @PutMapping(""+UrlProvider.Role.UPDATE)
    public ResponseEntity<StandardResponse> update(@Valid @RequestBody CreateOrUpdateRoleReq reqBody, BindingResult br) 
            throws MethodArgumentNotValidException 
    {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        Roles role = rolesFacade.find(reqBody.getRoleID());
        
        if (role == null) {
            br.rejectValue("roleID", "error.roleID", "That Role ID is not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        role.setRoleName(reqBody.getRoleName());
        
        rolesFacade.edit(role);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update role")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.Role.DELETE)
    public ResponseEntity<StandardResponse> delete(@RequestParam("ids") String ids) {
        
        String[] idArr = ids.split(",");
        
        for (String id : idArr) {
            Roles role = rolesFacade.find(id);
            
            if (role != null) {
                rolesFacade.remove(role);
            }
        }
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete roles")
                        .build()
        );
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
