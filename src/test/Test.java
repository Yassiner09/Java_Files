package test;

import java.nio.file.Paths;

import metier.ServiceVilles;

public class Test {
	public static void main(String[] args) {
		ServiceVilles service;
		service=new ServiceVilles(Paths.get("villes.txt"));
		System.out.println(service.lireBDVilles_IO());
		//service.trierVilles(service.lireBDVilles_IO());
	}
}
