package br.com.empresa.api_comercio.resources.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StandardError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss'Z'")
	private LocalDateTime timeStamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
