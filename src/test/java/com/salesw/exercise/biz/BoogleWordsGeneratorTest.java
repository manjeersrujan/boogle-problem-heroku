package com.salesw.exercise.biz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.salesw.exercise.dao.BoogleDao;
import com.salesw.exercise.exception.SalesWhalesServiceException;
import com.salesw.exercise.model.BoogleBoard;

public class BoogleWordsGeneratorTest {

	@Mock
	BoogleDao boogleDao;

	@Test
	public void testHappyCase() throws IOException, SalesWhalesServiceException {
		BoogleWordsGenerator boogleWordsGenerator = new BoogleWordsGenerator();
		boogleWordsGenerator.setBoogleDao(boogleDao);
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		boogleWordsGenerator.generateWordsAndSave(boogleBoard);
		assertTrue(boogleBoard.getWords().contains("ACE"));
	}

	@Test
	public void testWrongCase() throws IOException, SalesWhalesServiceException {
		BoogleWordsGenerator boogleWordsGenerator = new BoogleWordsGenerator();
		boogleWordsGenerator.setBoogleDao(boogleDao);
		BoogleBoard boogleBoard = new BoogleBoard("A, C, E, D, L, U, G, *, E, *, H, T, G, A, F, K", 1000L);
		boogleWordsGenerator.generateWordsAndSave(boogleBoard);
		assertFalse(boogleBoard.getWords().contains("ADE"));
	}
}
