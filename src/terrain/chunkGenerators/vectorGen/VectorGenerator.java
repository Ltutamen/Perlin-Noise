package terrain.chunkGenerators.vectorGen;

import vectors.Vectorf;

import java.util.Random;

@FunctionalInterface
public interface VectorGenerator {
    Vectorf genVector(Random rand);

}
