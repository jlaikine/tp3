package Exercice1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VueController {
	
	@FXML
	private TextField idBill;
	@FXML
	private TextField idPourcentage;
	@FXML
	private TextField idNombre;
	
	@FXML
	private Button calculate;
	
	@FXML
	private TextField idTip;
	@FXML
	private TextField idTotal;
	
	@FXML
	private Label erreur;
	@FXML
	private TextField idDate;
	
	@FXML
	private Button verification;
	
	private void vide(String bill, String pourcentage, String nombre) throws IndexOutOfBoundsException{
		if (bill == "" || pourcentage == "" || nombre == "") {
			idTip.setText("");
			idTotal.setText("");
			erreur.setText("Veuillez compléter les champs !");
			throw new IndexOutOfBoundsException ("Veuillez compléter les champs !");
		}
	}
	
	private void negatif(int bill, int pourcentage, int nombre) {
		if (bill < 0 || pourcentage < 0 || nombre < 0) {
			idTip.setText("");
			idTotal.setText("");
			erreur.setText("Champ(s) négatif(s) !");
			throw new IndexOutOfBoundsException ("Champ(s) négatif(s) !");
		}
	}	
	private void divisionzero(int nombre) {
		if (nombre == 0) {
			idTip.setText("");
			idTotal.setText("");
			erreur.setText("Impossible d'avoir un nombre égale à 0");
			throw new IndexOutOfBoundsException ("Impossible d'avoir un nombre égale à 0");
		}
	}
	
	private void date(String date) throws ParseException, IOException {
	      try {
	    	  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		      format.setLenient(false);
	          format.parse(date);
	          erreur.setText("");
	      }
	      // Date invalide
	      catch (ParseException e){
	    	  erreur.setText("Format de date invalide");
	    	  throw new IndexOutOfBoundsException ("Date invalide");
	      }
	}
	
	public void enregistrementHistorique(String d, String b, String p, String n) throws IOException {
		//Ajoute dans des tableaux temporaires le contenu du ficher texte
		ArrayList<String> tab = new ArrayList<>();
		ArrayList<String> tabDate = new ArrayList<>();
		File file = new File("sauvegarde.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
	    String line;
	    int index = 0;
	    boolean ok = false;
	    String s = d+";"+b+";"+p+";"+n;
	    
	    while((line = br.readLine()) != null) {
	    	tab.add(line);
	    	String dateT = line.substring(0,10);
	    	tabDate.add(dateT);
	    	if (dateT.equals(d)) {
	    		//remplace dans le tableau si on a la même date
	    		tab.set(index, s);
	    		ok = true;
	    		
	    	}
	    	index+=1;
	    }
		fr.close();
		br.close();
	    
	    if (ok) {
	    	if (file.delete()) {
		    	FileWriter writer = new FileWriter("sauvegarde.txt", true);
			    for(int i=0; i<tab.size(); i++) {
			    	writer.write(tab.get(i) + "\n");
			    }
			    writer.close();
	    	}
	    }else {
	    	FileWriter writer = new FileWriter("sauvegarde.txt", true);
		    writer.write(s);
		    writer.write("\n");
		    writer.close();
	    }
	}
	
	public void clickVerification(ActionEvent e) throws ParseException, IOException {
		
		try {
		String d = idDate.getText();
		date(d);
		ArrayList<String> tab = new ArrayList<>();
		ArrayList<String> tabDate = new ArrayList<>();
		File file = new File("sauvegarde.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
	    String line;
	    boolean b = false;
	    while((line = br.readLine()) != null) {
	    	
	    	tab.add(line.substring(11, line.length()));
	    	String dateT = line.substring(0,10);
	    	tabDate.add(dateT);
	    	if (dateT.equals(d)) {
	    		String[] words = line.substring(11,line.length()).split(";");
		        for (String word : words) {
		            idBill.setText(words[0]);
		            idPourcentage.setText(words[1]);
		            idNombre.setText(words[2]);
		        }
		        b=true;
	    	}
	    }
	    if (!b) {
	    	idBill.setText("");
            idPourcentage.setText("");
            idNombre.setText("");
	    }
		fr.close();
		br.close();
		} catch(IndexOutOfBoundsException ee) {
			System.err.println("Erreur : " + ee.getMessage());
		}
	}
	
	public void clickMe(ActionEvent e) throws ParseException, IOException{
		
		try {
		
			String b = idBill.getText();
			String p = idPourcentage.getText();
			String n = idNombre.getText();
			String d = idDate.getText();
			
			vide(b, p, n);
			int bill = Integer.valueOf(b);
			int pourcentage = Integer.valueOf(p);
			int nombre = Integer.valueOf(n);
			
			negatif(bill, pourcentage, nombre);
			divisionzero(nombre);
			date(d);
			
			double resultCalcul = ((bill * pourcentage) / 100) / nombre;
			double resultCalcul2 = (bill / nombre) + resultCalcul;
			idTip.setText(Double.toString(resultCalcul));
			idTotal.setText(Double.toString(resultCalcul2));
			erreur.setText("");
			
			enregistrementHistorique(d, b, p, n);
		    
		}catch(IndexOutOfBoundsException ee) {
			System.err.println("Erreur : " + ee.getMessage());
		}catch (NumberFormatException ee) {
			erreur.setText("Insérer des entiers");
			System.err.println("Entrée incorrecte : " + ee.getMessage());
		}
	}
}
