package com.pact.parse.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class InteractionDTO<J> {

    private List<Interaction<J>> interactions;
}
