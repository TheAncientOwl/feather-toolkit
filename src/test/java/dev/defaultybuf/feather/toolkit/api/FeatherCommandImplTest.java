/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherCommandImplTest.java
 * @author Alexandru Delegeanu
 * @version 0.11
 * @test_unit FeatherCommand#0.4
 * @description Unit tests for FeatherCommand
 */

package dev.defaultybuf.feather.toolkit.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import dev.defaultybuf.feather.toolkit.api.dummies.DummyCommand;
import dev.defaultybuf.feather.toolkit.testing.FeatherCommandTest;

class FeatherCommandImplTest extends FeatherCommandTest<DummyCommand> {
    @Mock Command mockCommand;
    @Mock CommandSender mockSender;

    @Override
    protected Class<DummyCommand> getCommandClass() {
        return DummyCommand.class;
    }

    @Test
    void onTabComplete() {
        assertDoesNotThrow(() -> {
            var expectedCompletions = new String[] {"comp0", "comp1"};
            var completions = commandInstance.onTabComplete(mockSender, mockCommand, "alias",
                    expectedCompletions);
            assertEquals(Arrays.asList(expectedCompletions), completions);
        });
    }

    @Test
    void onCommand_01() {
        assertDoesNotThrow(() -> {
            assertTrue(
                    commandInstance.onCommand(mockSender, mockCommand, "label", new String[] {}));
        });
    }

    @Test
    void onCommand_02() {
        assertDoesNotThrow(() -> {
            assertTrue(
                    commandInstance.onCommand(mockSender, mockCommand, "label",
                            new String[] {"arg1"}));
        });
    }

    @Test
    void onCommand_03() {
        assertDoesNotThrow(() -> {
            assertTrue(
                    commandInstance.onCommand(mockSender, mockCommand, "label",
                            new String[] {"noperm"}));
        });
    }

}
