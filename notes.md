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
+ inheritance
  + can receive members from the parent class in child class
  + extends key word
  + "is-a" rule -- should always be able to say child class "is a" parent class
  + every class extends object (directly or indirectly)
  + all instance vars, all non-private, non-static methods inherited
+ method overriding
  + methods in parent should do something different in child - do with same signature in child
  + can't make access model more restrictive
  + if want to call the overidden method -- use super
  + @Override -- not actually required, but prevents potential bugs
+ implementing a hashcode method
  + represents an object as an integer
  + hashcode method invoked on same/equivalent object should produce the same result
  + its not required that two unequal objects (according to equals method) produce distinct hash codes
  + standard method = convert instance vars to integers and add together
  + multiply by 31 (prime number) to avoid hash collisions
+ method overloading
  + reuse a method name with different argument list
  + lets you reuse name for conceptually the same thing, but different data types
+ final keyword
  + create constants and non virtual methods
  + thing declared cannot be changed
  + for reference variables, like an array list, the reference can't change, but the array list can
  + methods cannot be overwritten if they are final
+ "this" reference
  + all objects have, can refer to itself
  + like "self"
  + sometimes the compiler can infer it so it doesn't need to be written
+ enums
  + like a class
  + use in place of unrestricted string
  + make it a finite set of values that can be passed in
+ object oriented design
  + decompose a program into classes
  + separate class for each concept in application domain
  + identify relationships between classes
  + "has-a"/"uses-a" relationships with references
  + keep data private - not all fields need getters and setters
  + static methods as an exception not a rule
  + static vars, instance vars, main method, constructors, methods - grouped by functionality not accessibility (in that order)


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


PROGRAMMING EXAM
+ all info is on the schedule
+ look at practice programming exam


RECORDS AND EXCEPTIONS
+ java records
  + data objects - carry data between parts of a program
  + even a simple data object class declaration can easily 50+ lines
    + most of the code is boilerplate/repetitive and easy to get wrong
  + a record can do the same thing with less code
    + automatically includes the immutable fields, getters, equals, hash, toString, and constructor
    + compact way to define data objects
  + records don't have setters and cannot be modified (if you think you're changing it youre actually making a new one)
  + getters use format t.varName() (not t.getVarName())
  + can have other methods but are more for data manipulation rather than behavior
    + derived values
    + formatting
    + updated copy operations (return a new record)
  + good when class is mostly data, want immutability, equality defined by value equivalence
  + bad when object has lots of mutable state, complex life cycle/behavior heavy logic, identity shouldn't be based on all fields
+ exceptions
  + abnormal conditions that occur in a java class
  + not necessarily errors
  + allows you to separate normal processing logic from abnormal processing logic
  + command B - takes you to the reference of something
  + try/catch blocks
    + catching but not resolving is !!bad!! practice - you should handle it
    + you can have multiple catch blocks connected to one try block (each catch block catches a particular type of exception)
    + multi catch -- basically an or in the catch block 'declaration'
  + checked exception --> won't let your code compile until you put a try/catch (handle it) or throw (declare it)
  + unchecked exception --> code will still compile
  + finally block -- regardless of what happens you run this section of code
  + try with resources
    + in declaration of try block, can specify if you want to open file
    + automatically creates finally for you so that if you have an exception those files get closed
  + rethrowing an exception
    + when you catch it, just throw it instead of handling it (it'll go back to wherever that method was called)
  + overridden method exception
    + can throw fewer or the same exceptions, but cannot throw more than the base class
    + throws same or narrower exceptions


INTERFACES AND ABSTRACT CLASSES
+ POLYMORPHISM
  + many forms -- an object can take on many forms
  + each object can take on the form of its parents classes
  + heterogeneous collections 
    + collection of parent class type so you can put multiple different types of children in it
  + polymorphic parameters
    + parameter of parent class type to method
+ ABSTRACT CLASSES
  + don't give a method a body -- so now you have to override it in the subclasses
  + basically this class has a behavior that will be defined in the subclass
  + cannot be instantiated
  + can be used as reference types and array types and can have non-abstract methods
+ INTERFACES
  + allow objects to take on different forms when not related through inheritance
  + have all abstract methods
  + cannot be instantiated
  + can be used as reference types, collection types
  + can NOT have non-abstract methods
  + all methods are public
  + breaks inheritance barrier of polymorphism
  + can have constant variables -- all are public static and final
+ CREATING AN INTERFACE
  + use interface instead of class
  + create methods but without bodies
  + no single inheritance limit with interfaces
+ IMPLEMENTING AN INTERFACE
  + use implements keyword
  + can implement multiple at same time, just use a comma -- make sure you implement all the methods of both
  + 
  




