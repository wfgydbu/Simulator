/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.units;

import project.demo.CommonResources;

/**
 *
 * @author Zhe Yang
 * @verison 1.0 
 * @since OCT 11,2015
 * 
 */
public class Divider extends LogicOperation{
    
    /**
        * @description calculate division of two byte arrays, store quotient in one byte array
        *           and store remainder in another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 11
        * @param dvd the dividend array
        * @param dvs the divisor array
        * @param quot the quotient array
        * @param remd the remainder array
        * @param insCommonResources contain some common methods
        * @return 1-success 0-fail(divisor is 0)
        * @edit record:
    */
    public boolean calculateDivisionByte(byte[] dvd, byte[] dvs, byte[] quot, byte[] remd, CommonResources insCommonResources){
        
        //quotient
        //byte[] quot = new byte[dvd.length];
        //remainder
        //byte[] remd = new byte[dvd.length];
        byte signBitDvd = dvd[0];
        byte signBitDvs = dvd[0];
        
        //integer dividend
        int iDvd = 0;
        //integer divisor
        int iDvs = 0;
        
        //result of division
        double result = 0;
        int quotient = 0;
        int remainder = 0;
       
        //temporary variables to store values.
        String strQuot = new String();
        String strRemd = new String();
        int temp = 0;
        
        if(insCommonResources.setByteToInteger(dvs) == 0){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your divisor is 0, DIVZERO flag is set!\n");
            return false;
        }
        
        //two positive numbers.
        if(signBitDvd == 0 && signBitDvs == 0){
            iDvd = insCommonResources.setByteToInteger(dvd);
            iDvs = insCommonResources.setByteToInteger(dvs);
            result = iDvd / iDvs;  
            quotient = (int)result;
            remainder = iDvd % iDvs;
        }
        //one positive number and one negative number.
        else if(signBitDvd == 1 && signBitDvs == 0){
            iDvd = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(dvd)) + 1);
            iDvs = insCommonResources.setByteToInteger(dvs);
            result = iDvd / iDvs;
            quotient = (int)result;
            remainder = iDvd % iDvs;
        }
        else if(signBitDvd == 0 && signBitDvs == 1){
            iDvd = insCommonResources.setByteToInteger(dvd);
            iDvs = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(dvs)) + 1);
            result = iDvd / iDvs;
            quotient = (int)result;
            remainder = iDvd % iDvs;
        }  
        //two negative numbers.
        else if(signBitDvd == 1 && signBitDvs == 1){
            iDvd = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(dvd)) + 1);
            iDvs = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(dvs)) + 1);
            result = iDvd / iDvs;
            quotient = (int)result;
            remainder = iDvd % iDvs;
        }
        
        //set quotient to a binary string
        if(quotient >= 0){
            strQuot = insCommonResources.setIntegerToString(quotient);
            strQuot = insCommonResources.VerifyMachineString(strQuot, quot.length);           
        } 
        else if(quotient < 0){
            temp = - quotient - 1;
            strQuot = insCommonResources.setIntegerToString(temp);
            strQuot = insCommonResources.VerifyMachineString(strQuot, quot.length);
        }
        
        //set remainder to a binary string
        if(remainder >= 0){
            strRemd = insCommonResources.setIntegerToString(remainder);
            strRemd = insCommonResources.VerifyMachineString(strRemd, remd.length);
        }
        else if(remainder < 0){
            temp = - remainder - 1;
            strRemd = insCommonResources.setIntegerToString(temp);
            strRemd = insCommonResources.VerifyMachineString(strRemd, remd.length);
        }
        
        insCommonResources.setStringToByte(quot, strQuot, 0, 15);
        insCommonResources.setStringToByte(remd, strRemd, 0, 15);
        return true;
    }
}
