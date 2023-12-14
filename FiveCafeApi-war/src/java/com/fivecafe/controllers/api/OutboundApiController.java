package com.fivecafe.controllers.api;

import com.fivecafe.body.outbound.CreateOutboundDetailReq;
import com.fivecafe.body.outbound.CreateOutboundResq;
import com.fivecafe.body.outbound.OutboundDetailResponse;
import com.fivecafe.body.outbound.OutboundResponse;
import com.fivecafe.body.outbound.UpdateOutboundResq;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Outbound.PREFIX + "")
public class OutboundApiController {

    OutboundDetailsFacadeLocal outboundDetailsFacade = lookupOutboundDetailsFacadeLocal();

    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    OutboundsFacadeLocal outboundsFacade = lookupOutboundsFacadeLocal();

    @GetMapping("" + UrlProvider.Outbound.ALL)
    public ResponseEntity<?> allOutbound(HttpServletRequest request) {

        List<Outbounds> allOut = outboundsFacade.findAll();

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
        responses.setMessage("Successfully get all outbound");
        responses.setData(dataOut);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("" + UrlProvider.Outbound.STORE)
    public ResponseEntity<StandardResponse> storeOutbound(
            @Valid @RequestBody CreateOutboundResq createResponse, BindingResult br
    ) throws MethodArgumentNotValidException, java.text.ParseException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Employees employees = employeesFacade.find(createResponse.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "That employee ID does not exist");
        }

        for (CreateOutboundResq.CreateOutboundDetailReq detail : createResponse.getDetails()) {
            Materials materials = materialsFacade.find(detail.getMaterialID());

            if (materials.getQuantityInStock() < detail.getQuantity()) {
                br.rejectValue("forError", "error.forError", "Not enough materials, please add more materials");
                break;
            }
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        for (CreateOutboundResq.CreateOutboundDetailReq detail : createResponse.getDetails()) {
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
            for (CreateOutboundResq.CreateOutboundDetailReq detail : createResponse.getDetails()) {
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

    @PostMapping("" + UrlProvider.Outbound.STORE_MAT_ITEM)
    public ResponseEntity<?> storeMaterialItem(@Valid @RequestBody CreateOutboundDetailReq reqBody, BindingResult br) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate
        Outbounds outbounds = outboundsFacade.find(reqBody.getOutboundID());
        if (outbounds == null) {
            br.rejectValue("outboundID", "error.outboundID", "Outbound ID you provided is not exist");
        }

        Materials materials = materialsFacade.find(reqBody.getMaterialID());
        if (materials == null) {
            br.rejectValue("materialID", "error.materialID", "Material ID you provided is not exist");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Valid
        OutboundDetailsPK idPK = new OutboundDetailsPK();
        idPK.setOutboundID(reqBody.getOutboundID());
        idPK.setMaterialID(reqBody.getMaterialID());

        OutboundDetails outboundDetailNew = new OutboundDetails();

        outboundDetailNew.setOutboundDetailsPK(idPK);
        outboundDetailNew.setOutbounds(outbounds);
        outboundDetailNew.setMaterials(materials);
        outboundDetailNew.setQuantity(reqBody.getQuantity());

        // Update material quantity in stock
        materials.setQuantityInStock(materials.getQuantityInStock() - reqBody.getQuantity());

        outboundDetailsFacade.create(outboundDetailNew);
        materialsFacade.edit(materials);

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update outbound material item data")
                .build();

        return ResponseEntity.ok(res);
    }

    @PutMapping("" + UrlProvider.Outbound.UPDATE)
    public ResponseEntity<StandardResponse> updateOutbound(@Valid @RequestBody UpdateOutboundResq updateRes, BindingResult br) throws MethodArgumentNotValidException, java.text.ParseException {

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Outbounds updateOutbound = outboundsFacade.find(updateRes.getOutboundID());

        if (updateOutbound == null) {
            br.rejectValue("outboundID", "error.outboundID", "Outbound ID you provided is not exist");
        }

        Materials materials = materialsFacade.find(updateRes.getMaterialID());
        if (materials == null) {
            br.rejectValue("materialID", "error.materialID", "Material ID you provided is not exist");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Valid
        OutboundDetailsPK idPK = new OutboundDetailsPK();
        idPK.setOutboundID(updateRes.getOutboundID());
        idPK.setMaterialID(updateRes.getMaterialID());

        OutboundDetails outboundDetailNew = outboundDetailsFacade.find(idPK);

        if (outboundDetailNew != null) {

            // Update material quantity in stock
            materials.setQuantityInStock(materials.getQuantityInStock() - (updateRes.getQuantity() - outboundDetailNew.getQuantity()));

            // Update import detail
            outboundDetailNew.setQuantity(updateRes.getQuantity());

            outboundDetailsFacade.edit(outboundDetailNew);
            materialsFacade.edit(materials);
        }

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .status(200)
                        .success(true)
                        .message("Successfully update outbound")
                        .build()
        );
    }

    @DeleteMapping("" + UrlProvider.Import.DELETE_MAT_ITEM)
    public ResponseEntity<?> deleteMatItem(@RequestParam("matID") int matID, @RequestParam("outboundID") int outboundID) {

        OutboundDetailsPK idPK = new OutboundDetailsPK();
        idPK.setOutboundID(outboundID);
        idPK.setMaterialID(matID);

        OutboundDetails outboundDetails = outboundDetailsFacade.find(idPK);

        if (outboundDetails == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found outbound detail with outbound id and material id you provided");

            return ResponseEntity.ok(res);
        }

        outboundDetailsFacade.remove(outboundDetails);

        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully delete outbound detail");

        return ResponseEntity.ok(res);
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
            if (obs != null) {
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

    @GetMapping("" + UrlProvider.Outbound.SEARCH)
    public ResponseEntity<DataResponse<List<OutboundResponse>>> searchOutbound(
            @RequestParam(name = "dateForm", defaultValue = "") Date dateForm,
            @RequestParam(name = "dateTo", defaultValue = "") Date dateTo,
            HttpServletRequest request) {

        //format date
        SimpleDateFormat formDate = new SimpleDateFormat("dd/MM/yyyy");
        formDate.setLenient(false); // Không cho phép ngày tháng không hợp lệ

        List<Outbounds> allOuts = outboundsFacade.getOutboundsByDaterange(dateForm, dateTo);

        List<OutboundResponse> data = new ArrayList<>();

        for (Outbounds outbounds : allOuts) {
            data.add(
                    OutboundResponse.builder()
                            .outboundID(outbounds.getOutboundID())
                            .employeeID(outbounds.getEmployeeID().getEmployeeID())
                            .date(formDate.format(outbounds.getDate()))
                            .build()
            );
        }

        DataResponse<List<OutboundResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching outbound");
        res.setData(data);
        return ResponseEntity.ok(res);
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
