package comp207p.target;

public class NestedLoops {
    public long foo() {
        final int a = 21;

        int b = 24;
        for(int i = 0; i < 22; i++) {
            for(int j = 0; i < 32; j++) {
                b += a * j * i;
            }
        }

        return b;
    }
}