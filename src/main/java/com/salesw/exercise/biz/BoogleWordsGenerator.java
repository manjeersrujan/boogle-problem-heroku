package com.salesw.exercise.biz;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.salesw.exercise.dao.BoogleDao;
import com.salesw.exercise.model.BoogleBoard;
import com.salesw.exercise.service.impl.BoogleServiceImpl;

/**
 * @author yeddulamanjeersrujan
 *
 * May 12, 2019
 * 
 * The actual class which finds all words for a board
 *
 */
@Component
public class BoogleWordsGenerator {

	public BoogleWordsGenerator() throws IOException {
		super();
		BoogleWordsGenerator.loadDict();
	}

	@Autowired
	BoogleDao boogleDao;

	/**
	 * @author yeddulamanjeersrujan
	 *
	 * May 12, 2019
	 * 
	 * Trie node structure
	 *
	 */
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

	private static Set<String> dict = new HashSet<>();

	private static void loadDict() throws IOException {
		InputStream in = BoogleServiceImpl.class.getClassLoader().getResourceAsStream("dictionary.txt");
		IOUtils.readLines(in).stream().forEach(x -> dict.add(((String) x).trim().toUpperCase()));
	}

	/**
	 * @param boogleBoard
	 */
	@Async
	public void generateWordsAndSave(BoogleBoard boogleBoard) {
		boogleDao.save(boogleBoard);
		synchronized (boogleBoard) {
			generateWords(boogleBoard);
		}

	}

	/**
	 * @param boogleBoard
	 */
	private void generateWords(BoogleBoard boogleBoard) {
		TrieNode root = new TrieNode();
		for (String str : dict) {
			insert(root, str);
		}
		Set<String> wordsFound = new HashSet<>();
		findWords(boogleBoard.getBoard(), root, wordsFound);
		boogleBoard.setWords(wordsFound);
	}

	/**
	 * @param board
	 * @param root
	 * @param wordsFound
	 */
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

	/**
	 * @param root
	 * @param board
	 * @param i
	 * @param j
	 * @param visited
	 * @param str
	 * @param wordsFound
	 */
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

	/**
	 * @param root
	 * @param word
	 */
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

	/**
	 * @param i
	 * @param j
	 * @param visited
	 * @return
	 * 
	 * Is in board limits
	 */
	boolean inLimit(int i, int j, boolean visited[][]) {
		return (i >= 0 && i < 4 && j >= 0 && j < 4 && !visited[i][j]);
	}

	public BoogleDao getBoogleDao() {
		return boogleDao;
	}

	public void setBoogleDao(BoogleDao boogleDao) {
		this.boogleDao = boogleDao;
	}
	
	

}
