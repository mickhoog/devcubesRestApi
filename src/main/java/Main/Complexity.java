package Main;

import javax.persistence.*;

@Entity
public class Complexity extends SonarResult{

	private int overall;
	private int functions;
	private int file;
	private int classes;
	private SonarPush sonarPush;
	
	protected Complexity()
	{}
	
	public Complexity(int overall, int functions, int file, int classes) {
		this.overall = overall;
		this.functions = functions;
		this.file = file; 
		this.classes = classes;
	}
	public int getOverall() {
		return overall;
	}
	public void setOverall(int overall) {
		this.overall = overall;
	}
	public int getFunctions() {
		return functions;
	}
	public void setFunctions(int functions) {
		this.functions = functions;
	}
	public int getFile() {
		return file;
	}
	public void setFile(int file) {
		this.file = file;
	}
	public int getClasses() {
		return classes;
	}
	public void setClasses(int classes) {
		this.classes = classes;
	}
	@OneToOne
	public SonarPush getSonarPush() {
		return sonarPush;
	}
	public void setSonarPush(SonarPush sonarPush) {
		this.sonarPush = sonarPush;
	}
}
