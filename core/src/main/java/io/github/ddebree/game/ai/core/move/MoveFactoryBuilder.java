package io.github.ddebree.game.ai.core.move;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A useful move factory builder class that can provide a filtered view of a fixed list of move
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class MoveFactoryBuilder<S, P, M> {

    public IMoveFactory<S, P, M> moveFactory = new NoopMoveFactory();

    public static <S, P, M> MoveFactoryBuilder<S, P, M> aMoveFactory() {
        return new MoveFactoryBuilder<>();
    }

    public MoveFactoryBuilder<S, P, M> withMoves(@Nonnull final Iterable<M> moves) {
        this.moveFactory = new FixedListMoveFactory(ImmutableList.copyOf(moves));
        return this;
    }

    public MoveFactoryBuilder<S, P, M> withMoves(@Nonnull final M... moves) {
        this.moveFactory = new FixedListMoveFactory(ImmutableList.copyOf(moves));
        return this;
    }

    public MoveFactoryBuilder<S, P, M> withIsValidMoveTester(IIsValidMoveTester<S, P, M> isValidMoveTester) {
        moveFactory = new FilteredMoveFactory(isValidMoveTester, moveFactory);
        return this;
    }

    public IMoveFactory<S, P, M> build() {
        return moveFactory;
    }

    private class NoopMoveFactory implements IMoveFactory<S, P, M> {
        @Nonnull
        @Override
        public Stream<M> getMoves(@Nonnull S state, @Nonnull P playerKey) {
            return Stream.empty();
        }
    }

    private class FilteredMoveFactory implements IMoveFactory<S, P, M> {

        @Nonnull
        private final IMoveFactory<S, P, M> moveFactory;
        @Nonnull
        private final IIsValidMoveTester<S, P, M> isValidMoveTester;

        FilteredMoveFactory(@Nonnull final IIsValidMoveTester<S, P, M> isValidMoveTester,
                                   @Nonnull final IMoveFactory<S, P, M> moveFactory) {
            this.moveFactory = checkNotNull(moveFactory);
            this.isValidMoveTester = isValidMoveTester;
        }

        @Nonnull
        @Override
        public Stream<M> getMoves(@Nonnull final S state, @Nonnull P playerKey) {
            return moveFactory.getMoves(state, playerKey)
                    .filter(m -> isValidMoveTester.isValidMove(state, playerKey, m));
        }
    }

    private class FixedListMoveFactory implements IMoveFactory<S, P, M> {

        @Nonnull
        private final ImmutableList<M> moves;

        FixedListMoveFactory(@Nonnull final ImmutableList<M> moves) {
            this.moves = moves;
        }

        @Nonnull
        @Override
        public Stream<M> getMoves(@Nonnull final S state, @Nonnull P playerKey) {
            return moves.stream();
        }
    }

}
