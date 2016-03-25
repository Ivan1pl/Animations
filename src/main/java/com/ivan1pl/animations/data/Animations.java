/* 
 *  Copyright (C) 2016 Ivan1pl
 * 
 *  This file is part of Animations.
 * 
 *  Animations is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Animations is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Animations.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ivan1pl.animations.data;

import com.ivan1pl.animations.AnimationsPlugin;
import com.ivan1pl.animations.constants.Messages;
import com.ivan1pl.animations.constants.OperationResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan1pl
 */
public class Animations {
    
    private static final Map<String, Animation> animations = new HashMap<>();
    
    private static final File PLUGIN_DIR = new File(AnimationsPlugin.getPluginInstance().getDataFolder()
            + File.separator + "animations");
    
    static {
        if (!PLUGIN_DIR.exists()) {
            PLUGIN_DIR.mkdirs();
        }
    }
    
    public static void reload() {
        FilenameFilter aFilter = new FilenameFilter() {

            @Override
            public boolean accept(File file, String string) {
                return string.endsWith(".anim");
            }
            
        };
        
        animations.clear();
        
        for (File f : PLUGIN_DIR.listFiles(aFilter)) {
            FileInputStream fstream = null;
            ObjectInputStream ostream = null;
            try {
                String name = f.getName();
                name = name.substring(0, name.length()-5);
                
                fstream = new FileInputStream(f);
                ostream = new ObjectInputStream(fstream);
                
                Animation animation = (Animation) ostream.readObject();
                if (animation != null) {
                    animations.put(name, animation);
                    AnimationsPlugin.getPluginInstance().getLogger().info(Messages.INFO_ANIMATION_LOADED + name);
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Animations.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (ostream != null) {
                        ostream.close();
                    }
                    if (fstream != null) {
                        fstream.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Animations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static Animation getAnimation(String name) {
        return animations.get(name);
    }
    
    public static void setAnimation(String name, Animation animation) {
        animations.put(name, animation);
    }
    
    public static OperationResult saveAnimation(String name) {
        Animation animation = animations.get(name);
        if (animation != null) {
            if (!saveAnimation(name, animation)) {
                return OperationResult.INTERNAL_ERROR;
            }
        } else {
            return OperationResult.NOT_FOUND;
        }
        return OperationResult.SUCCESS;
    }
    
    private static boolean saveAnimation(String name, Animation animation) {
        FileOutputStream fstream = null;
        ObjectOutputStream ostream = null;
        boolean result = true;
        try {
            File f = new File(PLUGIN_DIR, name + ".anim");
            Files.deleteIfExists(f.toPath());
            
            fstream = new FileOutputStream(f);
            ostream = new ObjectOutputStream(fstream);
            
            ostream.writeObject(animation);
        } catch (IOException ex) {
            Logger.getLogger(Animations.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        } finally {
            try {
                if (ostream != null) {
                    ostream.close();
                }
                if (fstream != null) {
                    fstream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Animations.class.getName()).log(Level.SEVERE, null, ex);
                result = false;
            }
        }
        return result;
    }
    
}