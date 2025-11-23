package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ToolInvocation {
    private String tool;                  // ej: "consultarUsoMonopatines"
    private Map<String, Object> arguments;
}
