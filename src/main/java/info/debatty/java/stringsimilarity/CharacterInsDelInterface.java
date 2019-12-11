package info.debatty.java.stringsimilarity;


/**
 * As an adjunct to CharacterSubstitutionInterface, this interface
 * allows you to specify the cost of deletion or insertion of a
 * character.
 * 作为CharacterSubstitutionInterface的辅助，此接口允许您指定删除或插入字符的费用 结合权重使用
 */
public interface CharacterInsDelInterface {
    /**
     * @param c The character being deleted.
     * @return The cost to be allocated to deleting the given character,
     * in the range [0, 1].
     */
    double deletionCost(char c);

    /**
     * @param c The character being inserted.
     * @return The cost to be allocated to inserting the given character,
     * in the range [0, 1].
     */
    double insertionCost(char c);
}
