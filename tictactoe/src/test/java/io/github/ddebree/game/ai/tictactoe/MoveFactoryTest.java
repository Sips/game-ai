package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.ddebree.game.ai.tictactoe.StateBuilder.builder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class MoveFactoryTest {

    private final MoveFactory moveFactory = new MoveFactory();

    @Test
    public void testEmptyBoard() {
        List<Move> moves = moveFactory.getMoves(builder().build(), TwoPlayerKey.PLAYER_1).collect(Collectors.toList());
        assertThat(moves, is(Arrays.asList(Move.values())));
    }

    @Test
    public void testPartialBoard() {
        List<Move> moves = moveFactory.getMoves(builder().allOccupiedBy(TwoPlayerKey.PLAYER_2)
                        .emptyPoints(Move.MIDDLE_MIDDLE)
                        .build(), TwoPlayerKey.PLAYER_1)
                .collect(Collectors.toList());
        assertThat(moves, is(Collections.singletonList(Move.MIDDLE_MIDDLE)));
    }

    @Test
    public void testFullBoard() {
        List<Move> moves = moveFactory.getMoves(builder().allOccupiedBy(TwoPlayerKey.PLAYER_2).build(), TwoPlayerKey.PLAYER_1)
                .collect(Collectors.toList());
        assertTrue(moves.isEmpty());
    }

}
