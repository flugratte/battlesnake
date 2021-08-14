package dev.flugratte.battlesnake.logic;

import java.util.Deque;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;

public class SimpleLogic extends IMovementLogic {

    Deque<Move> nextMoves = null;
    boolean headingForFood = false;

    public SimpleLogic(Gamestate gamestate) {
        super(gamestate);
    }

    @Override
    public Move decideMove(GameRequest moveRequest) {
        if (!headingForFood) {
            Deque<Move> shortestPathToFood = new PathSolver(moveRequest.getYou(), moveRequest.getBoard())
                    .shortestPathToFood();
            if ((moveRequest.getYou().getHealth() + 2) < shortestPathToFood.size()) {
                nextMoves = shortestPathToFood;
            }
            headingForFood = true;
        }

        if (nextMoves == null || nextMoves.isEmpty()) {
            nextMoves = new PathSolver(moveRequest.getYou(), moveRequest.getBoard()).longestPathToTail();
        }

        Move nextMove = nextMoves.poll();
        if (moveRequest.getBoard().getFood().contains(moveRequest.getYou().getHead().applyMove(nextMove))) {
            nextMoves = null;
            headingForFood = false;
        }

        return nextMove;
    }

}
