package io.camunda.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import io.camunda.redis.dto.Authentication;
import io.camunda.redis.dto.MyConnectorRequest;
import io.camunda.redis.dto.MyConnectorResult;
import io.camunda.redis.dto.OperationType;
import org.junit.jupiter.api.Test;

public class MyFunctionTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturnReceivedMessageWhenExecute() throws Exception {
    // given
    var input = new MyConnectorRequest(
            "Hello World!", OperationType.GET, "key",
            new Authentication("testUser", "testToken")
    );
    var function = new MyConnectorFunction();
    var context = OutboundConnectorContextBuilder.create()
      .variables(objectMapper.writeValueAsString(input))
      .build();
    // when
    var result = function.execute(context);
    // then
    assertThat(result)
      .isInstanceOf(MyConnectorResult.class)
      .extracting("myProperty")
      .isEqualTo("Message received: Hello World!");
  }

  @Test
  void shouldThrowWithErrorCodeWhenMessageStartsWithFail() throws Exception {
    // given
    var input = new MyConnectorRequest(
            "Fail: unauthorized", OperationType.GET,  "key",
            new Authentication("testUser", "testToken")
    );
    var function = new MyConnectorFunction();
    var context = OutboundConnectorContextBuilder.create()
        .variables(objectMapper.writeValueAsString(input))
        .build();
    // when
    var result = catchThrowable(() -> function.execute(context));
    // then
    assertThat(result)
        .isInstanceOf(ConnectorException.class)
        .hasMessageContaining("started with 'fail'")
        .extracting("errorCode").isEqualTo("FAIL");
  }

  @Test
  void shouldThrowWithErrorCodeForGetOperation() throws Exception {
    // given
    var input = new MyConnectorRequest(
            "connection-string-url", OperationType.GET,  "key",
            new Authentication("testUser", "testToken")
    );
    var function = new MyConnectorFunction();
    var context = OutboundConnectorContextBuilder.create()
            .variables(objectMapper.writeValueAsString(input))
            .build();
    // when
    var result = catchThrowable(() -> function.execute(context));
    // then
    assertThat(result)
            .isInstanceOf(ConnectorException.class)
            .hasMessageContaining("Redis GET operation is not yet supported")
            .extracting("errorCode").isEqualTo("UNSUPPORTED");
  }
}