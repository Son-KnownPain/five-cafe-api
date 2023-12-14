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
public class OrderingReq {
    @Valid
    @NotEmpty(message = "Details can't empty")
    private List<OrderingDetail> details;
    
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
    public static class OrderingDetail {
        @NotNull(message = "Bill status ID is required")
        private int productID;

        @NotNull(message = "Quatity is required")
        @Min(value = 1, message = "Min quantity is 1")
        private int quantity;

    }
}
