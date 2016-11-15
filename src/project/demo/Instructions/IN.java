/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import project.demo.CommonResources;
import project.demo.Procedures;

/**
 *
 * @author 一天
 */
public class IN extends Procedures{
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        String pattern = "([0-3],)([0|2-9])";
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
 
        System.out.println("Encode IN content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;

    }
    
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] DevID = new byte[5];

        int iRX = 0;
        int iDevID = 0;
        
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
        
        // get the character from Device
        switch(iDevID) {
            //KEY_BOARD
            case 0:
                int count = 0;
                char a;
                                
                str = insCommonResources.GUI.InputAChar();
                
                if ( true == insCommonResources.CheckIsNumberString(str) ) {
                    count = Integer.parseInt(str);
                }
                else {
                    for ( int i = 0; i < str.length(); i++){
                        a = str.charAt(i);
                        count += a;

                        if ( a >= 48 && a <= 57) {
                            count = count - 48;
                        }
                    }
                }
                
                
                if( count > 65535) {
                    insCommonResources.GUI.AddContectToPaneForERROR("[ERROR]The number " + count + " is greater than 65535, it will be % by 65536.");
                    
                    count = count % 65536;
                }
                
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, count);
                insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Raw input:" + str + "\n");
                
                insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Ingeter:" + count + "\n");
                insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Binary:" + insCommonResources.setByteToString(tmp_words) + "\n");

                break;
            //CARD_READER
            case 2:
                //not supported now
                break;
            //R0
            case 3:
                tmp_words = insCommonResources.R0;
                break;
            //R1
            case 4:
                tmp_words = insCommonResources.R1;
                break;
            //R2
            case 5:
                tmp_words = insCommonResources.R2;
                break;
            //R3
            case 6:
                tmp_words = insCommonResources.R3;
                break;
            //PC
            case 7:
                int Ipc = insCommonResources.setByteToInteger(insCommonResources.PC);
                tmp_words = insCommonResources.setIntegerToByte(tmp_words, Ipc);
                break;
            //File System
            case 8:
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.showOpenDialog(null);
                int line = 0;
                String content = new String();
                                
                File insFile =  fileChooser.getSelectedFile();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(insFile));
                    String tempString = null;
                    
                    insCommonResources.GUI.AddContectToPaneForUSER("Get contents from file:\n");
                    insCommonResources.GUI.AddContectToPaneForUSER("--------------------------\n");
                    while ((tempString = reader.readLine()) != null) {
                        line++;
                        insCommonResources.GUI.AddContectToPaneForUSER("[" + line + "] " + tempString + "\n");
                        insCommonResources.GUI.AddContectToPaneForUSER("--------------------------\n");
                        content += tempString;
                        content += '#';                        
                    }
                    
                    content += '@';
                    if (content.length() % 2 != 0) {
                        content += '@';
                    }
                    
                    System.out.println("!!!" + content);
                    content = FitterTheSymbols(content);
                    System.out.println("!!!" + content);
                    
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
                
                //Stornge content into Memory                
                int Memlocation = getMemLocation(insCommonResources, iRX);
                int pos = 0;
               
        
                while( pos < content.length()) {
                    byte[] ttemp_words = new byte[16];
                    String res = Integer.toBinaryString(content.charAt(pos));
                                        
                    res = insCommonResources.VerifyMachineString(res, 16);
                    
                    
                    
                    ttemp_words = insCommonResources.setStringToByte(ttemp_words, res, 0, 15);
                    if (false == insCommonResources.setStorge(insCommonResources, ttemp_words, Memlocation)) {
                        return false;
                    }
                    
                    Memlocation = Memlocation + 1;
                    pos  = pos + 1;
                }
                
                
                
                insCommonResources.GUI.AddContectToPane("Successfully put content into storage.\n");
                                
                return true;
            case 9:
                String userword =  insCommonResources.GUI.InputAChar();
                userword += '#';
                
                int word_Memlocation = 15;
                int word_pos = 0;
               
        
                while( word_pos < userword.length()) {
                    byte[] ttemp_words = new byte[16];
                    String res = Integer.toBinaryString(userword.charAt(word_pos));
                                        
                    res = insCommonResources.VerifyMachineString(res, 16);
                    
                    
                    
                    ttemp_words = insCommonResources.setStringToByte(ttemp_words, res, 0, 15);
                    if (false == insCommonResources.setStorge(insCommonResources, ttemp_words, word_Memlocation)) {
                        return false;
                    }
                      
                    
                    word_Memlocation = word_Memlocation + 1;
                    word_pos  = word_pos + 1;
                }
                
                
                return true;
            
            default:
                break;
        }
        
        //set the character to RX
        switch(iRX) {
            case 0:
                insCommonResources.R0 = tmp_words;
                break;
            case 1:
                insCommonResources.R1 = tmp_words;
                break;
            case 2:
                insCommonResources.R2 = tmp_words;
                break;
            case 3:
                insCommonResources.R3 = tmp_words;
                break;
            default:
                break;
        }
        return true;
    }
    
    public int getMemLocation(CommonResources insCommonResources, int iRX) {
        int res = 0;
        
        switch(iRX) {
            case 0:
                res = insCommonResources.setByteToInteger(insCommonResources.R0);
                break;
            case 1:
                res = insCommonResources.setByteToInteger(insCommonResources.R1);
                break;
            case 2:
                res = insCommonResources.setByteToInteger(insCommonResources.R2);
                break;
            case 3:
                res = insCommonResources.setByteToInteger(insCommonResources.R3);
                break;
            default:
                break;
        }
        
        return res;
    }
    
    String FitterTheSymbols(String src) {
        String res = new String();
        char tem;
        
        for (int i = 0; i < src.length(); i++) {
            tem = src.charAt(i);
            if ( ( tem >=65 && tem <=90 )|| ( tem >=97 && tem <= 122 ) || tem == 32 || tem == 35 || tem == 64 ) {
                res += tem;
            }
            
        }        
        
        return res;
        
    }
    
    
}
