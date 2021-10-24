package me.goodgamer123.ServerSpawn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompleterClass implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("serverspawn")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("set");
				commands.add("get");
				commands.add("disable");
				commands.add("enable");
				commands.add("settings");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			} else if (args.length == 2) {
				 if (args[0].equalsIgnoreCase("settings")) {
					List<String> commands = new ArrayList<>();
					commands.add("prefix");
					commands.add("language");
					
					final List<String> completions = new ArrayList<>();
					StringUtil.copyPartialMatches(args[1], commands, completions);
					Collections.sort(completions);
					return completions;
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("settings") && args[1].equalsIgnoreCase("prefix")) {
					List<String> commands = new ArrayList<>();
					commands.add("set");
					commands.add("enable");
					commands.add("disable");
					
					final List<String> completions = new ArrayList<>();
					StringUtil.copyPartialMatches(args[2], commands, completions);
					Collections.sort(completions);
					return completions;
				} else if (args[0].equalsIgnoreCase("settings") && args[1].equalsIgnoreCase("language")) {
					List<String> commands = new ArrayList<>();
					commands.add("english");
					commands.add("dutch");
					
					final List<String> completions = new ArrayList<>();
					StringUtil.copyPartialMatches(args[2], commands, completions);
					Collections.sort(completions);
					return completions;
				}
			}
			
		} else if (cmd.getName().equalsIgnoreCase("fake")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("join");
				commands.add("leave");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			}
		} else if (cmd.getName().equalsIgnoreCase("custommessages")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("enable");
				commands.add("disable");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			}
		} else if (cmd.getName().equalsIgnoreCase("joinmessage")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("set");
				commands.add("get");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			}
		} else if (cmd.getName().equalsIgnoreCase("leavemessage")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("set");
				commands.add("get");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			}
		} else if (cmd.getName().equalsIgnoreCase("spawncommands")) {
			if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("hub");
				commands.add("spawn");
				commands.add("lobby");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			} else if (args.length == 1) {
				List<String> commands = new ArrayList<>();
				commands.add("enable");
				commands.add("disable");
				
				final List<String> completions = new ArrayList<>();
				StringUtil.copyPartialMatches(args[0], commands, completions);
				Collections.sort(completions);
				return completions;
			}
		}
		
		return null;
	}
}