package com.fivecafe.controllers.api;

import com.fivecafe.body.outbound.CreateOutboundResponse;
import com.fivecafe.body.outbound.OutboundDetailResponse;
import com.fivecafe.body.outbound.OutboundResponse;
import com.fivecafe.body.outbound.UpdateOutboundResponse;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.OutboundDetails;
import com.fivecafe.entities.OutboundDetailsPK;
import com.fivecafe.entities.Outbounds;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import com.fivecafe.session_beans.OutboundDetailsFacadeLocal;
import com.fivecafe.session_beans.OutboundsFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.expression.ParseException;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Outbound.PREFIX + "")
public class OutboundApiController {

    OutboundDetailsFacadeLocal outboundDetailsFacade = lookupOutboundDetailsFacadeLocal();

    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    OutboundsFacadeLocal outboundsFacade = lookupOutboundsFacadeLocal();

    @GetMapping("" + UrlProvider.Outbound.ALL)
    public ResponseEntity<?> allOutbound() {

        List<Outbounds> allOut = outboundsFacade.findAll();

        //format date
        SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy");
        DataResponse<List<OutboundResponse>> responses = new DataResponse<>();
        List<OutboundResponse> dataOut = new ArrayList<>();

        for (Outbounds outboundItem : allOut) {
            List<OutboundDetailResponse> listOutboundDetail = new ArrayList<>();
            for (OutboundDetails outboundDetailItem : outboundItem.getOutboundDetailsCollection()) {
                listOutboundDetail.add(
                        OutboundDetailResponse.builder()
                                .employeeID(outboundDetailItem.getOutbounds().getEmployeeID().getEmployeeID())
                                .name(outboundDetailItem.getOutbounds().getEmployeeID().getName())
                                .phone(outboundDetailItem.getOutbounds().getEmployeeID().getPhone())
                                .image(outboundDetailItem.getOutbounds().getEmployeeID().getImage())
                                .username(outboundDetailItem.getOutbounds().getEmployeeID().getUsername())
                                .password(outboundDetailItem.getOutbounds().getEmployeeID().getPassword())
                                .materialID(outboundDetailItem.getMaterials().getMaterialID())
                                .materialName(outboundDetailItem.getMaterials().getName())
                                .unit(outboundDetailItem.getMaterials().getUnit())
                                .materialImage(outboundDetailItem.getMaterials().getImage())
                                .quantityInStock(outboundDetailItem.getMaterials().getQuantityInStock())
                                .quantity(outboundDetailItem.getQuantity())
                                .build()
                );
            }

            //add item
            dataOut.add(
                    OutboundResponse.builder()
                            .outboundID(outboundItem.getOutboundID())
                            .date(formDate.format(outboundItem.getDate()))
                            .outboundDetail(listOutboundDetail)
                            .build()
            );
        }

        responses.setSuccess(true);
        responses.setStatus(200);
        responses.setMessage("Successfully get all outbound");
        responses.setData(dataOut);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("" + UrlProvider.Outbound.STORE)
    public ResponseEntity<StandardResponse> storeOutbound(@Valid @RequestBody CreateOutboundResponse createResponse, BindingResult br) throws MethodArgumentNotValidException, java.text.ParseException {

        Employees employees = employeesFacade.find(createResponse.getEmployeeID());

        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "That employee ID is current existing");
        }

        Materials materials = materialsFacade.find(createResponse.getMaterialID());
        if (materials == null) {
            br.rejectValue("materialID", "error.materialID", "That material ID is current existing");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // create outbound object
        Outbounds obNew = new Outbounds();

        //format date
        SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formDate.parse(createResponse.getDate());
            obNew.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        obNew.setEmployeeID(employees);

        outboundsFacade.create(obNew);

        // Create outbound detail
        OutboundDetails obDetail = new OutboundDetails();
        obDetail.setQuantity(createResponse.getQuantity());

        // Create outbound detail PK
        OutboundDetailsPK obDetailPK = new OutboundDetailsPK();
        obDetailPK.setOutboundID(obNew.getOutboundID());
        obDetailPK.setMaterialID(materials.getMaterialID());
        obDetail.setOutboundDetailsPK(obDetailPK);

        outboundDetailsFacade.create(obDetail);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new outbound")
                        .build()
        );
    }
    
    @PutMapping(""+UrlProvider.Outbound.UPDATE)
    public ResponseEntity<StandardResponse> updateOutbound(@Valid @RequestBody UpdateOutboundResponse updateRes, BindingResult br) throws  MethodArgumentNotValidException, java.text.ParseException{
        
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Employees employees = employeesFacade.find(updateRes.getEmployeeID());

        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "That employee ID is current existing");
        }

        Materials materials = materialsFacade.find(updateRes.getMaterialID());
        if (materials == null) {
            br.rejectValue("materialID", "error.materialID", "That material ID is current existing");
        }
        
        Outbounds updateOutbound = outboundsFacade.find(updateRes.getOutboundID());
        
        if(updateOutbound == null){
            br.rejectValue("outboundID", "error.outboundID", "That outbound ID is current existing");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        //format date
        SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formDate.parse(updateRes.getDate());
            updateOutbound.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updateOutbound.setEmployeeID(employees);

        outboundsFacade.edit(updateOutbound);

        // Create outbound detail
        OutboundDetails obDetail = new OutboundDetails();
        obDetail.setQuantity(updateRes.getQuantity());

        // Create outbound detail PK
        OutboundDetailsPK obDetailPK = new OutboundDetailsPK();
        obDetailPK.setOutboundID(updateOutbound.getOutboundID());
        obDetailPK.setMaterialID(materials.getMaterialID());
        obDetail.setOutboundDetailsPK(obDetailPK);

        outboundDetailsFacade.edit(obDetail);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                    .status(200)
                    .success(true)
                    .message("Successfully update outbound")
                    .build()
        );
    }
    
    @DeleteMapping("" + UrlProvider.Outbound.DELETE)
    public ResponseEntity<?> deleteOutbound(@RequestParam("ids") String ids) {
        String[] idsPC = ids.split(",");
        
        for (String id : idsPC) {
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            
            Outbounds obs = outboundsFacade.find(idInt);
            if(obs != null){
                outboundsFacade.remove(obs);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete outbound")
                        .build()
        );
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

    private EmployeesFacadeLocal lookupEmployeesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeesFacade!com.fivecafe.session_beans.EmployeesFacadeLocal");
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

    private OutboundDetailsFacadeLocal lookupOutboundDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OutboundDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/OutboundDetailsFacade!com.fivecafe.session_beans.OutboundDetailsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
