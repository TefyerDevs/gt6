package com.gregtechceu.gt6.api.machine.steam;

import com.gregtechceu.gt6.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gt6.api.capability.recipe.IO;
import com.gregtechceu.gt6.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gt6.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gt6.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gt6.api.recipe.GTRecipe;
import com.gregtechceu.gt6.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gt6.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gt6.common.data.GTMaterials;
import com.gregtechceu.gt6.utils.GTMath;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SteamEnergyRecipeHandler implements IRecipeHandler<EnergyStack> {

    private final NotifiableFluidTank steamTank;
    private final double conversionRate; // mB steam per EU

    public SteamEnergyRecipeHandler(NotifiableFluidTank steamTank, double conversionRate) {
        this.steamTank = steamTank;
        this.conversionRate = conversionRate;
    }

    @Override
    public List<EnergyStack> handleRecipeInner(IO io, GTRecipe recipe, List<EnergyStack> left, boolean simulate) {
        for (var it = left.listIterator(); it.hasNext();) {
            EnergyStack stack = it.next();
            if (stack.isEmpty()) {
                it.remove();
                continue;
            }

            long totalEU = stack.getTotalEU();
            int totalSteam = GTMath.saturatedCast((long) Math.ceil(totalEU * conversionRate));
            if (totalSteam > 0) {
                var steam = io == IO.IN ? FluidIngredient.of(GTMaterials.Steam.getFluidTag(), totalSteam) :
                        FluidIngredient.of(GTMaterials.Steam.getFluid(totalSteam));
                var list = new ArrayList<FluidIngredient>();
                list.add(steam);
                var leftSteam = steamTank.handleRecipeInner(io, recipe, list, simulate);
                if (leftSteam == null || leftSteam.isEmpty()) {
                    it.remove();
                } else {
                    totalEU = (long) (leftSteam.get(0).getAmount() / conversionRate);
                    it.set(new EnergyStack(totalEU));
                }
            }
        }
        return left.isEmpty() ? null : left;
    }

    @Override
    public @NotNull List<Object> getContents() {
        List<FluidStack> tankContents = new ArrayList<>();
        for (int i = 0; i < steamTank.getTanks(); ++i) {
            FluidStack stack = steamTank.getFluidInTank(i);
            if (!stack.isEmpty()) {
                tankContents.add(stack);
            }
        }
        long sum = tankContents.stream().mapToLong(FluidStack::getAmount).sum();
        long realSum = (long) Math.ceil(sum * conversionRate);
        return List.of(realSum);
    }

    @Override
    public double getTotalContentAmount() {
        List<FluidStack> tankContents = new ArrayList<>();
        for (int i = 0; i < steamTank.getTanks(); ++i) {
            FluidStack stack = steamTank.getFluidInTank(i);
            if (!stack.isEmpty()) {
                tankContents.add(stack);
            }
        }
        long sum = tankContents.stream().mapToLong(FluidStack::getAmount).sum();
        return (long) Math.ceil(sum * conversionRate);
    }

    @Override
    public RecipeCapability<EnergyStack> getCapability() {
        return EURecipeCapability.CAP;
    }

    public long getCapacity() {
        return steamTank.getTankCapacity(0);
    }

    public long getStored() {
        FluidStack stack = steamTank.getFluidInTank(0);
        if (stack != FluidStack.EMPTY) {
            return stack.getAmount();
        }
        return 0;
    }
}
