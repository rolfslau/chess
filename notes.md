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
+ PACKAGES IMPORTS AND CLASSPATH
  + packages - provide a way to organize classes into logical groups (denoted by a statement at the top of a file)
  + package structure needs to match folder structure
  + package becomes part of class name
  + imports allow you to shorten the name so you don't have to type the whole thing every time
  + "*" imports all classes in the package but not subpackages
  + you don't need to import java.lang or classes in your same package
  + classpath - starting point for all of your packages - colon separated
  + classpath is basically all the places your program will look for things


PHASE 0 - CHESS MOVES
+ some pieces can jump over your own pieces, some can't -- pay attention
+ don't repeat code obviously -- write some methods that you can call
  + maybe methods for each direction that can move (pass in how much to move?)
+ take calculation of how a piece moves and put it in separate class
  + move calculator that has a common parent class
  + calculator subclass for each piece 
+ create an interface/abstract class/base class [pieceMovesCalculator]
  + create a set of classes who calculate piece movements


CLASSES AND OBJECTS:
+ classes - compile time things, objects - runtime things
+ objects are created with the 'new' keyword
+ references are places memories that hold memory addresses - tell you where an object is in memory
+ objects are allocated on the heap, references on the stack 
+ instance variables - each object gets its own copy of all the instance vars in the class
+ static variables - associated with the class not with instances (ex: MATH.pi)
+ instance methods - associated with a specific instance/object (invoked fom a reference to that instance)
+ static methods - associated with a class (not an instance) (ex: MATH.pow() doesn't require an instance)

ACCESSORS AND MUTATORS
+ (getter and setter)
+ accessor - get val, mutator - set val
+ when you create a class, make instance vars private so only instances can access them

CONSTRUCTORS
+ constructor name must match class name
+ like a method without a return type
+ classes can have multiple constructors 
  + you might have multiple different requirements that still constitute an instance
+ every constructor calls another constructor

