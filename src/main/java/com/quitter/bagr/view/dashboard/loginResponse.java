package com.quitter.bagr.view.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class loginResponse {
    private String token;
}
