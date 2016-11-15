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
public class adder extends LogicOperation {
    
    /**
        * @description calculate addition result of two byte arrays 
        *           and store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 8
        * @param bt1 the first byte array
        * @param bt2 the second byte array
        * @param res the addition result byte array
        * @param insCommonResources contain some common methods
        * @return 1-success 0-fail(overflow or underflow)
        * @edit record:
    */
    public boolean calculateAdditionByte(byte[] bt1, byte[] bt2, byte[] res, CommonResources insCommonResources){
        //byte[] res = new byte[bt1.length];
        byte signBit1 = bt1[0];
        byte signBit2 = bt2[0];
        int ibt1 = 0;
        int ibt2 = 0;
        int ires = 0;
        // two temporary variables to store values.
        String str = new String();
        int temp = 0;
        
        //two positive numbers.
        if(signBit1 == 0 && signBit2 == 0){
            ibt1 = insCommonResources.setByteToInteger(bt1);
            ibt2 = insCommonResources.setByteToInteger(bt2);
            ires = ibt1 + ibt2;                  
        }
        //one positive number and one negative number.
        else if(signBit1 == 1 && signBit2 == 0){
            ibt1 = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt1)) + 1);
            ibt2 = insCommonResources.setByteToInteger(bt2);
            ires = ibt1 + ibt2;
        }
        else if(signBit1 == 0 && signBit2 == 1){
            ibt1 = insCommonResources.setByteToInteger(bt1);
            ibt2 = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt2)) + 1);
            ires = ibt1 + ibt2;
        }  
        //two negative numbers.
        else if(signBit1 == 1 && signBit2 == 1){
            ibt1 = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt1)) + 1);
            ibt2 = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt2)) + 1);
            ires = ibt1 + ibt2;
        }
        
        //Overflow
        if(ires > Math.pow(2, 15) - 1){
            insCommonResources.SetCC(1, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your addition result overflows!\n");
            //res = insCommonResources.setIntegerToByte(res, ires); 
            
            return false;
        }
        //Underflow
        else if(ires < 1 - Math.pow(2, 15)){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your addition result underflows!\n");
            //temp = - ires - 1;
            //res = returnLogicalNOTByte(insCommonResources.setIntegerToByte(res, temp));
            
            return false;
        }
        //Normal
        else if(ires >= 0 && ires <= Math.pow(2, 15) - 1){
            str = insCommonResources.setIntegerToString(ires);
            str = insCommonResources.VerifyMachineString(str, 16);
            insCommonResources.setStringToByte(res, str, 0, 15);
        }
        else if(ires < 0 && ires >= 1 - Math.pow(2, 15)) {
            temp = - ires - 1;
            str = insCommonResources.setIntegerToString(temp);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = returnLogicalNOTByte(insCommonResources.setStringToByte(res, str, 0, 15));
        }
        
        return true;
    }
    
    /**
        * @description return addition result of a byte array and an integer,
        *           store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 8
        * @param bt the byte array
        * @param immed the immediate integer
        * @param insCommonResources contain some common methods
        * @return the result in a byte array
        * @edit record:
    */
    public byte[] returnAdditionByte(byte[] bt, int immed, CommonResources insCommonResources){
        byte[] res = new byte[bt.length];
        byte signBit = bt[0];
        //byte signBit2 = bt2[0];
        int ibt = 0;
        //int ibt2 = 0;
        int ires = 0;
        
        // two temporary variables to store values.
        String str = new String();
        int temp = 0;

        if(signBit == 0){
            ibt = insCommonResources.setByteToInteger(bt);
            ires = ibt + immed;                  
        }

        else if(signBit == 1){
            ibt = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt)) + 1);
            ires = ibt + immed;
        }
        
        //Overflow
        if(ires > Math.pow(2, 15) - 1){
            insCommonResources.SetCC(1, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your addition result overflows!\n");
            res = insCommonResources.setIntegerToByte(res, ires);          
        }
        //Underflow
        else if(ires < 1 - Math.pow(2, 15)){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your addition result underflows!\n");
            temp = - ires - 1;
            res = returnLogicalNOTByte(insCommonResources.setIntegerToByte(res, temp));
        }
        //Normal
        else if(ires >= 0 && ires <= Math.pow(2, 15) - 1){
            str = insCommonResources.setIntegerToString(ires);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = insCommonResources.setStringToByte(res, str, 0, 15);
        }
        else if(ires < 0 && ires >= 1 - Math.pow(2, 15)) {
            temp = - ires - 1;
            str = insCommonResources.setIntegerToString(temp);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = returnLogicalNOTByte(insCommonResources.setStringToByte(res, str, 0, 15));
        }
        
        return res;
    }
    
    /**
        * @description calculate subtraction result of two byte arrays 
        *           and store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 8
        * @param Minuend the minuend array
        * @param Subtrahend the subtrahend array
        * @param res the subtraction result array
        * @param insCommonResources contain some common methods
        * @return 1-success 0-fail(overflow or underflow)
        * @edit record:
    */
    public boolean calculateSubtractionByte(byte[] Minuend, byte[] Subtrahend, byte[] res, CommonResources insCommonResources){
        //byte[] res = new byte[16];
        byte signBit1 = Minuend[0];
        byte signBit2 = Subtrahend[0];
        int iMinuend = 0;
        int iSubtrahend = 0;
        int ires = 0;
        // two temporary variables to store values.
        String str = new String();
        int temp = 0;
    
        //two positive numbers.
        if(signBit1 == 0 && signBit2 == 0){
            iMinuend = insCommonResources.setByteToInteger(Minuend);
            iSubtrahend = insCommonResources.setByteToInteger(Subtrahend);
            ires = iMinuend - iSubtrahend;                  
        }
        //one positive number and one negative number.
        else if(signBit1 == 1 && signBit2 == 0){
            iMinuend = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(Minuend)) + 1);
            iSubtrahend = insCommonResources.setByteToInteger(Subtrahend);
            ires = iMinuend - iSubtrahend; 
        }
        else if(signBit1 == 0 && signBit2 == 1){
            iMinuend = insCommonResources.setByteToInteger(Minuend);
            iSubtrahend = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(Subtrahend)) + 1);
            ires = iMinuend - iSubtrahend; 
        }  
        //two negative numbers.
        else if(signBit1 == 1 && signBit2 == 1){
            iMinuend = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(Minuend)) + 1);
            iSubtrahend = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(Subtrahend)) + 1);
            ires = iMinuend - iSubtrahend; 
        }
        
        //Overflow
        if(ires > Math.pow(2, 15) - 1){
            insCommonResources.SetCC(1, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your subtraction result overflows!\n");
            //res = insCommonResources.setIntegerToByte(res, ires); 
            
            return false;
        }
        //Underflow
        else if(ires < 1 - Math.pow(2, 15)){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your subtraction result underflows!\n");
            //temp = - ires - 1;
            //res = returnLogicalNOTByte(insCommonResources.setIntegerToByte(res, temp));
            
            return false;
        }
        //Normal
        else if(ires >= 0 && ires <= Math.pow(2, 15) - 1){
            str = insCommonResources.setIntegerToString(ires);
            str = insCommonResources.VerifyMachineString(str, 16);
            insCommonResources.setStringToByte(res, str, 0, 15);
        }
        else if(ires < 0 && ires >= 1 - Math.pow(2, 15)) {
            temp = - ires - 1;
            str = insCommonResources.setIntegerToString(temp);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = returnLogicalNOTByte(insCommonResources.setStringToByte(res, str, 0, 15));
        }
        
        return true;
    }
    
    /**
        * @description return subtraction result of a byte array and an integer,
        *           store the content into another byte array
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 8
        * @param bt the byte array
        * @param immed the immediate integer
        * @param insCommonResources contain some common methods
        * @return the result in a byte array
        * @edit record:
    */
    public byte[] returnSubtractionByte(byte[] bt, int immed, CommonResources insCommonResources){
        byte[] res = new byte[bt.length];
        byte signBit = bt[0];
        //byte signBit2 = bt2[0];
        int ibt = 0;
        //int ibt2 = 0;
        int ires = 0;
        
        // two temporary variables to store values.
        String str = new String();
        int temp = 0;

        if(signBit == 0){
            ibt = insCommonResources.setByteToInteger(bt);
            ires = ibt - immed;                  
        }

        else if(signBit == 1){
            ibt = - (insCommonResources.setByteToInteger(returnLogicalNOTByte(bt)) + 1);
            ires = ibt - immed;
        }
        
        //Overflow
        if(ires > Math.pow(2, 15) - 1){
            insCommonResources.SetCC(1, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your subtraction result overflows!\n");
            res = insCommonResources.setIntegerToByte(res, ires);          
        }
        //Underflow
        else if(ires < 1 - 32768){
            insCommonResources.SetCC(2, 1);
            insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]" + "Your subtraction result underflows!\n");
            temp = - ires - 1;
            res = returnLogicalNOTByte(insCommonResources.setIntegerToByte(res, temp));
        }
        //Normal
        else if(ires >= 0 && ires <= Math.pow(2, 15) - 1){
            str = insCommonResources.setIntegerToString(ires);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = insCommonResources.setStringToByte(res, str, 0, 15);
        }
        else if(ires < 0 && ires >= 1 - Math.pow(2, 15)) {
            temp = - ires - 1;
            str = insCommonResources.setIntegerToString(temp);
            str = insCommonResources.VerifyMachineString(str, 16);
            res = returnLogicalNOTByte(insCommonResources.setStringToByte(res, str, 0, 15));
        }
        
        return res;
    }
}
