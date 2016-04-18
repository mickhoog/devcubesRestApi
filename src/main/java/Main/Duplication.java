package Main;

import javax.persistence.*;

@Entity
public class Duplication extends SonarResult{

	private int overall;
	private int blocks;
	private int files;
	private int lines;
	private SonarPush sonarPush;
	
	protected Duplication()
	{}
	
	public Duplication(int overall, int blocks, int files, int lines, SonarPush sonarPush){
		this.blocks = blocks;
		this.overall = overall;
		this.files = files;
		this.lines = lines;
		this.sonarPush = sonarPush;
	}
	
	public int getOverall() {
		return overall;
	}
	public void setOverall(int overall) {
		this.overall = overall;
	}
	public int getBlocks() {
		return blocks;
	}
	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}
	public int getFiles() {
		return files;
	}
	public void setFiles(int files) {
		this.files = files;
	}
	public int getLines() {
		return lines;
	}
	public void setLines(int lines) {
		this.lines = lines;
	}
	@OneToOne
	public SonarPush getSonarPush() {
		return sonarPush;
	}
	public void setSonarPush(SonarPush sonarPush) {
		this.sonarPush = sonarPush;
	}	
}
