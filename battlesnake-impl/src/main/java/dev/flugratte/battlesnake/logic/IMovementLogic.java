package dev.flugratte.battlesnake.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;

public abstract class IMovementLogic {

    protected Logger log = LoggerFactory.getLogger(getClass());

    protected final Gamestate gamestate;

    public IMovementLogic(Gamestate gamestate) {
        this.gamestate = gamestate;
    }

    public abstract Move decideMove(GameRequest gameRequest);

}
