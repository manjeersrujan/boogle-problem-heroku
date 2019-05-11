package com.salesw.exercise.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesw.exercise.model.GenericServiceResponse;

/**
 * @author yeddulamanjeersrujan
 *
 * May 11, 2019
 *
 */
@ControllerAdvice("com.salesw")
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	/**
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * 
	 *             Loads all configured error responses form errorConfig.json.
	 */
	public ExceptionControllerAdvice() throws JsonParseException, JsonMappingException, IOException {
		super();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("errorConfig.json");
		HashMap<String, LinkedHashMap> errorHashMapMap = (HashMap<String, LinkedHashMap>) new ObjectMapper()
				.readValue(in, HashMap.class);

		for (String key : errorHashMapMap.keySet()) {
			LinkedHashMap<String, Object> errorMessageHashMap = errorHashMapMap.get(key);
			errorCodeMap.put(key, new SalesWhalesServiceError((Integer) errorMessageHashMap.get("status"),
					(String) errorMessageHashMap.get("message")));
		}
	}

	Map<String, SalesWhalesServiceError> errorCodeMap = new HashMap<>();

	/**
	 * @param request
	 * @param ex
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(SalesWhalesServiceException.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		SalesWhalesServiceError salesWhalesServiceError = null;
		if (ex != null) {
			ex.printStackTrace();
			salesWhalesServiceError = errorCodeMap.get(ex.getMessage());
			if (salesWhalesServiceError == null) {
				salesWhalesServiceError = SalesWhalesServiceError.getGenericError();
			} else if (ex instanceof SalesWhalesServiceException) {
				if (ex.getMessage() == null) {
					salesWhalesServiceError = SalesWhalesServiceError.getGenericError();
				} else {
					salesWhalesServiceError = errorCodeMap.get(ex.getMessage());
					if (salesWhalesServiceError == null) {
						salesWhalesServiceError = SalesWhalesServiceError.getGenericError();
					}
				}

			} else {
				salesWhalesServiceError = SalesWhalesServiceError.getGenericError();
			}
		} else {
			salesWhalesServiceError = SalesWhalesServiceError.getGenericError();
		}
		return new ResponseEntity<GenericServiceResponse>(
				new GenericServiceResponse("FAIL", salesWhalesServiceError.message, null),
				HttpStatus.valueOf(salesWhalesServiceError.status));
	}
}