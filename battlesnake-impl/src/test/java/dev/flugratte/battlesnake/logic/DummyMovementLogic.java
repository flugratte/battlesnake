package dev.flugratte.battlesnake.logic;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;

public class DummyMovementLogic extends IMovementLogic {

    public DummyMovementLogic(Gamestate gamestate) {
        super(gamestate);
    }

    @Override
    public Move decideMove(GameRequest gameRequest) {
        return Move.UP;
    }
    
}
