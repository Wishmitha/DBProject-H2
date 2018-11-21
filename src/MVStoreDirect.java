import org.h2.mvstore.*;

public class MVStoreDirect {
    public static void main(String[] args) {
        // open the store (in-memory if fileName is null)
        MVStore s = MVStore.open("newfile");

// create/get the map named "data"
        MVMap<Integer, String> map = s.openMap("data");

// add and read some data
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(4, "d");
        map.put(5, "e");

        String st = map.get(4);
        System.out.println(st);

// close the store (this will persist changes)
        s.close();
    }
}
