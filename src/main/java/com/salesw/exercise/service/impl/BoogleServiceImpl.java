package com.salesw.exercise.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.salesw.exercise.biz.BoogleWordsGenerator;
import com.salesw.exercise.dao.BoogleDao;
import com.salesw.exercise.exception.SalesWhalesServiceException;
import com.salesw.exercise.model.BoogleBoard;
import com.salesw.exercise.model.CreateBoardRequest;
import com.salesw.exercise.model.CreateBoardResponse;
import com.salesw.exercise.model.GetGameResponse;
import com.salesw.exercise.model.PlayGameRequest;
import com.salesw.exercise.model.PlayGameResponse;
import com.salesw.exercise.service.BoogleService;


/**
 * @author yeddulamanjeersrujan
 *
 * May 11, 2019
 * 
 * Actual implementation of service
 *
 */
@Component
@EnableAsync
public class BoogleServiceImpl implements BoogleService {

	public BoogleServiceImpl() throws IOException {
		super();
	}

	@Autowired
	BoogleDao boogleDao;

	@Autowired
	BoogleWordsGenerator boogleWordsGenerator;

	/* (non-Javadoc)
	 * @see com.salesw.exercise.service.BoogleService#createBoard(com.salesw.exercise.model.CreateBoardRequest)
	 */
	@Override
	public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) throws SalesWhalesServiceException {

		validateAndHandleCreateBoardRequest(createBoardRequest);

		BoogleBoard boogleBoard = new BoogleBoard(createBoardRequest.getBoard(), createBoardRequest.getDuration());

		boogleWordsGenerator.generateWordsAndSave(boogleBoard);

		CreateBoardResponse createBoardResponse = getCreateBoardResponse(boogleBoard);

		return createBoardResponse;
	}

	/**
	 * @param createBoardRequest
	 * @throws SalesWhalesServiceException
	 */
	private void validateAndHandleCreateBoardRequest(CreateBoardRequest createBoardRequest)
			throws SalesWhalesServiceException {
		if (createBoardRequest.isRandom() == null) {
			throw new SalesWhalesServiceException("IS_RANDOM_FIELD_REQUIRED");
		}
		if (createBoardRequest.isRandom()) {
			createBoardRequest.setBoard(generateRandomBoard());
		} else if (StringUtils.isEmpty(createBoardRequest.getBoard())) {
			createBoardRequest.setBoard("T, A, P, *, E, A, K, S, O, B, R, S, S, *, X, D");
		}

		createBoardRequest.setBoard(createBoardRequest.getBoard().toUpperCase());
		if (createBoardRequest.getDuration() == null) {
			throw new SalesWhalesServiceException("BOARD_DURATION_IS_REQUIRED");
		}
		
		if (createBoardRequest.getDuration() <= 0L) {
			throw new SalesWhalesServiceException("BOARD_DURATION_MUST_BE_GREATER_THAN_ZERO");
		}
	}

	/* (non-Javadoc)
	 * @see com.salesw.exercise.service.BoogleService#playGame(long, com.salesw.exercise.model.PlayGameRequest)
	 */
	@Override
	public PlayGameResponse playGame(long id, PlayGameRequest playGameRequest) throws SalesWhalesServiceException {
		BoogleBoard boogleBoard = boogleDao.get(id);

		validatePlayGameRequest(playGameRequest, boogleBoard);

		playGame(boogleBoard, playGameRequest.getWord());
		
		PlayGameResponse playGameResponse = getPlayGameResponse(boogleBoard);
		
		return playGameResponse;
	}

	/**
	 * @param playGameRequest
	 * @param boogleBoard
	 * @throws SalesWhalesServiceException
	 */
	private void validatePlayGameRequest(PlayGameRequest playGameRequest, BoogleBoard boogleBoard)
			throws SalesWhalesServiceException {
		if (boogleBoard == null) {
			throw new SalesWhalesServiceException("NO_BOARD_FOUND");
		}

		if ((StringUtils.isEmpty(boogleBoard.getToken()))
				|| !boogleBoard.getToken().equals(playGameRequest.getToken())) {
			throw new SalesWhalesServiceException("INVALID_TOKEN");
		}

		if (boogleBoard.getExpiryTime() < new Date().getTime()) {
			throw new SalesWhalesServiceException("BOARD_EXPIRED");
		}

		playGameRequest.setWord(playGameRequest.getWord().toUpperCase());
	}

	/* (non-Javadoc)
	 * @see com.salesw.exercise.service.BoogleService#getGame(long)
	 * 
	 * Check the word and update map and points
	 */
	@Override
	public GetGameResponse getGame(long id) throws SalesWhalesServiceException {
		BoogleBoard boogleBoard = boogleDao.get(id);
		if (boogleBoard == null) {
			throw new SalesWhalesServiceException("NO_BOARD_FOUND");
		}
		return getGetGameResponse(boogleBoard);
	}

	/**
	 * @param boogleBoard
	 * @return
	 */
	private GetGameResponse getGetGameResponse(BoogleBoard boogleBoard) {
		GetGameResponse getGameResponse = new GetGameResponse();
		getGameResponse.setBoard(boogleBoard.getBoardString());
		getGameResponse.setDuration(boogleBoard.getDuration());
		getGameResponse.setId(boogleBoard.getId());
		getGameResponse.setPoints(boogleBoard.getPoints());
		long timeLeft = boogleBoard.getExpiryTime() - new Date().getTime();
		getGameResponse.setTime_left(timeLeft > 0 ? (timeLeft/1000) : 0);
		getGameResponse.setToken(boogleBoard.getToken());
		return getGameResponse;
	}

	/**
	 * @return
	 */
	private String generateRandomBoard() {
		StringBuffer buffer = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < 16; i++) {
			int num = rand.nextInt(26 + 2);
			if (num < 26) {
				buffer.append((char) ('A' + num));
				buffer.append(',');
			} else {
				buffer.append('*');
				buffer.append(',');
			}
		}
		return buffer.delete(buffer.length() - 1, buffer.length()).toString();
	}

	/**
	 * @param boogleBoard
	 * @return
	 */
	private CreateBoardResponse getCreateBoardResponse(BoogleBoard boogleBoard) {
		CreateBoardResponse createBoardResponse = new CreateBoardResponse();
		createBoardResponse.setBoard(boogleBoard.getBoardString());
		createBoardResponse.setDuration(boogleBoard.getDuration());
		createBoardResponse.setId(boogleBoard.getId());
		createBoardResponse.setToken(boogleBoard.getToken());
		return createBoardResponse;
	}

	/**
	 * @param boogleBoard
	 * @return
	 */
	private PlayGameResponse getPlayGameResponse(BoogleBoard boogleBoard) {
		PlayGameResponse playGameResponse = new PlayGameResponse();
		playGameResponse.setBoard(boogleBoard.getBoardString());
		playGameResponse.setDuration(boogleBoard.getDuration());
		playGameResponse.setId(boogleBoard.getId());
		playGameResponse.setPoints(boogleBoard.getPoints());
		playGameResponse.setTime_left(boogleBoard.getExpiryTime() - new Date().getTime());
		playGameResponse.setToken(boogleBoard.getToken());
		return playGameResponse;
	}

	/**
	 * @param boogleBoard
	 * @param word
	 * @throws SalesWhalesServiceException
	 */
	private void playGame(BoogleBoard boogleBoard, String word) throws SalesWhalesServiceException {
		if (boogleBoard != null) {
			synchronized (boogleBoard) {
				if (!boogleBoard.getWords().contains(word)) {
					throw new SalesWhalesServiceException("INVALID_WORD_NO_POINTS");
				}

				boogleBoard.setPoints(boogleBoard.getPoints() + word.length());
				boogleBoard.getWords().remove(word);
			}
		}
	}

}
