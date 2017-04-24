package io.github.ddebree.game.ai.core.player;

/**
 * Basic enum for keying on two players
 */
public enum TwoPlayerKey {

    PLAYER_1,
    PLAYER_2;
    
    public TwoPlayerKey otherPlayer() {
        if (this == TwoPlayerKey.PLAYER_1) {
            return TwoPlayerKey.PLAYER_2;
        } else {
            return TwoPlayerKey.PLAYER_1;
        }
    }
    
}
