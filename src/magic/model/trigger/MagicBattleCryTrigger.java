package magic.model.trigger;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.action.MagicChangeTurnPTAction;
import magic.model.event.MagicEvent;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;

import java.util.Collection;

public class MagicBattleCryTrigger extends MagicWhenAttacksTrigger {

    private static final MagicBattleCryTrigger INSTANCE = new MagicBattleCryTrigger();

    private MagicBattleCryTrigger() {
        super(8);
    }
    
    public static MagicBattleCryTrigger create() {
        return INSTANCE;
    }
    
    @Override
    public MagicEvent executeTrigger(
            final MagicGame game,
            final MagicPermanent permanent,
            final MagicPermanent data) {
        return (permanent == data) ?
            new MagicEvent(
                permanent,
                this,
                "Each other attacking creature gets +1/+0 until end of turn."
            ):
            MagicEvent.NONE;
    }

    @Override
    public void executeEvent(
            final MagicGame game,
            final MagicEvent event,
            final Object[] data,
            final Object[] choiceResults) {
        final MagicPermanent permanent = event.getPermanent();
        final Collection<MagicTarget> targets = game.filterTargets(
                event.getPlayer(),
                MagicTargetFilter.TARGET_ATTACKING_CREATURE);
        for (final MagicTarget target : targets) {
            final MagicPermanent creature = (MagicPermanent)target;
            if (creature != permanent && creature.isAttacking()) {
                game.doAction(new MagicChangeTurnPTAction(creature,1,0));
            }
        }
    }
}
