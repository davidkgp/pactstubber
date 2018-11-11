package com.pact.parse.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class RequestData<J> {

    private Optional<J> body;

    private String url;

    private HeaderObj header;

    private String method;


}
