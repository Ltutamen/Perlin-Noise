package terrain.chunkGenerators.interpolation;

@FunctionalInterface
public interface Interpolation {
    float interpolate(float x0, float x1, float alpha);
}
