package io.github.ddebree.game.ai.core.optimization;

import java.util.function.ToDoubleFunction;

/**
 * A common interface to find the input solution that maximises a particular function
 *
 * @param <S>
 */
public interface SolutionFinder<S> {

    S findMaximumSolution(ToDoubleFunction<S> function);

    default S findMinimumSolution(final ToDoubleFunction<S> function) {
        return findMaximumSolution(s -> -1 * function.applyAsDouble(s));
    }

}
