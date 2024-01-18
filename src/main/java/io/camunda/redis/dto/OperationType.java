package io.camunda.redis.dto;

import java.util.Map;
import java.util.HashMap;
public enum OperationType {
    GET("get"), PUT("put"), DELETE( "delete" );
    private static Map<String, OperationType> operationTypeMap;


    private String operationType;

    OperationType(String operationType) {
        this.operationType = operationType;
    }

    public boolean equals(OperationType obj){
        if (obj == null) return false;

        if (! (obj instanceof OperationType) ){
            return false;
        }

        // this is of same enum type
        return  this.operationType.equalsIgnoreCase( obj.getOperationType() );
    }

    public String getOperationType() {
        return operationType;
    }

    public static OperationType getOperationType(String code) {
        if (operationTypeMap == null) {
            operationTypeMap = new HashMap<>();
            operationTypeMap.put("get",     OperationType.GET);
            operationTypeMap.put("put",     OperationType.PUT);
            operationTypeMap.put("delete",  OperationType.DELETE);
        }

        return operationTypeMap.get(code);
    }
}