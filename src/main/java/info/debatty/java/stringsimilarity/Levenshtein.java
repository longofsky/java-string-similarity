package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import net.jcip.annotations.Immutable;

/**
 * 两个单词之间的Levenshtein距离是将一个字符串转换为另一个字符串所需的单字符编辑（插入，删除或替换）的最小数量
 *
 * @author Thibault Debatty
 */
@Immutable
public class Levenshtein implements MetricStringDistance {

    /**
     * Equivalent to distance(s1, s2, Integer.MAX_VALUE).
     */
    @Override
    public final double distance(final String s1, final String s2) {
        return distance(s1, s2, Integer.MAX_VALUE);
    }

    /**
     * The Levenshtein distance, or edit distance, between two words is the
     * minimum number of single-character edits (insertions, deletions or
     * substitutions) required to change one word into the other.
     *
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     *
     * It is always at least the difference of the sizes of the two strings.
     * It is at most the length of the longer string.
     * It is zero if and only if the strings are equal.
     * If the strings are the same size, the Hamming distance is an upper bound
     * on the Levenshtein distance.
     * The Levenshtein distance verifies the triangle inequality (the distance
     * between two strings is no greater than the sum Levenshtein distances from
     * a third string).
     *
     * Implementation uses dynamic programming (Wagner–Fischer algorithm), with
     * only 2 rows of data. The space requirement is thus O(m) and the algorithm
     * runs in O(mn).
     *
     * 两个单词之间的Levenshtein距离或编辑距离是将一个单词转换为另一个单词所需的最小单字符编辑（插入，删除或*替换）次数。
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     * 始终至少是两个字符串的大小之差。 它最多是较长字符串的长度。 当且仅当字符串相等时为零。
     * 如果字符串大小相同，则说明距离为Levenshtein距离的上限*。 *
     * Levenshtein距离验证三角形不等式（两个字符串之间的距离*不大于与*第三个字符串的Levenshtein距离之和）。
     * 实现使用动态编程（Wagner–Fischer算法），仅2行数据。因此，空间需求为O（m），算法 在O（mn）中运行
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @param limit The maximum result to compute before stopping. This
     *              means that the calculation can terminate early if you
     *              only care about strings with a certain similarity.
     *              Set this to Integer.MAX_VALUE if you want to run the
     *              calculation to completion in every case.
     * 停止前要计算的最大结果。这表示如果您只在乎*具有相似性的字符串，则计算可以提前终止。
     *              如果要在任何情况下都执行计算以完成操作，请将其设置为Integer.MAX_VALUE
     * @return The computed Levenshtein distance.
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final double distance(final String s1, final String s2,
                                 final int limit) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 0;
        }

        if (s1.length() == 0) {
            return s2.length();
        }

        if (s2.length() == 0) {
            return s1.length();
        }

        // create two work vectors of integer distances
        int[] v0 = new int[s2.length() + 1];
        int[] v1 = new int[s2.length() + 1];
        int[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s1.length(); i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            int minv1 = v1[0];

            // use formula to fill in the rest of the row
            for (int j = 0; j < s2.length(); j++) {
                int cost = 1;
                if (s1.charAt(i) == s2.charAt(j)) {
                    cost = 0;
                }
                v1[j + 1] = Math.min(
                        v1[j] + 1,              // Cost of insertion
                        Math.min(
                                v0[j + 1] + 1,  // Cost of remove
                                v0[j] + cost)); // Cost of substitution

                minv1 = Math.min(minv1, v1[j + 1]);
            }

            if (minv1 >= limit) {
                return limit;
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            //System.arraycopy(v1, 0, v0, 0, v0.length);

            // Flip references to current and previous row
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;

        }

        return v0[s2.length()];
    }
}
