package com.quitter.bagr.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApiResponse<T> {
    private Status status;
    private T payload;
}
