package terrain;

import terrain.chunkGenerators.interpolation.Interpolation;
import terrain.chunkGenerators.interpolation.LinearInterpolation;
import terrain.chunkGenerators.vectorGen.VectorGenerator;
import vectors.Vectorf;
import vectors.Vectori;

import java.util.Random;

public class PerlinTerrainGenerator {
    private VectorGenerator vectorGenerator;
    private Random random;


    public PerlinTerrainGenerator(VectorGenerator vectorGenerator, Random random) {
        this.vectorGenerator = vectorGenerator;
        this.random = random;
    }


    public float[][] getHeightMap(Vectori size, int octaves) {
        float[][] heightMap = new float[size.x][size.y];

        for(int i=1 ; i<=octaves ; ++i)
            getHeightMap(heightMap, size, size.x / i, new LinearInterpolation());

        return heightMap;
    }


    private void getHeightMap(float[][] heightMap, Vectori size, int scale, Interpolation interpolation) {
        //  on every moment we need only 2 horizontal
        //  arrays of perlin vectors
        Vectorf[] tempArrHorz = new Vectorf[size.x / scale + 2];
        Vectorf[] tempArrHorzPlus = new Vectorf[size.x / scale + 2];

        //  initial array initialization
        for (int i = 0; i < size.x / scale + 2; ++i) {
            tempArrHorz[i] = vectorGenerator.genVector(random);
            tempArrHorzPlus[i] = vectorGenerator.genVector(random);
        }

        for (int i = 0; i < size.y; ++i) {
            for (int j = 0; j < size.x; ++j) {
                heightMap[i][j] += genDot(
                        new Vectorf((float) (j % scale) / scale, (float) (i % scale) / scale),
                        tempArrHorz[j / scale],
                        tempArrHorz[j / scale + 1],
                        tempArrHorzPlus[j / scale],
                        tempArrHorzPlus[j / scale + 1],
                        interpolation);
            }

            if (i % scale == scale - 1) {
                //  shifting two arrays after processing
                tempArrHorz = tempArrHorzPlus;
                tempArrHorzPlus = new Vectorf[size.x / scale + 2];
                for (int j = 0; j < size.x / scale + 2; ++j)
                    tempArrHorzPlus[j] = new Vectorf(random).Norm();
            }
        }
    }


    public float[][] getTerrainHardness(Vectori size, int octaves) {
        float[][] rockType = new float[size.x][size.y];

        for(int i=1 ; i<=octaves ; ++i)
            getHeightMap(rockType, size, size.x / 2, new LinearInterpolation());

        return rockType;

    }


    private float genDot(Vectorf dxdy, Vectorf upL, vectors.Vectorf upR, Vectorf dnL, Vectorf dnR, Interpolation interpolation) {
        //  the difference between the right Vector and thr current dot
        Vectorf tdxdy = new Vectorf(dxdy.x - 1.f , dxdy.y);

        float tempAltA = interpolation.interpolate(upL.scalar(dxdy), upR.scalar(tdxdy), dxdy.x);
        dxdy.y = dxdy.y - 1.f;
        tdxdy.y = dxdy.y;
        float tempAltB = interpolation.interpolate(dnL.scalar(dxdy), dnR.scalar(tdxdy), dxdy.x);

        return interpolation.interpolate(tempAltA, tempAltB,  dxdy.y + 1.f);
    }

}
