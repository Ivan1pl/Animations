/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan1pl.animations.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eriol_Eandur
 */
public class SerializationUtils {
    
    public static <T extends Serializable> T clone(T object) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteStream);
            out.writeObject(object);
            byte[] bytes = byteStream.toByteArray();
            ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byteInStream);
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SerializationUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
