import java.io.*;
import java.util.*;
import java.util.StringTokenizer;


// Array (llamar a archivo1) y me devuelva o un objeto o un contenido
// Arregloculiao["archivo1"] => Objeto o un contenido

public class Expand {
	public String[] archivos;
	public Hashtable<String, String> FileArray = new Hashtable<String, String>();

	public Expand (String[] filenames){
		this.archivos=filenames;
	}

	public boolean hasRefference(String file){
		String content = FileArray.get(file);
		StringTokenizer reference = new StringTokenizer(content, " \n");
		boolean refference = false;
		while (reference.hasMoreTokens()){
			String ref = reference.nextToken();
			ref = ref.trim();
			int a = ref.indexOf("<<<");
			int b = ref.indexOf(">>>");
			if (a < b && a >=0 && b >= 0){
				return true;
			}	
		}
		return false;
	}

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

	public void print(){
		if (this.hasRefference(this.archivos[0])){ 
			// tiene referencias y solo imprimimos el primero ??
			System.out.println(this.replaceRefference(this.archivos[0]));
		}
		else{ 
			// Si no tiene referencias la suma de los largos deberia ser menor que el largo del primero
			for (int i=0;i<this.archivos.length ; i++ ) {
				System.out.println(this.replaceRefference(this.archivos[i]));
			}
		}
	}

	public void Expandir() throws FileNotFoundException{
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

		this.print();
	}
	public static void main(String[] args) throws FileNotFoundException {
		Expand a = new Expand(args);
		a.Expandir();
	}
}
