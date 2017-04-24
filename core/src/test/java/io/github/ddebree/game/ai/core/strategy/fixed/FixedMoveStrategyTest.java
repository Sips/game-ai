package io.github.ddebree.game.ai.core.strategy.fixed;

import io.github.ddebree.game.ai.core.move.IIsValidMoveTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FixedMoveStrategyTest {

    private interface IPlayerKey {}
    private interface IState {}
    private interface IMove {}

    @Mock
    IPlayerKey playerKey;
    @Mock
    IMove move;
    @Mock
    IState state;

    @Mock
    private IIsValidMoveTester<IState, IPlayerKey, IMove> hasNextStateTester;

    @Test
    public void testGetBestMaximiserMove_noNextStateFactory() {
        FixedMoveStrategy<IState, IPlayerKey, IMove> toTest = new FixedMoveStrategy<>(move);
        Stream<IMove> result = toTest.getBestMoves(state, playerKey);
        assertEquals(Collections.singletonList(move), result.collect(Collectors.toList()));
    }

    @Test
    public void testGetBestMaximiserMove_passingNextStateFactory() {
        FixedMoveStrategy<IState, IPlayerKey, IMove> toTest = new FixedMoveStrategy<>(move, hasNextStateTester);
        when(hasNextStateTester.isValidMove(state, playerKey, move)).thenReturn(true);
        Stream<IMove> result = toTest.getBestMoves(state, playerKey);

        assertEquals(Collections.singletonList(move), result.collect(Collectors.toList()));
    }

    @Test
    public void testGetBestMaximiserMove_failingNextStateFactory() {
        FixedMoveStrategy<IState, IPlayerKey, IMove> toTest = new FixedMoveStrategy<>(move, hasNextStateTester);
        when(hasNextStateTester.isValidMove(state, playerKey, move)).thenReturn(false);
        Stream<IMove> result = toTest.getBestMoves(state, playerKey);

        assertTrue(result.collect(Collectors.toList()).isEmpty());
    }

}