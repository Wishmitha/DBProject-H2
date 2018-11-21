import org.h2.mvstore.*;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.currentTimeMillis;

public class MVStoreDirect {
    public static void main(String[] args) {
        // open the store (in-memory if fileName is null)
        MVStore s = MVStore.open("newfile");

        MVMap<Integer, String> map = s.openMap("data");

        int[] arr = new int[]{10000, 100000, 1000000, 10000000};
        boolean random = true;
        int maxVal = arr[arr.length - 1];


//        map.keySet().toArray();

        for (int i = 0; i < maxVal; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, maxVal);
            map.put(i, i + "");
        }


        for (int lim : arr) {
            long init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (random) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, maxVal);
                    String sd = map.getInterpolate(randomNum);
                } else {
                    String sd = map.getInterpolate(i);
                }
            }

            long end = currentTimeMillis();
            System.out.print(lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");


            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (random) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, maxVal);
                    String sd = map.get(randomNum);
                } else {
                    String sd = map.get(i);
                }
            }

            end = currentTimeMillis();
            System.out.println("Binary: " + Long.toString(end - init));
        }

        s.close();
    }
}
