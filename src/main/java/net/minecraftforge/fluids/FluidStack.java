package net.minecraftforge.fluids;

import net.minecraft.world.level.material.Fluid;

public class FluidStack {

    public static final FluidStack EMPTY = new FluidStack(null, 0);

    private Fluid fluid;
    private int amount;

    public FluidStack(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isEmpty() {
        return fluid == null || amount <= 0;
    }

    public FluidStack copy() {
        return new FluidStack(fluid, amount);
    }

    public void grow(int amount) {
        this.amount += amount;
    }

    public void shrink(int amount) {
        this.amount -= amount;
    }
}
