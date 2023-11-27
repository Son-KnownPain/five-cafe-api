package com.fivecafe.controllers.api;

import com.fivecafe.body.billstatus.BillStatusResponse;
import com.fivecafe.body.billstatus.CreateBillStatus;
import com.fivecafe.body.billstatus.UpdateAndDeleteBillStatus;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.BillStatusesFacadeLocal;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.BillStatus.PREFIX + "")
public class BillStatusApiController {

    BillStatusesFacadeLocal billStatusesFacade = lookupBillStatusesFacadeLocal();

    @GetMapping(""+UrlProvider.BillStatus.ALL)
    public ResponseEntity<DataResponse<List<BillStatusResponse>>> all(){
        List<BillStatuses> allBillStatuses = billStatusesFacade.findAll();
        
        List<BillStatusResponse> data = new ArrayList<>();
        
        for(BillStatuses billStatus : allBillStatuses){
            data.add(BillStatusResponse.builder().billStatusID(billStatus.getBillStatusID()).billStatusValue(billStatus.getBillStatusValue()).build()); 
        }
        
        DataResponse<List<BillStatusResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all bill status");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.BillStatus.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateBillStatus reqBody, BindingResult br) throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        BillStatuses billStatusAdd = new BillStatuses();
        
        billStatusAdd.setBillStatusValue(reqBody.getBillStatusValue());
        
        billStatusesFacade.create(billStatusAdd);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                .success(true)
                .status(200)
                .message("Successfully create new bill status")
                .build());
    }
    
    @PutMapping(""+UrlProvider.BillStatus.UPDATE)
    public ResponseEntity<StandardResponse> update(@Valid @RequestBody UpdateAndDeleteBillStatus reqBody, BindingResult br) throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        BillStatuses billStatusUpdate = billStatusesFacade.find(reqBody.getBillStatusID());
        
        if(billStatusUpdate == null){
            br.rejectValue("billStatusID", "error.billStatusID", "The bill status ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        billStatusUpdate.setBillStatusValue(reqBody.getBillStatusValue());
        
        billStatusesFacade.edit(billStatusUpdate);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.BillStatus.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idBS= ids.split(",");
        
        for (String id : idBS){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            BillStatuses billStatus= billStatusesFacade.find(idInt);
            if(billStatus != null){
                billStatusesFacade.remove(billStatus);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete bill status")
                        .build()
        );

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
    
}
