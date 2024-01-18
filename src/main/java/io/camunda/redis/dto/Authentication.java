package io.camunda.redis.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Authentication(
    @NotEmpty
    @TemplateProperty(group = "authentication", label = "Hostname", description = "Host server name, preferably FQDN")
    String hostname,

    @NotEmpty
    @Pattern(regexp = "^[1-9][0-9]*")
    @Size( min = 2 )
    @TemplateProperty(group = "authentication", label = "Port", description = "Port number")
    String port,

    @NotEmpty
    @TemplateProperty(group = "authentication", label = "Username", description = "The username for authentication")
    String user,

    @NotEmpty @TemplateProperty(group = "authentication", description = "The token or password for authentication")
    String token) { }
