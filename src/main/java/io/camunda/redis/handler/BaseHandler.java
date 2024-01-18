package io.camunda.redis.handler;

import io.camunda.redis.dto.MyConnectorRequest;
import io.camunda.redis.dto.MyConnectorResult;
import io.camunda.redis.dto.OperationType;

import io.camunda.connector.api.error.ConnectorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseHandler {
    // base handler class
    public abstract MyConnectorResult execute(MyConnectorRequest connectorRequest);

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);


    public static final BaseHandler getInstance(MyConnectorRequest connectorRequest){
        if ( OperationType.GET == connectorRequest.operationType() ){
            LOGGER.info("Returning instance of GetHandler...");
            return new GetHandler();
        }

        throw new ConnectorException("UNSUPPORTED", "Handler does not support this operation - " + connectorRequest.operationType() );
    }
}