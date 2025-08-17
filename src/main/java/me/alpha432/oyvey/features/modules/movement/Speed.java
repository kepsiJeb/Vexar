package com.example.speedhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;

public class SpeedHackAdvanced implements ClientModInitializer {

    private final float speedMultiplier = 1.2f;
    private final float strafeBoost = 0.1f;
    private final boolean useLowHop = true;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                simulateAdvancedSpeed(client.player);
            }
        });
    }

    private void simulateAdvancedSpeed(ClientPlayerEntity player) {
        if (player.isOnGround() && player.input.movementForward != 0) {
            Vec3d direction = getMovementDirection(player);
            Vec3d velocity = direction.multiply(speedMultiplier);

            if (player.input.movementSideways != 0) {
                velocity = velocity.add(0, 0, strafeBoost * Math.signum(player.input.movementSideways));
            }

            player.setVelocity(velocity.x, useLowHop ? 0.1 : 0, velocity.z);
            player.velocityModified = true;
        }
    }

    private Vec3d getMovementDirection(ClientPlayerEntity player) {
        float yaw = player.getYaw();
        double rad = Math.toRadians(yaw);
        double x = -Math.sin(rad);
        double z = Math.cos(rad);
        return new Vec3d(x, 0, z);
    }
}
