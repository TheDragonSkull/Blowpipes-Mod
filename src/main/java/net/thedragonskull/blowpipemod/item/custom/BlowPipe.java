package net.thedragonskull.blowpipemod.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mehvahdjukaar.moonlight.api.client.util.RotHlpr;
import net.mehvahdjukaar.moonlight.api.item.IFirstPersonAnimationProvider;
import net.mehvahdjukaar.moonlight.api.item.IThirdPersonSpecialItemRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantments;
import net.thedragonskull.blowpipemod.entity.custom.*;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.network.C2SHamelinTriggerPacket;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.sound.BlowpipeSoundInstance;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.util.BlowpipeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class BlowPipe extends ProjectileWeaponItem implements IFirstPersonAnimationProvider ,IThirdPersonSpecialItemRenderer {
    private static final int DEFAULT_PROJECTILE_RANGE = 15;
    private static final int USE_DURATION = 72000;

    public static BlowpipeSoundInstance activeRatSound = null;

    public BlowPipe(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(ModItems.DART_BASE.get());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return UseAnim.SPYGLASS;
        } else
            return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    @Override
    public int getDefaultProjectileRange() {
        return DEFAULT_PROJECTILE_RANGE;
    }

    /**
     * Animate blowpipe item in first person
     */
    @Override
    public void animateItemFirstPerson(Player entity, ItemStack stack, InteractionHand hand, HumanoidArm arm, PoseStack poseStack, float partialTicks, float pitch, float attackAnim, float handHeight) {
        if (entity.isUsingItem() && entity.getUseItemRemainingTicks() > 0 && entity.getUsedItemHand() == hand) {
            float timeLeft = stack.getUseDuration() - (entity.getUseItemRemainingTicks() - partialTicks + 1.0F);
            float f12 = Mth.clamp(timeLeft / 5.0F, 0.0F, 1.0F);

            //Translation
            poseStack.translate(-0.625D, 0.25D, -0.35D + f12 * 0.55D);

            //Rotation
            poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            poseStack.mulPose(Axis.YP.rotationDegrees(2.1F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-16));

        }
    }

    /**
     * Animate blowpipe item in third person
     */
    @Override
    public <T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> void renderThirdPersonItem(
            M parentModel, LivingEntity entity, ItemStack stack, HumanoidArm humanoidArm,
            PoseStack poseStack, MultiBufferSource bufferSource, int light) {

        if (!stack.isEmpty()) {
            boolean leftHand = humanoidArm == HumanoidArm.LEFT;
            ItemDisplayContext transform;

            poseStack.pushPose();

            if (entity.getUseItem() == stack) {
                ModelPart head = parentModel.getHead();

                float oldRot = head.xRot;
                head.xRot = getMaxHeadXRot(Mth.wrapDegrees(oldRot));
                head.translateAndRotate(poseStack);
                head.xRot = oldRot;

                CustomHeadLayer.translateToHead(poseStack, false);

                poseStack.translate(0, -4.25 / 16f, -8.5 / 16f);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90));
                poseStack.scale(1.5F,1.5F,1.5F);

                transform = ItemDisplayContext.HEAD;
            } else {
                parentModel.translateToHand(humanoidArm, poseStack);
                poseStack.mulPose(RotHlpr.XN90);
                poseStack.mulPose(RotHlpr.Y180);
                poseStack.translate((leftHand ? -1 : 1) / 16.0F, 0.125D, -0.625D);

                transform = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(
                    entity, stack, transform, leftHand, poseStack, bufferSource, light
            );

            poseStack.popPose();
        }
    }

    private float getMaxHeadXRot(float xRot) {
        return Mth.clamp(xRot, (-(float) Math.PI / 2.5F), ((float) Math.PI / 2F));
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.pass(stack);
    }


    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);

        if (livingEntity instanceof Player player) {
            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                if (Minecraft.getInstance().options.keyAttack.isDown() && !player.getCooldowns().isOnCooldown(this)) {
                    if (!BlowpipeUtil.isLoaded(stack)) {
                        if (level.isClientSide) {

                            if (activeRatSound != null && !activeRatSound.isStopped()) {
                                return;
                            }

                            if (activeRatSound != null && activeRatSound.isStopped()) {
                                activeRatSound = null;
                            }

                            if (Math.random() < 0.01) {
                                activeRatSound = new BlowpipeSoundInstance((LocalPlayer) player, ModSounds.BLOWPIPE_RAT.get());
                                Minecraft.getInstance().getSoundManager().play(activeRatSound);
                                player.getCooldowns().addCooldown(this, 20);

                                //Activate a secret advancement
                                PacketHandler.sendToServer(new C2SHamelinTriggerPacket());

                            } else {
                                SimpleSoundInstance emptySoundInstance = new SimpleSoundInstance(ModSounds.BLOWPIPE_EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.0F, SoundInstance.createUnseededRandom(),0,0,0);
                                Minecraft.getInstance().getSoundManager().play(emptySoundInstance);
                                player.getCooldowns().addCooldown(this, 20);
                            }
                        }
                    } else {
                        shootDart(level, player, stack);
                    }
                }
            }
        }
    }

    private void shootDart(Level level, Player player, ItemStack stack) {
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains("Dart", Tag.TAG_COMPOUND)) {
            CompoundTag dartTag = tag.getCompound("Dart");
            ItemStack dartStack = ItemStack.of(dartTag);

            AbstractDart dartProjectile;

            if (dartStack.is(ModItems.POISON_DART.get())) {
                dartProjectile = new PoisonDartProjectileEntity(level, player);
            } else if (dartStack.is(ModItems.POWDER_DART.get())) {
                dartProjectile = new PowderDartProjectileEntity(level, player);
            } else if (dartStack.is(ModItems.LURE_DART.get())) {
                dartProjectile = new LureDartProjectileEntity(level, player);
            } else if (dartStack.is(ModItems.IRON_HEAD_DART.get())) {
                dartProjectile = new IronHeadDartProjectileEntity(level, player);
            } else if (dartStack.is(ModItems.RAZOR_DART.get())) {
                dartProjectile = new RazorDartProjectileEntity(level, player);
            } else {
                dartProjectile = new DartProjectileEntity(level, player);
            }

            Vec3 eyePos = player.getEyePosition();
            Vec3 viewVector = player.getViewVector(1.0F);
            Vec3 offset = viewVector.scale(0.5);
            Vec3 spawnPos = eyePos.add(offset);

            dartProjectile.setPos(spawnPos.x, spawnPos.y - 0.1, spawnPos.z);

            int blowPowerLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BLOW_POWER.get(), stack);
            dartProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F + (1.0F * blowPowerLevel), 0F);

            level.addFreshEntity(dartProjectile);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BLOWPIPE_SHOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, 30);

            //Reset blowpipe
            stack.getOrCreateTag().putBoolean("loaded", false);
            stack.getOrCreateTag().remove("Dart");
            stack.getOrCreateTag().remove("dart_type");
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
        if (activeRatSound != null) {
            activeRatSound = null;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        ItemStack loadedDart = BlowpipeUtil.getLoadedDart(stack);

        if (!loadedDart.isEmpty()) {
            tooltip.add(Component.literal("Loaded Dart: ").withStyle(ChatFormatting.GOLD).append(loadedDart.getHoverName().copy()
                    .withStyle(ChatFormatting.DARK_PURPLE)));
        } else {
            tooltip.add(Component.literal("Empty")
                    .withStyle(ChatFormatting.GRAY));
        }
    }



}
