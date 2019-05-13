/**
 * 
 */
package com.salesw.exercise.model;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.salesw.exercise.exception.SalesWhalesServiceException;

/**
 * @author yeddulamanjeersrujan
 *
 *         May 14, 2019
 *
 */
public class BoogleBoardTest {

	@Test
	public void testHappyCase() throws IOException, SalesWhalesServiceException {
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		assertTrue(boogleBoard.getBoardString().equals("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K"));
	}

	@Test
	public void testHappyCase1() throws IOException, SalesWhalesServiceException {
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		assertTrue(boogleBoard.getBoard() != null && boogleBoard.getBoard().length == 4
				&& boogleBoard.getBoard()[0].length == 4);
	}

	@Test
	public void testHappyCase3() throws IOException, SalesWhalesServiceException {
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		char[][] board = { { 'A', 'C', 'E', 'D' }, { 'L', 'U', 'G', '*' }, { 'E', '*', 'H', 'T' },
				{ 'G', 'A', 'F', 'K' } };

		for (int i = 0; i < boogleBoard.getBoard().length; i++) {
			for (int j = 0; j < boogleBoard.getBoard().length; j++) {
				assertTrue(boogleBoard.getBoard()[i][j]== board[i][j]);
			}
		}
	}
	
	@Test
	public void testHappyCase4() throws IOException, SalesWhalesServiceException {
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		char[][] board = { { 'A', 'C', 'E', 'D' }, { 'L', 'U', 'G', '*' }, { 'E', '*', 'H', 'T' },
				{ 'G', 'A', 'F', 'K' } };

		for (int i = 0; i < boogleBoard.getBoard().length; i++) {
			for (int j = 0; j < boogleBoard.getBoard().length; j++) {
				assertTrue(boogleBoard.getBoard()[i][j]== board[i][j]);
			}
		}
	}

}
