package com.fivecafe.body.imports;

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
public class ImportDetailRes {
    // Material data
    private int materialID;
    private String materialName;
    private String unit;
    private String materialImage;
    // Supplier data
    private int supplierID;
    private String supplierContactName;
    private String supplierContactNumber;
    private String suppplierAddress;
    // Import detail data
    private double unitPrice;
    private int quantity;
}
