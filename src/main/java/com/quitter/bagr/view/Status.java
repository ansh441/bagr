package com.quitter.bagr.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {
    private int code = 200;
    private String message = "OK";
    private String reason = "All_OK";
}
