package com.fivecafe.controllers.api;

import com.fivecafe.body.shift.CreateShift;
import com.fivecafe.body.shift.ShiftResponse;
import com.fivecafe.body.shift.UpdateAndDeleteShift;
import com.fivecafe.entities.Shifts;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Shift.PREFIX + "")
public class ShiftApiController {

    ShiftsFacadeLocal shiftsFacade = lookupShiftsFacadeLocal();

    @GetMapping(""+UrlProvider.Shift.ALL)
    public ResponseEntity<DataResponse<List<ShiftResponse>>> all(){
        List<Shifts> allShifts = shiftsFacade.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        List<ShiftResponse> data = new ArrayList<>();
        
        for(Shifts shifts : allShifts){
            data.add(ShiftResponse.builder()
                    .shiftID(shifts.getShiftID())
                    .name(shifts.getName())
                    .salary(shifts.getSalary())
                    .timeFrom(sdf.format(shifts.getTimeFrom()))
                    .timeTo(sdf.format(shifts.getTimeTo()))
                    .build());
        }
        
        DataResponse<List<ShiftResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all shift");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Shift.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateShift reqBody, BindingResult br) 
        throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Shifts shiftAdd = new Shifts();
        
        shiftAdd.setName(reqBody.getName());
        shiftAdd.setSalary(reqBody.getSalary());
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date timeFrom = sdf.parse(reqBody.getTimeFrom());
            Date timeTo = sdf.parse(reqBody.getTimeTo());
            shiftAdd.setTimeFrom(timeFrom);
            shiftAdd.setTimeTo(timeTo);
        } catch (ParseException  e) {
             e.printStackTrace();
        }
        
        shiftsFacade.create(shiftAdd);
        
        return ResponseEntity.ok(StandardResponse
                .builder()
                .success(true)
                .status(200)
                .message("Successfully create new material category")
                .build());
    }
    
    @PutMapping(""+UrlProvider.Shift.UPDATE)
    public ResponseEntity<StandardResponse> update(@Valid @RequestBody UpdateAndDeleteShift reqBody, BindingResult br) 
            throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Shifts shiftUpdate = shiftsFacade.find(reqBody.getShiftID());
        
        if(shiftUpdate == null){
            br.rejectValue("shiftID", "error.shiftID", "The shift ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        shiftUpdate.setName(reqBody.getName());
        shiftUpdate.setSalary(reqBody.getSalary());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date timeFrom = sdf.parse(reqBody.getTimeFrom());
            Date timeTo = sdf.parse(reqBody.getTimeTo());
            shiftUpdate.setTimeFrom(timeFrom);
            shiftUpdate.setTimeTo(timeTo);
        } catch (ParseException  e) {
             e.printStackTrace();
        }

        shiftsFacade.edit(shiftUpdate);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.Shift.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idsh= ids.split(",");
        
        for (String id : idsh){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            Shifts shifts= shiftsFacade.find(idInt);
            if(shifts != null){
                shiftsFacade.remove(shifts);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete shift")
                        .build()
        );

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
