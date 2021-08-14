package dev.flugratte.battlesnake.deserialisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.flugratte.battlesnake.enums.Move;

public class Coordinate {
    private static Logger log = LoggerFactory.getLogger(Coordinate.class);

    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Coordinate [x=");
        builder.append(x);
        builder.append(", y=");
        builder.append(y);
        builder.append("]");
        return builder.toString();
    }

    public Coordinate applyMove(Move move) {
        return new Coordinate(this.x + move.xChange, this.y + move.yChange);
    }

    public Move directionTo(Coordinate adjacent) {
        int diffX = this.x - adjacent.getX();
        int diffY = this.y - adjacent.getY();

        Move direction = Move.NONE;

        if (diffX == 0) {
            if (diffY == 1) {
                direction = Move.DOWN;
            } else if (diffY == -1) {
                direction = Move.UP;
            }
        } else if (diffY == 0) {
            if (diffX == 1) {
                direction = Move.LEFT;
            } else if (diffX == -1) {
                direction = Move.RIGHT;
            }
        }

        log.debug("Direction from {} to {}: {}", this, adjacent, direction);
        return direction;
    }

}
