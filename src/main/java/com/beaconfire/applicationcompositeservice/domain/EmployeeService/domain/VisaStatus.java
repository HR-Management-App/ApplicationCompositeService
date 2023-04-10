package com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisaStatus {

    private String visaType;

    private boolean activeFlag;

    private Date startDate;

    private Date endDate;

    private Date lastModificationDate;
}
