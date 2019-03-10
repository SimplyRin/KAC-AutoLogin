package net.simplyrin.kokuminautologin;

import java.io.File;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplyrin.kokuminautologin.command.CommandAutoLogin;
import net.simplyrin.kokuminautologin.util.JsonManager;

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
@Mod(modid = "KAC AutoLogin", version = "1.0")
public class Main {

	@Getter @Setter
	private boolean debugMode;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		File file = new File("config/kokuminautologin.json");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
			}

			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("Password", "Empty");

			JsonManager.saveJson(jsonObject, file);
		}

		MinecraftForge.EVENT_BUS.register(this);
		ClientCommandHandler.instance.registerCommand(new CommandAutoLogin(this));
	}

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		String message = event.message.getUnformattedText();
		// String[] args = message.split(" ");

		if (this.debugMode) {
			System.out.println(message);
			this.sendMessageRaw(message);
		}
		if (message.endsWith("Please login by /login <password>")) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/login " + this.getCurrentPassword());
		}
	}

	public String getCurrentServerAddress() {
		return Minecraft.getMinecraft().getCurrentServerData().serverIP;
	}

	public void setPassword(String password) {
		File file = new File("config/kokuminautologin.json");
		JsonObject jsonObject = JsonManager.getJson(file);
		jsonObject.addProperty(this.getCurrentServerAddress(), password);
		JsonManager.saveJson(jsonObject, file);
	}

	public String getCurrentPassword() {
		File file = new File("config/kokuminautologin.json");
		JsonObject jsonObject = JsonManager.getJson(file);
		try {
			return jsonObject.get(this.getCurrentServerAddress()).getAsString();
		} catch (Exception e) {
			return "";
		}
	}

	public void sendMessage(String message) {
		message = "&6&lKokuminAutoLogin &r" + message;
		message = message.replaceAll("&", "\u00a7");
		message = message.replaceAll("§", "\u00a7");

		this.sendMessageRaw(message);
	}

	public void sendMessageRaw(String message) {
		Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
	}

}
