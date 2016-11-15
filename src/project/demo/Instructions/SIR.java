/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.demo.CommonResources;
import project.demo.ExecuteInstructions;
import project.demo.Procedures;
import project.demo.units.adder;

/**
* To simulate the Cache in the computer.
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
* @author Zhe Yang
* @Version 2.0
* @since OCT 10,2015
* 
*/
public class SIR extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        //加一个正则判断式，用来判断是不是3-4个数字。
        
        String pattern = "([0-3],)([0-9]|[1-2]{1}[0-9]{1}|30|31)";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        
        int phase0 = 0;
        int phase1 = 0;
                        
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        

        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),5);
 
        System.out.println("Encode SIR content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }

    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] Immed = new byte[5];
        int iImmed = 0;
        int iRX = 0;
        String str = new String();
        //first ALU input
        byte[] fALUInput = new byte[16];
        //second ALU input
        byte[] sALUInput = new byte[16];
        adder insAdder = new adder();
        
        insCommonResources.GetPartofBytes(words, Immed, 11, 15);
        
        //IAR
        str = insCommonResources.setByteToString(Immed);
        iImmed = insCommonResources.setByteToInteger(Immed);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.IAR.length);
        insCommonResources.IAR = insCommonResources.setStringToByte(insCommonResources.IAR, str, 0, 15);        
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IR->IAR:", insCommonResources.setByteToString(insCommonResources.IAR) +"\n");
        
        //
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        iRX = insCommonResources.setByteToInteger(RX);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Address:" + iImmed + "\n");
        
  
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode immediate number:" + iImmed + "\n");
        
        //
        if( iImmed == 0)
        {
            insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "The immediate number is zero, do nothing.\n");
            return true;
        }
        
        insCommonResources.MBR = new byte[16];
        insCommonResources.MBR = insCommonResources.setIntegerToByte(insCommonResources.MBR, iImmed);       
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]iImmed->MBR:", insCommonResources.setByteToString(insCommonResources.MBR) +"\n");
        
        insCommonResources.IRR = insCommonResources.MBR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MBR->IRR:", insCommonResources.setByteToString(insCommonResources.IRR) +"\n");
        
        /*int difference = 0;
        //->R0-R3
        if (iRX == 0) {
            difference = insCommonResources.setByteToInteger(insCommonResources.R0) - insCommonResources.setByteToInteger(insCommonResources.IRR);
            if( difference < 0 ) {
                insCommonResources.GUI.AddContectToPane("[ERROR]","Computation for signed numbers are not support now, and will be fixed in later phase.\n");
            }
            else {
                insCommonResources.setIntegerToByte(insCommonResources.R0, difference);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]R0-IRR->R0:", insCommonResources.setByteToString(insCommonResources.R0) +"\n");
            }
            
            
        }
        else if (iRX == 1) {
            difference = insCommonResources.setByteToInteger(insCommonResources.R1) - insCommonResources.setByteToInteger(insCommonResources.IRR);
            if( difference < 0 ) {
                insCommonResources.GUI.AddContectToPane("[ERROR]","Computation for signed numbers are not support now, and will be fixed in later phase.\n");
            }
            else {
                insCommonResources.setIntegerToByte(insCommonResources.R1, difference);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]R1-IRR->R1:", insCommonResources.setByteToString(insCommonResources.R1) +"\n");
            }
            
        }
        else if (iRX == 2) {
            difference = insCommonResources.setByteToInteger(insCommonResources.R2) - insCommonResources.setByteToInteger(insCommonResources.IRR);
            if( difference < 0 ) {
                insCommonResources.GUI.AddContectToPane("[ERROR]","Computation for signed numbers are not support now, and will be fixed in later phase.\n");
            }
            else {
                insCommonResources.setIntegerToByte(insCommonResources.R2, difference);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]R2-IRR->R2:", insCommonResources.setByteToString(insCommonResources.R2) +"\n");
            }
        } 
        else if (iRX == 3) {
             difference = insCommonResources.setByteToInteger(insCommonResources.R3) - insCommonResources.setByteToInteger(insCommonResources.IRR);
            if( difference < 0 ) {
                insCommonResources.GUI.AddContectToPane("[ERROR]","Computation for signed numbers are not support now, and will be fixed in later phase.\n");
            }
            else {
                insCommonResources.setIntegerToByte(insCommonResources.R3, difference);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]R3-IRR->R3:", insCommonResources.setByteToString(insCommonResources.R3) +"\n");
            }
         }
        else {
             return false;
        }*/
        
        //R0-R3
        //R0-R3
        if (iRX == 0) {
            fALUInput = insCommonResources.IRR;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.IRR) + "\n");
            sALUInput = insCommonResources.R0;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R0)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
            //insAdder.calculateSubtractionByte(sALUInput, fALUInput, insCommonResources.R0, insCommonResources);
            insCommonResources.R0 = insAdder.returnSubtractionByte(insCommonResources.R0, iImmed, insCommonResources);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R0:", insCommonResources.setByteToString(insCommonResources.R0) +"\n");
        }
        else if (iRX == 1) {
            fALUInput = insCommonResources.IRR;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.IRR) + "\n");
            sALUInput = insCommonResources.R1;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R1)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n");
            //insAdder.calculateSubtractionByte(sALUInput, fALUInput, insCommonResources.R1, insCommonResources);
            insCommonResources.R1 = insAdder.returnSubtractionByte(insCommonResources.R1, iImmed, insCommonResources);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R1:", insCommonResources.setByteToString(insCommonResources.R1) +"\n");
        }
        else if (iRX == 2) {
            fALUInput = insCommonResources.IRR;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.IRR) + "\n");
            sALUInput = insCommonResources.R2;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R2)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");
            //insAdder.calculateSubtractionByte(sALUInput, fALUInput, insCommonResources.R2, insCommonResources);
            insCommonResources.R2 = insAdder.returnSubtractionByte(insCommonResources.R2, iImmed, insCommonResources);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R2:", insCommonResources.setByteToString(insCommonResources.R2) +"\n");
        }
        else if (iRX == 3) {
            fALUInput = insCommonResources.IRR;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.IRR) + "\n");
            sALUInput = insCommonResources.R3;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R3)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n");
            //insAdder.calculateSubtractionByte(sALUInput, fALUInput, insCommonResources.R3, insCommonResources);
            insCommonResources.R3 = insAdder.returnSubtractionByte(insCommonResources.R3, iImmed, insCommonResources);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R3:", insCommonResources.setByteToString(insCommonResources.R3) +"\n");
         }
        else {
             return false;
        }
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
}
