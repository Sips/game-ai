package io.github.ddebree.game.ai.core.strategy.prompt;

import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.move.MoveFactoryBuilder;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PromptUserStrategy<S, P, M> implements IStrategy<S, P, M> {

    private Scanner scan = new Scanner(System.in);
    private IMoveFactory<S, P, M> moveFactory = MoveFactoryBuilder.<S, P, M>aMoveFactory().build();

    public PromptUserStrategy(IMoveFactory<S, P, M> moveFactory) {
        this.moveFactory = moveFactory;
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull S state, P playerKey) {
        List<M> moves = moveFactory.getMoves(state, playerKey).collect(Collectors.toList());
        System.out.println("Select a move:");
        int i = 0;
        for (M m : moves) {
            System.out.println(i + ": " + m);
            i++;
        }
        System.out.println("Select an available option");
        return Stream.of(moves.get(scan.nextInt()));
    }

}
