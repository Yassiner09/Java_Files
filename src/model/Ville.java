package model;

public class Ville{
	int id;
	String nom;
	int population;
	String region;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Ville(int id, String nom, int population, String region)
	{
		this.id=id;
		this.nom=nom;
		this.population=population;
		this.region=region;
	}
	@Override
	public String toString() {
		return "["+nom+"\t"+population+"\t"+region+"]";
	}
}
