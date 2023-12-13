package com.fivecafe.body.mattopro;

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
public class MatToProResponse {
    private int materialID;
    private String materialName;
    private int productID;
    private String description;
}
