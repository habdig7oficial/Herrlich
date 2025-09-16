compile: 
	javac lib/Stack.java
	kotlinc lib/LinkedList.kt lib/Stack.java Main.kt  
	
run:
	kotlin MainKt

all: compile run 