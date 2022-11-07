package metier;

import java.util.List;

import model.Ville;

public interface IServiceVille {
	public List<Ville> lireBDVilles_IO();
	public List<Ville> lireBDVilles_NIO();
	public List<Ville> lireBDVilles_Scanner();
	public void trierVilles(List<Ville> villes);
}
