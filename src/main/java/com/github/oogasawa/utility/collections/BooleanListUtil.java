package com.github.oogasawa.utility.collections;

import java.util.List;

public class BooleanListUtil {

    public static boolean allTrue(List<Boolean> data) {

        return data.stream()
            .allMatch(elem->{ return elem == true; });
    }

}
