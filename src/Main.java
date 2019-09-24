import terrain.World;
import vectors.Vectori;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("Program started");

        new World(1223L, new Vectori(2048, 2048), 32).writeIntoFile(new File("world.png"));

        System.out.println("Hello World!");
    }
}