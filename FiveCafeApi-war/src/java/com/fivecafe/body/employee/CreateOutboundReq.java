package com.fivecafe.body.employee;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOutboundReq {
    @Valid
    @NotEmpty(message = "Details can't empty")
    private List<CreateOutboundDetailReq> details;
    
    private Object forError;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateOutboundDetailReq {
        @NotNull(message = "Material ID is required")
        private int materialID;
        
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Min quantity is 1")
        private int quantity;
    }
}
