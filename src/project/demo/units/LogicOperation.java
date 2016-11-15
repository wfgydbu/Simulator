/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.units;

import project.demo.CommonResources;

/**
* To simulate the Cache in the computer.
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
*/
public class LogicOperation {
    public byte LOGIC_AND (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 0;
        }
        else if (a == 0 && b == 1) {
            return 0;
        }
        else if (a == 1 && b == 0) {
            return 0;
        }
        else if (a == 1 && b == 1) {
            return 1;
        }
        else {
            return 9;
        }

    }
    
    public byte LOGIC_NOT (byte a){
        if( a == 0) {
            return 1;
        }
        else if (a == 1) {
            return 0;
        }
        else {
            return 9;
        }
    }
    
    public byte LOGIC_NAND (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 1;
        }
        else if (a == 0 && b == 1) {
            return 1;
        }
        else if (a == 1 && b == 0) {
            return 1;
        }
        else if (a == 1 && b == 1) {
            return 0;
        }
        else {
            return 9;
        }

    }
    
    public byte LOGIC_OR (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 0;
        }
        else if (a == 0 && b == 1) {
            return 1;
        }
        else if (a == 1 && b == 0) {
            return 1;
        }
        else if (a == 1 && b == 1) {
            return 1;
        }
        else {
            return 9;
        }

    }
    
    public byte LOGIC_NOR (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 1;
        }
        else if (a == 0 && b == 1) {
            return 0;
        }
        else if (a == 1 && b == 0) {
            return 0;
        }
        else if (a == 1 && b == 1) {
            return 0;
        }
        else {
            return 9;
        }

    }
    
    public byte LOGIC_XOR (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 0;
        }
        else if (a == 0 && b == 1) {
            return 1;
        }
        else if (a == 1 && b == 0) {
            return 1;
        }
        else if (a == 1 && b == 1) {
            return 0;
        }
        else {
            return 9;
        }

    }
    
    public byte LOGIC_XNOR (byte a, byte b){
        if( a == 0 && b == 0 ) {
            return 1;
        }
        else if (a == 0 && b == 1) {
            return 0;
        }
        else if (a == 1 && b == 0) {
            return 0;
        }
        else if (a == 1 && b == 1) {
            return 1;
        }
        else {
            return 9;
        }

    }
    
    /**
        * @description return logical AND result of two byte arrays 
        *           and store the content into another byte array, require 
        *           two arrays must have same length now.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param bt1 the first byte array
        * @param bt2 the second byte array
        * @return the result in a byte array
        * @edit record:
    */
    public byte[] returnLogicalANDByte(byte[] bt1, byte[] bt2){
        byte[] res = new byte[bt1.length];
        for(int i = 0; i < res.length; i++){
            res[i] = LOGIC_AND(bt1[i], bt2[i]);
        }
        
        return res;
    }
    
    /**
        * @description return logical OR result of two byte arrays 
        *           and store the content into another byte array, require 
        *           two arrays must have same length now.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param bt1 the first byte array
        * @param bt2 the second byte array
        * @return the result in a byte array
        * @edit record:
    */
    public byte[] returnLogicalORByte(byte[] bt1, byte[] bt2){
        byte[] res = new byte[bt1.length];
        for(int i = 0; i < res.length; i++){
            res[i] = LOGIC_OR(bt1[i], bt2[i]);
        }
        
        return res;
    }
    
    /**
        * @description return logical NOT result of a byte array 
        *           and store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param bt the original byte array
        * @return the result in a byte array
        * @edit record:
    */
    public byte[] returnLogicalNOTByte(byte[] bt){
        byte[] res = new byte[bt.length];
        for(int i = 0; i < res.length; i++){
            res[i] = LOGIC_NOT(bt[i]);
        }
        
        return res;
    }
    
    /**
        * @description test whether two registers contain same content
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param fR the first register
        * @param sR the second register
        * @param insCommonResources contain common methods
        * @return the equality test result
        * @edit record:
    */
    public boolean TestEqualityBetweenRegisters(byte[] fR, byte[] sR, CommonResources insCommonResources){
        int ifR = insCommonResources.setByteToInteger(fR);
        int isR = insCommonResources.setByteToInteger(sR);
        return ifR == isR;
    }
    
    /**
        * @description arithmetic shift, move a bit string to the right or left, with excess bits discarded. The sign bit is not shifted.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param src the original byte array that needs to be shifted
        * @param drt direction of shifting, 0 means shift to right and 1 means shift to left
        * @param count identify how many bits need to be shifted
        * @param insCommonResources contain common methods
        * @return the result bit array.
        * @edit record:
    */
    public byte[] ArithmeticShift(byte[] src, int drt, int count, CommonResources insCommonResources){
        byte[] res = new byte[src.length];
        String strRes = new String();
        byte[] sign = insCommonResources.GetPartofBytes(src, 0, 0);
        byte byteSign = src[0];
        String strSign = insCommonResources.setByteToString(sign);
        String temp = new String();
        
        //shift to left
        if(drt == 1){
            if(count >= 0 && count < 15){
                temp = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, count + 1, 15));               
            }
            else if(count == 15){
                temp = "";
            }
            
            strRes = insCommonResources.RightInsertWithZero(temp, 15);
            strRes = strSign + strRes;
            
            res = insCommonResources.setStringToByte(res, strRes, 0, 15);           
        }
        
        // shift to right
        else if(drt == 0){
            if(count >= 0 && count < 15){
                temp = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, 1, 15 - count));
            }
            else if(count == 15){
                temp = "";
            }
            
            //positive value
            if(byteSign == 0){
                strRes = insCommonResources.VerifyMachineString(temp, 15);
                strRes = strSign + strRes;
                res = insCommonResources.setStringToByte(res, strRes, 0, 15);
            }
            //negative value
            else if(byteSign == 1){
                strRes = insCommonResources.LeftInsertWithOne(temp, 15);
                strRes = strSign + strRes;
                res = insCommonResources.setStringToByte(res, strRes, 0, 15);
            }
        }
        
        return res;
    }
   
    
    /**
        * @description logical shift, move a bit string to the right or left, with excess bits discarded.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param src the original byte array that needs to be shifted
        * @param drt direction of shifting, 0 means shift to right and 1 means shift to left
        * @param count identify how many bits need to be shifted
        * @param insCommonResources contain common methods
        * @return the result bit array.
        * @edit record:
    */
    public byte[] LogicalShift(byte[] src, int drt, int count, CommonResources insCommonResources){
        byte[] res = new byte[src.length];
        String strRes = new String();
        String temp = new String();
        if(drt == 1){
            temp = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, count, 15));
            strRes = insCommonResources.RightInsertWithZero(temp, 16);
            res = insCommonResources.setStringToByte(res, strRes, 0, 15);
        }
        else if(drt == 0){
            temp = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, 0, 15 - count));
            strRes = insCommonResources.VerifyMachineString(temp, 16);
            res = insCommonResources.setStringToByte(res, strRes, 0, 15);
        }
        
        return res;
    }
    
    /**
        * @description logical rotate, move a bit string to the right or left, the bits shifted out one end will be returned on the other end
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param src the original byte array that needs to be rotated
        * @param drt direction of rotating, 0 means rotate to right and 1 means rotate to left
        * @param count identify how many bits need to be rotated
        * @param insCommonResources contain common methods
        * @return the result bit array.
        * @edit record:
    */
    public byte[] LogicalRotate(byte[] src, int drt, int count, CommonResources insCommonResources){
        byte[] res = new byte[src.length];
        String strRes = new String();
        String strOffPart = new String();
        String strRemainPart = new String();
        
        if(count == 0){
            res = src;
        }
        
        else{
            if(drt == 1){
                strOffPart = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, 0, count - 1));
                strRemainPart = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, count, 15));
                strRes = strRemainPart + strOffPart;       
            }
        
            else if(drt == 0){
                strOffPart = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, 15 - count + 1, 15));
                strRemainPart = insCommonResources.setByteToString(insCommonResources.GetPartofBytes(src, 0, 15 - count));
                strRes = strOffPart + strRemainPart;
            }
            
            res = insCommonResources.setStringToByte(res, strRes, 0, 15);
        }  
        
        return res;
    }
}
