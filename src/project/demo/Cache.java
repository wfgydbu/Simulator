/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;

import java.util.ArrayList;
import java.util.List;

//structure of cacheline
class CacheLine {
    byte[] tag = new byte[8];
    byte[] words = new byte[4];
    byte[][] content = new byte[16][16];
}

/**
* To simulate the Cache in the computer.
*
* @author Yitian Huang
* @verison 1.0 
* @since Oct 10,2015
* 
*/
public class Cache {
    
    public Memory insMemory = new Memory();    
    
    //structure of cache
    public List insCache = new ArrayList();
    
    
     public byte[] get(CommonResources comRes, int position){
        CacheLine temp;
        byte[] tmp_pos = new byte[12];
        byte[] tmp_tag = new byte[8];
        byte[] tmp_words = new byte[4];
           
        tmp_pos = comRes.setIntegerToByte(tmp_pos, position);
        tmp_tag =  comRes.GetPartofBytes(tmp_pos, 0, 7);
        tmp_words = comRes.GetPartofBytes(tmp_pos, 8, 11);
        
        int hit;
        //hit?
        for(int i = 0; i < comRes.insCache.insCache.size(); i++) {
            temp = (CacheLine)comRes.insCache.insCache.get(i);
            if(comRes.setByteToInteger(temp.tag) == comRes.setByteToInteger(tmp_tag)) {
                //hit!
                hit = comRes.setByteToInteger(tmp_words);
                comRes.GUI.AddContectToPane("[Cache][GET]", "Hit! Update Cache! tag:" + comRes.setByteToInteger(temp.tag) + " words:" + comRes.setByteToInteger(tmp_words) + " content:" + comRes.setByteToString(temp.content[hit]) + "\n");
                return temp.content[hit];
                
            }
        }
               
        //NOT hit, get it from memory and updata the cache.
        temp = new CacheLine();
        temp.tag = tmp_tag;          
        
        for( int i = 0; i < 16; i++) {
            temp.content[i] = comRes.insCache.getMemory(comRes, comRes.setByteToInteger(temp.tag) * 16 + i);
        }   
        
        comRes.GUI.AddContectToPane("[Cache][GET]", "NOT Hit! get from Memory, and Update Cache!\n");
                
        if ( comRes.insCache.insCache.size() < 16 ) {
            comRes.insCache.insCache.add(temp);
        }
        else {
            //FIFO
            comRes.insCache.insCache.remove(0);
            comRes.insCache.insCache.add(temp);
        }
        
        return temp.content[comRes.setByteToInteger(tmp_words)];
    }
    
    public boolean put(CommonResources comRes, byte[] content, int position){
        System.out.println("[put]" + position +" "+ comRes.setByteToString(content));
        CacheLine temp;
        byte[] tmp_pos = new byte[12];
        byte[] tmp_tag = new byte[8];
        byte[] tmp_words = new byte[4];

        tmp_pos = comRes.setIntegerToByte(tmp_pos, position);
        tmp_tag = comRes.GetPartofBytes(tmp_pos, 0, 7);
        tmp_words = comRes.GetPartofBytes(tmp_pos, 8, 11);
        
        //hit?
        for(int i = 0; i < comRes.insCache.insCache.size(); i++) {
            temp = (CacheLine)comRes.insCache.insCache.get(i);
            if(comRes.setByteToInteger(temp.tag) == comRes.setByteToInteger(tmp_tag)) {
                //hit!update cache
                temp.content[comRes.setByteToInteger(tmp_words)] = content;
                comRes.insCache.insCache.set(i, temp);
                comRes.GUI.AddContectToPane("[Cache][PUT]", "Hit! Update Cache! tag:" + comRes.setByteToInteger(temp.tag) + " words:" + comRes.setByteToInteger(tmp_words) + " content:" + comRes.setByteToString(content) + "\n");
            }
        }
        
        //not hit
        //do nothing
        
        //update memmory
        comRes.insCache.setMemory(comRes, content, position);
        comRes.GUI.AddContectToPane("[Cache][PUT]", "Update Memory! Location:" + position + " content:" + comRes.setByteToString(content) + "\n");
             
        return true;
    }
    
    /**
        * @description set the content of src into a specific position(start) into memmory
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param content 
        * @param position the start position you want to set in the Memory
        * @return 1-success 0-fail
        * @edit record:
    */
    /*
    public boolean setMemory(byte[] mem, byte[] src, int start) {
        
        for( int i = 0; i < 16; i++) {
            mem[start + i] = src[i];
        }
        
        return true;
    }
    */
     public boolean setMemory(CommonResources comRes, byte[] content, int position) {
            byte[] tmp = new byte[16];
            tmp = content;
            comRes.insCache.insMemory.Memory[position] = tmp;
            comRes.GUI.AddContectToPane("[setMemory]", "Set content:" + comRes.setByteToString(content) + " to location:" + position + "\n");                
            return true;
    }
    
    /**
        * @description get the content of the memory in a specific position
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param position the position
        * @return the content in a byte array
        * @edit record:
    */
    public byte[] getMemory(CommonResources comRes, int position) {
        byte[] res = new byte[16];
        res = comRes.insCache.insMemory.Memory[position];
        comRes.GUI.AddContectToPane("[getMemory]", "Get content:" + comRes.setByteToString(res) + "from location:" + position + "\n");
        return res;
    }


}
