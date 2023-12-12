package com.fivecafe.body.outbound;

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
public class OutboundDetailResponse {
    //Material
    private int materialID;
    private String materialName;
    private String unit;
    private String materialImage;
    //Outbound details
    private int quantity;
}