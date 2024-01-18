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

  // Use below values only for scenarios for successful operations against Redis.
  // Make sure to set the env using the linux command: export a=value-for-a
  public static final String redisHost    = System.getenv("redisHost");
  public static final String redisPort    = System.getenv("redisPort");
  public static final String redisUser    = System.getenv("redisUser");
  public static final String redisSecret  = System.getenv("redisSecret");

  @Test
  void shouldThrowWithErrorCodeWhenKeyStartsWithFail() throws Exception {
    // given
    var input = new MyConnectorRequest(
            OperationType.GET,
            "fail",
            new Authentication(
                        "redis-fqdn.cloud.redislabs.com",
                        "80",
                        "api-user-2",
                        "test token" )
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
        .hasMessageContaining("Invalid key started with 'fail'")
        .extracting("errorCode").isEqualTo("FAIL");
  }

  @Test
  void shouldWarn_NoDataFoundForKey() throws Exception {

    // For security, the details of the Redis server and access is read from env data.
    // given
    var input = new MyConnectorRequest(
            OperationType.GET,
            "no-data",
            new Authentication(
                    redisHost,
                    redisPort,
                    redisUser,
                    redisSecret )
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
            .extracting("jsonDataAsString")
            .isEqualTo( null )
    ;
  }
  @Test
  void shouldReturnJSONDataForGetOperation() throws Exception {

    // For security, the details of the Redis server and access is read from env data.
    // given
    var input = new MyConnectorRequest(
            OperationType.GET,
            "make-labs",
            new Authentication(
                          redisHost,
                          redisPort,
                          redisUser,
                          redisSecret )
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
            .extracting("jsonDataAsString")
            .matches( p -> p.toString().indexOf("camunda-chapter-bengaluru") >= 0
                            && p.toString().indexOf("makelabs.in") >= 0  )
    ;
  }

}