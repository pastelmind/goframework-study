package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class CollectionHelpers {

    public static <T> List<T> listOf( T... items ) {
        return Arrays.asList( items );
    }

    public static <T, U> HashMap<T, U> newHashMap() {
        return new HashMap<T, U>();
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> ArrayList<T> newArrayList( Collection<? extends T> contents ) {
        return new ArrayList<T>( contents );
    }

    public static <T> Vector<T> newVector() {
        return new Vector<T>();
    }

}
