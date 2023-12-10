    package com.fivecafe.body.bills;

import java.util.List;
import javax.validation.Valid;
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
public class CreateBill {
    @Valid
    @NotEmpty(message = "Details can't empty")
    private List<CreateBillDetail> details;
    
    @NotNull(message = "Employee ID is required")
    private int employeeID;

    @NotNull(message = "Bill status ID is required")
    private int billStatusID;

    @NotEmpty(message = "Card code cannot be empty")
    private String cardCode;

    private Object forError;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateBillDetail {
        @NotNull(message = "Bill status ID is required")
        private int productID;

        @NotNull(message = "quatity is required")
        private int quantity;

    }
}
