package io.camunda.redis.handler;

import io.camunda.redis.dto.MyConnectorRequest;
import io.camunda.redis.dto.MyConnectorResult;
import io.camunda.redis.dto.Authentication;

import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.error.ConnectorInputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.HostAndPort;

public class GetHandler extends BaseHandler {
    // Implements action for GET operation

    private static final Logger LOGGER = LoggerFactory.getLogger(GetHandler.class);

    public MyConnectorResult execute(MyConnectorRequest request){
        MyConnectorResult result = null;

        // TODO - implement GET from Redis operation
        if (true) {
            LOGGER.info("TODO - GET operation is under construction...!");

            validateAuthentication(request.authentication());

            result = runCommand(request.authentication(), request.key() );
        }

        LOGGER.info("Result = {}", result);
        return result;
    }

    private MyConnectorResult runCommand(Authentication authentication, String key){
        MyConnectorResult result = null;

        HostAndPort address = new HostAndPort( authentication.hostname(), Integer.parseInt( authentication.port().toString() ) );
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user( authentication.user() )
                .password( authentication.token() )
                .build();

        JedisPooled jedis = new JedisPooled(address, config);

        return new MyConnectorResult( jedis.get( key ) );
    }
}