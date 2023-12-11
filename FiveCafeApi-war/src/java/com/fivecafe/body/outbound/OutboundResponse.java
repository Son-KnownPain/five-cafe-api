package com.fivecafe.body.outbound;

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
public class OutboundResponse {
    private int outboundID;
    private String date;
    private int employeeID;
    private String name;
    private List<OutboundDetailResponse> details;
}