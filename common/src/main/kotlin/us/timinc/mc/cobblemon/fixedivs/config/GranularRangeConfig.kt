package us.timinc.mc.cobblemon.fixedivs.config

import com.cobblemon.mod.common.api.pokemon.stats.Stat

data class GranularRangeConfig(
    val ranges: Map<Stat, IntRange>,
    val enabled: Boolean,
)
