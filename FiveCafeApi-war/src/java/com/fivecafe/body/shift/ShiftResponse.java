package com.fivecafe.body.shift;

import java.util.Date;
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
public class ShiftResponse {
    private int shiftID;
    private String name;
    private double salary;
    private String timeFrom;
    private String timeTo;
}
