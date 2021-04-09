package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.api.infix.en_GB.creating.Values
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.logic.*
import ch.tutteli.atrium.logic.creating.transformers.SubjectChangerBuilder
import ch.tutteli.atrium.logic.creating.typeutils.IterableLike
import ch.tutteli.atrium.logic.utils.iterableLikeToIterable
import kotlin.reflect.KClass

/**
 * Expects that the subject of `this` expectation is (equal to) [expected].
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.toEqual
 */
infix fun <T> Expect<T>.toEqual(expected: T): Expect<T> = _logicAppend { toBe(expected) }

/**
 * Expects that the subject of `this` expectation is not (equal to) [expected].
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToEqual
 */
infix fun <T> Expect<T>.notToEqual(expected: T): Expect<T> = _logicAppend { notToBe(expected) }

/**
 * Expects that the subject of `this` expectation is the same instance as [expected].
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.toBeTheInstance
 */
infix fun <T> Expect<T>.toBeTheInstance(expected: T): Expect<T> = _logicAppend { isSameAs(expected) }

/**
 * Expects that the subject of `this` expectation is not the same instance as [expected].
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToBeTheInstance
 */
infix fun <T> Expect<T>.notToBeTheInstance(expected: T): Expect<T> = _logicAppend { isNotSameAs(expected) }


/**
 * Expects that the subject of `this` expectation is either `null` in case [assertionCreatorOrNull]
 * is `null` or is not `null` and holds all assertions [assertionCreatorOrNull] creates.
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.toEqualNullIfNullGivenElse
 */
infix fun <T : Any> Expect<T?>.toEqualNullIfNullGivenElse(
    assertionCreatorOrNull: (Expect<T>.() -> Unit)?
): Expect<T?> = _logicAppend { toBeNullIfNullGivenElse(assertionCreatorOrNull) }


/**
 * Expects that the subject of `this` expectation is not null and changes the subject to the non-nullable version.
 *
 * @param o The filler object [o].
 *
 * @return An [Expect] with the non-nullable type [T] (was `T?` before).
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToEqualNullFeature
 */
inline infix fun <reified T : Any> Expect<T?>.notToEqualNull(@Suppress("UNUSED_PARAMETER") o: o): Expect<T> =
    notToEqualNullButToBeA(T::class).transform()

/**
 * Expects that the subject of `this` expectation is not null and
 * that it holds all assertions the given [assertionCreator] creates.
 *
 * @return An [Expect] with the non-nullable type [T] (was `T?` before).
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToEqualNull
 */
inline infix fun <reified T : Any> Expect<T?>.notToEqualNull(noinline assertionCreator: Expect<T>.() -> Unit): Expect<T> =
    notToEqualNullButToBeA(T::class).transformAndAppend(assertionCreator)

@PublishedApi // in order that _logic does not become part of the API we have this extra function
internal fun <T : Any> Expect<T?>.notToEqualNullButToBeA(kClass: KClass<T>): SubjectChangerBuilder.ExecutionStep<T?, T> =
    _logic.notToBeNullButOfType(kClass)


/**
 * Expects that the subject of `this` expectation *is a* [TSub] (the same type or a sub-type)
 * and changes the subject to this type.
 *
 * Notice, that asserting a function type is [flawed](https://youtrack.jetbrains.com/issue/KT-27846).
 * The actual types are ignored as function types erase to Function0,
 * Function1 etc. on byte code level, which means the assertion holds as long as the subject is a
 * function and has the same amount of arguments regardless if the types differ. For instance
 * `assert({x: Int -> "hello"}).isA<String -> Unit>{}` holds, even though `(Int) -> String` is clearly not
 * a `(String) -> Unit`.
 *
 * More generally speaking, the [flaw](https://youtrack.jetbrains.com/issue/KT-27826) applies to all generic types.
 * For instance `isA<List<String>>` would only check if the subject is a `List` without checking if
 * the element type is actually `String`. Or in other words
 * `assert(listOf(1, 2)).isA<List<String>>{}` holds, even though `List<Int>` is clearly not a `List<String>`.
 *
 * @return An [Expect] with the new type [TSub].
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.toBeAFeature
 */
//TODO make infix and add `o` as parameter as soon as https://youtrack.jetbrains.com/issue/KT-21593 is fixed
inline fun <reified TSub : Any> Expect<*>.toBeA(): Expect<TSub> =
    toBeA(TSub::class).transform()

@PublishedApi // in order that _logic does not become part of the API we have this extra function
internal fun <TSub : Any> Expect<*>.toBeA(kClass: KClass<TSub>): SubjectChangerBuilder.ExecutionStep<out Any?, TSub> =
    _logic.isA(kClass)

/**
 * Expects that the subject of `this` expectation *is a* [TSub] (the same type or a sub-type) and
 * that it holds all assertions the given [assertionCreator] creates.
 *
 * Notice, in contrast to other assertion functions which expect an [assertionCreator], this function returns not
 * [Expect] of the initial type, which was some type `T `, but an [Expect] of the specified type [TSub].
 * This has the side effect that a subsequent call has only assertion functions available which are suited for [TSub].
 * Since [Expect] is invariant it especially means that an assertion function which was not written in a generic way
 * will not be available. Fixing such a function is easy (in most cases),
 * you need to transform it into a generic from. Following an example:
 *
 * ```
 * interface Person
 * class Student: Person
 * fun Expect<Person>.foo()        = "dummy"  // limited only to Person, not recommended
 * fun <T: Person> Expect<T>.bar() = "dummy"  // available to Person and all subtypes, the way to go
 * fun Expect<Student>.baz()       = "dummy"  // specific only for Student, ok since closed class
 *
 * val p: Person = Student()
 * expect(p)               // subject of type Person
 *   .isA<Student> { ... } // subject now refined to Student
 *   .baz()                // available via Student
 *   .foo()                // not available to Student, only to Person, results in compilation error
 *   .bar()                // available via T : Person
 * ```
 *
 * Notice, that asserting a function type is [flawed](https://youtrack.jetbrains.com/issue/KT-27846).
 * The actual types are ignored as function types erase to Function0,
 * Function1 etc. on byte code level, which means the assertion holds as long as the subject is a
 * function and has the same amount of arguments regardless if the types differ. For instance
 * `assert({x: Int -> "hello"}).isA<String -> Unit>{}` holds, even though `(Int) -> String` is clearly not
 * a `(String) -> Unit`.
 *
 * More generally speaking, the [flaw](https://youtrack.jetbrains.com/issue/KT-27826) applies to all generic types.
 * For instance `isA<List<String>>` would only check if the subject is a `List` without checking if
 * the element type is actually `String`. Or in other words
 * `assert(listOf(1, 2)).isA<List<String>>{}` holds, even though `List<Int>` is clearly not a `List<String>`.
 *
 * @return An [Expect] with the new type [TSub].
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.toBeA
 */
inline infix fun <reified TSub : Any> Expect<*>.toBeA(noinline assertionCreator: Expect<TSub>.() -> Unit): Expect<TSub> =
    toBeA(TSub::class).transformAndAppend(assertionCreator)

/**
 * Expects that the subject of `this` expectation is not (equal to) in [values].
 *
 * @param values The values which are not expected to be contained within the subject of `this` expectation
 *   -- use the function `values(t, ...)` to create a [Values].
 *
 * @return an [Expect] for the subject of `this` expectation.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToEqualOneOf
 */
infix fun <T> Expect<T>.notToEqualOneOf(values: Values<T>): Expect<T> =
    _logicAppend { isNotIn(values.toList()) }

/**
 * Expects that the subject of `this` expectation is not (equal to) any value of [expected].
 *
 * Notice that a runtime check applies which assures that only [Iterable], [Sequence] or one of the [Array] types
 * are passed. This function expects [IterableLike] (which is a typealias for [Any]) to avoid cluttering the API.
 *
 * @return an [Expect] for the subject of `this` expectation.
 * @throws IllegalArgumentException in case the iterable is empty.
 *
 * @since 0.17.0
 *
 * @sample ch.tutteli.atrium.api.infix.en_GB.samples.AnyExpectationSamples.notToEqualOneIn
 */
infix fun <T> Expect<T>.notToEqualOneIn(expected: IterableLike): Expect<T> =
    _logicAppend { isNotIn(iterableLikeToIterable(expected)) }
