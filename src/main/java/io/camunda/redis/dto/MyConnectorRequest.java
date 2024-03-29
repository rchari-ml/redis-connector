package io.camunda.redis.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record MyConnectorRequest(
    @NotNull  @TemplateProperty(group = "compose", type = PropertyType.Dropdown) OperationType operationType,
    @NotEmpty @TemplateProperty(group = "compose", type = PropertyType.Text)     String key,
    @Valid @NotNull Authentication authentication) {}
