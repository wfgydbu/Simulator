# CISC Computer Simulator

## First
This is a class project aims to have a deeper understanding of the design, structure and operations of a computer system, principally focusing on the ISA and how it is executed. In addition, we will also focus on memory structure and operations, and simple I/O capabilities.

In simple terms，we build a virtual computer with certain characteristics. We also implement some intrusions and these instructions are executed exactly same as they are executed in the physical machine. Then we use these instructions to write programs with certain functionalities, import them into the virtual computer, and let the virtual computer to execute it, to test if the correct will come up, or not.

## Environment
We develop this simulator with NetBeans, so you can directly import the whole structure into Netbeans, and run it. Other IDEs should work as well with some simple adjustments.
 
## Components
**A clear and detailed document for this project is under /doc.**

### Virtual Units
+ Basic characteristics:
	+ 16-bits words.
	+ Simple ROM contains a simple loader
	+ 2028 words of Memory, expandable to 4096 words.
	+ 2014 words of Cache, 16 cache lines, using FIFO algorithm.
	+ Word addressable.
+ Registers:
	+ 4 General Purpose Registers (GPRs) - each 16 bits
	+ 3 Index registers - 12 bits
	+ Program Counter Register 
	+ Condition Code Register
	+ Instruction Register
	+ Memory Address Register
	+ Memory Buffer Register
	+ Machine Status Register
	+ Machine Fault Register
+ User Interface
+ Operators console

### Supported Instructions
+ **Miscellaneous Instructions**: HLT, TRAP
+ **Load/Store Instructions**: LDR, STR, LDA, LDX, STX
+ **Transfer Instructions**: JZ, JNE, JCC, JMA, JSR, RFS, SOB, JGE
+ **Arithmetic and Logical Instructions**: AMR, SMR, AIR, SIR, MLT, DVD, TRR, AND, ORR, NOT
+ **Shift/Rotate Operations**: SRC, RRC
+ **I/O Operations**: IN, OUT, CHK
+ **Floating Point Instructions**: FADD, FSUB, CNVRT, LDFR, STFR
+ **Vector Operations**: not supported.

Detailed description of these instructions can be found in the document.

### Test Programs 
We have two test programs, both of them are written by the instructions listed above, you can find their code in */test_program*

**Program 1**: A program that reads 20 numbers (integers) from the keyboard, prints the numbers to the console printer, requests a number from the user, and searches the 20 numbers read in for the number closest to the number entered by the user. Print the number entered by the user and the number closest to that number. Your numbers should not be 1…10, but distributed over the range of 0 … 65,535. Therefore, as you read a character in, you need to check it is a digit, convert it to a number, and assemble the integer.

**Program 2**: A program that reads a set of a paragraph of 6 sentences from a file into memory. It prints the sentences on the console printer. It then asks the user for a word. It searches the paragraph to see if it contains the word. If so, it prints out the word, the sentence number, and the word number in the sentence.

We also provide two simple programs for testing overflow and machine fault.

## Cooperator
Zhe yang


