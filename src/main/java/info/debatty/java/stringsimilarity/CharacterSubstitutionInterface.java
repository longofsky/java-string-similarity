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

/**
 * Used to indicate the cost of character substitution.
 * 用于指示字符替换的成本
 *
 * Cost should always be in [0.0 .. 1.0]
 * For example, in an OCR application, cost('o', 'a') could be 0.4
 * In a checkspelling application, cost('u', 'i') could be 0.4 because these are
 * next to each other on the keyboard...
 * 费用应始终为[0.0 .. 1.0] 、
 * 例如，在OCR应用程序中，cost（'o'，'a'）可以为0.4
 * 在拼写检查应用程序中，cost（'u'，'i'）可以为0.4，因为这些在键盘上彼此相邻
 *
 * @author Thibault Debatty
 */
public interface CharacterSubstitutionInterface {
    /**
     * Indicate the cost of substitution c1 and c2.
     * 指出替代成本c1和c2
     * @param c1 The first character of the substitution.
     * @param c2 The second character of the substitution.
     * @return The cost in the range [0, 1].
     */
    double cost(char c1, char c2);
}
