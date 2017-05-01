package io.github.ddebree.game.ai.core.strategy.score;

import com.google.common.collect.ImmutableSet;
import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.score.IMoveScorer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MoveScoreStrategyTest {

    private interface IPlayerKey {}
    private interface IState {}
    private interface IMove {}

    @Mock
    IPlayerKey playerKey;
    @Mock
    IMoveFactory<IState, IPlayerKey, IMove> moveFactory;
    @Mock
    IMoveScorer<IState, IMove> moveScorer;

    @Mock
    IState currentState;
    @Mock
    IMove move1, move2;

    private MoveScoreStrategy<IState, IPlayerKey, IMove> moveMoveScoreStrategy;

    @Before
    public void setup() {
        moveMoveScoreStrategy = new MoveScoreStrategy<>(moveFactory, moveScorer);
    }

    @Test
    public void testGetBestMove_NoMoves() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.empty());

        Set<IMove> moves = moveMoveScoreStrategy.getBestMoves(currentState, playerKey);

        assertTrue(moves.isEmpty());
    }

    @Test
    public void testGetBestMove_OneMove() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1));
        when(moveScorer.scoreMove(currentState, move1)).thenReturn(123);

        Set<IMove> moves = moveMoveScoreStrategy.getBestMoves(currentState, playerKey);

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move1));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(moveScorer).scoreMove(currentState, move1);
    }

    @Test
    public void testGetBestMove_TwoMoves_sameScore() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(moveScorer.scoreMove(currentState, move1)).thenReturn(123);
        when(moveScorer.scoreMove(currentState, move2)).thenReturn(123);

        Set<IMove> moves = moveMoveScoreStrategy.getBestMoves(currentState, playerKey);

        assertEquals(2, moves.size());
        assertEquals(ImmutableSet.copyOf(moves), ImmutableSet.of(move1, move2));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(moveScorer).scoreMove(currentState, move1);
        verify(moveScorer).scoreMove(currentState, move2);
    }

    @Test
    public void testGetBestMove_TwoMoves_differentScores() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(moveScorer.scoreMove(currentState, move1)).thenReturn(123);
        when(moveScorer.scoreMove(currentState, move2)).thenReturn(321);

        Set<IMove> moves = moveMoveScoreStrategy.getBestMoves(currentState, playerKey);

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move2));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(moveScorer).scoreMove(currentState, move1);
        verify(moveScorer).scoreMove(currentState, move2);
    }

    @Test
    public void testGetBestMove_InvalidMoveAndValidMove() throws Exception {
        when(moveFactory.getMoves(currentState, playerKey)).thenReturn(Stream.of(move1, move2));
        when(moveScorer.scoreMove(currentState, move1)).thenReturn(123);
        when(moveScorer.scoreMove(currentState, move2)).thenThrow(InvalidMoveException.INSTANCE);

        Set<IMove> moves = moveMoveScoreStrategy.getBestMoves(currentState, playerKey);

        assertEquals(1, moves.size());
        assertTrue(moves.contains(move1));

        verify(moveFactory).getMoves(currentState, playerKey);
        verify(moveScorer).scoreMove(currentState, move1);
        verify(moveScorer).scoreMove(currentState, move2);
    }

}