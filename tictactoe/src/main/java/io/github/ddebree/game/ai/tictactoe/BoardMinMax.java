package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.score.MaxScoreMoves;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class BoardMinMax implements IStrategy<State, TwoPlayerKey, Move> {

    private IMoveFactory<State, TwoPlayerKey, Move> moveFactory = new MoveFactory();
    private IGameOverTester<State, TwoPlayerKey> gameOverTester = new GameOverTester();

    @Nonnull
    @Override
    public Stream<Move> getBestMoves(@Nonnull State board, @Nonnull final TwoPlayerKey maximizingPlayer) {
        if (gameOverTester.isGameOver(board)) {
            return Stream.empty();
        }

        MaxScoreMoves<Move> bestMoves = new MaxScoreMoves<>();

        moveFactory.getMoves(board, maximizingPlayer).forEach(move -> {
            State newBoard = board.placeAMove(move, maximizingPlayer);
            int currentScore = minimax(newBoard, 1, false, maximizingPlayer);

            bestMoves.addMove(move, currentScore);

            System.out.println("Score for move " + move + " = " + currentScore);
        });
        return bestMoves.getMoves().stream();
    }

    private int minimax(State board, int depth, boolean isMaximiser, final TwoPlayerKey maximizingPlayer) {
        checkArgument(depth > 0);

        if (gameOverTester.isGameOver(board)) {
            return scoreState(board, maximizingPlayer);
        }

        IntStream scores = moveFactory.getMoves(board, isMaximiser ? maximizingPlayer : maximizingPlayer.otherPlayer()).mapToInt(move -> {
            State newBoard = board.placeAMove(move, isMaximiser ? maximizingPlayer : maximizingPlayer.otherPlayer());
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
