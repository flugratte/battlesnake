package dev.flugratte.battlesnake.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dev.flugratte.battlesnake.deserialisation.Coordinate;
import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;

public class SoloMovementLogic extends IMovementLogic {

    public SoloMovementLogic(Gamestate gamestate) {
        super(gamestate);
    }

    @Override
    public Move decideMove(GameRequest moveRequest) {
        log.debug("You: {}", moveRequest.getYou());

        List<Move> possibleMoves = Arrays.asList(Move.values());

        possibleMoves = avoidBody(moveRequest, possibleMoves);
        possibleMoves = avoidWalls(moveRequest, possibleMoves);
        possibleMoves = avoidHazards(moveRequest, possibleMoves);

        log.debug("PossibleMoves: {}", possibleMoves);
        if (1 < possibleMoves.size()) {
            Random random = new Random();
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        } else if (1 == possibleMoves.size()) {
            return possibleMoves.get(0);
        } else {
            return Move.UP;
        }
    }

    private List<Move> avoidBody(GameRequest moveRequest, List<Move> possibleMoves) {
        return possibleMoves.stream().filter(m -> isAvoiding(testMove(moveRequest, m), moveRequest.getYou().getBody()))
                .collect(Collectors.toList());
    }

    private List<Move> avoidWalls(GameRequest moveRequest, List<Move> possibleMoves) {
        return possibleMoves.stream().filter(m -> inBounds(moveRequest, m)).collect(Collectors.toList());
    }

    private List<Move> avoidHazards(GameRequest moveRequest, List<Move> possibleMoves) {
        return possibleMoves.stream()
                .filter(m -> isAvoiding(testMove(moveRequest, m), moveRequest.getBoard().getHazards()))
                .collect(Collectors.toList());
    }

    private Coordinate testMove(GameRequest moveRequest, Move move) {
        return moveRequest.getYou().getHead().applyMove(move);
    }

    private boolean inBounds(GameRequest moveRequest, Move move) {
        return inBounds(moveRequest, testMove(moveRequest, move));
    }

    private boolean inBounds(GameRequest moveRequest, Coordinate coordinate) {
        return coordinate.getX() > -1 && coordinate.getY() > -1 && coordinate.getX() < moveRequest.getBoard().getWidth()
                && coordinate.getY() < moveRequest.getBoard().getHeight();
    }

    private boolean isAvoiding(Coordinate coordinate, Collection<Coordinate> coordinatesToAvoid) {
        return coordinatesToAvoid.stream().noneMatch(coordinate::equals);
    }
}
