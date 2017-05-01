package io.github.ddebree.game.ai.core.strategy.all;

import com.google.common.collect.Sets;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllMovesStrategyTest {

    private interface IPlayerKey {}
    private interface IState {}
    private interface IMove {}

    @Mock
    IPlayerKey playerKey;
    @Mock
    IMove move1, move2;
    @Mock
    IState state;

    @Mock
    private IMoveFactory<IState, IPlayerKey, IMove> moveFactory;

    @Test
    public void testGetBestMoves_nullNextStateFactory() throws Exception {
        AllMovesStrategy<IState, IPlayerKey, IMove> allMovesStrategy = new AllMovesStrategy<>(moveFactory);
        Set<IMove> moves = Sets.newHashSet(Arrays.asList(move1, move2));

        when(moveFactory.getMoves(state, playerKey)).thenReturn(moves.stream());
        Set<IMove> results = allMovesStrategy.getBestMoves(state, playerKey);

        assertEquals(moves, results);
    }

    @Test
    public void testGetBestMoves_noFilterNextStateFactory() throws Exception {
        AllMovesStrategy<IState, IPlayerKey, IMove> allMovesStrategy = new AllMovesStrategy<>(moveFactory);
        Set<IMove> moves = Sets.newHashSet(Arrays.asList(move1, move2));

        when(moveFactory.getMoves(state, playerKey)).thenAnswer(invocationOnMock -> moves.stream());
        final Set<IMove> result = allMovesStrategy.getBestMoves(state, playerKey);

        assertEquals(moves, result);
    }

}