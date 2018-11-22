import org.h2.mvstore.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.currentTimeMillis;

public class MVStoreDirect {
    public static void main(String[] args) {
        // open the store (in-memory if fileName is null)
        MVStore s = MVStore.open("newfile");

        MVMap<Integer, String> map_int = s.openMap("interpolate");
        MVMap<Integer, String> map_bin = s.openMap("binary");


        int[] arr = new int[]{1000,10000,100000,1000000,10000000,100000000}; // array inex out of bound 100000000
        boolean randomPut = true;
        boolean randomGet = true;
        boolean randomRem = true;

        for (int lim : arr) {

            long init = currentTimeMillis();

            for (int i = 0; i < lim; i++) {
                if(randomPut){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    map_int.putInterpolate(randomNum, i + "");
                }else {
                    map_int.putInterpolate(i, i + "");
                }
            }
            long end = currentTimeMillis();

            System.out.print("Put "+lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");

            init = currentTimeMillis();

            for (int i = 0; i < lim; i++) {
                if(randomPut){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    map_bin.put(randomNum, i + "");
                }else {
                    map_bin.put(i, i + "");
                }
            }
            end = currentTimeMillis();

            System.out.println("Binary: " + Long.toString(end - init));


            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (randomGet) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map_int.getInterpolate(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map_int.getInterpolate(i);
                    //System.out.println(sd);
                }
            }

            end = currentTimeMillis();
            System.out.print("Get "+lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");


            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (randomGet) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map_bin.get(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map_bin.get(i);
                    //System.out.println(sd);
                }
            }

            end = currentTimeMillis();
            System.out.println("Binary: " + Long.toString(end - init));

            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (randomRem) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map_int.removeInterpolate(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map_int.removeInterpolate(i);
                    //System.out.println(Arrays.toString(map_int.keyList().toArray()));
                }
            }

            end = currentTimeMillis();
            System.out.print("Remove "+lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");

            for (int i = 0; i < lim + 1; i++) {
                if (randomGet) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map_bin.remove(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map_bin.remove(i);
                    //System.out.println(sd);
                }
            }

            end = currentTimeMillis();
            System.out.println("Binary: " + Long.toString(end - init));

            try {
                Files.deleteIfExists(Paths.get("newfile"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
