package ru.nsu.bukhanov.config;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest {
    @Test
    void testReadValidConfig() throws IOException {
        File tempFile = File.createTempFile("test_config", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("{\"bakersSpeeds\": [100, 200], \"couriersCapacities\": [2], \"warehouseCapacity\": 5}");
        }

        PizzeriaConfig config = ConfigReader.readConfig(tempFile.getAbsolutePath());

        assertNotNull(config);
        assertEquals(5, config.warehouseCapacity);
        assertEquals(2, config.bakersSpeeds.size());
        assertEquals(1, config.couriersCapacities.size());

        tempFile.delete();
    }

    @Test
    void testReadInvalidPath() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                ConfigReader.readConfig("invalid_path.json")
        );
        assertTrue(exception.getMessage().contains("Ошибка чтения конфигурации"));
    }
}