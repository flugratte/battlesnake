package dev.flugratte.battlesnake;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.flugratte.battlesnake.deserialisation.GameRequest;
import dev.flugratte.battlesnake.util.ResourceUtils;

public class DeserialisationTest {

    @Test
    void testDeserialisation() throws IOException {
        String inputJson = ResourceUtils.getResourceFileAsString("moverequest.json");

        ObjectMapper om = new ObjectMapper();
        GameRequest moveRequest = om.readValue(inputJson, GameRequest.class);

        Assertions.assertThat(moveRequest).isNotNull();
    }

}
