package terrain;

import terrain.chunkGenerators.interpolation.GeologyInterpolator;
import terrain.chunkGenerators.interpolation.Interpolation;
import terrain.chunkGenerators.interpolation.LinearInterpolation;
import terrain.chunkGenerators.vectorGen.RandomVectorGenerator;
import vectors.Vectori;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class World {
    private final Vectori worldSize;
    //  private final long seed;
    //  private final Random random;

    private final float[][] terrainHeight;
    private final float[][] terrainHardness;
    //  private final float[][] fertility;

    public World(long seed, Vectori worldSize, int octava){
        this.worldSize = worldSize;
        //  this.seed = seed;
        //  this.random = new Random(seed);

        //  has to generate the basic terrainHeight in the constructor
        PerlinTerrainGenerator generator = new PerlinTerrainGenerator(new RandomVectorGenerator(), new Random(seed), new LinearInterpolation());

        terrainHeight = generator.getHeightMap(worldSize, octava);
        terrainHardness = generator.getTerrainHardness(worldSize, octava);

    }


    public void writeIntoFile(File path) {
        int[] pixelArray = getPixelArray();
        try{
            BufferedImage image = new BufferedImage(worldSize.x, worldSize.y, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0,0, worldSize.x, worldSize.y, pixelArray,0, worldSize.x);
            ImageIO.write(image, "png", path);
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }


    private int[] getPixelArray() {
        Pair<Float, Float> pair = analyze(terrainHeight);
        Interpolation geologyInterpolation = new GeologyInterpolator();

        int[] result = new int[worldSize.x * worldSize.y];

        //  write height as blue
        for(int i = 0; i< terrainHeight.length ; ++i)
            for (int j = 0; j< terrainHeight[i].length ; ++j)
                result[i*worldSize.x + j] = (int)(pair.getKey() * (terrainHeight[i][j] - pair.getValue()));

        //  write hardness as red
        pair = analyze(terrainHardness);
        for (int i=0 ; i<terrainHardness.length ; ++i)
            for (int j=0 ; j<terrainHardness[i].length ; ++j)
                result[i*worldSize.x + j] += (int)(256 * geologyInterpolation.interpolate(0.f, 1.f, terrainHardness[i][j] - pair.getValue())) << 16;

        return result;
    }


    //  returns Pair<Multiplier, min>
    private Pair<Float, Float> analyze(float[][] terrainHeight) {
        float max = Float.MIN_NORMAL;
        float min = Float.MAX_VALUE;

        for(float[] arr : terrainHeight)
            for(float val : arr)
                if(val > max)
                    max = val;
                else if(val < min)
                    min = val;

        return new Pair<>(256.f / (max - min + 0.001f), min);
    }
}
