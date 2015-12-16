all: mklibs domain

mklibs: ./src/FicheroNoExiste.java ./src/FicheroYaExistente.java ./src/Gestio_Dades.java
	javac -d ./ ./src/FicheroNoExiste.java ./src/FicheroYaExistente.java
	javac -d ./ -cp ./ ./src/Gestio_Dades.java

domain: java.txt
	javac -cp ./ @java.txt

clean: rm *.class
