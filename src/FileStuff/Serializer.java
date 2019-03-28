package FileStuff;

import java.io.*;

public class Serializer {

    /**
     * Serializes an object to a file.
     * @param obj the object to serialize
     * @param filePath the file to serialize into
     */
    public static void writeObject(Object obj, String filePath) {
        try{
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (IOException ioException){
            System.out.println("An error occured while writing the object.");
        }
    }


    /**
     * Deserializes an object from a file.
     * @param filePath the file to read from
     * @return the deserialized object
     */
    @SuppressWarnings("unchecked")
    public static <T> T readObject(String filePath){
        T obj = null;
        try{
            FileInputStream fileIn = new FileInputStream(filePath);
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
        return obj;
    }


}
