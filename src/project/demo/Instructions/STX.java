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
* To simulate the Cache in the computer.
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
*/
public class STX extends Procedures{
    
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
 
        System.out.println("Encode STX content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
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
        
        insCommonResources.GetPartofBytes(words, Address, 11, 15);
        
        //IAR
        //insCommonResources.SetPartofBytes(insCommonResources.IAR, Address, 0, 4);
        str = insCommonResources.setByteToString(Address);
        iAddress = insCommonResources.setByteToInteger(Address);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.IAR.length);
        insCommonResources.IAR = insCommonResources.setStringToByte(insCommonResources.IAR, str, 0, 15);        
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IR->IAR:", insCommonResources.setByteToString(insCommonResources.IAR) +"\n");
        
        //
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
        
        EA = iAddress;    
    
        insCommonResources.IAR = insCommonResources.setIntegerToByte(insCommonResources.IAR, EA);
  
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode effective address successfully:EA:" + EA + "\n");
        
        //
        insCommonResources.MAR = insCommonResources.IAR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR->MAR:", insCommonResources.setByteToString(insCommonResources.MAR) +"\n");

        //->X1-X3
        if (iIX == 1) {
            insCommonResources.IRR = insCommonResources.getStorge(insCommonResources, insCommonResources.setByteToInteger(insCommonResources.X1));
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]X1->IRR:", insCommonResources.setByteToString(insCommonResources.IRR) +"\n");
        }
        else if (iIX == 2) {
            insCommonResources.IRR = insCommonResources.getStorge(insCommonResources, insCommonResources.setByteToInteger(insCommonResources.X2));
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]X2->IRR:", insCommonResources.setByteToString(insCommonResources.IRR) +"\n");
        } 
        else if (iIX == 3) {
             insCommonResources.IRR = insCommonResources.getStorge(insCommonResources, insCommonResources.setByteToInteger(insCommonResources.X3));
             insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]X3->IRR:", insCommonResources.setByteToString(insCommonResources.IRR) +"\n");
         }
        else {
             return false;
        }       
        
        insCommonResources.MBR = insCommonResources.IRR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->MBR:", insCommonResources.setByteToString(insCommonResources.MBR) +"\n");
        
        if ( false == insCommonResources.setStorge(insCommonResources, insCommonResources.MBR,insCommonResources.setByteToInteger(insCommonResources.MAR))) {
            return false;
        }
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MBR->MEM in c(MAR).", "\n");
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
}
