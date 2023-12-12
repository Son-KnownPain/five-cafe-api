package com.fivecafe.body.bills;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private int billID;
    private int employeeID;
    private int billStatusID;
    private String createDate;
    private String cardCode;
    private List<BillDetailsResponse> details;
}
