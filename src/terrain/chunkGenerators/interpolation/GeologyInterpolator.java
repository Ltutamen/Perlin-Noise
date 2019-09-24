package terrain.chunkGenerators.interpolation;

public class GeologyInterpolator implements Interpolation {
    private static final float BASE = 0.15f;
    private static final float MULTIPLICATOR = 3.f;

    @Override
    public float interpolate(float x0, float x1, float alpha) {
        x0 = MULTIPLICATOR * x0 + BASE;
        x1 = MULTIPLICATOR * x1 + BASE;

        x0 = ((float)((int)x0)) / MULTIPLICATOR;
        x1 = ((float)((int)x1)) / MULTIPLICATOR;

        //  System.out.println(alpha);

        return x0 * (1.f - alpha) + alpha * x1;
    }
}
