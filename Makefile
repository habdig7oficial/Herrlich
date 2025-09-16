compile: 
	kotlinc lib/LinkedList.kt lib/Stack.kt Main.kt  
	
run:
	kotlin MainKt

all: compile run 