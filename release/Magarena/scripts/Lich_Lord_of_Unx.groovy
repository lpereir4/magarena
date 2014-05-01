[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Mill"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{U}{U}{B}{B}")];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
             return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_PLAYER,
                this,
                "Target player\$ loses X life and puts the top X cards of his or her library into his or her graveyard, " + 
                "where X is the number of Zombies you control."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer player ->
                int amount = event.getPlayer().getNrOfPermanents(MagicSubType.Zombies);
                game.doAction(new MagicChangeLifeAction(player,-amount));
                game.doAction(new MagicMillLibraryAction(player,amount));
            });
        }
    }
]
