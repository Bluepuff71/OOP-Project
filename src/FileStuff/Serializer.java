package FileStuff;

import java.io.*;

public class Serializer {

    /**
     * Serializes an object to a file.
     * @param obj the object to serialize
     * @param fileName the file to serialize into
     */
    public static void writeObject(Object obj, String fileName) {
        try{
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (IOException ioException){
            System.out.println("An error occured while writing the object.");
        }
    }

    /**
     * Deserializes an object from a file. Generically handles all exceptions.
     * @param fileName the file to read from
     * @return the deserialized object or null if an error occurred
     */
    @SuppressWarnings("unchecked")
    public static <T> T q_ReadObject(String fileName){
        T obj = null;
        try{
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = (T) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException ioException){
            System.out.println("An error occured while reading the object.");
        } catch (ClassNotFoundException cnf){
            System.out.println("Class not found.");
        } catch (Exception e){
            System.out.println("An unknown error has occurred.");
        }
        if(obj == null){
            System.out.println("Warning, the deserialized object is null.");
        }
        return obj;
    }

    /**
     * Deserializes an object from a file.
     * @param fileName the file to read from
     * @return the deserialized object
     * @throws IOException if there is a problem opening the file
     * @throws ClassNotFoundException if the data type specified doesn't exist
     */
    public static <T> T readObject(String fileName) throws IOException, ClassNotFoundException{
        T obj;
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        obj = (T) in.readObject();
        in.close();
        fileIn.close();
        return obj;
    }
}
