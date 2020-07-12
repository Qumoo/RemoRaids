package ca.landonjw.remoraids.implementation.rewards.options;

import ca.landonjw.remoraids.RemoRaids;
import ca.landonjw.remoraids.api.battles.IBossBattle;
import ca.landonjw.remoraids.api.rewards.contents.IRewardContent;
import ca.landonjw.remoraids.implementation.rewards.DropRewardBase;
import ca.landonjw.remoraids.internal.api.config.Config;
import ca.landonjw.remoraids.internal.config.GeneralConfig;
import ca.landonjw.remoraids.internal.config.MessageConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A reward that is given to players that do the highest damage in a boss battle.
 *
 * @author landonjw
 * @since  1.0.0
 */
public class TopDamageReward extends DropRewardBase {

    /** The number of players to receive this reward. Setting this as x would be top x damage dealers. */
    private int numberReceivers;

    /**
     * Constructor for the highest damage reward.
     *
     * @param numberReceivers the number of players to receive the reward.
     * @param contents        the contents of the reward.
     */
    public TopDamageReward(int numberReceivers, IRewardContent... contents){
        super(contents);
        this.numberReceivers = numberReceivers;
    }

    /** {@inheritDoc} **/
    @Override
    public List<EntityPlayerMP> getWinnersList(IBossBattle battle) {
        List<EntityPlayerMP> winners = new ArrayList<>();
        List<UUID> topDamageDealers = battle.getTopDamageDealers();

        PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        for(int i = 0; i < numberReceivers; i++){
            EntityPlayerMP player = playerList.getPlayerByUUID(topDamageDealers.get(i));
            winners.add(player);
        }

        return winners;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        Config config = RemoRaids.getMessageConfig();
        return config.get(MessageConfig.TOP_DAMAGE_REWARD_TITLE);
    }

    @Override
    public int getPriority() {
        Config config = RemoRaids.getGeneralConfig();
        return config.get(GeneralConfig.TOP_DAMAGE_REWARD_PRIORITY);
    }

    /**
     * Gets the number of players that can receive the reward.
     *
     * @return the number of players that can receive the reward
     */
    public int getNumberReceivers() {
        return numberReceivers;
    }

    /**
     * Sets the number of players that can receive the reward.
     *
     * @param numberReceivers new number of receivers
     */
    public void setNumberReceivers(int numberReceivers) {
        this.numberReceivers = numberReceivers;
    }

}
