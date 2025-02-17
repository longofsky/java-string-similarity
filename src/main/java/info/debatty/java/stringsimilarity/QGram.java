package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.jcip.annotations.Immutable;

/**
 * Q-gram distance, as defined by Ukkonen in "Approximate string-matching with
 * q-grams and maximal matches". The distance between two strings is defined as
 * the L1 norm of the difference of their profiles (the number of occurences of
 * each n-gram): SUM( |V1_i - V2_i| ). Q-gram distance is a lower bound on
 * Levenshtein distance, but can be computed in O(m + n), where Levenshtein
 * requires O(m.n).
 *
 * 由Ukkonen在“带有q-grams和最大匹配项的近似字符串匹配”中定义的Q-gram距离。两个字符串之间的距离定义为它们的轮廓差异的L1范数（每个n-gram出现的次数）：SUM（| V1_i-V2_i |）。
 * Q-gram距离是  Levenshtein距离的下限，但可以用O（m + n）计算，其中Levenshtein 需要O（m.n）
 *
 * @author Thibault Debatty
 */
@Immutable
public class QGram extends ShingleBased implements StringDistance {

    /**
     * Q-gram similarity and distance. Defined by Ukkonen in "Approximate
     * string-matching with q-grams and maximal matches",
     * Q-gram相似度和距离。由Ukkonen在“与q-gram和最大匹配项的近似*字符串匹配”中定义
     * http://www.sciencedirect.com/science/article/pii/0304397592901434 The
     * distance between two strings is defined as the L1 norm of the difference
     * of their profiles (the number of occurences of each k-shingle). Q-gram
     * distance is a lower bound on Levenshtein distance, but can be computed in
     * O(|A| + |B|), where Levenshtein requires O(|A|.|B|)
     * 两个字符串之间的距离定义为它们的轮廓之差（每个k形阴影的出现次数）的L1范数。
     * Q-gram 距离是Levenshtein距离的下限，但是可以用*O（| A | + | B |）计算，其中Levenshtein需要O（| A |。| B |）
     *
     * @param k
     */
    public QGram(final int k) {
        super(k);
    }

    public QGram() {
        super();
    }

    /**
     * The distance between two strings is defined as the L1 norm of the
     * difference of their profiles (the number of occurence of each k-shingle).
     * 两根弦之间的距离定义为它们的轮廓的差的L1范数（每个k形木片的出现次数）
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The computed Q-gram distance.
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

        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);

        return distance(profile1, profile2);
    }

    /**
     * Compute QGram distance using precomputed profiles.
     * 使用预先计算的配置文件计算QGram距离。
     *
     * @param profile1
     * @param profile2
     * @return
     */
    public final double distance(
            final Map<String, Integer> profile1,
            final Map<String, Integer> profile2) {

        Set<String> union = new HashSet<String>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());

        int agg = 0;
        for (String key : union) {
            int v1 = 0;
            int v2 = 0;
            Integer iv1 = profile1.get(key);
            if (iv1 != null) {
                v1 = iv1;
            }

            Integer iv2 = profile2.get(key);
            if (iv2 != null) {
                v2 = iv2;
            }
            agg += Math.abs(v1 - v2);
        }
        return agg;
    }
}
