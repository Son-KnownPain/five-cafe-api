package com.fivecafe.body.imports;

import javax.validation.constraints.NotNull;
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
public class DeleteImportDetailReq {
    @NotNull(message = "Import ID is required")
    private int importID;
    
    @NotNull(message = "Material ID is required")
    private int materialID;
}
