# my notes
JAVA BACKGROUND
+ similar syntax to c++ but some differences in semantics such as:
  + built in garbage collection
  + refs instead of pointers (won't let you access memory you shouldn't)
  + data types always the same size
  + specific bool data type and language constructs made to us it
  + classes dynamically linked at runtime (no separate link step)
  + java is a hybrid, compiled/interpreted language
+ install jdk from oracle's website + latest version of java!!!
+ java is compiled (which results in java byte code -- can run on any operating system) and then that is interpreted on the machine by a jvm
+ hotspot virtual machine -- dynamic recompilation at runtime (ex. for loops so that it can recompile and make it faster)

JAVA BASICS
+ Myclass.java = source file
  + generally there is one class per .java file (very few exceptions)
  + the file name must match the class name
+ all code needs to be in a class
+ Myclass.class = executable file (executable by the JVM)
  + created when compiled
+ main method
  + public static void main(String[] args) {in java arrays are objects that know how big they are, so you don't need the length as an argument}
  + public static void main(String...args) {newer way to write: can call main with comma separated list of arguments}
+ creating java classes
  + public class SimpleJavaClass {
        public static void main(String [] args) {
            System.out.println("Hello BYU!");
        }
  }
  + classes don't necessarily need to have a main method -- usually used by a class that does 
+ to compile: javac SimpleJavaClass.java
  + produces SimpleJavaClass.class
  + for now, you must be in the directory that contains the .java file
+ to run: java SimpleJavaClass
  + for now, you must be in the directory that contains the .class file
+ Javadoc
  + documentation for the Java class library -- generated from code and Javadoc comments in the code
  + download from Sun's website: google search Java 25 Api
  + javadoc comment: /** ... */
    + first sentence should summarize the whole thing, and the rest is the details
  + you can have this up in your browser during the exam
+ PRIMITIVE DATA TYPES
  + 8 types: byte, short, int, long, float, double, char, boolean
  + byte - long are all integers, they are just different sizes
  + float, double are decimals - double is the default 
    + x = 10L; (makes this a long)
  + char - single character, uses single quotes!! (double quotes specify a string)
    + rounding - % .2f
  + bools only True or False - numbers don't have truth vals
+ STRINGS
  + for every primitive there is a corresponding class -- to convert between data types
    + ex. int Integer.parseInt(String value)
  + creating a string (they are immutable)
    + String s = "Hello";
    + String s = new String("Hello"); -- this is inefficient
  + string formatting
    + String.format("%s %s", s1, s2);
  + concatenation is pretty inefficient, so avoid in loops
    + to do efficiently, use a StringBuilder
+ ARRAYS
  + have .length
  + two ways to iterate an array
    + for loop like in c++
    + for(int value : intArray) {} (for each loop)
  + you can print a backspace! "\b"
+ ARRAYS OF ARRAYS
+ argument list does not contain the name of the program


PHASE 0 - CHESS MOVES
+ some pieces can jump over your own pieces, some can't -- pay attention
+ don't repeat code obviously -- write some methods that you can call
  + maybe methods for each direction that can move (pass in how much to move?)
+ take calculation of how a piece moves and put it in separate class
  + move calculator that has a common parent class
  + calculator subclass for each piece 
+ create an interface/abstract class/base class [pieceMovesCalculator]
  + create a set of classes who calculate piece movements
  + 

