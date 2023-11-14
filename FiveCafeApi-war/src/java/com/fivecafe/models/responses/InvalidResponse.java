package com.fivecafe.models.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidResponse extends StandardResponse {
    private boolean invalid;
    private List<String> errors;
}
