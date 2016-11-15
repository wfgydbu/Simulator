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
 * @author yangzhe
 */
public class CHK extends Procedures{

    @Override
    public boolean EncodeContent(CommonResources insCommonResources, byte[] specificword) {
        
        String pattern = "([0-3],)([0-7])";
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

        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + "000"
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),5);
 
        System.out.println("Encode CHK content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    
    }

    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        insCommonResources.GUI.AddContectToPane("Device's Status Code: 1-available, 0-unavailable", "\n");
        
        byte[] RX = new byte[2];
        byte[] DevID = new byte[5];
        
        byte[] tmp_words = new byte[16];
        int DevStatus = 0;
        
        int iRX = 0;
        int iDevID = 0;
        
        RX = insCommonResources.GetPartofBytes(words, 6, 7);
        DevID = insCommonResources.GetPartofBytes(words, 11, 15);
        
        iRX = insCommonResources.setByteToInteger(RX);
        iDevID = insCommonResources.setByteToInteger(DevID);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:DevID:" + iDevID + "\n");
        
        switch (iDevID){
            //KEYBOARD
            case 0:
                DevStatus = 1;
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, DevStatus);
                break;
            //PRINTER
            case 1:
                DevStatus = 1;
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, DevStatus);
                break;
            //CARD_READER
            case 2:
                DevStatus = 0;
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, DevStatus);
                break;
            default:
                DevStatus = 0;
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, DevStatus);
                break;
                
        }
        
        switch (iRX){
            case 0:
                insCommonResources.R0 = tmp_words;
                insCommonResources.GUI.AddContectToPane("[CheckResult]", "Device status of DevID: " + iDevID + " is: " + insCommonResources.setByteToString(tmp_words) +"\n");
                break;
            case 1:
                insCommonResources.R1 = tmp_words;
                insCommonResources.GUI.AddContectToPane("[CheckResult]", "Device status of DevID: " + iDevID + " is: " + insCommonResources.setByteToString(tmp_words) +"\n");
                break;
            case 2:
                insCommonResources.R2 = tmp_words;
                insCommonResources.GUI.AddContectToPane("[CheckResult]", "Device status of DevID: " + iDevID + " is: " + insCommonResources.setByteToString(tmp_words) +"\n");               
                break;
            case 3:
                insCommonResources.R3 = tmp_words;
                insCommonResources.GUI.AddContectToPane("[CheckResult]", "Device status of DevID: " + iDevID + " is: " + insCommonResources.setByteToString(tmp_words) +"\n");                
                break;
            default:
                break;
        }
          
        return true;
    }
    
}
