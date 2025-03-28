package de.lalex.modsystem.dataStorage;

import de.lalex.modsystem.ModSystem;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    private File dataFile;
    private Map<String, Object> dataMap;

    public DataStorage() {
        this.dataFile = new File(ModSystem.getInstance().getDataFolder(), "data.dat");
        if (!dataFile.exists()) {
            try {
                File parentDir = dataFile.getParentFile();
                if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                    ModSystem.getLogger().error("Error creating/finding the directory");
                    return;
                }
                dataFile.createNewFile();
            } catch (IOException e) {
                ModSystem.getLogger().error("Error creating the data file: {}", e.getMessage());
                ModSystem.getLogger().debug(Arrays.toString(e.getStackTrace()));
                return;
            }
        }
        this.dataMap = new ConcurrentHashMap<>();
        loadFile();
    }

    private void loadFile() {
        if (!dataFile.exists()) {
            saveFile();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(dataFile.toPath()))) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof Map) {
                    dataMap = (Map<String, Object>) obj;
                } else {
                    ModSystem.getLogger().error("Deserializated data does not match the requested type");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            ModSystem.getLogger().error("Error loading the data file: {}", e.getMessage());
            ModSystem.getLogger().debug(Arrays.toString(e.getStackTrace()));
        }
    }

    public void saveFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dataFile.toPath()))) {
            oos.writeObject(dataMap);
        } catch (IOException e) {
            ModSystem.getLogger().error("Error saving the data file: {}", e.getMessage());
            ModSystem.getLogger().debug(Arrays.toString(e.getStackTrace()));
        }
    }

    public void set(String key, Object value) {
        dataMap.put(key, value);
        saveFile();
    }

    public Object get(String key) {
        return dataMap.get(key);
    }
}
