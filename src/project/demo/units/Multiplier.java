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
 */
public class Multiplier extends LogicOperation{
    
    /**
        * @description calculate multiplication result of two byte arrays 
        *           and store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 11
        * @param md the multiplicand array
        * @param mr the multiplier array
        * @param highOB high order bits of the product
        * @param lowOB low order bites of the product
        * @param insCommonResources contain some common methods
        * @return 1-success 0-fail(overflow or underflow)
        * @edit record:
    */
    public boolean calculateMultiplicationByte(byte[] md, byte[] mr, byte[] highOB, byte[] lowOB, CommonResources insCommonResources){
        
        /*int negativeimd = insCR.setByteToInteger(returnLogicalNOTByte(md));
        //int imr = insCR.setByteToInteger(mr);
        byte[] AddFactor = new byte[md.length + mr.length + 1];
        byte[] SubFactor = new byte[md.length + mr.length + 1];
        byte[] Product = new byte[md.length + mr.length + 1];
        
        AddFactor = insCR.setStringToByte(AddFactor, insCR.RightInsertWithZero(insCR.setByteToString(md), AddFactor.length), 0, AddFactor.length - 1);
        SubFactor = insCR.setStringToByte(SubFactor, insCR.RightInsertWithZero(insCR.setByteToString(return), length), 0, SubFactor.length - 1);*/
        
        byte[] pro = new byte[md.length + mr.length];
        byte signBit1 = md[0];
        byte signBit2 = mr[0];
        //integer multiplicand
        int imd = 0;
        //integer multiplier
        int imr = 0;
        //integer product
        int ipro = 0;
        //two temporary variables to store values.
        String str = new String();
        int temp = 0;
        
        //two positive numbers.
        if(signBit1 == 0 && signBit2 == 0){
            imd = insCommonResources.setByteToInteger(md);
            imr = insCommonResources.setByteToInteger(mr);
            ipro = imd * imr;                  
        }
        //one positive number and one negative number.
        else if(signBit1 == 1 && signBit2 == 0){
            imd = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(md)) + 1);
            imr = insCommonResources.setByteToInteger(mr);
            ipro = imd * imr;
        }
        else if(signBit1 == 0 && signBit2 == 1){
            imd = insCommonResources.setByteToInteger(md);
            imr = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(mr)) + 1);
            ipro = imd * imr;
        }  
        //two negative numbers.
        else if(signBit1 == 1 && signBit2 == 1){
            imd = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(md)) + 1);
            imr = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(mr)) + 1);
            ipro = imd * imr;
        }
        
        //Overflow
        if(ipro > Math.pow(2, 31) - 1){
            insCommonResources.SetCC(1, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your multiplication result overflows!\n");
            //pro = insCommonResources.setIntegerToByte(pro, ipro);   
            
            return false;
        }
        //Underflow
        else if(ipro < 1 - Math.pow(2, 31)){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" +"Your multiplication result underflows!\n");
            //temp = - ipro - 1;
            //pro = returnLogicalNOTByte(insCommonResources.setIntegerToByte(pro, temp));
            
            return false;
        }
        //Normal
        else if(ipro >= 0 && ipro <= Math.pow(2, 31) - 1){
            str = insCommonResources.setIntegerToString(ipro);
            str = insCommonResources.VerifyMachineString(str, 32);
            pro = insCommonResources.setStringToByte(pro, str, 0, 31);
        }
        else if(ipro < 0 && ipro >= 1 - Math.pow(2, 31)) {
            temp = - ipro - 1;
            str = insCommonResources.setIntegerToString(temp);
            str = insCommonResources.VerifyMachineString(str, 32);
            pro = returnLogicalNOTByte(insCommonResources.setStringToByte(pro, str, 0, 31));
        }
        
        insCommonResources.GetPartofBytes(pro, highOB, 0, 15);
        insCommonResources.GetPartofBytes(pro, lowOB, 16, 31);
        return true;
    }
}
