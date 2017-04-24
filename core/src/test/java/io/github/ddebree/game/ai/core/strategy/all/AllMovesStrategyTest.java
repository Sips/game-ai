package io.github.ddebree.game.ai.core.strategy.all;

import io.github.ddebree.game.ai.core.move.IMoveFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<IMove> moves = Stream.of(move1, move2);

        when(moveFactory.getMoves(state, playerKey)).thenReturn(moves);
        allMovesStrategy.getBestMoves(state, playerKey);

        assertSame(moves, allMovesStrategy.getBestMoves(state, playerKey));
    }

    @Test
    public void testGetBestMoves_noFilterNextStateFactory() throws Exception {
        AllMovesStrategy<IState, IPlayerKey, IMove> allMovesStrategy = new AllMovesStrategy<>(moveFactory);
        List<IMove> moves = Arrays.asList(move1, move2);

        when(moveFactory.getMoves(state, playerKey)).thenAnswer(invocationOnMock -> moves.stream());
        final Stream<IMove> result = allMovesStrategy.getBestMoves(state, playerKey);

        assertEquals(moves, result.collect(Collectors.toList()));
    }

}