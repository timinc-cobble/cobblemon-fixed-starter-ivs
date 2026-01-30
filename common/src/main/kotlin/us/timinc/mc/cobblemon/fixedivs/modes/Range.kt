package us.timinc.mc.cobblemon.fixedivs.modes

import com.cobblemon.mod.common.api.events.starter.StarterChosenEvent
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import us.timinc.mc.cobblemon.fixedivs.FixedIvs
import us.timinc.mc.cobblemon.timcore.AbstractHandler
import us.timinc.mc.cobblemon.timcore.getIdentifier

object Range : AbstractHandler<StarterChosenEvent>() {
    override fun handle(evt: StarterChosenEvent) {
        if (!(FixedIvs.enabledCount == 1 && FixedIvs.config.range.enabled)) return

        val debugger = FixedIvs.debugger.getCaseDebugger()

        debugger.debug("${evt.player.name.string} chose starter ${evt.pokemon.getIdentifier()}.")
        debugger.debug("Range mode is enabled.")
        val range = FixedIvs.config.range.range
        Stats.PERMANENT.forEach { stat ->
            val value = range.random()
            evt.pokemon.ivs[stat] = value
            debugger.debug("Set $stat to $value, rolled from $range")
        }
    }
}