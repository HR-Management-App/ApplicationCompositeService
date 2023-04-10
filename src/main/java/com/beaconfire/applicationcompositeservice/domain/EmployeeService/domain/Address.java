package com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
}