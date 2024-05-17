package com.uber.driverfinder.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationDto {
    private String driverId;

    private double latitude;

    private double longitude;
}
