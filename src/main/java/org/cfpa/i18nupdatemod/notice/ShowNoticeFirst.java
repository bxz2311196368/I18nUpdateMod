package org.cfpa.i18nupdatemod.notice;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.cfpa.i18nupdatemod.I18nUpdateMod;
import org.cfpa.i18nupdatemod.config.MainConfig;

@Mod.EventBusSubscriber(modid = I18nUpdateMod.MODID)
public class ShowNoticeFirst {

    @SubscribeEvent
    public static void onPlayerFirstJoin(RenderGameOverlayEvent.Post event) {
        if (I18nUpdateMod.showNotice && event.getType() != RenderGameOverlayEvent.ElementType.HELMET && MainConfig.notice.showNoticeConfig) {
            I18nUpdateMod.showNotice = false;
            new NoticeShower();
        }
    }
}
