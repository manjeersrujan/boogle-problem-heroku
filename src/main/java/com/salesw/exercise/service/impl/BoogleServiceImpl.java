package com.salesw.exercise.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
 */
@Component
public class BoogleServiceImpl implements BoogleService {

	public BoogleServiceImpl() throws IOException {
		super();
		BoogleServiceImpl.loadDict();
	}

	@Autowired
	BoogleDao boogleDao;

	private static Set<String> dict = new HashSet<>();

	private static void loadDict() throws IOException {
		InputStream in = BoogleServiceImpl.class.getClassLoader().getResourceAsStream("dictionary.txt");
		IOUtils.readLines(in).stream().forEach(x -> dict.add(((String) x).trim().toUpperCase()));
	}

	static class TrieNode {
		public static final int size = 26;
		TrieNode[] child = new TrieNode[size];
		boolean leaf;

		public TrieNode() {
			leaf = false;
			for (int i = 0; i < size; i++)
				child[i] = null;
		}
	}

	public static void main(String[] args) throws IOException, SalesWhalesServiceException {
		BoogleServiceImpl boogleServiceImpl = new BoogleServiceImpl();
		CreateBoardRequest x = new CreateBoardRequest();
		x.setDuration(100000L);
		x.setRandom(false);
		x.setBoard("A, C, E, D, L, U, G, *, E, *, H,T, G, A, F, K");
		            
		boogleServiceImpl.createBoard(x);
	}

	@Override
	public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) throws SalesWhalesServiceException {

		validateAndHandleCreateBoardRequest(createBoardRequest);

		BoogleBoard boogleBoard = new BoogleBoard(createBoardRequest.getBoard(), createBoardRequest.getDuration());

		generateWords(boogleBoard);

		boogleDao.save(boogleBoard);

		CreateBoardResponse createBoardResponse = getCreateBoardResponse(boogleBoard);

		return createBoardResponse;
	}

	private void validateAndHandleCreateBoardRequest(CreateBoardRequest createBoardRequest)
			throws SalesWhalesServiceException {
		if(createBoardRequest.isRandom() ==null) {
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
	}

	@Override
	public PlayGameResponse playGame(long id, PlayGameRequest playGameRequest) throws SalesWhalesServiceException {
		BoogleBoard boogleBoard = boogleDao.get(id);

		validatePlayGameRequest(playGameRequest, boogleBoard);

		playGame(boogleBoard, playGameRequest.getWord());

		boogleDao.save(boogleBoard);

		PlayGameResponse playGameResponse = getPlayGameResponse(boogleBoard);

		return playGameResponse;
	}

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

	@Override
	public GetGameResponse getGame(long id) throws SalesWhalesServiceException {
		BoogleBoard boogleBoard = boogleDao.get(id);
		if(boogleBoard == null) {
			throw new SalesWhalesServiceException("NO_BOARD_FOUND");
		}
		return getGetGameResponse(boogleBoard);
	}

	private GetGameResponse getGetGameResponse(BoogleBoard boogleBoard) {
		GetGameResponse getGameResponse = new GetGameResponse();
		getGameResponse.setBoard(boogleBoard.getBoardString());
		getGameResponse.setDuration(boogleBoard.getDuration());
		getGameResponse.setId(boogleBoard.getId());
		getGameResponse.setPoints(boogleBoard.getPoints());
		long timeLeft = boogleBoard.getExpiryTime() - new Date().getTime();
		getGameResponse.setTime_left(timeLeft > 0 ? timeLeft : 0);
		getGameResponse.setToken(boogleBoard.getToken());
		return getGameResponse;
	}

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

	private CreateBoardResponse getCreateBoardResponse(BoogleBoard boogleBoard) {
		CreateBoardResponse createBoardResponse = new CreateBoardResponse();
		createBoardResponse.setBoard(boogleBoard.getBoardString());
		createBoardResponse.setDuration(boogleBoard.getDuration());
		createBoardResponse.setId(boogleBoard.getId());
		createBoardResponse.setToken(boogleBoard.getToken());
		return createBoardResponse;
	}

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

	private void generateWords(BoogleBoard boogleBoard) {
		TrieNode root = new TrieNode();
		for (String str : dict) {
			insert(root, str);
		}
		Set<String> wordsFound = new HashSet<>();
		findWords(boogleBoard.getBoard(), root, wordsFound);
		boogleBoard.setWords(wordsFound);
		System.out.println(wordsFound);
	}

	private void findWords(char[][] board, TrieNode root, Set<String> wordsFound) {
		boolean[][] visited = new boolean[4][4];
		TrieNode pChild = root;

		String str = "";

		if (pChild != null && pChild.child != null) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (board[i][j] != '*' && (board[i][j]) - 'A' < 26 && pChild.child[(board[i][j]) - 'A'] != null) {
						str = str + board[i][j];
						searchWord(pChild.child[(board[i][j]) - 'A'], board, i, j, visited, str, wordsFound);
						str = "";
					} else if (board[i][j] == '*') {
						for (char k = 'A'; k <= 'Z'; k++) {
							str = str + k;
							searchWord(pChild.child[k - 'A'], board, i, j, visited, str, wordsFound);
							str = "";
						}
					}
				}
			}
		}

	}

	private void searchWord(TrieNode root, char[][] board, int i, int j, boolean[][] visited, String str,
			Set<String> wordsFound) {

		if (root == null) {
			return;
		}
		if (root.leaf == true) {
			wordsFound.add(str);
		}

		if (inLimit(i, j, visited)) {
			visited[i][j] = true;
			for (int k = 0; k < TrieNode.size; k++) {
				if (root.child[k] != null) {
					char ch = (char) (k + 'A');
					int[] moves = { 0, 1, -1 };
					for (int m = 0; m < moves.length; m++) {
						for (int n = 0; n < moves.length; n++) {
							if (!(m == 0 && n == 0)) {
								int o = i + moves[m];
								int p = j + moves[n];
								if (inLimit(o, p, visited)) {
									if (board[o][p] == ch) {
										searchWord(root.child[k], board, o, p, visited, str + ch, wordsFound);
									} else if (board[o][p] == '*') {
										for (char l = 'A'; l <= 'Z'; l++) {
											searchWord(root.child[l - 'A'], board, o, p, visited, str + l, wordsFound);
										}
									}
								}
							}
						}
					}
				}
			}
			visited[i][j] = false;
		}

	}

	void insert(TrieNode root, String word) {
		int n = word.length();
		TrieNode cur = root;

		for (int i = 0; i < n; i++) {
			int index = word.charAt(i) - 'A';
			if (cur.child[index] == null) {
				cur.child[index] = new TrieNode();
			}
			cur = cur.child[index];
		}
		cur.leaf = true;
	}

	boolean inLimit(int i, int j, boolean visited[][]) {
		return (i >= 0 && i < 4 && j >= 0 && j < 4 && !visited[i][j]);
	}

}
