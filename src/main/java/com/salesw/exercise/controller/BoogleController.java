package com.salesw.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.salesw.exercise.exception.SalesWhalesServiceException;
import com.salesw.exercise.model.CreateBoardRequest;
import com.salesw.exercise.model.CreateBoardResponse;
import com.salesw.exercise.model.GetGameResponse;
import com.salesw.exercise.model.PlayGameRequest;
import com.salesw.exercise.model.PlayGameResponse;
import com.salesw.exercise.service.BoogleService;

/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 *
 */
@RestController
public class BoogleController {

	@Autowired
	BoogleService boogleService;

	/**
	 * @param createBoardRequest
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	@RequestMapping(value = "/games", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateBoardResponse> createBoard(@RequestBody CreateBoardRequest createBoardRequest) throws SalesWhalesServiceException {
		CreateBoardResponse createBoardResponse = boogleService.createBoard(createBoardRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createBoardResponse);

	}

	/**
	 * @param id
	 * @param playGameRequest
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	@RequestMapping(value = "/games/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlayGameResponse> playGame(@PathVariable Long id,
			@RequestBody PlayGameRequest playGameRequest) throws SalesWhalesServiceException {
		PlayGameResponse playGameResponse = boogleService.playGame(id, playGameRequest);
		return ResponseEntity.status(HttpStatus.OK).body(playGameResponse);
	}

	/**
	 * @param id
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	@RequestMapping(value = "/games/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetGameResponse> getGame(@PathVariable Long id) throws SalesWhalesServiceException {
		GetGameResponse getGameResponse = boogleService.getGame(id);
		return ResponseEntity.status(HttpStatus.OK).body(getGameResponse);
	}

}
