package com.gregtechceu.gt6.gametest.example;

import com.gregtechceu.gt6.GTCEu;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(GTCEu.MOD_ID)
public class ExampleTest {

    @GameTest(template = "empty")
    public static void myTest(GameTestHelper helper) {
        helper.assertTrue(true, "true is false");
        helper.succeed();
    }
}
