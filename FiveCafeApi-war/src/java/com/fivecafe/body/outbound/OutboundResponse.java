package com.fivecafe.body.outbound;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Pattern;
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
public class OutboundResponse {
    private int outboundID;
    private String date;
    private List<OutboundDetailResponse> outboundDetail;
}