package io.github.ddebree.game.ai.tictactoe;

import com.google.common.collect.Maps;
import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.state.INextStateBuilder;

import javax.annotation.Nonnull;
import java.util.Map;

public class NextStateBuilder implements INextStateBuilder<State, TwoPlayerKey, Move> {

    @Nonnull
    @Override
    public State buildNextState(@Nonnull State currentState, @Nonnull TwoPlayerKey playerKey, @Nonnull Move move) throws InvalidMoveException {
        Map<Move, TwoPlayerKey> newOccupiedPoints = Maps.newHashMap(currentState.getOccupiedPoints());
        if (playerKey == null) {
            newOccupiedPoints.remove(move);
        } else {
            newOccupiedPoints.put(move, playerKey);   //player = TwoPlayer.PLAYER_1 for X, TwoPlayer.PLAYER_2 for O
        }
        return new State(newOccupiedPoints);
    }

}
