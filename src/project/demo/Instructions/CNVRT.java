/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.demo.CommonResources;
import project.demo.Procedures;

/**
 *
 * @author 一天
 */
public class CNVRT extends Procedures{
        
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        //加一个正则判断式，用来判断是不是3-4个数字。
        
        String pattern = "([0-3],)([0-3],)([0-9]|[1-2]{1}[0-9]{1}|30|31)(,1){0,1}";
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
        int phase3 = 0;
        
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        phase2 = Integer.parseInt(temp[2]);
        
        if( 4 == temp.length) {
            phase3 = Integer.parseInt(temp[3]);
        }
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase3),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase2),5);
 
        System.out.println("Encode CNVRT content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] IX = new byte[2];
        byte[] I = new byte[1];
        byte[] Address = new byte[5];
        int iAddress = 0;
        int iRX = 0;
        int iIX = 0;
        int iI = 0;
        int EA = 0;
        String str = new String();
        
        insCommonResources.GetPartofBytes(words, Address, 11, 15);
        
        //IAR, any instruction related to address, should put the address into IAR first.
        str = insCommonResources.setByteToString(Address);
        iAddress = insCommonResources.setByteToInteger(Address);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.IAR.length);
        insCommonResources.IAR = insCommonResources.setStringToByte(insCommonResources.IAR, str, 0, 15);        
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IR->IAR:", insCommonResources.setByteToString(insCommonResources.IAR) +"\n");
        
        //encode the content
        insCommonResources.GetPartofBytes(words, I, 10, 10);
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        insCommonResources.GetPartofBytes(words, IX, 8, 9);
        
        iI = insCommonResources.setByteToInteger(I);
        iIX = insCommonResources.setByteToInteger(IX);
        iRX = insCommonResources.setByteToInteger(RX);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:IX:" + iIX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:I:" + iI + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Address:" + iAddress + "\n");
        
 
        
        //EA find the effectinve address
        if(iIX == 0){
            EA = iAddress;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 1){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X1);
            insCommonResources.IAR = insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X1->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 2){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X2);
            insCommonResources.IAR = insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X2->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else if(iIX == 3){
            EA = iAddress + insCommonResources.setByteToInteger(insCommonResources.X3);
            insCommonResources.IAR = insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR+X3->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        else {
            return false;
        }       

        if(iI == 1) {
            EA = insCommonResources.setByteToInteger(insCommonResources.getStorge(insCommonResources, EA));
            insCommonResources.IAR = insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]c(IAR)->IAR:", insCommonResources.VerifyMachineString(insCommonResources.setIntegerToString(EA), 16) +"\n");
        }
        
 
        
        byte[] content = new byte[16];
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode effective address successfully:EA:" + EA + "\n");
        
        content = insCommonResources.getStorge(insCommonResources, EA);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Get content at EA:" + insCommonResources.setByteToString(content) + "\n");
        
        int F = 0;
        //->R0-R3, show the result
        if (iRX == 0) {
            F = insCommonResources.setByteToInteger(insCommonResources.R0);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Get F value:", F +"\n");
        }
        else if (iRX == 1) {
            F = insCommonResources.setByteToInteger(insCommonResources.R1);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Get F value:", F +"\n");
        }
        else if (iRX == 2) {
            F = insCommonResources.setByteToInteger(insCommonResources.R2);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Get F value:", F +"\n");
        } 
        else if (iRX == 3) {
             F = insCommonResources.setByteToInteger(insCommonResources.R3);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Get F value:", F +"\n");
         }
        else {
             return false;
        }  
        
        if (F == 0) {
            switch(iRX) {
                case 0:
                    insCommonResources.R0 = insCommonResources.setIntegerToByte(insCommonResources.R0, insCommonResources.ConvertFP2INT(content));
                    break;
                case 1:
                    insCommonResources.R1 = insCommonResources.setIntegerToByte(insCommonResources.R1, insCommonResources.ConvertFP2INT(content));
                    break;
                case 2:
                    insCommonResources.R2 = insCommonResources.setIntegerToByte(insCommonResources.R2, insCommonResources.ConvertFP2INT(content));
                    break;
                case 3:
                    insCommonResources.R3 = insCommonResources.setIntegerToByte(insCommonResources.R3, insCommonResources.ConvertFP2INT(content));
                    break;
                default: break;
            }
        } 
        
        if (F == 1) {
            insCommonResources.FR0 = insCommonResources.ConvertINT2FP(insCommonResources.setByteToInteger(content));
        }        
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
    
}
