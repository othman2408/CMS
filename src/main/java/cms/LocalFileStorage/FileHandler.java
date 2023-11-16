package cms.LocalFileStorage;

import java.io.*;
import java.util.List;

public class FileHandler<T extends Serializable> {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    // Method to save object to file
    public void saveObject(T object) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(object);
            System.out.println("Object saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving object: " + e.getMessage());
        }
    }

    // Method to load object from file
    public T loadObject() {
        T object = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            object = (T) inputStream.readObject();
            System.out.println("Object loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object: " + e.getMessage());
        }
        return object;
    }

    public List<Object> readAllObjects() {
        List<Object> objects = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            objects = (List<Object>) inputStream.readObject();
            System.out.println("Objects loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading objects: " + e.getMessage());
        }
        return objects;
    }
}
