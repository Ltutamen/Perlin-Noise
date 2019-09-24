package terrain.chunkGenerators.vectorGen;

import vectors.Vectorf;

import java.util.Random;

public class RandomVectorGenerator implements VectorGenerator {
    @Override
    public Vectorf genVector(Random rand) {
        return new Vectorf(rand).Norm();
    }
}
