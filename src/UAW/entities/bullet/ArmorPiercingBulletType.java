package UAW.entities.bullet;

import arc.Events;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall;

import static mindustry.Vars.player;

public class ArmorPiercingBulletType extends TrailBulletType {
    /**
     * Percentage of damage that bypasses shields and armor, 1 equals to 100% bypass
     */
    public float armorIgnoreScl;

    public ArmorPiercingBulletType(float speed, float damage, float armorIgnoreScl) {
        this.speed = speed;
        this.damage = damage;
        this.armorIgnoreScl = armorIgnoreScl;
        hitEffect = Fx.hitBulletBig;
        pierce = true;
        pierceBuilding = true;
        buildingDamageMultiplier = 0.5f;
    }
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        float realDamage = b.damage * armorIgnoreScl;
        if (entity instanceof Healthc h) {
            h.damagePierce(realDamage);
            if (armorIgnoreScl < 1) {
                h.damage(b.damage - realDamage);
            }
        }
        if (entity instanceof Unit unit) {
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if (impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
        }

        //for achievements
        if (b.owner instanceof Wall.WallBuild && player != null && b.team == player.team() && entity instanceof Unit unit && unit.dead) {
            Events.fire(EventType.Trigger.phaseDeflectHit);

        }
    }
}

