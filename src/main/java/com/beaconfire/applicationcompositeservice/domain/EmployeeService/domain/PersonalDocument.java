package com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDocument {

    private String path;

    private String title;

    private String comment;

    private Date createDate;
}
