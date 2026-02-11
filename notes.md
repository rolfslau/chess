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


COLLECTIONS
+ when regular arrays [] don't suffice
+ can only store objects not primitives
+ program to the interface -- reference type should be to the interface (ex: Collection<object> = new ArrayList(); )
  + what you hide you can change
+ list interface
  + sequence of elements accessed by index (order matters) - get, set
  + ArrayList class (resizable array implementation)
  + LinkedList class (doubly linked list implementation)
  + support more powerful iterator called ListIterator
+ Set interface
  + collection that contains no duplicates - order doesn't matter tho so no indexing
  + add, contains, remove
  + HashSet class (hash table implementation)
  + TreeSet class (bst implementation)
  + LinkedHashSet class (hash table + linked list impl)
+ Queue interface
  + designed for holding elements prior to processing
  + add, peek (returns it but doesn't remove it from queue), remove
  + ArrayDeque (FIFO, resizable array implementation)
  + LinkedList class (FIFO, linked list impl) [can implement as list or queue]
  + PriorityQueue (binary heap impl)
+ Deque interface
  + supports insertion and removal at both ends
  + addFirst, addLast, peekFirst, peekLast, removeFirst, removeLast
  + ArrayDeque (resizable array impl)
  + Linked list 
+ Stack class
  + deprecated
  + if you need stack, use a Deque
    + push -> Deque.addFirst
    + pop -> Deque.removeFirst
    + peek -> Deque.peekFirst
+ Map interface
  + basically a dictionary - maps keys to vals
  + keys are unique
  + put (k, v), get(k), contains(k), remove(k), keySet(), values(), entrySet()
  + HashMap (hash table implementation)
  + TreeMap (bst implementation)
  + LinkedHashMap (hash table + linked list impl)
+ Iterable interface
  + all collections (not maps - can do map.keys/.values etc) implement this
  + allows them to work with "for each" loops
+ equality checking
  + by default, checks if exact same object (like the same place in memory)
  + override to see if the values are the same
+ hashing based collections
  + by default simply returns object's address
  + override to be based on value rather than identity
  + equals and hashcode should be based on the same thing (ie values or reference) and should use same fields in calcs
  + can't change information in objects that are used as keys in data structures (remove and re insert)
+ Sorted Collections
  + TreeSet, TreeMap, PriorityQueue
  + elements must be sortable, be able to compare elements and determine < > or ==
  + Comparable interface (can implement in any obj, obj becomes sortable) and Comparator interface (objs that aren't comparable, 
     you specify algorithm to compare them)
  + can't change information in objects that are used as keys in data structures (remove and re insert)


COPYING OBJECTS
+ it is common to need to make a copy of an existing object 
  + support "undo" capability in a program
+ two ways
  + shallow copy -- copy the variable values from the original object to the copy (usually not what you want)
    + basically a copy of a reference of something, so if you change something it changes in both places
  + deep copy -- copy the object and all objects it references, recursively
  + if the thing you copy is mutable - use deep copy
+ safe to shallow copy strings, ints, bools, doubles etc (object versions of the primitives)
+ writing classes that support copying
  + clone method
  + copy constructors


INNER CLASSES/NESTED CLASSES
+ private class inside of another class
+ static class = simplest class that can be nested - doesn't have access to instance variables of outer class
+ can make package less messy (for example putting the iterator for each type of collection inside it)
+ can only have one top level non-static class per file
+ inside but not static
  + associated with instance that it is declared inside of - can access containing class's vars and methods
+ declare things as close as possible to where you use them
+ you can declare inside of the method where it is needed
  + doesn't need "private" or "public"
  + doesn't need a constructor now bc can access local vars of method
  + local inner classes cannot access all local vars, only ones marked as final or 'effectively final' (they don't change)
+ anonymous inner class (doesn't have a name)
  + declare class inside of a statement
    + ex: return  new Iterator() { declare a class here that implements iterator interface }


PHASE 1
+ have to move king out of check if he is in check
  + move king out of check, capture threat piece, block threat piece
+ pare down all moves to ones that are currently valid
+ staleMate - no legal moves but king not in check
+ check
  + is king's position in a list of valid moves of other team
  + iterate over board
+ validMoves
  + moves that don't put you in check, or that get you out of it
  + simulate another board where move happens and then call "isInCheck"
    + make a copy of the board (make board clonable)
    + on every iteration clone your board
    + make the move on cloned board
      + find every one of your pieces and all their possible moves
      + check each move to see if after that move you're in check
+ makeMove
  + call valid moves first, if move they are trying to make isn't in there, throw error
  + invalid if not valid for that piece, or not that team's turn
+ check mate
  + no valid moves
  + call valid moves and if empty then it's true
  + also check to make sure it's your turn, and you're already in check
+ extra credit moves: castling
  + king and rook move at the same time
  + king moves 2 to left or right (toward rook) and rook goes on other side
  + only if neither have moved since start of game, no pieces between them, king is never in check
+ extra credit moves: en passant
  + opponent double moves pawn so its right next to you
  + your pawn moves to where you would have captured them - you still capture


DESIGN PRINCIPLES
+ goals
  + create systems that work, satisfy customers, easy to understand/debug/maintain, hold up well under changes
+ design is inherently iterative
  + design implement test repeat
  + interweave design and implementation activities in relatively short iterations
+ abstraction
  + primary tool to deal with complexity
  + abstractions are classes in OOP
  + sometimes correspond to things in real world sometimes not (ex a chess move isn't something physical)
  + remember you can't fully represent real world things - just focus on what is relevant
+ good naming
  + should convey function or purpose
  + Class and variable names are usually nouns
  + Method names are usually verbs
+ Single-Responsibility Principle
  + Each class/method should have a single responsibility
  + each class should represent one well defined concept
  + each method should perform one well defined task
  + some methods should delegate if they need to perform multiple tasks
+ Decomposition
  + large problems divided into smaller sub-problems
  

STREAMS AND FILES (I/O OVERVIEW)
+ streams - read or write a file sequentially
  + primary way of working with data
  + writing to / reading data from files
  + binary formatted or text formatted
  + InputStream and OutputStream - reading/writing bytes/binary-formatted data
    + you can attach streams to each other; each one changes the data (Filter Input/Output streams)
    + every input stream has a corresponding output stream
  + Reader and Writer - reading/writing characters/text-formatted data [interfaces]
    + PrintWriter -- lets you write text formatted things (String, Bool etc)
    + InputStreamReader/OutputStreamReader - convert from in/output stream to a reader/writer
  + DataOutputStream class lets you write binary-formatted data values
  + DataInputStream class lets you read binary-formatted data values (float, int, bool etc)
+ scanner class - tokenize stream input
  + default delimiter is whitespace (to create the tokens)
+ files class - read, copy, etc whole files
  + need a path first
+ randomAccessFile class - use a file pointer to read and write from random parts of the file
  + the pointer represents the current location in the file (similar to array index)
  + seek(long) (from origin go this many bytes in) or skipBytes(int) (wherever you are move this many bytes) to move the file pointer
+ File Class
  + represents a file in the system
  + used to represent, create, or delete a file, but not to read one


JSON
+ curly brace represents objects
+ square brackets represent arrays
+ types of values: string, integer, double, other object
+ objects in an array do not have to have the same type
+ only one object at the very top level (so that it is a tree)
+ create POJOs that match the structure of a json doc


SERIALIZER
+ takes a tree and then returns java objects from the tree
+ use gson


PHASE 2
+ create a set of diagrams 
+ make sure you understand the phase 3 spec
+ sequence diagram
+ our client is terminal based
+ chess server
  + server - receives network requests
  + handlers - deserialize information into java objects
  + services - process business logic for the application
  + DataAccess - provide methods that persistently store and retrieve the application data
+ read entire phase 2 spec, then read phase 3 spec in detail, and then do the assignment
+ create a diagram for each end point (urls with code attached)


PHASE 3
+ you can group related servies into the same class (ex: login/out and register)
  + create game, join game, list games
+ make a parent response class that the other classes inherit so you don't have to write it seven times


GENERICS
+ allow you to specify placeholder class whose actual type is determined when instance is created
+ to instantiate, use <> and put what those objects will be
+ using generic classes
  + specify types in extends clause
  + you can pass generic types from a generic subclass (ex. you specify one type, and make one generic)
+ generic interfaces
  + when you implement, you need to specify what the type will be
+ generic type wildcards
  + List<? super T> -- list has objects which can be type T or any of its parent classes
  + can also do extends and it would work with any of its subclasses?

LAMBDA EXPRESSIONS
+ anything you can do with a variable, you can do with a lambda
+ block of code that can be stored in variable, passed as parameters and executed later
+ like a function without actually declaring a function
+ how they work
  + at runtime infers data type for lambda, return type, and parameter types
  + constructs in mem instance of a class expected by called method
  + part before arrow names parameters
+ what is the data type of lambda expression?
  + functional interface - interface with exactly one abstract method (can have any number of 
  static default and redeclared object methods)
+ syntax
  + (Parameter List) -> Body
  + don't need to specify data types in parameter list
  + () if no parameters
  + the return is inferred for single expression
  + can have a single line or a block
+ function/lambda variables
+ creating APIs with lambdas
+ method references
  + even shorter than lambdas
  + system.out::println
  + not a method call
  + don't give a parameter list
  + 