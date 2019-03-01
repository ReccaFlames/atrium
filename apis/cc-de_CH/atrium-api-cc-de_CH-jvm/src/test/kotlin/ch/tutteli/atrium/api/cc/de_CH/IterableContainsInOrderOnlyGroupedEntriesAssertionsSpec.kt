package ch.tutteli.atrium.api.cc.de_CH

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.builders.utils.Group

class IterableContainsInOrderOnlyGroupedEntriesAssertionsSpec : ch.tutteli.atrium.spec.integration.IterableContainsInOrderOnlyGroupedEntriesAssertionsSpec(
    AssertionVerbFactory,
    getContainsPair(),
    Companion::groupFactory,
    getContainsNullablePair(),
    Companion::nullableGroupFactory,
    "* ", "(/) ", "(x) ", "(!) ", "- ", "» ", ">> ", "=> ",
    "[Atrium][Builder] "
) {
    companion object : IterableContainsSpecBase() {
        fun getContainsPair()
            = "$contains.$inOrder.$only.$grouped.$within.$withinInAnyOrder" to Companion::containsInOrderOnlyGroupedInAnyOrderEntries

        private fun containsInOrderOnlyGroupedInAnyOrderEntries(
            plant: Assert<Iterable<Double>>,
            a1: Group<Assert<Double>.() -> Unit>,
            a2: Group<Assert<Double>.() -> Unit>,
            aX: Array<out Group<Assert<Double>.() -> Unit>>
        ): Assert<Iterable<Double>> {
            return plant.enthaelt.inGegebenerReihenfolge.nur.gruppiert.innerhalb.inBeliebigerReihenfolge(a1, a2, *aX)
        }

        private fun groupFactory(groups: Array<out Assert<Double>.() -> Unit>): Group<Assert<Double>.() -> Unit> {
            return when(groups.size){
                0 -> object: Group<Assert<Double>.() -> Unit>{ override fun toList() = listOf<Assert<Double>.() -> Unit>() }
                1 -> Eintrag(groups[0])
                else -> Eintraege(groups[0], *groups.drop(1).toTypedArray())
            }
        }

        fun getContainsNullablePair()
            = "$contains.$inOrder.$only.$grouped.$within.$withinInAnyOrder nullable" to Companion::containsInOrderOnlyGroupedNullableEntriesPair

        private fun containsInOrderOnlyGroupedNullableEntriesPair(
            plant: Assert<Iterable<Double?>>,
            a1: Group<(Assert<Double>.() -> Unit)?>,
            a2: Group<(Assert<Double>.() -> Unit)?>,
            aX: Array<out Group<(Assert<Double>.() -> Unit)?>>
        ): Assert<Iterable<Double?>> {
            return plant.enthaelt.inGegebenerReihenfolge.nur.gruppiert.innerhalb.inBeliebigerReihenfolge(a1, a2, *aX)
        }

        private fun nullableGroupFactory(groups: Array<out (Assert<Double>.() -> Unit)?>): Group<(Assert<Double>.() -> Unit)?> {
            return when(groups.size){
                0 -> object: Group<(Assert<Double>.() -> Unit)?>{ override fun toList() = listOf<Assert<Double>.() -> Unit>() }
                1 -> Eintrag(groups[0])
                else -> Eintraege(groups[0], *groups.drop(1).toTypedArray())
            }
        }
    }
}
