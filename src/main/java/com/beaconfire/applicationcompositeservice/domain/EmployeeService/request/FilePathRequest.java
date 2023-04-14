package com.beaconfire.applicationcompositeservice.domain.EmployeeService.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilePathRequest {
    int emp_id;
    String title;
    String path;
}
