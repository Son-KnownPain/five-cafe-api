package com.fivecafe.body.outbound;

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
public class CreateOutboundResq {

    @Valid
    @NotEmpty(message = "Details can't empty")
    private List<CreateOutboundDetailReq> details;

    @NotNull(message = "Employee ID is required")
    private int employeeID;

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
//    @NotEmpty(message = "Date is required")
//    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Invalid date format (dd/MM/yyyy)")
//    private String date;

}
