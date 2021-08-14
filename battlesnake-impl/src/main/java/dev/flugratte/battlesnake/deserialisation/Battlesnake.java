package dev.flugratte.battlesnake.deserialisation;

import java.util.LinkedList;

public class Battlesnake {

    private String id;
    private String name;
    private int health;
    private LinkedList<Coordinate> body;
    private LinkedList<Coordinate> bodyExcludingTail;
    private String latency;
    private Coordinate head;
    private int length;
    private String shout;
    private String squad;

    public Battlesnake() {
    }

    public Battlesnake(String id, String name, int health, LinkedList<Coordinate> body, String latency, Coordinate head,
            int length, String shout, String squad) {
        this.id = id;
        this.name = name;
        this.health = health;
        setBody(body);
        this.latency = latency;
        this.head = head;
        this.length = length;
        this.shout = shout;
        this.squad = squad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public LinkedList<Coordinate> getBody() {
        return body;
    }

    public void setBody(LinkedList<Coordinate> body) {
        this.body = body;

        @SuppressWarnings("unchecked") // NOSONAR
        LinkedList<Coordinate> bodyExcludingTail = (LinkedList<Coordinate>) body.clone();
        bodyExcludingTail.removeLast();
        this.bodyExcludingTail = bodyExcludingTail;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public Coordinate getHead() {
        return head;
    }

    public void setHead(Coordinate head) {
        this.head = head;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getShout() {
        return shout;
    }

    public void setShout(String shout) {
        this.shout = shout;
    }

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        result = prime * result + health;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((latency == null) ? 0 : latency.hashCode());
        result = prime * result + length;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((shout == null) ? 0 : shout.hashCode());
        result = prime * result + ((squad == null) ? 0 : squad.hashCode());
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
        Battlesnake other = (Battlesnake) obj;
        if (body == null) {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        if (head == null) {
            if (other.head != null)
                return false;
        } else if (!head.equals(other.head))
            return false;
        if (health != other.health)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (latency == null) {
            if (other.latency != null)
                return false;
        } else if (!latency.equals(other.latency))
            return false;
        if (length != other.length)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (shout == null) {
            if (other.shout != null)
                return false;
        } else if (!shout.equals(other.shout))
            return false;
        if (squad == null) {
            if (other.squad != null)
                return false;
        } else if (!squad.equals(other.squad))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Battlesnake [body=");
        builder.append(body);
        builder.append(", head=");
        builder.append(head);
        builder.append(", health=");
        builder.append(health);
        builder.append(", id=");
        builder.append(id);
        builder.append(", latency=");
        builder.append(latency);
        builder.append(", length=");
        builder.append(length);
        builder.append(", name=");
        builder.append(name);
        builder.append(", shout=");
        builder.append(shout);
        builder.append(", squad=");
        builder.append(squad);
        builder.append("]");
        return builder.toString();
    }

    public boolean blocks(Coordinate coordinate) {
        return body.contains(coordinate);
    }
}
