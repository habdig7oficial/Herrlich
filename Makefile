compile: 
	javac lib/Stack.java lib/Queue.java
	kotlinc lib/LinkedList.kt lib/HashMap.kt lib/Stack.java Main.kt  
	
run:
	kotlin MainKt

all: compile run 