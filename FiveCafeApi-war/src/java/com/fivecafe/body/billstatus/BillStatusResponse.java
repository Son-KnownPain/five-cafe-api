package com.fivecafe.body.billstatus;

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
public class BillStatusResponse {
    private int billStatusID;
    
    private String billStatusValue;
}
