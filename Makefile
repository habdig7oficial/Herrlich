libs:
	javac lib/Stack.java lib/Queue.java

compile: libs
	kotlinc lib/LinkedList.kt lib/HashMap.kt lib/Stack.java models/Symbol.kt models/Command.kt Main.kt   

generate_jar: libs
	kotlinc lib/LinkedList.kt lib/HashMap.kt lib/Stack.java models/Symbol.kt models/Command.kt Main.kt -include-runtime -d Herrlich.jar
	
run: compile
	kotlin MainKt

jar: generate_jar 
	java -jar Herrlich.jar

