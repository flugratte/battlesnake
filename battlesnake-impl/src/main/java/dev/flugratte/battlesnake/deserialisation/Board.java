package dev.flugratte.battlesnake.deserialisation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int height;
    private int width;
    private Collection<Coordinate> food;
    private Collection<Coordinate> hazards;
    private Collection<Battlesnake> snakes;

    public Board() {
    }

    public Board(int height, int width, Collection<Coordinate> food, Collection<Coordinate> hazards,
            Collection<Battlesnake> snakes) {
        this.height = height;
        this.width = width;
        this.food = food;
        this.hazards = hazards;
        this.snakes = snakes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Collection<Coordinate> getFood() {
        return food;
    }

    public void setFood(Collection<Coordinate> food) {
        this.food = food;
    }

    public Collection<Coordinate> getHazards() {
        return hazards;
    }

    public void setHazards(Collection<Coordinate> hazards) {
        this.hazards = hazards;
    }

    public Collection<Battlesnake> getSnakes() {
        return snakes;
    }

    public void setSnakes(Collection<Battlesnake> snakes) {
        this.snakes = snakes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((food == null) ? 0 : food.hashCode());
        result = prime * result + ((hazards == null) ? 0 : hazards.hashCode());
        result = prime * result + height;
        result = prime * result + ((snakes == null) ? 0 : snakes.hashCode());
        result = prime * result + width;
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
        Board other = (Board) obj;
        if (food == null) {
            if (other.food != null)
                return false;
        } else if (!food.equals(other.food))
            return false;
        if (hazards == null) {
            if (other.hazards != null)
                return false;
        } else if (!hazards.equals(other.hazards))
            return false;
        if (height != other.height)
            return false;
        if (snakes == null) {
            if (other.snakes != null)
                return false;
        } else if (!snakes.equals(other.snakes))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Board [food=");
        builder.append(food);
        builder.append(", hazards=");
        builder.append(hazards);
        builder.append(", height=");
        builder.append(height);
        builder.append(", snakes=");
        builder.append(snakes);
        builder.append(", width=");
        builder.append(width);
        builder.append("]");
        return builder.toString();
    }

    public List<Coordinate> getAdjacent(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        List<Coordinate> adjacent = new LinkedList<>();
        adjacent.add(new Coordinate(x - 1, y));
        adjacent.add(new Coordinate(x + 1, y));
        adjacent.add(new Coordinate(x, y - 1));
        adjacent.add(new Coordinate(x, y + 1));
        adjacent.removeIf(this::notInBounds);
        Collections.shuffle(adjacent);
        return adjacent;
    }

    public boolean notInBounds(Coordinate coordinate) {
        return !inBounds(coordinate);
    }

    public boolean inBounds(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < this.getWidth() && coordinate.getY() >= 0
                && coordinate.getY() < this.getHeight();
    }

    public boolean isSafe(Coordinate coordinate) {
        return inBounds(coordinate) && !getHazards().contains(coordinate)
                && (getSnakes() == null || getSnakes().stream().noneMatch(bs -> bs.blocks(coordinate)));
    }
}
