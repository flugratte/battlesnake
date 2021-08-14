package dev.flugratte.battlesnake.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;

public class Gamestate {

    private static Logger log = LoggerFactory.getLogger(Gamestate.class);

    private static Map<String, Gamestate> currentGames = new HashMap<>();

    public static Gamestate startGame(GameRequest gameRequest) {
        String identifier = gameRequest.getGame().getId() + "_" + gameRequest.getYou().getId();
        currentGames.putIfAbsent(identifier, new Gamestate(identifier, gameRequest));
        return currentGames.get(identifier);
    }

    public static Gamestate startGame(GameRequest gameRequest, Class<? extends IMovementLogic> movementLogicClass) {
        String identifier = gameRequest.getGame().getId() + "_" + gameRequest.getYou().getId();
        currentGames.putIfAbsent(identifier, new Gamestate(identifier, gameRequest, movementLogicClass));
        return currentGames.get(identifier);
    }

    public static Move nextMove(GameRequest gameRequest) {
        String identifier = gameRequest.getGame().getId() + "_" + gameRequest.getYou().getId();
        return currentGames.get(identifier).move(gameRequest);
    }

    public static void endGame(String gameId, String snakeId) {
        String identifier = gameId + "_" + snakeId;
        if (currentGames.containsKey(identifier)) {
            currentGames.remove(identifier).end();
        }
    }

    private final String identifier;
    private final LinkedList<GameRequest> history = new LinkedList<>();
    private final IMovementLogic movementLogic;

    private Gamestate(String identifier, GameRequest gameRequest) {
        this(identifier, gameRequest, SimpleLogic.class);
    }

    private Gamestate(String identifier, GameRequest gameRequest, Class<? extends IMovementLogic> movementLogicClass) {
        this.identifier = identifier;
        log.info("[{}] Game is starting", identifier);

        history.add(gameRequest);

        // movementLogic = new SoloMovementLogic(this);
        try {
            movementLogic = movementLogicClass.getConstructor(Gamestate.class).newInstance(this);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to initialise MovementLogic of class " + movementLogicClass.getName(), e);
        }

        log.info("[{}] Decided to use MovementLogic-Impl: {}", identifier, movementLogic.getClass().getSimpleName());
    }

    public Move move(GameRequest gameRequest) {
        log.info("[{}] Deciding on next move", identifier);

        Move move = movementLogic.decideMove(gameRequest);
        history.addLast(gameRequest);

        log.info("[{}] Next move: {}", identifier, move);
        return move;
    }

    public void end() {
        log.info("[{}] GameOver", identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    public LinkedList<GameRequest> getHistory() {
        return history;
    }

}
