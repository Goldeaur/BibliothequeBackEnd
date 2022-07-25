package com.bibliotheque.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {


    /**
     * As described yesterday but this solution doesn't work if 0 is the highest value.
     *
     * @param entries not null, not empty and not [0].
     * @return closest value to Zero and the positive one in case of equality.
     */
    int extractClosestValueToZero(List<Integer> entries) {
        Assert.assertNotNull(entries);
        Assert.assertNotEquals(List.of(), entries);
        Assert.assertNotEquals(Arrays.asList(0), entries);

        final int[] result = {entries.stream().max(Comparator.comparing(Integer::valueOf)).get()};

        entries.forEach(currentValue -> {
            if (currentValue != 0) {
                int absoluteCurrentValue = Math.abs(currentValue);
                if (absoluteCurrentValue < Math.abs(result[0])) {
                    result[0] = currentValue;
                } else if (absoluteCurrentValue == Math.abs(result[0]) && result[0] < 0) {
                    result[0] = currentValue;
                }
            }

        });
        return result[0];
    }

    /**
     * Solution 1 Removing 0 from the entries.
     *
     * @param entries not null, not empty and not [0].
     * @return closest value to Zero and the positive one in case of equality.
     */
    int extractClosestValueToZeroRemovingZeros(List<Integer> entries) {
        Assert.assertNotNull(entries);
        var entriesCopy = entries.stream().filter(integer -> integer != 0).toList();
        Assert.assertNotEquals(Arrays.asList(), entriesCopy);

        final int[] result = {entriesCopy.stream().max(Comparator.comparing(Integer::valueOf)).get()};

        entriesCopy.forEach(currentValue -> {
            int absoluteCurrentValue = Math.abs(currentValue);
            if (absoluteCurrentValue < Math.abs(result[0])) {
                result[0] = currentValue;
            } else if (absoluteCurrentValue == Math.abs(result[0]) && result[0] < 0) {
                result[0] = currentValue;
            }
        });
        return result[0];
    }

    /**
     * Solution2 : initiate result with the highest Integer Value.
     *
     * @param entries not null, not empty and not [0].
     * @return closest value to Zero and the positive one in case of equality.
     */
    int extractClosestValueToZeroSettingWithHighestIntValue(List<Integer> entries) {
        Assert.assertNotNull(entries);
        Assert.assertNotEquals(Arrays.asList(), entries);
        Assert.assertNotEquals(Arrays.asList(0), entries);

        final int[] result = {Integer.MAX_VALUE};

        entries.forEach(currentValue -> {
            if (currentValue != 0) {
                int absoluteCurrentValue = Math.abs(currentValue);
                if (absoluteCurrentValue < Math.abs(result[0])) {
                    result[0] = currentValue;
                } else if (absoluteCurrentValue == Math.abs(result[0]) && result[0] < 0) {
                    result[0] = currentValue;
                }
            }

        });
        return result[0];
    }
    @Test
    void shouldReturnAssertionErrorIfEntryIsNull() {
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZero(null);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroRemovingZeros(null);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroSettingWithHighestIntValue(null);
        });

    }

    @Test
    void shouldReturnAssertionErrorIfEntryIsEmpty() {
        List<Integer> entries = Arrays.asList();
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZero(entries);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroRemovingZeros(entries);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroSettingWithHighestIntValue(entries);
        });
    }

    @Test
    void shouldReturnAssertionErrorIfEntryIsZeroOnly() {
        List<Integer> entries = Arrays.asList(0);
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZero(entries);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroRemovingZeros(entries);
        });
        Assertions.assertThrows(AssertionError.class, () -> {
            extractClosestValueToZeroSettingWithHighestIntValue(entries);
        });
    }

    @Test
    void shouldReturn2() {
        List<Integer> entries = Arrays.asList(0, 2, 3, 15);
        Assertions.assertEquals(2, extractClosestValueToZero(entries));
        Assertions.assertEquals(2, extractClosestValueToZeroRemovingZeros(entries));
        Assertions.assertEquals(2, extractClosestValueToZeroSettingWithHighestIntValue(entries));
    }

    @Test
    void shouldReturn3() {
        List<Integer> entries = Arrays.asList(-5, -8, 3, 15);
        Assertions.assertEquals(3, extractClosestValueToZero(entries));
        Assertions.assertEquals(3, extractClosestValueToZeroRemovingZeros(entries));
        Assertions.assertEquals(3, extractClosestValueToZeroSettingWithHighestIntValue(entries));
    }

    @Test
    void shouldReturnMinus5() {
        List<Integer> entries = Arrays.asList(-5, -8, -12, -15);
        Assertions.assertEquals(-5, extractClosestValueToZero(entries));
        Assertions.assertEquals(-5, extractClosestValueToZeroRemovingZeros(entries));
        Assertions.assertEquals(-5, extractClosestValueToZeroSettingWithHighestIntValue(entries));
    }

    @Test
    void shouldReturnMinus2() {
        List<Integer> entries = Arrays.asList(0, -2, -3, -15);
        Assertions.assertEquals(0, extractClosestValueToZero(entries)); // fail;
        Assertions.assertEquals(-2, extractClosestValueToZeroRemovingZeros(entries));
        Assertions.assertEquals(-2, extractClosestValueToZeroSettingWithHighestIntValue(entries));
    }

    @Test
    void shouldReturn1() {
        List<Integer> entries = Arrays.asList(0, -1, -3, -15, 0, 1);
        Assertions.assertEquals(1, extractClosestValueToZero(entries));
        Assertions.assertEquals(1, extractClosestValueToZeroRemovingZeros(entries));
        Assertions.assertEquals(1, extractClosestValueToZeroSettingWithHighestIntValue(entries));
    }


}
