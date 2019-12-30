package cberg.aoc2019.puzzle14

import cberg.aoc2019.common.readInputLines
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle14 {
    @Test
    fun testPart1() {
        run {
            val input = listOf(
                "10 ORE => 10 A",
                "1 ORE => 1 B",
                "7 A, 1 B => 1 C",
                "7 A, 1 C => 1 D",
                "7 A, 1 D => 1 E",
                "7 A, 1 E => 1 FUEL"
            )
            assertEquals(31, part1(input))
        }
        run {
            val input = listOf(
                "9 ORE => 2 A",
                "8 ORE => 3 B",
                "7 ORE => 5 C",
                "3 A, 4 B => 1 AB",
                "5 B, 7 C => 1 BC",
                "4 C, 1 A => 1 CA",
                "2 AB, 3 BC, 4 CA => 1 FUEL"
            )
            assertEquals(165, part1(input))
        }
        run {
            val input = listOf(
                "157 ORE => 5 NZVS",
                "165 ORE => 6 DCFZ",
                "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
                "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
                "179 ORE => 7 PSHF",
                "177 ORE => 5 HKGWZ",
                "7 DCFZ, 7 PSHF => 2 XJWVT",
                "165 ORE => 2 GPVTF",
                "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
            )
            assertEquals(13312, part1(input))
        }
        run {
            val input = listOf(
                "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
                "17 NVRVD, 3 JNWZP => 8 VPVL",
                "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
                "22 VJHF, 37 MNCFX => 5 FWMGM",
                "139 ORE => 4 NVRVD",
                "144 ORE => 7 JNWZP",
                "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
                "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
                "145 ORE => 6 MNCFX",
                "1 NVRVD => 8 CXFTF",
                "1 VJHF, 6 MNCFX => 4 RFSQX",
                "176 ORE => 6 VJHF"
            )
            assertEquals(180697, part1(input))
        }
        run {
            val input = listOf(
                "171 ORE => 8 CNZTR",
                "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
                "114 ORE => 4 BHXH",
                "14 VRPVC => 6 BMBT",
                "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
                "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
                "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
                "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
                "5 BMBT => 4 WPTQ",
                "189 ORE => 9 KTJDG",
                "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
                "12 VRPVC, 27 CNZTR => 2 XDBXC",
                "15 KTJDG, 12 BHXH => 5 XCVML",
                "3 BHXH, 2 VRPVC => 7 MZWV",
                "121 ORE => 7 VRPVC",
                "7 XCVML => 6 RJRHP",
                "5 BHXH, 4 VRPVC => 5 LTCX"
            )
            assertEquals(2210736, part1(input))
        }
        run {
            val input = readInputLines("input14.txt")
            assertEquals(387001, part1(input))
        }
    }

    @Test
    fun testPart2() {
        run {
            val input = listOf(
                "157 ORE => 5 NZVS",
                "165 ORE => 6 DCFZ",
                "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
                "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
                "179 ORE => 7 PSHF",
                "177 ORE => 5 HKGWZ",
                "7 DCFZ, 7 PSHF => 2 XJWVT",
                "165 ORE => 2 GPVTF",
                "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
            )
            assertEquals(82892753, part2(input))
        }
        run {
            val input = listOf(
                "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
                "17 NVRVD, 3 JNWZP => 8 VPVL",
                "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
                "22 VJHF, 37 MNCFX => 5 FWMGM",
                "139 ORE => 4 NVRVD",
                "144 ORE => 7 JNWZP",
                "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
                "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
                "145 ORE => 6 MNCFX",
                "1 NVRVD => 8 CXFTF",
                "1 VJHF, 6 MNCFX => 4 RFSQX",
                "176 ORE => 6 VJHF"
            )
            assertEquals(5586022, part2(input))
        }
        run {
            val input = listOf(
                "171 ORE => 8 CNZTR",
                "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
                "114 ORE => 4 BHXH",
                "14 VRPVC => 6 BMBT",
                "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
                "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
                "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
                "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
                "5 BMBT => 4 WPTQ",
                "189 ORE => 9 KTJDG",
                "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
                "12 VRPVC, 27 CNZTR => 2 XDBXC",
                "15 KTJDG, 12 BHXH => 5 XCVML",
                "3 BHXH, 2 VRPVC => 7 MZWV",
                "121 ORE => 7 VRPVC",
                "7 XCVML => 6 RJRHP",
                "5 BHXH, 4 VRPVC => 5 LTCX"
            )
            assertEquals(460664, part2(input))
        }
        run {
            val input = readInputLines("input14.txt")
            assertEquals(3412429, part2(input))
        }
    }
}

private fun part1(input: List<String>): Long {
    val reactions = parseReactions(input)
    return getOresNeeded(reactions, Chemical(1, "FUEL"))
}

private fun part2(input: List<String>): Long {
    val reactions = parseReactions(input)
    val ores = 1000000000000

    var low = ores / getOresNeeded(reactions, Chemical(1, "FUEL"))
    var high = 2 * ores - low
    while (low + 1 < high) {
        val middle = (high + low) / 2
        if (getOresNeeded(reactions, Chemical(middle, "FUEL")) >= ores) {
            high = middle
        } else {
            low = middle
        }
    }
    return low
}

private fun getOresNeeded(reactions: Map<String, Reaction>, needed: Chemical): Long {
    val balance = mutableMapOf(needed.name to -needed.amount)
    val neededChemicals = mutableSetOf(needed.name)

    while (neededChemicals.isNotEmpty()) {
        val neededChemical = neededChemicals.first()
        neededChemicals.remove(neededChemical)

        val neededAmount = -(balance[neededChemical] ?: 0)
        val reaction = reactions[neededChemical] ?: error("No reaction found for $neededChemical")
        val factor = neededAmount / reaction.result.amount + if (neededAmount % reaction.result.amount == 0L) 0 else 1
        for (ingredient in reaction.ingredients) {
            val amount = ingredient.amount * factor
            val newBalance = balance.merge(ingredient.name, -amount, Long::plus)!!
            if (newBalance < 0 && ingredient.name != "ORE") {
                neededChemicals += ingredient.name
            }
        }
        val newBalance = balance.merge(neededChemical, reaction.result.amount * factor, Long::plus)
        if (newBalance == 0L) {
            balance.remove(neededChemical)
        }
    }

    return -balance.getOrDefault("ORE", 0L)
}

private fun parseReactions(input: List<String>) =
    input.map { line ->
        val (ingredientsStr, resultStr) = line.split(" => ")
        val result = resultStr.toChemical()
        val ingredients = ingredientsStr.split(", ").map { ingredient -> ingredient.toChemical() }
        result.name to Reaction(ingredients, result)
    }.toMap()

private data class Chemical(val amount: Long, val name: String)

private fun String.toChemical() = split(" ").let { (amount, name) -> Chemical(amount.toLong(), name) }

private class Reaction(val ingredients: List<Chemical>, val result: Chemical)
