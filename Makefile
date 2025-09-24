libs:
	javac lib/Stack.java lib/Queue.java

compile: libs
	kotlinc lib/LinkedList.kt lib/HashMap.kt lib/Stack.java models/Symbol.kt models/Command.kt internals/Interpreter.kt internals/Repl.kt Main.kt  

run: compile
	kotlin MainKt



