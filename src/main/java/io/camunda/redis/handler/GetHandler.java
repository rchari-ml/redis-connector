package io.camunda.redis.handler;

import io.camunda.redis.dto.MyConnectorRequest;
import io.camunda.redis.dto.MyConnectorResult;

import io.camunda.connector.api.error.ConnectorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetHandler extends BaseHandler {
    // Implements action for GET operation

    private static final Logger LOGGER = LoggerFactory.getLogger(GetHandler.class);

    public MyConnectorResult execute(MyConnectorRequest request){
        MyConnectorResult result = null;

        // TODO - implement GET from Redis operation
        if (true) {
            LOGGER.info("TODO - GET operation is under construction...!");
            throw new ConnectorException("UNSUPPORTED", "Redis GET operation is not yet supported");
        }

        return result;
    }
}