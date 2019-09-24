package terrain.chunkGenerators.interpolation;

public class CubicInterpolation implements Interpolation {
    @Override
    public float interpolate(float x0, float x1, float alpha) {
        alpha = -2.f * alpha * alpha * alpha + 3.f * alpha * alpha;
        return x0 * (1.f - alpha) + alpha * x1;
    }
}
