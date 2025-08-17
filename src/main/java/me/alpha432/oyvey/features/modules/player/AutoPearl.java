  private void onEntityAdded(EntityAddedEvent event) {
        if (event.entity instanceof EnderPearlEntity pearl) {
            if (pearl.getOwner() == mc.player) return;

            new Thread(() -> {
                Vec3d pos = pearl.getPos();
                Vec3d vel = pearl.getVelocity();

                for (int i = 0; i < 200; i++) {
                    pos = pos.add(vel);
                    vel = vel.add(0, -0.03, 0);

                    if (mc.world.getBlockState(new BlockPos(pos)).isSolidBlock(mc.world, new BlockPos(pos))) {
                        Vec3d finalPos = pos.add(0.5, 1.0, 0.5);

                        Rotations.rotate(finalPos, () -> {
                            InvUtils.swap(InvUtils.findItemInHotbar(Items.ENDER_PEARL).slot(), false);
                            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                        });

                        break;
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {}
                }
            }).start();
        }
    }
}
