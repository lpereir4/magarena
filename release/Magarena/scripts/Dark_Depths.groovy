[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Remove an ice counter"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{3}")];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Remove an ice counter from SN. " +
                "If SN has no ice counters on it, sacrifice it and put a legendary 20/20 black Avatar creature token with flying and indestructible named Marit Lage onto the battlefield."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPermanent perm = event.getPermanent()
            game.doAction(new MagicChangeCountersAction(
                perm,
                MagicCounterType.Charge,
                -1,
                true
            ));            
            if (perm.getCounters(MagicCounterType.Charge) == 0) {
                game.doAction(new MagicSacrificeAction(perm));
                game.doAction(new MagicPlayTokenAction(
                    event.getPlayer(), 
                    TokenCardDefinitions.get("legendary 20/20 black Avatar creature token with flying and indestructible named Marit Lage")
                ))
            }
        }
    }
]
