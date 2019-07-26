package studio.attect.framework666.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class IntegerArrayUtilsTest {

    @Test
    public void toIntArray() {
        int[] checkArray = {0, 0, 2, 3};
        Integer[] integers = new Integer[4];
        integers[0] = 0;
        integers[1] = null;
        integers[2] = 2;
        integers[3] = 3;
        assertArrayEquals(checkArray, IntegerArrayUtils.toIntArray(integers));
    }
}