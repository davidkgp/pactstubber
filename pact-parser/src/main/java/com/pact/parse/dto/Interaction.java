package com.pact.parse.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Interaction<J> {

    private RequestData<J> requestBodyData;

    private ResponseData<J> responseBodyData;

}
