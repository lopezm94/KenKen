all: mklibs else

mklibs: ./src/FicheroNoExiste.java ./src/FicheroYaExistente.java ./src/Gestio_Dades.java
	javac -d ./ ./src/FicheroNoExiste.java ./src/FicheroYaExistente.java
	javac -d ./ -cp ./ ./src/Gestio_Dades.java

else: *.java
	javac -cp ./ *.java

clean:
	rm *.class
	rm -rf Excepcions Persistencia
