/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan1pl.animations.utils;

import java.util.Arrays;

/**
 *
 * @author Eriol_Eandur
 */
public class ArrayUtils {
    
    public static String[] add(String[] array, String addition) {
        String[] result = Arrays.copyOf(array, array.length+1);
        result[result.length-1] = addition;
        return result;
    }
}
