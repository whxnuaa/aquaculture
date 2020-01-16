package com.jit.aquaculture.transport.tcp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EPReportData {
    String deveui;
    String date;
    float airtemp;
    float airhumi;
    float soiltemp;
    float soilhumi;
    float co2;
    float illu;
    float vlot;
    int seq;
}
