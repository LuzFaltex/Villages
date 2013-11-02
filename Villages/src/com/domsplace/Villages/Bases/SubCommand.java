package com.domsplace.Villages.Bases;

import com.domsplace.Villages.Objects.VillageHelpTopic;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand extends Base implements Helpable {
    private String[] subs;
    private BukkitCommand cmd;
    private String permission = "none";
    
    public SubCommand(String cmd, String... extenders) {
        this.subs = extenders;
        this.cmd = BukkitCommand.getCommand(cmd);
        Bukkit.getHelpMap().addTopic(new VillageHelpTopic(this));
        if(this.cmd != null) this.cmd.addSubCommand(this);
    }
    
    public String[] getSubs() {return this.subs;}
    public BukkitCommand getCmd() {return this.cmd;}
    public String getCommand() {return this.getSubs()[this.getSubs().length - 1];}
    public String getPermission() {return "Villages." + this.permission;}
    public String asCommand() {return this.cmd.getCommand() + " " + arrayToString(subs, " ");}
    
    public void setPermission(String permission) {this.permission = permission;}
    
    public void deRegister() {
        this.cmd.removeSubCommand(this);
    }

    public int getMatches(String[] args) {
        int i = 0;
        
        for(int x = 0; x < args.length; x++) {
            try {
                if(args[x].replaceAll(" ", "").equalsIgnoreCase("")) continue;
                if(!args[x].equalsIgnoreCase(this.subs[x])) {i--; continue;}
                i++;
            } catch(IndexOutOfBoundsException e) {
                break;
            }
        }
        
        return i;
    }

    public boolean transExecute(BukkitCommand bkcmd, CommandSender sender, Command cmd, String label, String[] args) {
        List<String> c = new ArrayList<String>();
        for(int i = 0; i < args.length; i++) {
            if((i < this.subs.length) && args[i].equalsIgnoreCase(this.subs[i])) continue;
            c.add(args[i]);
        }
        
        String[] cargs = Base.listToArray(c);
        
        return this.tryCmd(bkcmd, sender, cmd, label, cargs);
    }
    
    public boolean tryCmd(BukkitCommand bkcmd, CommandSender sender, Command cmd, String label, String[] args) {
        if(!hasPermission(sender, getPermission())) return bkcmd.noPermission(sender, cmd, label, args);
        boolean c = cmd(bkcmd, sender, cmd, label, args);
        if(c) return true;
        return this.failedCommand(bkcmd, sender, cmd, label, args);
    }
    
    public abstract boolean cmd(BukkitCommand bkcmd, CommandSender sender, Command cmd, String label, String[] args);
    
    public boolean failedCommand(BukkitCommand bkcmd, CommandSender sender, Command cmd, String label, String[] args) {
        sendMessage(sender, new String[] {
            ChatImportant + "Command Help:",
            "\t" + ChatImportant + "Usage: " + ChatDefault + this.getCommandUsage().replaceAll(this.cmd.getCommand(), label),
            "\t" + ChatImportant + "Info: " + ChatDefault + this.getHelpTextShort()
        });
        return true;
    }
    
    @Override public String getHelpPermission() {return this.getPermission();}
    @Override public String getHelpTopic() {return "/" + this.asCommand();}
    @Override public String getHelpTextLarge(CommandSender forWho) {return this.getHelpTextShort();}
    @Override public void setHelpPermission(String permission) {this.setPermission(permission);}
    
    @Override
    public String getHelpTextShort() {
        for(String s : DataManager.HELP_MANAGER.helps.keySet()) {
            if(!s.toLowerCase().startsWith(this.asCommand().toLowerCase())) continue;
            return DataManager.HELP_MANAGER.helps.get(s);
        }
        
        return ChatError + "Unknown Help.";
    }
    
    public String getCommandUsage() {
        for(String s : DataManager.HELP_MANAGER.helps.keySet()) {
            if(!s.toLowerCase().startsWith(this.asCommand().toLowerCase())) continue;
            return s;
        }
        
        return this.cmd.getCommand();
    }
}
