package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import net.jcip.annotations.Immutable;

/**
 * The longest common subsequence (LCS) problem consists in finding the longest
 * subsequence common to two (or more) sequences. It differs from problems of
 * finding common substrings: unlike substrings, subsequences are not required
 * to occupy consecutive positions within the original sequences.
 *
 * It is used by the diff utility, by Git for reconciling multiple changes, etc.
 *
 * The LCS distance between Strings X (length n) and Y (length m) is n + m - 2
 * |LCS(X, Y)| min = 0 max = n + m
 *
 * LCS distance is equivalent to Levenshtein distance, when only insertion and
 * deletion is allowed (no substitution), or when the cost of the substitution
 * is the double of the cost of an insertion or deletion.
 *
 * ! This class currently implements the dynamic programming approach, which has
 * a space requirement O(m * n)!
 *
 * 最长公共子序列（LCS）问题在于找到两个（或更多）序列公共的最长*子序列。
 * 它不同于查找公共子字符串的问题：与子字符串不同，不需要子序列占据原始序列中的连续位置。
 * 它由diff实用程序，Git用于协调多个更改等。字符串X（长度n）和Y（长度m）之间的LCS距离为n + m-2
 * | LCS（X，Y ）| min = 0 max = n + m
 * LCS距离等于Levenshtein距离，仅允许插入和删除（不允许替换），或者替换成本是插入或删除成本的两倍时。
 * ！此类当前实现了动态编程方法，该方法具有空间要求O（m * n）！
 *
 * @author Thibault Debatty
 */
@Immutable
public class LongestCommonSubsequence implements StringDistance {

    /**
     * Return the LCS distance between strings s1 and s2, computed as |s1| +
     * |s2| - 2 * |LCS(s1, s2)|.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return the LCS distance between strings s1 and s2, computed as |s1| +
     * |s2| - 2 * |LCS(s1, s2)|
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
    public final double distance(final String s1, final String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 0;
        }

        return s1.length() + s2.length() - 2 * length(s1, s2);
    }

    /**
     * Return the length of Longest Common Subsequence (LCS) between strings s1
     * and s2.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return the length of LCS(s1, s2)
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final int length(final String s1, final String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        /* function LCSLength(X[1..m], Y[1..n])
         C = array(0..m, 0..n)

         for i := 0..m
         C[i,0] = 0

         for j := 0..n
         C[0,j] = 0

         for i := 1..m
         for j := 1..n
         if X[i] = Y[j]
         C[i,j] := C[i-1,j-1] + 1
         else
         C[i,j] := max(C[i,j-1], C[i-1,j])
         return C[m,n]
         */
        int s1_length = s1.length();
        int s2_length = s2.length();
        char[] x = s1.toCharArray();
        char[] y = s2.toCharArray();

        int[][] c = new int[s1_length + 1][s2_length + 1];

        for (int i = 1; i <= s1_length; i++) {
            for (int j = 1; j <= s2_length; j++) {
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        return c[s1_length][s2_length];
    }
}
