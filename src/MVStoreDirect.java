import org.h2.mvstore.*;

import static java.lang.System.currentTimeMillis;

public class MVStoreDirect {
    public static void main(String[] args) {
        // open the store (in-memory if fileName is null)
        MVStore s = MVStore.open("newfile");

// create/get the map named "data"
        MVMap<Integer, String> map = s.openMap("data");

// add and read some data
        for(int i=1;i<1001;i++){
            map.put(i*2, Integer.toString(i));
        }

        int index = 990;

        long init = currentTimeMillis();
        String st = map.get(index,"True");
        long end = currentTimeMillis();

        System.out.println("Interpolation : " + Long.toString(end-init));

        init = currentTimeMillis();
        st = map.get(index);
        end = currentTimeMillis();

        System.out.println("Binary : " + Long.toString(end-init));


// close the store (this will persist changes)
        s.close();
    }
}
