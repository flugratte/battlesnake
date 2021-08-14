package dev.flugratte.battlesnake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.logic.DummyMovementLogic;
import dev.flugratte.battlesnake.logic.Gamestate;
import dev.flugratte.battlesnake.util.ResourceUtils;

public class SnakeTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private Snake.Handler handler;

    @BeforeEach
    void setUp() {
        handler = new Snake.Handler();
    }

    @Test
    void indexTest() throws IOException {

        Map<String, String> response = handler.index();
        assertNotNull(response.get("color"));
        assertNotNull(response.get("head"));
        assertNotNull(response.get("tail"));
    }

    @Test
    void startTest() throws IOException {
        String requestJson = ResourceUtils.getResourceFileAsString("moverequest.json");
        Map<String, String> response = handler.start(requestJson);
        assertEquals(0, response.size());
    }

    @Test
    void moveTest() throws IOException {
        String moveRequestJson = ResourceUtils.getResourceFileAsString("moverequest.json");
        GameRequest gameRequest = OBJECT_MAPPER.readValue(moveRequestJson, GameRequest.class);

        Gamestate.startGame(gameRequest, DummyMovementLogic.class);

        Map<String, String> response = handler.move(moveRequestJson);

        List<String> options = new ArrayList<String>();
        options.add("up");
        options.add("down");
        options.add("left");
        options.add("right");

        assertTrue(options.contains(response.get("move")));
    }

    @Test
    void endTest() throws IOException {
        String requestJson = ResourceUtils.getResourceFileAsString("moverequest.json");
        Map<String, String> response = handler.end(requestJson);
        assertEquals(0, response.size());
    }
}
