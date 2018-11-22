import org.h2.mvstore.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

public class MVStoreDirect {
    public static void main(String[] args) {
        // open the store (in-memory if fileName is null)
        MVStore s = MVStore.open("newfile");

        MVMap<Integer, String> map = s.openMap("data");

        map.remove(1);

        int[] arr = new int[]{1000}; // array inex out of bound 100000000
        boolean randomPut = false;
        boolean randomGet = false;

        for (int lim : arr) {

            long init = currentTimeMillis();

            for (int i = 0; i < lim; i++) {
                if(randomPut){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    map.putInterpolate(randomNum, i + "");
                }else {
                    map.putInterpolate(i, i + "");
                }
            }
            long end = currentTimeMillis();

            System.out.print("Put "+lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");

            init = currentTimeMillis();

            for (int i = 0; i < lim; i++) {
                if(randomPut){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    map.put(randomNum, i + "");
                }else {
                    map.put(i, i + "");
                }
            }
            end = currentTimeMillis();

            System.out.println("Binary: " + Long.toString(end - init));


            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (randomGet) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map.getInterpolate(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map.getInterpolate(i);
                    //System.out.println(sd);
                }
            }

            end = currentTimeMillis();
            System.out.print("Get "+lim + " -->  Interpolation: " + Long.toString(end - init) + "   ----    ");


            init = currentTimeMillis();

            for (int i = 0; i < lim + 1; i++) {
                if (randomGet) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, lim);
                    String sd = map.get(randomNum);
                    //System.out.println(sd);
                } else {
                    String sd = map.get(i);
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
