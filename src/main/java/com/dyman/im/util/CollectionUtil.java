package com.dyman.im.util;

import java.util.Collection;
import java.util.List;

/**
 * @author dyman
 * @describe
 * @date 2020/4/10
 */
public class CollectionUtil {

    public static boolean notNullAndNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
}
