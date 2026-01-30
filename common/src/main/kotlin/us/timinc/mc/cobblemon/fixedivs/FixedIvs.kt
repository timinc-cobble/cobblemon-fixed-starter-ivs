package us.timinc.mc.cobblemon.fixedivs

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.IVs
import us.timinc.mc.cobblemon.fixedivs.config.CappedConfig
import us.timinc.mc.cobblemon.fixedivs.config.GranularConfig
import us.timinc.mc.cobblemon.fixedivs.config.GranularRangeConfig
import us.timinc.mc.cobblemon.fixedivs.config.RangeConfig
import us.timinc.mc.cobblemon.fixedivs.config.SimpleConfig
import us.timinc.mc.cobblemon.fixedivs.modes.Capped
import us.timinc.mc.cobblemon.fixedivs.modes.Granular
import us.timinc.mc.cobblemon.fixedivs.modes.GranularRange
import us.timinc.mc.cobblemon.fixedivs.modes.Range
import us.timinc.mc.cobblemon.fixedivs.modes.Simple
import us.timinc.mc.cobblemon.timcore.AbstractConfig
import us.timinc.mc.cobblemon.timcore.AbstractMod
import us.timinc.mc.cobblemon.timcore.event.ReloadConfigEvent

const val MOD_ID: String = "fixed_ivs"

object FixedIvs : AbstractMod<FixedIvs.FixedIvsConfig>(MOD_ID, FixedIvsConfig::class.java) {
    class FixedIvsConfig : AbstractConfig() {
        val simple: SimpleConfig = SimpleConfig(IVs.MAX_VALUE, false)
        val range: RangeConfig = RangeConfig(0..IVs.MAX_VALUE, false)
        val capped: CappedConfig = CappedConfig(0, leaveRolledPerfects = true, enabled = false)
        val granular: GranularConfig = GranularConfig(emptyMap(), true)
        val granularRange: GranularRangeConfig = GranularRangeConfig(emptyMap(), false)
    }

    val enabledCount
        get() = listOf(
            config.simple.enabled,
            config.range.enabled,
            config.capped.enabled,
            config.granular.enabled,
            config.granularRange.enabled
        ).count { it }

    private fun validateConfig(evt: ReloadConfigEvent) {
        if (enabledCount > 1) {
            debugger.debug("$enabledCount modes enabled. Only one can be enabled at a time.", true)
        }
        if (enabledCount < 1) {
            debugger.debug("All modes have been disabled. At least one must be enabled.", true)
        }
    }

    init {
        RELOAD_CONFIG.subscribe(::validateConfig)
        CobblemonEvents.STARTER_CHOSEN.subscribe(Simple::handle)
        CobblemonEvents.STARTER_CHOSEN.subscribe(Range::handle)
        CobblemonEvents.STARTER_CHOSEN.subscribe(Capped::handle)
        CobblemonEvents.STARTER_CHOSEN.subscribe(Granular::handle)
        CobblemonEvents.STARTER_CHOSEN.subscribe(GranularRange::handle)
    }
}