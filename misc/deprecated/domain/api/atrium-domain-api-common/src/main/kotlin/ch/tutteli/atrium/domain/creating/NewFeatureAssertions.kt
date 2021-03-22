//TODO remove file with 1.0.0
@file:Suppress("DEPRECATION")
package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.core.None
import ch.tutteli.atrium.core.Option
import ch.tutteli.atrium.core.Some
import ch.tutteli.atrium.reporting.translating.Translatable
import ch.tutteli.atrium.reporting.translating.Untranslatable

/**
 * Represents an extracted feature of type [T] defined by the given [maybeSubject] including a [description]
 * and a [representation]
 *
 * @property description Will be used in reporting to describe the feature extraction - e.g. the name of a property,
 *   a method call etc.
 * @property representation The representation of the feature, in most cases the value behind the feature.
 * @property maybeSubject The feature as such where it is [Some] in case the extraction was successful or [None] if it
 *   was not.
 */
@Deprecated("Use MetaFeature from atrium-logic; will be removed with 0.17.0")
data class MetaFeature<T>(val description: Translatable, val representation: Any?, val maybeSubject: Option<T>) {
    constructor(description: String, representation: Any?, maybeSubject: Option<T>) :
        this(Untranslatable(description), representation, maybeSubject)

    constructor(description: String, subject: T) : this(description, subject, Some(subject))
}
