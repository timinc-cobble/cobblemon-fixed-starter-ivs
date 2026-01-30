package us.timinc.mc.cobblemon.fixedivs.config

import com.cobblemon.mod.common.api.pokemon.stats.Stat

data class GranularConfig(
    val values: Map<Stat, Int>,
    val enabled: Boolean,
)