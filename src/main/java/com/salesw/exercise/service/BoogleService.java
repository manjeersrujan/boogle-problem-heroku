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

	public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) throws SalesWhalesServiceException;

	public PlayGameResponse playGame(long id,  PlayGameRequest playGameRequest) throws SalesWhalesServiceException;

	public GetGameResponse getGame(long id) throws SalesWhalesServiceException;

}