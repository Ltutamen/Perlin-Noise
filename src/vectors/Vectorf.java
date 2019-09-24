package vectors;

import java.util.Random;

public class Vectorf {
    public float x;
    public float y;


    public Vectorf(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public Vectorf Norm() {
        float len = (float)Math.sqrt(x*x + y*y);
        this.x = x / len;
        this.y = y / len;
        return this;
    }


    public Vectorf(Random random){
        this.x = random.nextFloat();
        this.y = random.nextFloat();
    }


    public float scalar(Vectorf vectorf2){
        return (this.x * vectorf2.x) + (this.y * vectorf2.y);
    }


    public float abs(){
        return (float)Math.sqrt(x*x + y*y);
    }


    public float gravity(){
        return (1.f - x) + (1.f - y);
    }


    @Override
    public String toString(){
        return "{" + x + "_" + y + "}";
    }

}
