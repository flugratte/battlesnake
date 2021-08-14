package dev.flugratte.battlesnake.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.flugratte.battlesnake.deserialisation.Battlesnake;
import dev.flugratte.battlesnake.deserialisation.Board;
import dev.flugratte.battlesnake.deserialisation.Coordinate;
import dev.flugratte.battlesnake.enums.Move;

/**
 * Based on https://github.com/chuyangliu/snake/blob/master/snake/solver/path.py
 */
public class PathSolver {

    private static Logger log = LoggerFactory.getLogger(PathSolver.class);

    Battlesnake snake;
    Board board;
    Cell[][] cellBoard;

    public PathSolver(Battlesnake snake, Board board) {
        this.snake = snake;
        this.board = board;

        cellBoard = new Cell[board.getWidth()][board.getHeight()];
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                cellBoard[y][x] = new Cell(new Coordinate(x, y));
            }
        }
    }

    public Deque<Move> shortestPathToFood() {
        return pathTo(board.getFood(), PathType.SHORTEST);
    }

    public Deque<Move> longestPathToTail() {
        return pathTo(snake.getBody().getLast(), PathType.LONGEST);
    }

    public Deque<Move> pathTo(Collection<Coordinate> any, PathType pathType) {
        return pathTo(any.iterator().next(), pathType);
    }

    public Deque<Move> pathTo(Coordinate coord, PathType pathType) {
        if (pathType == PathType.SHORTEST) {
            return shortestPathTo(coord);
        } else {
            return longestPathTo(coord);
        }
    }

    private Deque<Move> shortestPathTo(Coordinate destination) {
        Coordinate head = snake.getHead();
        log.debug("Planning shortest path from {} to {}", head, destination);
        cellBoard[head.getX()][head.getY()].distance = 0;

        Deque<Coordinate> queue = new ArrayDeque<>();
        queue.addFirst(head);
        while (!queue.isEmpty()) {
            Coordinate current = queue.pop();
            log.debug("Looking at: {}", current);
            if (current.equals(destination)) {
                log.debug("Shortest Path found");
                return buildPath(head, destination);
            }

            Move firstDirection;
            if (current.equals(head)) {
                firstDirection = snake.getBody().get(1).directionTo(head);
            } else {
                firstDirection = cellBoard[current.getX()][current.getY()].parent.directionTo(current);
            }
            log.debug("Current direction: {}", firstDirection);

            List<Coordinate> adjacents = board.getAdjacent(current);
            int toSwap = 0;
            for (int i = 0; i < adjacents.size(); i++) {
                if (current.directionTo(adjacents.get(i)) == firstDirection) {
                    toSwap = i;
                    break;
                }
            }
            if (toSwap != 0) {
                Collections.swap(adjacents, 0, toSwap);
            }
            log.debug("Adjacents: {}", adjacents);

            for (Coordinate pos : adjacents) {
                if (isValid(pos)) {
                    Cell adjCell = cellBoard[pos.getX()][pos.getY()];
                    if (adjCell.distance == Integer.MAX_VALUE) {
                        adjCell.parent = current;
                        adjCell.distance = cellBoard[current.getX()][current.getY()].distance + 1;
                        queue.add(pos);
                    }
                }
            }
        }

        log.debug("No Path found");
        return new ArrayDeque<>();
    }

    private Deque<Move> longestPathTo(Coordinate destination) {
        Coordinate current = snake.getHead();
        Deque<Move> path = shortestPathTo(destination);
        if (path.isEmpty()) {
            List<Coordinate> adjacents = board.getAdjacent(current);
            Coordinate next = adjacents.stream().filter(this::isValid).findFirst().orElse(null);
            if (next == null) {
                path.add(Move.UP);
            } else {
                path.add(current.directionTo(next));
            }
            return path;
        }

        for (Cell[] cellRow : cellBoard) {
            for (Cell cell : cellRow) {
                cell.reset();
            }
        }

        log.debug("Planning longest path from {} to {}", current, destination);
        cellBoard[current.getX()][current.getY()].visited = true;

        List<Move> moves = new ArrayList<>(path);
        for (Move move : moves) {
            current = current.applyMove(move);
            cellBoard[current.getX()][current.getY()].visited = true;
        }

        int idx = 0;
        current = snake.getHead();
        while (true) {
            Move currentDirection = moves.get(idx);
            Coordinate next = current.applyMove(currentDirection);

            Move[] tests = new Move[0];
            if (currentDirection == Move.LEFT || currentDirection == Move.RIGHT) {
                tests = new Move[] { Move.UP, Move.DOWN };
            } else if (currentDirection == Move.UP || currentDirection == Move.DOWN) {
                tests = new Move[] { Move.LEFT, Move.RIGHT };
            }

            boolean extended = false;
            for (Move dirTests : tests) {
                Coordinate curTest = current.applyMove(dirTests);
                Coordinate nxtTest = next.applyMove(dirTests);

                if (isValid(curTest) && isValid(nxtTest)) {
                    cellBoard[curTest.getX()][curTest.getY()].visited = true;
                    cellBoard[nxtTest.getX()][nxtTest.getY()].visited = true;
                    moves.add(idx, dirTests);
                    moves.add(idx + 2, dirTests.opposite());
                    extended = true;
                    break;
                }
            }

            if (!extended) {
                current = next;
                idx += 1;
                if (idx >= moves.size()) {
                    break;
                }
            }
        }

        return new ArrayDeque<Move>(moves);
    }

    private Deque<Move> buildPath(Coordinate src, Coordinate destination) {
        Deque<Move> moves = new ArrayDeque<>();
        if (src.equals(destination)) {
            moves.add(Move.NONE);
        } else {
            Coordinate tmp = destination;
            while (!tmp.equals(src)) {
                Coordinate parent = cellBoard[tmp.getX()][tmp.getY()].parent;
                moves.addFirst(parent.directionTo(tmp));
                tmp = parent;
            }
        }
        log.info("Path from {} to {} = {}", src, destination, moves);
        return moves;
    }

    private boolean isValid(Coordinate pos) {
        return board.isSafe(pos) && !cellBoard[pos.getX()][pos.getY()].visited && !snake.blocks(pos);
    }

    enum PathType {
        SHORTEST, LONGEST
    }

    class Cell {
        public final Coordinate coordinate;

        public Cell(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public Coordinate parent = null;
        public int distance = Integer.MAX_VALUE;
        public boolean visited = false;

        public void reset() {
            parent = null;
            distance = Integer.MAX_VALUE;
            visited = false;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Cell [coordinate=");
            builder.append(coordinate);
            builder.append(", distance=");
            builder.append(distance);
            builder.append(", parent=");
            builder.append(parent);
            builder.append(", visited=");
            builder.append(visited);
            builder.append("]");
            return builder.toString();
        }

    }

}
