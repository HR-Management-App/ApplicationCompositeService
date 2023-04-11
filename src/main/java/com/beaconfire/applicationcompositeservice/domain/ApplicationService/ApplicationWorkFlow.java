package com.beaconfire.applicationcompositeservice.domain.ApplicationService;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationWorkFlow {


    private int employee_id;

    private Date create_date;

    private Date last_modification_date;

    private String status;

    private String comment;
}