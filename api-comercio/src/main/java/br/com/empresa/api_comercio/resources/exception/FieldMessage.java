package br.com.empresa.api_comercio.resources.exception;

import lombok.Data;

@Data
public class FieldMessage {

    private String fieldName;
    private String fieldMessage;

    public FieldMessage(){
    }

    public FieldMessage(String fieldName, String fieldMessage) {
        this.fieldName = fieldName;
        this.fieldMessage = fieldMessage;
    }
}
