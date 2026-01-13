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

PHASE 0 - CHESS MOVES
+ 
