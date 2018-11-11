package com.pact.parse.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class HeaderObj {

    private Map<String, List> maps;
}
