package org.greyfire.transcendancy.ui;

public interface UI {
	public void initialise();
	public void activate();
	public void shutdown();
	public Boolean yn(String question);
	public Boolean ynq(String question);
	public String prompt(String question);
	public String prompt(String question, String default_value);
	public String choice(String question, String[] values, String default_value);
	public void notify(String message);
	public void warn(String message);
	public boolean running();
}
