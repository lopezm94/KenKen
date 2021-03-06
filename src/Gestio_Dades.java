package Persistencia;

import Excepcions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Classe creada per Jan Manrique Roca
 */

public class Gestio_Dades {


    /* Crea un nuevo directorio en el directorio pasado como parametro "directorio"
     con el nombre pasado por el parametro "nombre", siempre y cuando el directorio "nombre"
      no exista ya en la ruta parametro.. En caso de no ser directorio una ruta correcta
      esta sera creada.*/
    public static void Crear_directorio(String nombre, String directorio) throws FicheroYaExistente, IOException {

        File carpeta = new File(directorio + "/" + nombre);

        if (!carpeta.exists()) {
            boolean creado = carpeta.mkdirs();
            if (!creado) throw new IOException("No se pudo crear el directorio.");
        }
        else throw new FicheroYaExistente("Ya existe un directorio llamado asi.");

    }


    /* Borra el directorio cuyo nombre es pasado por el parametro "nombre" del directorio
       pasado por el parametro "directorio", siempre y cuando el directorio "nombre" exista
        y "directorio" sea una ruta correcta. Si el directorio tiene ficheros dentro
        estos se borraran tambien.*/
    public static void Borrar_directorio(String nombre, String directorio) throws IOException, FicheroNoExiste {

        File carpeta = new File(directorio + "/" + nombre);

        if (!carpeta.exists())
            throw new FicheroNoExiste("No se ha encontrado un directorio con ese nombre en la ruta indicada");
        else delete(carpeta);

    }


    /* Modifica el nombre del directorio "nombre_viejo" situado en el directorio
    "directorio", pasando este a ser "nombre_nuevo". Es necesario que exista el directorio
     "nombre_viejo" y que no exista el "nombre_nuevo". "directorio" debe ser una ruta
     correcta. */
    public static void Modificar_nombre_directorio(String nombre_nuevo, String nombre_viejo, String directorio)
        throws FicheroNoExiste, FicheroYaExistente, IOException {

        File f1 = new File(directorio + "/" + nombre_viejo);
        File f2 = new File(directorio + "/" + nombre_nuevo);

        if (!f1.exists())
            throw new FicheroNoExiste("No se ha encontrado un directorio con ese nombre en la ruta indicada");
        else if (f2.exists())
            throw new FicheroYaExistente("Ya existe un directorio con el nombre deseado.");
        else {
            boolean renamed = f1.renameTo(f2);
            if (!renamed) throw new IOException("No se pudo cambiar el nombre del directorio.");
        }

    }


    /* Crea un nuevo archivo con el nombre "nombre" y de tipo .txt en el directorio
     "directorio". "directorio" debe ser una ruta correcta, y el archivo solo se creara
     si no hay ningun otro archivo llamado asi. */
    public static void Crear_archivo(String nombre, String directorio) throws IOException, FicheroYaExistente {
    //TODO --> CRIDA IOException si no troba la ruta especificada.
        File archivo = new File(directorio + "/" + nombre + ".txt");

        if (!archivo.exists()) {
            boolean creado = archivo.createNewFile();
            if (!creado) throw new IOException("No se pudo crear el archivo.");
        }
        else throw new FicheroYaExistente("Ya existe un archivo con ese nombre.");

    }


    /* Borra el archivo "nombre" de extension .txt del directorio "directorio", siempre y cuando
    "directorio" sea una ruta valida y el archivo "nombre".txt exista.
     */
    public static void Borrar_archivo(String nombre, String directorio) throws FicheroNoExiste, IOException {

        File archivo = new File(directorio + "/" + nombre + ".txt");

        if (!archivo.exists())
            throw new FicheroNoExiste("No existe ningun archivo con ese nombre en el directorio especificado.");
        else {
            boolean borrado = archivo.delete();
            if (!borrado) throw new IOException("No se pudo borrar el fichero.");
        }

    }


    /* Modifica el nombre del fichero "nombre_viejo".txt del directorio "directorio",
    cambiandolo a "nombre_nuevo".txt. Es necesario que "directorio" sea una ruta valida,
    "nombre_viejo".txt exista y "nombre_nuevo".txt no exista. */
    public static void Modificar_nombre_archivo(String nombre_nuevo, String nombre_viejo, String directorio)
        throws FicheroNoExiste, FicheroYaExistente, IOException {

        File f1 = new File(directorio + "/" + nombre_viejo + ".txt");
        File f2 = new File(directorio + "/" + nombre_nuevo + ".txt");

        if (!f1.exists())
            throw new FicheroNoExiste("No existe ningun archivo con ese nombre en el directorio especificado");
        else if (f2.exists())
            throw new FicheroYaExistente("Ya existe un archivo con ese nombre en el directorio indicado.");
        else {
            boolean renombrado = f1.renameTo(f2);
            if (!renombrado) throw new IOException("No se pudo renombrar el fichero.");
        }

    }


    /* Escribe al final del archivo "nombre_archivo".txt del directorio "directorio" el string "contenido"
    seguido del string "separador" siempre y cuando "nombre_archivo".txt exista y "directorio" sea una ruta valida.
    Es recomendable usar "\n" como separador. Este se usa introduciendo como separador la palabra "salto". */
    public static void Escribir_string(String contenido, String separador, String nombre_archivo, String directorio)
            throws IOException, FicheroNoExiste {

        File archivo = new File(directorio + "/" + nombre_archivo + ".txt");

        if (!archivo.exists())
            throw new FicheroNoExiste("No existe ningun fichero con ese nombre en el directorio indicado.");
        else {
            FileWriter lecturaArchivo = new FileWriter(directorio + "/" + nombre_archivo + ".txt", true);
            if (separador.equals("salto")) separador = "\n";
            lecturaArchivo.write(contenido + separador);
            lecturaArchivo.close();
        }

    }


    /* Lee del archivo "nombre_archivo".txt del directorio "directorio" toda la informacion que hay entre el string
        "separador" numero n_veces y el n_veces+1. El archivo "nombre_archivo".txt debe existir y "directorio"
        debe ser una ruta valida. La primera linea de un fichero es la numero 0. Es recomendable usar
        "\n" como separador. Este se usa introduciendo como separador la palabra "salto". */
    public static String Leer_string(String nombre_archivo, String directorio, String separador, int n_veces)
            throws IOException, FicheroNoExiste {
    //TODO tira NoSuchElementException si en la primera linea no esta el separador indicado.
        File archivo = new File(directorio + "/" + nombre_archivo + ".txt");


        if (!archivo.exists())
            throw new FicheroNoExiste("No existe un fichero con ese nombre en el directorio indicado.");
        else if (separador.equals("salto") || separador.equals("\n")) {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String line = br.readLine();
            for (int i = 0; i < n_veces; ++i) {
                line = br.readLine();
            }
            br.close();
            return line;
        } else {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String line = br.readLine();
            StringTokenizer st = new StringTokenizer(line, separador);
            while (n_veces > 0) {
                st.nextToken();
                --n_veces;
            }
            br.close();
            return st.nextToken();
        }

    }


    /* Modifica un string del fichero "nombre_archivo".txt del directorio "directorio", cambiando el contenido de la
 linea numero "linea" por el del string "infoNueva". El fichero debe existir. La primera linea de un fichero
 es la numero 0. Debe haber informacion previamente en la linea a modificar. La primera linea tiene que contener
 el elemento separador */
    public static void modificarString(String nombre_archivo, String directorio, int linea, String infoNueva,
                                       String separador) throws IOException, FicheroNoExiste, FicheroYaExistente {

        File archivo = new File(directorio + "/" + nombre_archivo + ".txt");

        if (archivo.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String s = br.readLine();
            int i = 0;
            String c = nombre_archivo + "2";
            Crear_archivo(c, directorio);

            if (separador.equals("salto") || separador.equals("\n")) {

                while (s != null) {
                    if (i == linea) s = infoNueva;
                    Escribir_string(s, "\n", c, directorio);
                    s = br.readLine();
                    ++i;
                }

            } else {

                StringTokenizer st = new StringTokenizer(s, separador);
                while (st.hasMoreTokens()) {
                    String info = st.nextToken();
                    if (i == linea) info = infoNueva;
                    Escribir_string(info, separador, c, directorio);
                    ++i;
                }

            }

            br.close();
            Borrar_archivo(nombre_archivo, directorio);
            Modificar_nombre_archivo(nombre_archivo, c, directorio);

        } else throw new FicheroNoExiste("No existe ningun archivo con ese nombre en el directorio indicado.");

    }


    //Funcion privada para el borrado de ficheros. Se encarga de borrar de manera recursiva todos los ficheros
    // de un directorio.
    private static void delete(File f) throws IOException, FicheroNoExiste {
        if (f.exists()) {
            if (f.isDirectory()) {
                for (File c : f.listFiles())
                    delete(c);
            }
            boolean borrado = f.delete();
            if (!borrado) throw new IOException("No se pudo borrar el directorio.");
        } else throw new FicheroNoExiste("Ese directorio no existe.");
    }


}
