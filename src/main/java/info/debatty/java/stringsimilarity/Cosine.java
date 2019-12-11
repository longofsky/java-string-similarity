/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import java.util.Map;

import net.jcip.annotations.Immutable;

/**
 * 两个字符串之间的相似度是这两个向量表示之间的角度的余弦值。计算为V1。 V2 /（| V1 | * | V2 |）余弦距离计算为1-余弦相似度
 *
 * @author Thibault Debatty
 */
@Immutable
public class Cosine extends ShingleBased implements
        NormalizedStringDistance, NormalizedStringSimilarity {

    /**
     * Implements Cosine Similarity between strings. The strings are first
     * transformed in vectors of occurrences of k-shingles (sequences of k
     * characters). In this n-dimensional space, the similarity between the two
     * strings is the cosine of their respective vectors.
     *
     * 实现字符串之间的余弦相似性。首先是字符串
     * 转换成k-shingles(k-的序列)的向量字符)。在这个n维空间中，两者之间的相似性弦是它们各自向量的余弦。
     *
     * @param k
     */
    public Cosine(final int k) {
        super(k);
    }

    /**
     * Implements Cosine Similarity between strings. The strings are first
     * transformed in vectors of occurrences of k-shingles (sequences of k
     * characters). In this n-dimensional space, the similarity between the two
     * strings is the cosine of their respective vectors. Default k is 3.
     * 实现字符串之间的余弦相似度。首先将字符串转换为k-shingles（k个字符的序列）的出现向量。在这个n维空间中，两个字符串之间的相似度是它们各自向量的余弦值。默认值k为3
     */
    public Cosine() {
        super();
    }

    /**
     * Compute the cosine similarity between strings.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The cosine similarity in the range [0, 1]
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
    public final double similarity(final String s1, final String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 1;
        }

        if (s1.length() < getK() || s2.length() < getK()) {
            return 0;
        }

        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);

        return dotProduct(profile1, profile2)
                / (norm(profile1) * norm(profile2));
    }

    /**
     * Compute the norm L2 : sqrt(Sum_i( v_i²)).
     *
     * @param profile
     * @return L2 norm
     */
    private static double norm(final Map<String, Integer> profile) {
        double agg = 0;

        for (Map.Entry<String, Integer> entry : profile.entrySet()) {
            agg += 1.0 * entry.getValue() * entry.getValue();
        }

        return Math.sqrt(agg);
    }

    private static double dotProduct(
            final Map<String, Integer> profile1,
            final Map<String, Integer> profile2) {

        // Loop over the smallest map
        Map<String, Integer> small_profile = profile2;
        Map<String, Integer> large_profile = profile1;
        if (profile1.size() < profile2.size()) {
            small_profile = profile1;
            large_profile = profile2;
        }

        double agg = 0;
        for (Map.Entry<String, Integer> entry : small_profile.entrySet()) {
            Integer i = large_profile.get(entry.getKey());
            if (i == null) {
                continue;
            }
            agg += 1.0 * entry.getValue() * i;
        }

        return agg;
    }

    /**
     * Return 1.0 - similarity.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return 1.0 - the cosine similarity in the range [0, 1]
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
    public final double distance(final String s1, final String s2) {
        return 1.0 - similarity(s1, s2);
    }

    /**
     * {@inheritDoc}
     * @param profile1
     * @param profile2
     * @return
     */
    public final double similarity(
            final Map<String, Integer> profile1,
            final Map<String, Integer> profile2) {

        return dotProduct(profile1, profile2)
                / (norm(profile1) * norm(profile2));
    }

}
