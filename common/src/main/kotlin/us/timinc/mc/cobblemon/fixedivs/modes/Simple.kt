package us.timinc.mc.cobblemon.fixedivs.modes

import com.cobblemon.mod.common.api.events.starter.StarterChosenEvent
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import us.timinc.mc.cobblemon.fixedivs.FixedIvs
import us.timinc.mc.cobblemon.timcore.AbstractHandler
import us.timinc.mc.cobblemon.timcore.getIdentifier

object Simple : AbstractHandler<StarterChosenEvent>() {
    override fun handle(evt: StarterChosenEvent) {
        if (!(FixedIvs.enabledCount == 1 && FixedIvs.config.simple.enabled)) return

        val debugger = FixedIvs.debugger.getCaseDebugger()

        debugger.debug("${evt.player.name.string} chose starter ${evt.pokemon.getIdentifier()}.")
        debugger.debug("Simple mode is enabled.")
        val value = FixedIvs.config.simple.value
        Stats.PERMANENT.forEach { stat ->
            evt.pokemon.ivs[stat] = value
            debugger.debug("Set $stat to $value")
        }
    }
}