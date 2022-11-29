package Exercice1;

public class Tips {
	private int bill;
	private int pourcentage;
	private int nombre;
	
	public Tips(int bill, int pourcentage, int nombre) {
		this.bill = bill;
		this.pourcentage = pourcentage;
		this.nombre = nombre;
	}
	
	//-----------------GET
	public Integer getBill() {
		return this.bill;
	}
	public Integer getPourcentage() {
		return this.pourcentage;
	}
	public Integer getNombre() {
		return this.nombre;
	}
	
	//-----------------SET
	public void setBill(Integer bill) {
		this.bill = bill;
	}
	public void setPourcentage(Integer pourcentage) {
		this.pourcentage = pourcentage;
	}
	public void setNombre(Integer nombre) {
		this.nombre = nombre;
	}
}
