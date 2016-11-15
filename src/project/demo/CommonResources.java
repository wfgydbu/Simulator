/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;

import project.demo.units.LogicOperation;

/**
* @description serve as the public class, provide all common resources and 
*                common fuctions, you can use these resources whereever in ths project.
** @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
*/
public class CommonResources {
    
    public CommonResources () {
        ClockConter = 0;
        flag_content = false;
        flag_single_step = false;
        
        opcode = 0;
        opcode_str = new String();
        instruction1 = new String();
        instruction2 = new String();
        
        strFromUser = new String();

        words = new byte[16];

        X1 = new byte[16];
        X2 = new byte[16];
        X3 = new byte[16];

        R0 = new byte[16];
        R1 = new byte[16];
        R2 = new byte[16];
        R3 = new byte[16];

        PC = new byte[12];
        CC = new byte[4];
        IR = new byte[16];
        MAR = new byte[16];
        MBR = new byte[16];
        MSR = new byte[16];
        MFR = new byte[4];
        RSR = new byte[4];
        IOR = new byte[6];
        IAR = new byte[16];
        IRR = new byte[16];
        insCache = new Cache();
        
        FR0 = new byte[16];
        FR1 = new byte[16];
        
    }
    
    //
    public String strFromUser;
    
    //The point to UIInterface
    //using it to operate units in GUI.
    public UIInterface GUI;
    
    //A counter to count the clock cycly.
    public int ClockConter;
    
    //flag for judge whether enconde the content successfully.
    public boolean flag_content;
    public boolean flag_single_step;
    
    //CODE for units.
    //public static final int CODE_MEMORY = 0;
    //public static final int CODE_CACHE  = 1;
    //public static final int CODE_WORD   = 2;
    public static final int CODE_KEYBOARD = 0;
    public static final int CODE_PRINTER = 1;
    public static final int CODE_CARD_READER = 2;
    public static final int CODE_R0    = 3;
    public static final int CODE_R1    = 4;
    public static final int CODE_R2    = 5;
    public static final int CODE_R3    = 6;
    public static final int CODE_PC    = 7;
   
    public static final int CODE_X1    = 8;
    public static final int CODE_X2    = 9;
    public static final int CODE_X3    = 10;    
    public static final int CODE_CC     = 11;
    public static final int CODE_IR     = 12;
    public static final int CODE_MAR    = 13;
    public static final int CODE_MBR    = 14;
    public static final int CODE_MSR    = 15;
    public static final int CODE_MFR    = 16;

      
    //opcode 
    public int opcode;
    public String opcode_str;
    
    //instruction-part1
    public String instruction1;
    //instruction-part2
    public String instruction2;
    
    // words 16-bits
    public byte[] words;
    
    //3 Index Registers 16-bits each
    public byte[] X1;
    public byte[] X2;
    public byte[] X3;
    
    //4 General Purpose Registers (GPRs) R0-R3 16-bits each
    public byte[] R0;
    public byte[] R1;
    public byte[] R2;
    public byte[] R3;
    
    //PC 12-bits
    //Program Counter: address of next instruction to be executed. 
    //Note that 2^12 = 4096.
    public byte[] PC;
    
    //CC 4-bits
    //Condition Code: set when arithmetic/logical operations are executed; 
    //it has four 1-bit elements: overflow, underflow, division by zero, 
    //equal-or-not. They may be referenced as cc(0), cc(1), cc(2), cc(3). 
    //Or by the names OVERFLOW, UNDERFLOW, DIVZERO, EQUALORNOT
    public byte[] CC;
    
    //IR 16-bits
    //Instruction Register: holds the instruction to be executed
    public byte[] IR;
    
    //MAR 16-bits
    //Memory Address Register: holds the address of the word to be 
    //fetched from memory
    public byte[] MAR;
    
    //MBR 16-bits
    //Memory Buffer Register: holds the word just fetched from or 
    //stored into memory
    public byte[] MBR;
    
    //MSR 16-bits
    //Machine Status Register: certain bits record the status of the health of 
    //the machine
    public byte[] MSR;
    
    //MFR 4-bits
    //Machine Fault Register: contains the ID code if a machine fault 
    //after it occurs
    public byte[] MFR;
    
    //Other Registers to be defined here
    //RSR
    //Register Select Register
    public byte[] RSR;
    
    //IOR 6-bits
    //Internal Opcode Register 
    public byte[] IOR;
    
    //IAR 16-bits
    //instruction Address Register
    public byte[] IAR;
    
    //IRR 16-bits
    //Internal Result Register
    public byte[] IRR;
    
    // Memory and Cache
    //public Memory insMemory = new Memory();
    public Cache insCache;
    
    //Floating Point Registers
    public byte[] FR0;
    public byte[] FR1;
    
    /**
        * @description Get part of the content in a Byte array, in a certain length
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param src_code Byte array you need to capture from
        * @param des_code Byte array you put the content that captured from src_code
        * @param start start position in src_code to capture
        * @param end end position in src_code to capture
        * @return 1-success 0-fail
        * @recoed
    */
    public boolean GetPartofBytes(byte[] src_code, byte[] des_code, int start, int end) {
        if ( src_code.length > words.length
                   || des_code.length > words.length
                   || start < 0
                   || start > 15
                   || end < 0
                   || end > 15
                   || start > end ) {
            return false;
        }
        
        //capture process
        int count = 0;
        for (int i = start; i < end + 1; i++) {
           des_code[count] = src_code[i];
           count = count + 1;
        }
        return true;
    }
    
    /**
        * @description Same as boolean GetPartofBytes(byte[] src_code, byte[] des_code, int start, int end), but this method has a return value
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param src Byte array you need to capture from
        * @param start start position in src_code to capture
        * @param end end position in src_code to capture 
        * @return Byte array you put the content that captured from src_code
        * @edit record:
    */
    public byte[] GetPartofBytes(byte[] src, int start, int end) {
        byte[] res = new byte[end - start + 1];
        int count = 0;
        for (int i = start; i < end + 1; i++) {
           res[count] = src[i];
           count = count + 1;
        }
        return res;
    }
   
    /**
        * @description to dispaly the Byte array 'src' into string with 'type' scale, for example, make a array [1101] to "1101" in binary by type = 2; 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param type describle the scale, 2-bianry and 10-decimal
        * @param src the source byte array you need to convert
        * @return a 'type scale string
        * @edit record:
    */
    public String DisplayStrInStyles(int type, byte[] src){
        String str = new String();
        
        if ( 2 == type ) {
            for (int i = 0; i < src.length; i++) {
               str += src[i];
            }
            System.out.println("DisplayStrInBinary: "+ str );
        }
        

        return str;
    }
    
    /**
        * @description set a String(in binary) into a byte array. 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param br the object byte array
        * @param src the original string
        * @param start the start position you want to edit in the byte array
        * @param end the end position you want to edit in the byte array
        * @return the object byte array
        * @edit record:
    */
    public byte[] setStringToByte(byte[] br, String src, int start, int end){
        byte[] res = new byte[br.length];
        res = br;

        for(int i = start, j = 0; i <= end; i++,j++ ) {
            res[i] = (byte)Character.getNumericValue(src.charAt(j));
        }
        
        br = res;
        
        return res; 

    }
    
     /**
        * @description set a byte array into  String(in binary). 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param src the original byte array
        * @return the object string
        * @edit record:
    */
    public String setByteToString(byte[] src) {
        
        String str = new String();
        for(int i = 0; i < src.length; i++){
            str = str + src[i];
        }
        
        //System.out.println("setByteToString:" + str);        
        return str;
    }
    
    /**
        * @description set a Integer into Byte array 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param br the object byte array
        * @param i the number you need to set
        * @return the object byte array
        * @edit record:
    */
    public byte[] setIntegerToByte(byte[] br, int i) {
       // byte[] res = new byte[br.length];
        String str = new String();
        
        str = setIntegerToString(i);
       // System.out.println("setIntegerToByte:General Binary String:" + str);
        
        str = VerifyMachineString(str, br.length);
        //System.out.println("setIntegerToByte:Get Binary String:" + str);
        
        setStringToByte(br, str,0, br.length - 1);
        //System.out.println("setIntegerToByte:" + str);

        return br;
    }
    
    /**
        * @description set a Byte array into a Integer 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param bt the original byte array
        * @return the object Integer
        * @edit record:
    */
    public int setByteToInteger(byte[] bt) {
        String str = new String();
        int res = 0;
        str = setByteToString(bt);
        res = setStringToInteger(str);
       // System.out.println("setByteToInteger:" + res);      
        return res;
    }
    
    /**
        * @description set a Integer into String 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param i the original integer
        * @return the object String
        * @edit record:
    */
    public String setIntegerToString(int i) {
        String str = new String();
        str = Integer.toBinaryString(i);
       // System.out.println("setIntegerToString:" + str); 
        return str;
    }
    
    /**
        * @description set a String into Integer 
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param str the original string
        * @return the object Integer
        * @edit record:
    */
    public int setStringToInteger(String str) {
        int res = Integer.valueOf(str, 2);
        //System.out.println("setStringToInteger:" + res);        
        return res;
    }
    
    
    /**
        * @description using to get the content of units in computer(as a byte array) by Integter type
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param bt the original byte array
        * @return the object Integer
        * @edit record:
    */
    public int getByteUnits(byte[] bt) {
        int res = setByteToInteger(bt);
       // System.out.println("getByteUnits:" + res);
        return res ;
        
    }
    
    /**
        * @description verify the length of the string, if str.length() < length, fill with 0, on the left. For example, ("101",4) -> "0101"
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param str the original string
        * @param length the length of the string you want to set,
        * @return the object String
        * @edit record:
    */
    public String VerifyMachineString(String str, int length)
    {
        String res = new String();
        int diff = length - str.length();
        //System.out.println("VerifyBinaryString:Current length:" + str.length() + " Object length:"+ length);
        for (int i = 0; i < diff; i++) {
           res = res + "0";
        }
        
        res = res + str;
        //System.out.println("VerifyBinaryString:Current String:" + str + " Object String:" + res);
        return res;
    }
    
    /**
        * @description set the 0-5 position in Words, with the opcode encoded from instruction
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean SetOpcodeInWords(byte[] specificword) {
        //
        String str = new String();
        str = setIntegerToString(opcode);
        str = VerifyMachineString(str, 6);
        specificword = setStringToByte(specificword, str, 0, 5);
        
        return true;
    }
    
    /**
        * @description Check if a string contains char 
        * @author Yitian Huang
        * @version 1.0
        * @since oct 19
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean CheckIsNumberString(String str) {
        char a;
        for ( int i = 0; i < str.length(); i++){
            a = str.charAt(i);
            
            if ( a < 48 || a > 57) {
                return false;
            }
        }     
    
        return true;
    }
    
 
    
        /**
        * @description set the content of src into a specific position(start) into memmory
        * @author Yitian Huang
        * @version 1.0
        * @since Oct 10
        * @param content 
        * @param position the start position you want to set in the storge
        * @return 1-success 0-fail
        * @edit record:
    */
     public boolean setStorge(CommonResources insCommonResources, byte[] content, int position) {
        if (false == insCommonResources.CheckEA(insCommonResources, position, 1)) {
            return false;
        }
        this.insCache.put(insCommonResources, content, position);
       //  this.insCache.setMemory(insCommonResources, content, position);
         return true;
     }
    
    /**
        * @description get the content of the storge in a specific position
        * @author Yitian Huang
        * @version 1.0
        * @since  Oct 10
        * @param position the position
        * @return the content in a byte array[16]
        * @edit record:
    */
    public byte[] getStorge(CommonResources insCommonResources, int position) {
        if (false == insCommonResources.CheckEA(insCommonResources, position, 0)) {
            return null;
        }
        
        byte[] res = this.insCache.get(insCommonResources, position);
        
        //byte[] res = this.insCache.getMemory(insCommonResources,position);
        
        System.out.println("getStorge: Current String:" + setByteToString(res));     
        return res;
    }
    
    /**
        * @description set the units into Zero, unit is a byte array usually.
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param bt the units
        * @return the Zero set units,with the same length
        * @edit record:
    */
    public byte[] SetZero(byte[] bt) {
        for(int i = 0; i < bt.length; i++) {
            bt[i] = 0;
        }
        
        return bt;
    }
    
    public boolean SetCC(int pos, int value){
        if(pos >= 0 && pos <= 3){
            CC[pos] = (byte)value;
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
        * @description insert 0 on the right of original string to a pre-defined length.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param src the original string
        * @param length the pre-defined length
        * @return the filled result string
        * @edit record:
    */
    public String RightInsertWithZero(String src, int length){
        String res = new String();
        int diff = length - src.length();
        //System.out.println("VerifyBinaryString:Current length:" + str.length() + " Object length:"+ length);
        for (int i = 0; i < diff; i++) {
           res = res + "0";
        }
        
        res = src + res;
        //System.out.println("VerifyBinaryString:Current String:" + str + " Object String:" + res);
        return res;
    }
    
    /**
        * @description insert 1 on the left of original string to a pre-defined length.
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param src the original string
        * @param length the pre-defined length
        * @return the filled result string
        * @edit record:
    */
    public String LeftInsertWithOne(String src, int length){
        String res = new String();
        int diff = length - src.length();
        //System.out.println("VerifyBinaryString:Current length:" + str.length() + " Object length:"+ length);
        for (int i = 0; i < diff; i++) {
           res = res + "1";
        }
        
        res = res + src;
        //System.out.println("VerifyBinaryString:Current String:" + str + " Object String:" + res);
        return res;
    }
    

    /**
        * @description convert a byte array to an appropriate integer using two's complement notation
        * @author Zhe Yang
        * @version 1.0
        * @since Oct 4
        * @param bt the object byte array
        * @return the converted integer
        * @edit record:
    */
    public int returnAppropriateInteger(byte [] bt){
        int res = 0;
        //sign bit of the bite array
        byte signBit = bt[0];
        byte[] temp = new byte[bt.length];
        
        LogicOperation insLogicOperation = new LogicOperation();
        
        if(signBit == 0){
            res = setByteToInteger(bt);
        }
        else if(signBit == 1){
            temp = insLogicOperation.returnLogicalNOTByte(bt);
            res = - (setByteToInteger(temp) + 1);
        }
        
        return res;
    }
    
        //0- read 1- write
    public boolean CheckEA (CommonResources insCommonResources, int EA, int type) {
        ErrorControl insErrorControl  = new ErrorControl();  
        //fault 0
        if ((type == 1) && (EA == 40 || EA == 41 || EA == 42 || EA == 44)){
            insCommonResources.GUI.AddContectToPaneForERROR("[CheckEA]Illegal memory address！" + "\n");
            insErrorControl.MachineFaultHandler(insCommonResources, 0);
            return false;
        }

        //fault 3
        if (EA >  (4096) || EA < 0) {
            insCommonResources.GUI.AddContectToPaneForERROR("[CheckEA]Memory overflow！" + "\n");
            insErrorControl.MachineFaultHandler(insCommonResources, 3);
            return false;
        }    
     
  
        
        return true;
    }
    
    //
    public int ConvertFP2INT (byte[] operand){
        byte op_sign = operand[0];
        byte[] op_exponent = GetPartofBytes(operand, 1, 7);
        byte[] op_mantissa = GetPartofBytes(operand, 8, 15);
        
        int res = 0;
        
        int exponent = ConvertByte2Int_Signed(op_exponent);
        int mantissa = setByteToInteger(op_mantissa);
        
        res = mantissa * (int)Math.pow(2, exponent);
        
        if(op_sign == 1) {
            res = res * (-1);
        }
        
        return res;
    }
    
    //
    public byte[] ConvertINT2FP (int operand){
        byte[] res = new byte[16];
        int temp =  operand;
        int exponent = 0;
        int mantissa = 0;
        int flag = 1;
        
        if( operand < 0 ) {
            flag = -1;
            temp = temp * (-1);
        }
        
        while(true) {
            if(temp % 2 == 0) {
                exponent++;
                mantissa = temp / 2;
                temp = mantissa;
            }
            else {
                break;
            }   
            
        }
        
        mantissa = mantissa * flag;
        
        res = setFPresult(exponent, mantissa);
        
        
        
        return res;
    }
    
    //
    public int ConvertByte2Int_Signed (byte[] operand){
        byte[] temp = new byte[operand.length];
        temp = operand;
        int res = 0;
        int sign = 1;
        
        if ( temp[0] == 1) {
            sign = -1;
            temp[0] = 0;
            
            if(setByteToInteger(temp) == 0) {
                return -64;
            }
            
        }
                       
        res = setByteToInteger(operand);
        
        res = res * sign;
        
        return res;
    }

    // 
    public byte[] ConvertInt2Byte_Signed (int operand) {
        return null;
    }
    
    //
    public byte[] ConvertExponent2Byte(int exponent, byte[] word) {
        byte[] res = new byte[16];
        res = word;
        
        if (exponent == -64 ) {
            res[0] = 1;
            return res;
        } 
        
        if ( exponent < 0) {
            res[0] = 1;
            exponent = (-1) * exponent;
        }
        
        String str = new String();
        str = Integer.toBinaryString(exponent);
        str = VerifyMachineString(str, 6);
        
        for (int i = 0; i < 6; i++ ) {
            res[i + 2]  = (byte)Character.getNumericValue(str.charAt(i));
        }
        
        return res;
        
    }
    
    
    //
    public byte[] setFPresult(int exponent, int mantissa) {
        byte[] res = new byte[16];
  
        //check overflow
        while(true) {
            if(((mantissa >= 0)&&( mantissa <= 511))&&((exponent >= -64)&&(exponent <=63))) {
                break;
            }            
            
            if(mantissa > 511) {
                if(exponent + 1 <= 63) {
                    mantissa = mantissa / 2;
                    exponent++;
                }
                else {
                    SetCC(1, 1);
                    GUI.AddContectToPaneForERROR("[ERROR]" + "Overflows!\n");
                }
            }
            
            if(mantissa < -511) {
                if(exponent + 1 <= 63) {
                    mantissa = mantissa / 2;
                    exponent++;
                }
                else {
                    SetCC(2, 1);
                    GUI.AddContectToPaneForERROR("[ERROR]" + "Underflows!\n");
                }
            }
            
        }
        
        //Set S bit
        if( mantissa < 0 ) {
            res[0] = 1;
            mantissa = (-1) * mantissa;
        }
        
        //set exponent
        res = ConvertExponent2Byte(exponent, res);

        //set mantissa
        String str = new String();
        str = Integer.toBinaryString(mantissa);
        str = VerifyMachineString(str, 8);
        
        for (int i = 0; i < 8; i++ ) {
            res[i + 8]  = (byte)Character.getNumericValue(str.charAt(i));
        }
               
        return res;
    }

    //ALU fpr FP
    //TYPE: 0-addr 1-sub
    public byte[] AdderForFP(byte[] operand1, byte[] operand2, int type) {
    
        byte op1_sign = operand1[0];
        byte[] op1_exponent = GetPartofBytes(operand1, 1, 7);
        byte[] op1_mantissa = GetPartofBytes(operand1, 8, 15);
        byte op2_sign = operand2[0];
        byte[] op2_exponent = GetPartofBytes(operand2, 1, 7);
        byte[] op2_mantissa = GetPartofBytes(operand2, 8, 15);
        
        int exponent1 = ConvertByte2Int_Signed(op1_exponent);
        int mantissa1 = setByteToInteger(op1_mantissa);
        int exponent2 = ConvertByte2Int_Signed(op2_exponent);
        int mantissa2 = setByteToInteger(op2_mantissa);
        
        byte[] res = new byte[16];
        
        //signed
        if (op1_sign == 1) {
            mantissa1 = mantissa1 * (-1);
        }
        
        if (op2_sign == 1) {
            mantissa2 = mantissa2 * (-1);
        }
                
        //equal the exponents
        if(exponent1 > exponent2) {
            mantissa1 = mantissa1 * 2 * (exponent1 - exponent2);
            exponent1 = exponent2;
        }
        
        if( exponent2 > exponent1) {
            mantissa2 = mantissa2 * 2 * (exponent2 - exponent1);
            exponent2 = exponent1;
        }
        
        int res_exponent;
        int res_mantissa;
        
        //calculation
        if( type == 0 ) {
            //adder
            res_mantissa = mantissa1 + mantissa2;
        }
        else {
            //sub
            res_mantissa = mantissa1 - mantissa2;
        }
        
 
        
        
        //construct the result
        res = setFPresult(exponent1, res_mantissa);
        
        
        
        return res;
    }
}
