package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import model.Ville;

public class ServiceVilles implements IServiceVille{
	Path source;
	public ServiceVilles(Path path) {
		source=path;
	}
	@Override
	public List<Ville> lireBDVilles_IO() {
		//creation de la liste qui va contenir les villes
		List<Ville> villes=new ArrayList<>();
		//creation de la liste lignes de type string qui va contenir chaque ligne du fichier source
		List<String> lignes=new ArrayList<>();
		
		try {
			//creation du FileInputStream pour accéder au contenu du fichier source
			FileInputStream is=new FileInputStream(new File(source.toString()));
			//creation de la chaine de caractere ch pour stocker chaque caractere du fichier source
			String ch="";
			int b=0;
			//bouclage jusqu'à fin du fichier source
			while((b=is.read())!=-1) {
				//tester s'il y a un retour a la ligne
				if(b == 10) {
					//stocker la chaine ch dans la liste lignes
					lignes.add(ch);
					//vider le contenu de la chaine ch pour stocker les lignes suivantes
					ch="";
				}else {
					//s'il y a pas un retour à la ligne on stock caractere par caractere le contenu de chaque ligne
					ch+=(char)b;
				}
			}
			//on boucle sur la liste des lignes
			lignes.forEach(l->{
				//on teste si la ligne pas par Id (premiere ligne dont on n'a besoin)
				if(!l.startsWith("Id")) {
					//on crée un StringTokenizer dont la fonction est compatible a la fonction split(l,"\t")
					StringTokenizer s=new StringTokenizer(l,"\t");
					//on stocke le premier element dans une variable id
					int id=Integer.parseInt(s.nextToken());
					//on stocke le deuxieme elemenent dans une variable nom
					String nom=s.nextToken();
					//on stocke le troixieme element dans une variable population
					int population=Integer.parseInt(s.nextToken());
					//on stocke le dernier element dans une variale region
					String region=s.nextToken();
					//on crée une nouvelle instance de Ville a à l'aide du constructeur
					Ville v=new Ville(id,nom,population,region);
					//on ajoute la Ville v dans la liste des villes créée au début de la fonction
					villes.add(v);
				}});
			//on ferme le FileInputStream
			is.close();
			//on gère s'il y a des exceptions de type IOException
		} catch (IOException e) {
			e.printStackTrace();
		}
		//on retourne la liste villes (type de retour list de Ville)
		return villes;
	}
	@Override
	public List<Ville> lireBDVilles_NIO() {
		//creation de la liste villes de type Ville
		List<Ville> villes=new ArrayList<>();
		try {
			//creation de la liste lignes dont on va stocker toutes les lignes du fichier source
			List<String> lignes=Files.readAllLines(source);
			//on boucle sur chaque element de la liste lignes(chaque ligne)
			lignes.forEach(l->{
				//on teste si la ligne ne commence pas par Id
				if(!l.startsWith("Id")) {
					//on crée un StringTokenizer
					StringTokenizer s=new StringTokenizer(l,"\t");
					//on stocke chaque element délimité par \t dans une variable correspondante
					int id=Integer.parseInt(s.nextToken());
					String nom=s.nextToken();
					int population=Integer.parseInt(s.nextToken());
					String region=s.nextToken();
					//on crée une Ville v avec les variables collectées à l'aide de StringTokenizer et à l'aide du constructeur
					Ville v=new Ville(id,nom,population,region);
					//on ajoute la ville à la liste des villes
					villes.add(v);
				}});
			//on gere les exceptions
		} catch (IOException e) {
			e.printStackTrace();
		}
		//on retourne la liste des villes
		return villes;
	}
	@Override
	public List<Ville> lireBDVilles_Scanner() {
		//création de la liste villes de type Ville
		List<Ville> villes=new ArrayList<>();
		try {
			//on instancie un scanner dans la source est le fichier source
			Scanner scan=new Scanner(source);
			//on crée une liste lignes
			List<String> lignes=new ArrayList<>();
			//on boucle jusqu'à ce qu'il y a plus d'éléments lu par le scanner
			while(scan.hasNextLine()) {
				//on ajoute chaque ligne dans la liste lignes
				lignes.add(scan.nextLine());
			}
			//on boucle pour chaque lignes de la liste lignes
			lignes.forEach(l->{
				//on teste si la ligne ne commence pas par la premiere ligne
				if(!l.startsWith("Id")) {
					StringTokenizer s=new StringTokenizer(l,"\t");
					int id=Integer.parseInt(s.nextToken());
					String nom=s.nextToken();
					int population=Integer.parseInt(s.nextToken());
					String region=s.nextToken();
					Ville v=new Ville(id,nom,population,region);
					villes.add(v);
				}});
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return villes;
	}
	public void trierVilles(List<Ville> villes) {
		//on crée les chemins dont on va rediriger la sortie apres le trie
		//trie par nom
		Path villesParNom=Paths.get("villesParNom.txt");
		//trie par population
		Path villesParPopulation=Paths.get("villesParPopulation.txt");
		//trie par region
		Path villesParRegion=Paths.get("villesParRegion.txt");
		//on trie la liste des villes en utilisant un nouveau comparateur de type Ville vu que l'utilisation de la fonction lambda blocke la fonction compareTo en cas d'une deuxieme utilisation
		Collections.sort(villes,new Comparator<Ville>() {
			//on retourne une liste de villes triée par nom
			@Override
		    public int compare(Ville v1,Ville v2)
		    {
		             return v1.getNom().compareTo(v2.getNom());
		    }
			});
		try {
			//on crée une variable de type string pour stocker toutes les villes triées
			String result="";
			for (Ville ville : villes) {
				//on boucle sur la liste des villes triées par nom et on ajoute à une variale string le contenu(à l'aide de la fonction toString qui répond à la forme demandée
				result+=ville.toString()+"\n";
			}
			//on écrit dans le fichier villesParNom le resultat obtenu
			Files.write(villesParNom,result.getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(villes,new Comparator<Ville>() {
			@Override
		    public int compare(Ville v1,Ville v2)
		    {
		             return Integer.valueOf(v1.getPopulation()).compareTo(v2.getPopulation());
		    }
			});
		try {
			String result="";
			for (Ville ville : villes) {
				result+=ville.toString()+"\n";
			}
			Files.write(villesParPopulation,result.getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(villes,new Comparator<Ville>() {
			@Override
		    public int compare(Ville v1,Ville v2)
		    {
		             return v1.getRegion().compareTo(v2.getRegion());
		    }
			});
		try {
			String result="";
			for (Ville ville : villes) {
				result+=ville.toString()+"\n";
			}
			Files.write(villesParRegion,result.getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
