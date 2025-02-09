package UAW.ai.types;

import mindustry.ai.types.FlyingAI;
import mindustry.entities.units.UnitCommand;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.state;

public class BomberJetAI extends FlyingAI {
	@Override
	public void updateMovement() {
		unloadPayloads();
		if (target != null && unit.hasWeapons() && command() == UnitCommand.attack) {
			if (!unit.type.circleTarget) {
				moveTo(target, unit.type.range * 0.8f);
				unit.lookAt(target);
			} else {
				attack(unit.type.range * 0.8f);
				unit.lookAt(unit.vel().angle());
			}
		}

		if (target == null && command() == UnitCommand.attack && state.rules.waves && unit.team == state.rules.defaultTeam) {
			moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 240f);
			unit.lookAt(unit.vel().angle());
		}

		if (command() == UnitCommand.rally) {
			moveTo(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 60f);
			unit.lookAt(unit.vel().angle());
		}
	}
}
