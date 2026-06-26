package ru.nsu.bukhanov.config;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    public static PizzeriaConfig readConfig(String path) {
        try (FileReader reader = new FileReader(path)) {
            return new Gson().fromJson(reader, PizzeriaConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения конфигурации", e);
        }
    }
}