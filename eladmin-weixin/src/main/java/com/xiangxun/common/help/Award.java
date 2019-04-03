package com.xiangxun.common.help;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Award
{
    public static Integer[] getRandom(int count, Integer[]... list)
    {
        List<Integer> ret = new ArrayList();

        Integer[] all = list[0];
        if (list.length >= 2) {
            ret.addAll(Arrays.asList(list[1]));
        }
        count -= ret.size();
        for (int i = 0; i < count; i++)
        {
            int d = (int)(0.0D + Math.random() * (all.length - 1 + 1));
            if (ret.contains(all[d])) {
                i--;
            } else {
                ret.add(all[d]);
            }
        }
        return (Integer[])ret.toArray(new Integer[ret.size()]);
    }

    public static void main(String[] args)
    {
        Integer[] ret = getRandom(3, new Integer[][] { { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5) } });
        System.out.println(ret.length);
    }
}
