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

/**
 *
 * @author Zhe Yang
 * @verison 1.0 
 * @since OCT 4,2015
 * 
 */
public class JSR extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        //加一个正则判断式，用来判断是不是3-4个数字。
        
        String pattern = "([0-3],)([0-9]|[1-2]{1}[0-9]{1}|30|31)(,1){0,1}";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        } 
        
        int phase0 = 0;
        int phase1 = 0;
        int phase2 = 0;
                
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        
        if( 3 == temp.length) {
            phase2 = Integer.parseInt(temp[2]);
        }
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase2),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),5);
 
        System.out.println("Encode JSR content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words){
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        //byte[] RX = new byte[2];
        byte[] IX = new byte[2];
        byte[] I = new byte[1];
        byte[] Address = new byte[5];
        int iAddress = 0;
        //int iRX = 0;
        int iIX = 0;
        int iI = 0;
        int EA = 0;
        String str = new String();
        int pos = insCommonResources.getByteUnits(insCommonResources.PC);
        
        insCommonResources.GetPartofBytes(words, Address, 11, 15);
        
        str = insCommonResources.setByteToString(Address);
        iAddress = insCommonResources.setByteToInteger(Address);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.IAR.length);
        insCommonResources.setStringToByte(insCommonResources.IAR, str, 0, 15);        
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IR->IAR:", 
                insCommonResources.setByteToString(insCommonResources.IAR) +"\n");
        
        insCommonResources.GetPartofBytes(words, I, 10, 10);
        //insCommonResources.GetPartofBytes(words, RX, 6, 7);
        insCommonResources.GetPartofBytes(words, IX, 8, 9);
        
        iI = insCommonResources.setByteToInteger(I);
        iIX = insCommonResources.setByteToInteger(IX);
        //iRX = insCommonResources.setByteToInteger(RX);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:IX:" + iIX + "\n");
        //insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:I:" + iI + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Address:" + iAddress + "\n");
        
 
        
        //EA
        if(iIX == 0){
            EA = iAddress;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 1){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X1);
            insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X1->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 2){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X2);
            insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X2->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 3){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X3);
            insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X3->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else {
            return false;
        }
        
 
        
        if(iI == 1) {
            EA = insCommonResources.setByteToInteger(insCommonResources.getStorge(insCommonResources, EA));
            insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]c(IAR)->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
 
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode effective address successfully:EA:" + EA + "\n");
        
        //
        insCommonResources.MAR = insCommonResources.IAR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR->MAR:", insCommonResources.setByteToString(insCommonResources.MAR) +"\n");      
        
        //save return address to R3
        pos = pos + 1;
        str = insCommonResources.setIntegerToString(pos);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.R3.length);
        insCommonResources.R3 = insCommonResources.setStringToByte(insCommonResources.R3, str, 0, 15);
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]PC+1->R3:", insCommonResources.setByteToString(insCommonResources.R3) +"\n");
            
        //jump
        insCommonResources.PC = insCommonResources.GetPartofBytes(insCommonResources.MAR, 4, 15);
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter + "]c(MAR)->PC:", insCommonResources.setByteToString(insCommonResources.PC) +"\n");
        
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }   
}
