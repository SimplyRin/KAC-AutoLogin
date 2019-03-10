package net.simplyrin.kokuminautologin.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.simplyrin.kokuminautologin.Main;

/**
 * Created by SimplyRin on 2019/03/10.
 *
 * Copyright (c) 2019 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class CommandAutoLogin extends CommandBase {

	private Main instance;

	public CommandAutoLogin(Main instance) {
		this.instance = instance;
	}

	@Override
	public String getCommandName() {
		return "kokuminautologin";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("autologin", "kal", "al");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "&cUsage: /" + this.getCommandName() + " <set> <password>";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("!debug")) {
				this.instance.setDebugMode(!this.instance.isDebugMode());
				this.instance.sendMessage("&a&lDebug mode has been " + (this.instance.isDebugMode() ? "&b&lEnabled" : "&c&lDisabled") + "&a&l.");
				return;
			}

			if (args[0].equalsIgnoreCase("set")) {
				if (args.length > 1) {
					this.instance.setPassword(args[1]);
					this.instance.sendMessage("&bKokuminAntiCheat でログイン時使用するパスワードを設定しました！");
					return;
				}
			}
		}

		this.instance.sendMessage(this.getCommandUsage(null));
		return;
	}

}
