package com.gregtechceu.gt6.common.data.machines;

import com.gregtechceu.gt6.Gregtech;
import com.gregtechceu.gt6.api.data.RotationState;
import com.gregtechceu.gt6.api.machine.MachineDefinition;
import com.gregtechceu.gt6.api.machine.multiblock.PartAbility;
import com.gregtechceu.gt6.integration.ae2.machine.*;

import net.minecraft.network.chat.Component;

import static com.gregtechceu.gt6.api.GTValues.EV;
import static com.gregtechceu.gt6.api.GTValues.LuV;
import static com.gregtechceu.gt6.common.registry.GTRegistration.REGISTRATE;

@SuppressWarnings("unused")
public class GTAEMachines {

    public final static MachineDefinition ITEM_IMPORT_BUS_ME = REGISTRATE
            .machine("me_input_bus", MEInputBusPartMachine::new)
            .langValue("ME Input Bus")
            .tier(EV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_ITEMS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_input_bus"))
            .tooltips(
                    Component.translatable("gt6.machine.item_bus.import.tooltip"),
                    Component.translatable("gt6.machine.me.item_import.tooltip"),
                    Component.translatable("gt6.machine.me.copy_paste.tooltip"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public final static MachineDefinition STOCKING_IMPORT_BUS_ME = REGISTRATE
            .machine("me_stocking_input_bus", MEStockingBusPartMachine::new)
            .langValue("ME Stocking Input Bus")
            .tier(LuV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_ITEMS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_input_bus"))
            .tooltips(
                    Component.translatable("gt6.machine.item_bus.import.tooltip"),
                    Component.translatable("gt6.machine.me.stocking_item.tooltip.0"),
                    Component.translatable("gt6.machine.me_import_item_hatch.configs.tooltip"),
                    Component.translatable("gt6.machine.me.copy_paste.tooltip"),
                    Component.translatable("gt6.machine.me.stocking_item.tooltip.1"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public final static MachineDefinition ITEM_EXPORT_BUS_ME = REGISTRATE
            .machine("me_output_bus", MEOutputBusPartMachine::new)
            .langValue("ME Output Bus")
            .tier(EV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.EXPORT_ITEMS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_output_bus"))
            .tooltips(
                    Component.translatable("gt6.machine.item_bus.export.tooltip"),
                    Component.translatable("gt6.machine.me.item_export.tooltip"),
                    Component.translatable("gt6.machine.me.export.tooltip"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public final static MachineDefinition FLUID_IMPORT_HATCH_ME = REGISTRATE
            .machine("me_input_hatch", MEInputHatchPartMachine::new)
            .langValue("ME Input Hatch")
            .tier(EV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_FLUIDS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_input_hatch"))
            .tooltips(
                    Component.translatable("gt6.machine.fluid_hatch.import.tooltip"),
                    Component.translatable("gt6.machine.me.fluid_import.tooltip"),
                    Component.translatable("gt6.machine.me.copy_paste.tooltip"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public final static MachineDefinition STOCKING_IMPORT_HATCH_ME = REGISTRATE
            .machine("me_stocking_input_hatch", MEStockingHatchPartMachine::new)
            .langValue("ME Stocking Input Hatch")
            .tier(LuV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_FLUIDS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_input_hatch"))
            .tooltips(
                    Component.translatable("gt6.machine.fluid_hatch.import.tooltip"),
                    Component.translatable("gt6.machine.me.stocking_fluid.tooltip.0"),
                    Component.translatable("gt6.machine.me_import_fluid_hatch.configs.tooltip"),
                    Component.translatable("gt6.machine.me.copy_paste.tooltip"),
                    Component.translatable("gt6.machine.me.stocking_fluid.tooltip.1"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public final static MachineDefinition FLUID_EXPORT_HATCH_ME = REGISTRATE
            .machine("me_output_hatch", MEOutputHatchPartMachine::new)
            .langValue("ME Output Hatch")
            .tier(EV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.EXPORT_FLUIDS)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_output_hatch"))
            .tooltips(
                    Component.translatable("gt6.machine.fluid_hatch.export.tooltip"),
                    Component.translatable("gt6.machine.me.fluid_export.tooltip"),
                    Component.translatable("gt6.machine.me.export.tooltip"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();
    public static final MachineDefinition ME_PATTERN_BUFFER = REGISTRATE
            .machine("me_pattern_buffer", MEPatternBufferPartMachine::new)
            .tier(LuV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS,
                    PartAbility.EXPORT_ITEMS)
            .rotationState(RotationState.ALL)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_buffer_hatch"))
            .langValue("ME Pattern Buffer")
            .tooltips(
                    Component.translatable("block.gt6.pattern_buffer.desc.0"),
                    Component.translatable("block.gt6.pattern_buffer.desc.1"),
                    Component.translatable("block.gt6.pattern_buffer.desc.2"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();
    public static final MachineDefinition ME_PATTERN_BUFFER_PROXY = REGISTRATE
            .machine("me_pattern_buffer_proxy", MEPatternBufferProxyPartMachine::new)
            .tier(LuV)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS,
                    PartAbility.EXPORT_ITEMS)
            .rotationState(RotationState.ALL)
            .colorOverlayTieredHullModel(Gregtech.id("block/overlay/appeng/me_buffer_hatch_proxy"))
            .langValue("ME Pattern Buffer Proxy")
            .tooltips(
                    Component.translatable("block.gt6.pattern_buffer_proxy.desc.0"),
                    Component.translatable("block.gt6.pattern_buffer_proxy.desc.1"),
                    Component.translatable("block.gt6.pattern_buffer_proxy.desc.2"),
                    Component.translatable("gt6.part_sharing.enabled"))
            .register();

    public static void init() {}
}
