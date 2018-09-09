package song.jtslkj.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils implements ListUtilsHook {
    @Override
    public boolean keep(Object o) {
        return false;
    }

    public static <T> List<T> filter(List<T> list, ListUtilsHook<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {
            if (hook.keep(t)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }
}
