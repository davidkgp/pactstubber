package com.pact.parse.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ResponseData<J> {

    private int status;

    private Optional<J> body;

    private HeaderObj header;
}
