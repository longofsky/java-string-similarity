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
package info.debatty.java.stringsimilarity.examples;

import info.debatty.java.stringsimilarity.Cosine;
import java.util.Map;

/**
 * Example of computing cosine similarity with pre-computed profiles.
 * 用预先计算的轮廓计算余弦相似度的示例
 *
 * @author Thibault Debatty
 */
public class PrecomputedCosine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s1 = "My first string";
        String s2 = "My other string...";

        // Let's work with sequences of 2 characters...
        Cosine cosine = new Cosine(2);

        // Pre-compute the profile of strings
        Map<String, Integer> profile1 = cosine.getProfile(s1);
        Map<String, Integer> profile2 = cosine.getProfile(s2);

        // Prints 0.516185
        System.out.println(cosine.similarity(profile1, profile2));

    }

}
