package dev.flugratte.battlesnake.deserialisation;

public class Game {

    private String id;
    private Ruleset ruleset;
    private int timeout;

    public Game() {

    }

    public Game(String id, Ruleset ruleset, int timeout) {
        this.id = id;
        this.ruleset = ruleset;
        this.timeout = timeout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public void setRuleset(Ruleset ruleset) {
        this.ruleset = ruleset;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ruleset == null) ? 0 : ruleset.hashCode());
        result = prime * result + timeout;
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
        Game other = (Game) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ruleset == null) {
            if (other.ruleset != null)
                return false;
        } else if (!ruleset.equals(other.ruleset))
            return false;
        if (timeout != other.timeout)
            return false;
        return true;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Game [id=");
		builder.append(id);
		builder.append(", ruleset=");
		builder.append(ruleset);
		builder.append(", timeout=");
		builder.append(timeout);
		builder.append("]");
		return builder.toString();
	}

}
