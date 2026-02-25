package org.example;

import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

class NumberUtilsTest {

    /*
  -- Partitions
  Single input (left, right):
 null
 empty
 size == 1 and size > 1
 valid digits and invalid digits
 leading zeros (size>1 && first==0)
interaction:
 any null (left/right/both)
 any empty (left/right/both)
 same length vs different length
 no carry over vs carry over vs carry-chain vs final carry (new digit)
Output:
- null
- length = maxLen
- length = maxLen + 1

    */
    @Test
    void addreturnsNullwhenLeftIsNull() {
        // left = null, right = [1,2] -> null
        assertThat(NumberUtils.add(null, asList(1, 2))).isNull();
    }

    @Test
    void addreturnsNullwhenRightIsNull() {
        // left = [1,2], right = null -> null
        assertThat(NumberUtils.add(asList(1, 2), null)).isNull();
    }
    @Test
    void addbothNullreturnsNull() {
        // left = null, right = null -> null
        assertThat(NumberUtils.add(null, null)).isNull();
    }


    @Test
    void addsingleDigitnoCarryOverreturnsSingleDigitSum() {
        // left = [2], right [3] -> [5]
        assertThat(NumberUtils.add(asList(2), asList(3)))
                .containsExactly(5);
    }

    @Test
    void adddifferentLengthsnoCarryOverreturnsCorrectSum() {
        // left = [1,4,2], right = [3,5] -> [1,6,8]
        assertThat(NumberUtils.add(asList(1, 4, 2), asList(3, 5)))
                .containsExactly(1, 7, 7);
    }

    @Test
    void addinternalCarrynoNewLeadingDigitreturnsCorrectSum() {
        // left = [1,7], right = [2,1] -> [3,8]
        assertThat(NumberUtils.add(asList(1, 7), asList(2, 1)))
                .containsExactly(3, 8);
    }

    @Test
    void addcarryCreatesNewLeadingDigitreturnsCorrectSum() {
        // left = [9,7] , right = [3] -> [1, 0, 0]
        assertThat(NumberUtils.add(asList(9, 7), asList(3)))
                .containsExactly(1, 0, 0);
    }

    @Test
    void addthrowsIllegalArgumentExceptionwhenDigitNegative() {
        // left = [3,1,-2] , right [4,2] -> invalid since there is a negative value, returning IllegalArgument Exception
        assertThatThrownBy(() -> NumberUtils.add(asList(3, 1, -2), asList(4, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addthrowsIllegalArgumentExceptionwhenDigitAbove9() {
        // left = [1,11,2] , right [4,2] -> invalid since there is a  value greater than 9, returning IllegalArgument Exception
        assertThatThrownBy(() -> NumberUtils.add(asList(1, 11, 2), asList(4, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addremovesLeadingZerosInResult() {
        // left = [0,0,1], right = [0] -> [1]
        assertThat(NumberUtils.add(asList(0, 0, 1), asList(0)))
                .containsExactly(1);
    }

}