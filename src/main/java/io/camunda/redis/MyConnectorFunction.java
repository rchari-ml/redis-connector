package io.camunda.redis;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.redis.dto.MyConnectorRequest;
import io.camunda.redis.dto.MyConnectorResult;
import io.camunda.redis.handler.BaseHandler;
import io.camunda.redis.handler.GetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "REDIS-CONNECTOR",
    inputVariables = {"authentication", "operation", "key" },
    type = "io.camunda:template:1")
@ElementTemplate(
    id = "io.camunda.connector.Template.v1",
    name = "Redis connector",
    version = 1,
    description = "Redis connector allows to interact with Redis cache. It supports get (read), put (write) and delete (remove cache record) operations. Supported data format is <key,value> pair, where key is a valid String and value is JSON.",
    icon = "icon.svg",
    documentationRef = "https://docs.camunda.io/docs/components/connectors/out-of-the-box-connectors/available-connectors-overview/",
    propertyGroups = {
      @ElementTemplate.PropertyGroup(id = "authentication", label = "Authentication"),
      @ElementTemplate.PropertyGroup(id = "compose", label = "Compose")
    },
    inputDataClass = MyConnectorRequest.class)
public class MyConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) {
    final var connectorRequest = context.bindVariables(MyConnectorRequest.class);
    return executeConnector(connectorRequest);
  }

  private MyConnectorResult executeConnector(final MyConnectorRequest connectorRequest) {
    // TODO: implement connector logic
    LOGGER.info("Executing my connector with request {}", connectorRequest);
    String key = connectorRequest.key();
    if (key != null && key.toLowerCase().startsWith("fail")) {
      throw new ConnectorException("FAIL", "Invalid key started with 'fail', was: " + key );
    }

    LOGGER.info("Starting base handler...");
    BaseHandler handler = BaseHandler.getInstance(connectorRequest);
    return handler.execute(connectorRequest);

  }
}
