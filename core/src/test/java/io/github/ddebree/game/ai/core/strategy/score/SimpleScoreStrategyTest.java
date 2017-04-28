package io.github.ddebree.game.ai.core.strategy.score;

import com.google.common.collect.ImmutableSet;
import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.score.IStateScorer;
import io.github.ddebree.game.ai.core.state.INextStateBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleScoreStrategyTest {

    private interface IPlayerKey {}
    private interface IState {}
    private interface IMove {}

    @Mock
    IMoveFactory<IState, IPlayerKey, IMove> moveFactory;
    @Mock
    INextStateBuilder<IState, IPlayerKey, IMove> nextStateBuilder;
    @Mock
    IStateScorer<IState, IPlayerKey> stateScorer;

    @Mock
    IPlayerKey playerKey;
    @Mock
    IState currentState;
    @Mock
    IMove move1, move2;
    @Mock
    IState state1, state2;

    private SimpleScoreStrategy<IState, IPlayerKey, IMove> simpleScoreStrategy;

    @Before
    public void setup() {
        simpleScoreStrategy = new SimpleScoreStrategy<>(moveFactory, stateScorer, nextStateBuilder);
    }

    @Test
    public void testGetBestMove_NoMoves() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.empty());

        List<IMove> moves = simpleScoreStrategy.getBestMoves(currentState, playerKey).collect(Collectors.toList());

        assertTrue(moves.isEmpty());
    }

    @Test
    public void testGetBestMove_OneMove() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1));
        when(nextStateBuilder.buildNextState(currentState, playerKey, move1)).thenReturn(state1);
        when(stateScorer.scoreState(state1, playerKey)).thenReturn(123);

        List<IMove> moves = simpleScoreStrategy.getBestMoves(currentState, playerKey).collect(Collectors.toList());

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move1));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move1);
        verify(stateScorer).scoreState(state1, playerKey);
    }

    @Test
    public void testGetBestMove_TwoMoves_sameScore() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(nextStateBuilder.buildNextState(currentState, playerKey, move1)).thenReturn(state1);
        when(nextStateBuilder.buildNextState(currentState, playerKey, move2)).thenReturn(state2);
        when(stateScorer.scoreState(state1, playerKey)).thenReturn(123);
        when(stateScorer.scoreState(state2, playerKey)).thenReturn(123);

        List<IMove> moves = simpleScoreStrategy.getBestMoves(currentState, playerKey).collect(Collectors.toList());

        assertEquals(2, moves.size());
        assertEquals(ImmutableSet.copyOf(moves), ImmutableSet.of(move1, move2));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move1);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move2);
        verify(stateScorer).scoreState(state1, playerKey);
        verify(stateScorer).scoreState(state2, playerKey);
    }

    @Test
    public void testGetBestMove_TwoMoves_differentScores() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(nextStateBuilder.buildNextState(currentState, playerKey, move1)).thenReturn(state1);
        when(nextStateBuilder.buildNextState(currentState, playerKey, move2)).thenReturn(state2);
        when(stateScorer.scoreState(state1, playerKey)).thenReturn(123);
        when(stateScorer.scoreState(state2, playerKey)).thenReturn(321);

        List<IMove> moves = simpleScoreStrategy.getBestMoves(currentState, playerKey).collect(Collectors.toList());

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move2));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move1);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move2);
        verify(stateScorer).scoreState(state1, playerKey);
        verify(stateScorer).scoreState(state2, playerKey);
    }

    @Test
    public void testGetBestMove_InvalidMoveAndValidMove() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(nextStateBuilder.buildNextState(currentState, playerKey, move1)).thenReturn(state1);
        when(nextStateBuilder.buildNextState(currentState, playerKey, move2)).thenThrow(InvalidMoveException.INSTANCE);
        when(stateScorer.scoreState(state1, playerKey)).thenReturn(123);

        List<IMove> moves = simpleScoreStrategy.getBestMoves(currentState, playerKey).collect(Collectors.toList());

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move1));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move1);
        verify(nextStateBuilder).buildNextState(currentState, playerKey, move2);
        verify(stateScorer).scoreState(state1, playerKey);
        verify(stateScorer, never()).scoreState(state2, playerKey);
    }

}