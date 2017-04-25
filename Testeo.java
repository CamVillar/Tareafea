import java.io.*;
import java.util.*;
import java.util.StringTokenizer;


// Array (llamar a archivo1) y me devuelva o un objeto o un contenido
// Arregloculiao["archivo1"] => Objeto o un contenido

public class Testeo {
	public String[] archivos;
	public Hashtable<String, String> FileArray = new Hashtable<String, String>();

	public String replaceRefference(String file){
		try{
			String content = FileArray.get(file);
			StringTokenizer reference = new StringTokenizer(content, " \n");
			while (reference.hasMoreTokens()){
				String ref = reference.nextToken();
				ref = ref.trim();
				int a = ref.indexOf("<<<");
				int b = ref.indexOf(">>>");
				if (a < b && a >=0 && b >= 0){
					String archivo_nuevo = ref.substring(a+3,b);
					// System.out.println(archivo_nuevo);
					String meme = this.replaceRefference(archivo_nuevo);
					content = content.replace(ref, meme);
				}	
			}
			return content;	
		}
		catch(StackOverflowError e){
			System.out.println( "Referencia Ciclica");
			System.exit(0);
			return "0";

		}
		

	}

	public void Expand() throws FileNotFoundException{
		for (int i=0; i<this.archivos.length; i++) {
			String content = "";
			// Abrir Archivo(cómo lo abro?)
			String text = new Scanner(new File(this.archivos[i])).useDelimiter("\\Z").next();
			StringTokenizer linetext = new StringTokenizer(text, "\n");
			
			String line = linetext.nextToken();
			content = content +line.trim();
			while (linetext.hasMoreTokens()) {
				line = linetext.nextToken();
				content = content + "\n" +line.trim();
			}			
			FileArray.put(archivos[i],content);
		}

		// for (int i=0; i<this.archivos.length; i++){
			System.out.println(this.replaceRefference(this.archivos[0]));
		// }
		
		// String text = "esto es un texto con <<<referencia>>> \n esto es otro texto con otra <<<reference>>>";
		
			
			
		// }
	}
	public static void main(String[] args) throws FileNotFoundException {
		Testeo a = new Testeo();
		a.archivos = args;
		a.Expand();
	}
}


class Archivo {
	public String filename;
	public String content;
}