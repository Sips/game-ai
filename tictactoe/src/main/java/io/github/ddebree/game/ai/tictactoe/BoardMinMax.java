package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.score.MaxScoreMoves;
import io.github.ddebree.game.ai.core.strategy.IStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class BoardMinMax implements IStrategy<State, TwoPlayerKey, Move> {

    private static final Logger LOG = LogManager.getLogger(BoardMinMax.class);

    private IMoveFactory<State, TwoPlayerKey, Move> moveFactory = new MoveFactory();
    private IGameOverTester<State, TwoPlayerKey> gameOverTester = new GameOverTester();
    private NextStateBuilder nextStateBuilder = new NextStateBuilder();

    @Nonnull
    @Override
    public Stream<Move> getBestMoves(@Nonnull State board, @Nonnull final TwoPlayerKey maximizingPlayer) {
        if (gameOverTester.isGameOver(board)) {
            return Stream.empty();
        }

        MaxScoreMoves<Move> bestMoves = new MaxScoreMoves<>();

        moveFactory.getMoves(board, maximizingPlayer).forEach(move -> {
            try {
                State newBoard = nextStateBuilder.buildNextState(board, maximizingPlayer, move);

                int currentScore = minimax(newBoard, 1, false, maximizingPlayer);

                bestMoves.addMove(move, currentScore);

                LOG.info("Score for move {} = {}", move, currentScore);
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        });
        return bestMoves.getMoves().stream();
    }

    private int minimax(State board, int depth, final boolean isMaximiser, final TwoPlayerKey maximizingPlayer) {
        checkArgument(depth > 0);

        if (gameOverTester.isGameOver(board)) {
            return scoreState(board, maximizingPlayer);
        }

        final TwoPlayerKey keyToGetMovesFor = isMaximiser ? maximizingPlayer : maximizingPlayer.otherPlayer();
        IntStream scores = moveFactory.getMoves(board, keyToGetMovesFor).mapToInt(move -> {
            State newBoard = null;
            try {
                newBoard = nextStateBuilder.buildNextState(board,
                        keyToGetMovesFor,
                        move);
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
            return minimax(newBoard, depth + 1, !isMaximiser, maximizingPlayer);
        });

        if (isMaximiser) {
            return scores.max().orElse(Integer.MIN_VALUE);
        } else {
            return scores.min().orElse(Integer.MAX_VALUE);
        }
    }

    private int scoreState(State state, TwoPlayerKey maximizingPlayer) {
        Optional<TwoPlayerKey> winner = gameOverTester.getWinner(state);
        if (winner.isPresent()) {
            return winner.get() == maximizingPlayer ? +1 : -1;
        }
        return 0;
    }

}
