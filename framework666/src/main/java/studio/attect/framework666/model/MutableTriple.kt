package studio.attect.framework666.model

/**
 * Represents a triad of values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Triple exhibits value semantics, i.e. two triples are equal if all three components are equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @property first First value.
 * @property second Second value.
 * @property third Third value.
 */
data class MutableTriple<A, B, C>(var first: A, var second: B, var third: C) {
}