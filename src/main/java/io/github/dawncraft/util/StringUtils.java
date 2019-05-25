package io.github.dawncraft.util;

public class StringUtils
{
    private static final int[] A_ARRAY = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] R_ARRAY = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String toRome(int aNumber)
    {
        if (aNumber < 1 || aNumber > 3999)
        {
            return String.valueOf(aNumber);
        }
        String rNumber = "";
        for (int i = 0; i < A_ARRAY.length; i++)
        {
            while (aNumber >= A_ARRAY[i])
            {
                rNumber += R_ARRAY[i];
                aNumber -= A_ARRAY[i];
            }
        }
        return rNumber;
    }
}
