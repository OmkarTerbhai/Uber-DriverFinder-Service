package com.uber.driverfinder.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDriverDTO {
    private List<SaveDriverLocationDto> driverIds;
}
