package dev.flugratte.battlesnake;

import java.io.IOException;
import java.util.Deque;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.enums.Move;
import dev.flugratte.battlesnake.logic.PathSolver;
import dev.flugratte.battlesnake.util.ResourceUtils;

public class PathSolverTest {

    public static List<String> testCases = List.of("pathingCases/caseBorder.json", "pathingCases/caseSimple.json",
            "pathingCases/caseStart.json");

    @Test
    public void testShortestPath() throws JsonMappingException, JsonProcessingException, IOException {
        for (String testCase : testCases) {
            ObjectMapper om = new ObjectMapper();
            GameRequest request = om.readValue(ResourceUtils.getResourceFileAsString(testCase), GameRequest.class);

            PathSolver solver = new PathSolver(request.getYou(), request.getBoard());

            Deque<Move> moves = solver.shortestPathToFood();

            Assertions.assertThat(moves).isNotEmpty();
        }
    }

    @Test
    public void testLongestPath() throws JsonMappingException, JsonProcessingException, IOException {
        for (String testCase : testCases) {
            ObjectMapper om = new ObjectMapper();
            GameRequest request = om.readValue(ResourceUtils.getResourceFileAsString(testCase), GameRequest.class);

            PathSolver solver = new PathSolver(request.getYou(), request.getBoard());

            Deque<Move> moves = solver.longestPathToTail();

            Assertions.assertThat(moves).isNotEmpty();
        }
    }

}
