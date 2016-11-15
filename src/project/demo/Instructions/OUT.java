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
public class OUT  extends Procedures{
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
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
 
        System.out.println("Encode OUT content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
        
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] DevID = new byte[5];

        int iRX = 0;
        int iDevID = 0;
        int pos = 0;
        
        //for character
        String str = new String();  
        byte[] tmp_words = new byte[16];
        
        //encode the content       
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        insCommonResources.GetPartofBytes(words, DevID, 11, 15);
        
        iRX = insCommonResources.setByteToInteger(RX);
        iDevID = insCommonResources.setByteToInteger(DevID);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:DevID:" + iDevID + "\n");
        
        //get the character
        switch(iRX) {
            case 0:
                str = String.format("%c", insCommonResources.setByteToInteger(insCommonResources.R0));
                tmp_words = insCommonResources.R0;
                break;
            case 1:
                str = String.format("%c", insCommonResources.setByteToInteger(insCommonResources.R1));
                tmp_words = insCommonResources.R1;
                break;
            case 2:
                str = String.format("%c", insCommonResources.setByteToInteger(insCommonResources.R2));
                tmp_words = insCommonResources.R2;
                break;
            case 3:
                str = String.format("%c", insCommonResources.setByteToInteger(insCommonResources.R3));
                tmp_words = insCommonResources.R3;
                break;
            default:
                break;
        }
        
        switch(iDevID) {
            //PRINTER   -- by Number
            case 0:
                int temp_count = insCommonResources.returnAppropriateInteger(tmp_words);
                
                
                insCommonResources.GUI.AddContectToPaneForUSER(String.format("%d", temp_count));
                break;
            //PRINTER    -- by charactor
            case 1:
                char insChar = (char)insCommonResources.setByteToInteger(tmp_words);
                insCommonResources.GUI.AddContectToPaneForUSER(String.format("%c", insChar));
                break;
            //CARD_READER
            case 2:
                //not supported now
                break;
            //R0
            case 3:
                insCommonResources.R0 = tmp_words;
                break;
            //R1
            case 4:
                insCommonResources.R1 = tmp_words;
                break;
            //R2
            case 5:
                insCommonResources.R2 = tmp_words;
                break;
            //R3
            case 6:
                insCommonResources.R3 = tmp_words;
                break;
            //PC
            case 7:
                //insCommonResources.PC = insCommonResources.setIntegerToByte(insCommonResources.PC, insCommonResources.setByteToInteger(tmp_words));
                insCommonResources.PC = insCommonResources.GetPartofBytes(tmp_words, 4, 15);
                //pos = insCommonResources.setByteToInteger(insCommonResources.PC);
                //pos = pos + 1;
                //insCommonResources.setIntegerToByte(insCommonResources.PC, pos);
                break;
            default:
                break;
        }
        
        
        return true;
    }
}
