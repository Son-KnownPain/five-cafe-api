package com.fivecafe.body.imports;

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
public class ImportRes {
    private int importID;
    private String importDate;
    private double unitPrice;
    private int quantity;
    private int supplierID;
    private List<ImportDetailRes> details;
}