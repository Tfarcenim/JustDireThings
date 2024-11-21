package com.direwolf20.justdirethings.common.fluids.unrefinedt2fuel;

import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class UnrefinedT2Fuel extends BaseFlowingFluid {
    public static final Properties PROPERTIES = new Properties(
            Registration.UNREFINED_T2_FLUID_TYPE,
            Registration.UNREFINED_T2_FLUID_FLOWING,
            Registration.UNREFINED_T2_FLUID_SOURCE
    ).bucket(Registration.UNREFINED_T2_FLUID_BUCKET).block(Registration.UNREFINED_T2_FLUID_BLOCK);

    protected UnrefinedT2Fuel(Properties properties) {
        super(properties);
    }

    @Override
    public Fluid getFlowing() {
        return Registration.UNREFINED_T2_FLUID_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return Registration.UNREFINED_T2_FLUID_SOURCE.get();
    }

    @Override
    public Item getBucket() {
        return Registration.UNREFINED_T2_FLUID_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource(Level pLevel) {
        return false;
    }

    public static class Flowing extends UnrefinedT2Fuel {
        public Flowing() {
            super(PROPERTIES);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState pState) {
            return false;
        }
    }

    public static class Source extends UnrefinedT2Fuel {
        public Source() {
            super(PROPERTIES);
        }

        @Override
        public int getAmount(FluidState pState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState pState) {
            return true;
        }
    }
}