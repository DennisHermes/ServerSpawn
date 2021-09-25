package me.goodgamer123.ServerSpawn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterClass implements TabCompleter{
    
	@Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        
		ArrayList<String> TabList = new ArrayList<String>();
		
			if (cmd.getName().equalsIgnoreCase("custommessages")) {
				if (args.length > 0) {
					TabList.clear();
					if (args[0].startsWith("e")) { TabList.add("enable"); }
					else if (args[0].startsWith("d")) { TabList.add("disable"); } 
					else if (args[0].startsWith("")) {
						TabList.add("enable");
						TabList.add("disable");
					} else {
						TabList.add("set");
						TabList.add("get"); 
					}
				} else { TabList.clear(); }
				return TabList;
			}
			
			
			if (cmd.getName().equalsIgnoreCase("serverspawn")) {
				if (args.length > 0) {
					TabList.clear();
					if (args[0].startsWith("e")) { TabList.add("enable"); }
					else if (args[0].startsWith("d")) { TabList.add("disable"); }
					else if (args[0].startsWith("s")) { TabList.add("set"); }
					else if (args[0].startsWith("g")) { TabList.add("get"); }
					else if (args[0].startsWith("")) {
						TabList.add("enable");
						TabList.add("disable");
						TabList.add("set");
						TabList.add("get"); 
					}
					else { TabList.clear(); }
				}
				return TabList;
			}
			
			
			if (cmd.getName().equalsIgnoreCase("fake")) {
				if (args.length > 0) {
					TabList.clear();
					if (args[0].startsWith("j")) { TabList.add("join"); }
					else if (args[0].startsWith("l")) { TabList.add("leave"); } 
					else if (args[0].startsWith("")) {
						TabList.add("join");
						TabList.add("leave");
					} else {
						TabList.add("set");
						TabList.add("get"); 
					}
				} else { TabList.clear(); }
				return TabList;
			}
			
			
			if (cmd.getName().equalsIgnoreCase("joinmessage")) {
				if (args.length > 0) {
					TabList.clear();
					if (args[0].startsWith("set")) { TabList.add("set"); }
					else if (args[0].startsWith("g")) { TabList.add("get"); }
					else if (args[0].startsWith("")) {
						TabList.add("set");
						TabList.add("get"); 
					} else {
						TabList.add("set");
						TabList.add("get"); 
					}
				} else { TabList.clear(); }
				return TabList;
			}
			
			
			if (cmd.getName().equalsIgnoreCase("leavemessage")) {
				if (args.length > 0) {
					TabList.clear();
					if (args[0].startsWith("s")) { TabList.add("set"); }
					else if (args[0].startsWith("g")) { TabList.add("get"); } 
					else if (args[0].startsWith("")) {
						TabList.add("set");
						TabList.add("get"); 
					} else {
						TabList.add("set");
						TabList.add("get"); 
					}
				} else { TabList.clear(); }
				return TabList;
			}
			
		Collections.sort(TabList);
		return null;
	}
	
}