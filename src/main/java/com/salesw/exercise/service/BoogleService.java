package com.salesw.exercise.service;

import com.salesw.exercise.exception.SalesWhalesServiceException;
import com.salesw.exercise.model.CreateBoardRequest;
import com.salesw.exercise.model.CreateBoardResponse;
import com.salesw.exercise.model.GetGameResponse;
import com.salesw.exercise.model.PlayGameRequest;
import com.salesw.exercise.model.PlayGameResponse;

/**
 * @author yeddulamanjeersrujan
 *
 */
public interface BoogleService {

	/**
	 * @param createBoardRequest
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) throws SalesWhalesServiceException;

	/**
	 * @param id
	 * @param playGameRequest
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	public PlayGameResponse playGame(long id,  PlayGameRequest playGameRequest) throws SalesWhalesServiceException;

	/**
	 * @param id
	 * @return
	 * @throws SalesWhalesServiceException
	 */
	public GetGameResponse getGame(long id) throws SalesWhalesServiceException;

}