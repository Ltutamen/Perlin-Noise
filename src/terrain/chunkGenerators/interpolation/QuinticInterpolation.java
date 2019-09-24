package terrain.chunkGenerators.interpolation;

public class QuinticInterpolation implements Interpolation {
    @Override
    public float interpolate(float x0, float x1, float alpha) {
        alpha = alpha * alpha * alpha * (alpha * (alpha * 6 - 15) + 10);
        return x0 * (1.f - alpha) + alpha * x1;
    }
}
