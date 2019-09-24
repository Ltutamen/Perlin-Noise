package terrain.chunkGenerators.interpolation;

public class LinearInterpolation implements Interpolation{
    @Override
    public float interpolate(float x0, float x1, float alpha) {
        return x0 * (1.f - alpha) + alpha * x1;
    }
}
