package io.github.ddebree.game.ai.core.strategy.noop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class NoopStrategyTest {

    private interface IPlayerKey {}
    private interface IState {}
    private interface IMove {}

    @Mock
    IPlayerKey playerKey;
    @Mock
    IMove move;
    @Mock
    IState state;

    @Test
    public void testGetBestMaximiserMove_noNextStateFactory() {
        NoopStrategy<IState, IPlayerKey, IMove> toTest = new NoopStrategy<>();
        Set<IMove> result = toTest.getBestMoves(state, playerKey);
        assertTrue(result.isEmpty());
    }

}