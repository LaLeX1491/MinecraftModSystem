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
                    ModSystem.log.error("Fehler beim Erstellen der Verzeichnisse.");
                    return;
                }
                if (dataFile.createNewFile()) {
                    ModSystem.log.info("Datei 'data.dat' wurde erstellt.");
                } else {
                    ModSystem.log.info("Datei 'data.dat' existiert bereits.");
                }
            } catch (IOException e) {
                ModSystem.log.error("Fehler beim Erstellen der Datei: {}", e.getMessage());
                e.printStackTrace(); // Detaillierte Fehlerausgabe
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
                    ModSystem.log.error("Deserialisierte Daten entsprechen nicht dem erwarteten Typ.");
                    break;
                }
            }
        } catch (EOFException e) {
            // Ende der Datei erreicht, keine weiteren Objekte zum Lesen
        } catch (IOException | ClassNotFoundException e) {
            ModSystem.log.error("Fehler beim Laden der Datei: {}", e.getMessage());
            ModSystem.log.debug(Arrays.toString(e.getStackTrace()));
        }
    }

    public void saveFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dataFile.toPath()))) {
            oos.writeObject(dataMap);
        } catch (IOException e) {
            ModSystem.log.error("Error saving the file: {}", e.getMessage());
            ModSystem.log.debug(Arrays.toString(e.getStackTrace()));
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
