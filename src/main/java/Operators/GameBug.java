package Operators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import info.gridworld.actor.*;
import info.gridworld.grid.*;


public abstract class GameBug extends Bug {

	private int score;
	private int health;
	private JFrame frame;
	private String lastKey = "";
	private int lastArrow = Location.NORTH;
	private boolean keySet;
	private JProgressBar healthBar;
	private JLabel scoreLabel = new JLabel("0");
	private JTextArea userMessage = new JTextArea("");
	private JLabel icon = new JLabel();
	private String imageExtension = "";
	private final String imageType = ".gif";
	private boolean stop = false;
	private boolean alive = false;

	public GameBug(){
	//	if(GameWorld.checkWorld()==null){
	//		System.out.println("A GameBug must be placed in a GameWorld.");
	//		throw new NullPointerException();
	//	}
		System.setProperty("info.gridworld.gui.selection", "hide");
		UIManager.put("ProgressBar.foreground", Color.decode("#FF8D93"));
		UIManager.put("ProgressBar.selectionForeground", Color.black);
		healthBar = new JProgressBar(0, 100);
		score = 0;
		health = 100;
		keySet = false;
		createWindow();
	}

	public void createWindow(){
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(250,100));
		frame.setUndecorated(true);  
		frame.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG); 
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setTitle(this.getClass().getName());
		frame.setResizable(false);
		JPanel gamePane = new JPanel();
		JPanel healthLine = new JPanel();
		JPanel border = new JPanel();
		healthLine.setLayout(new BoxLayout(healthLine, BoxLayout.Y_AXIS));
		healthLine.add(healthBar);
		Box b = Box.createHorizontalBox();
		b.add(new JLabel("Score: "));
		b.add(scoreLabel);
		b.add(Box.createHorizontalGlue());
		healthLine.add(b);
		gamePane.add(Box.createGlue());
		gamePane.setLayout(new BoxLayout(gamePane, BoxLayout.X_AXIS));
		changeImage(null);
		gamePane.add(icon);
		gamePane.add(Box.createGlue());
		gamePane.add(healthLine);
		gamePane.add(Box.createGlue());
		Box textBox = Box.createHorizontalBox();
		textBox.add(Box.createHorizontalGlue());
		textBox.add(Box.createHorizontalGlue());
		border.setLayout(new BoxLayout(border, BoxLayout.Y_AXIS));
		border.add(Box.createGlue());
		border.add(gamePane);
		border.add(Box.createGlue());
		userMessage.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 10));
		userMessage.setLineWrap(true);
		userMessage.setWrapStyleWord(true);
		userMessage.setBackground(null);
		userMessage.setEditable(false);
		final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(0);
		split.setDividerLocation(50);
		split.setTopComponent(border);
		split.setBottomComponent(userMessage);
		frame.setContentPane(split);
		userMessage.addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				userMessage.setSize(userMessage.getPreferredSize());
				frame.setSize(new Dimension(240, 53 + userMessage.getHeight()));

			}
			public void componentMoved(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});
		healthBar.setStringPainted(true);
		paintWindow();
		frame.setVisible(true);
		frame.repaint();
		frame.setSize(new Dimension(240, 53 + userMessage.getHeight()));
	}

	public void paintWindow(){
		if(health < 0){
			healthBar.setValue(0);
		} else if(health > 100){
			healthBar.setValue(100);
		} else {
			healthBar.setValue(health);
		}
		healthBar.setString(health + "/100");
		scoreLabel.setText(score + " ");
		frame.repaint();
	}

	public void setLastKey(String key){
		key = key.toUpperCase();
		if(key.equals("UP")){ lastArrow = Location.NORTH; keySet = true; stop = false; return;}
		if(key.equals("DOWN")){ lastArrow = Location.SOUTH; keySet = true; stop = false; return;}
		if(key.equals("LEFT")){ lastArrow = Location.WEST; keySet = true; stop = false; return;}
		if(key.equals("RIGHT")){ lastArrow = Location.EAST; keySet = true; stop = false; return;}
		lastKey = key;
	}

	public String keyPress(){
		String returnKey = lastKey;
		lastKey = "";
		return returnKey;
	}

	public void addPoints(int points){
		score += points;
		paintWindow();
	}

	public void loseHealth(int damage){
		health -= damage;
		paintWindow();
	}

	public int getPoints(){
		return score;
	}

	public int getHealth(){
		return health;
	}

	public void stop(){
		stop = true;
	}

	public void removeSelfFromGrid() {
		super.removeSelfFromGrid();
		if(alive){
			changeMessage(deathMessage());
		}
		
	}

	public abstract boolean automove();
	public abstract void pickFlower(Flower flower);
	public abstract void touchBug(Bug bug);
	public abstract String deathMessage();

	public void makeMove(){
		if(!stop){
			setDirection(lastArrow);
			if(canMove()){
				move();
			}
		}

	}

	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null){
			return;
		}
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		Actor act = gr.get(next);
		if(act != null && act instanceof Flower){
			gr.remove(next);
			pickFlower((Flower)act);
		}
		if (gr.isValid(next)){
			moveTo(next);
		}else{
			removeSelfFromGrid();
		}
	}
	
	private void touchBugs(){
		Grid<Actor> gr = getGrid();
		if (gr == null){
			return;
		}
		Location loc = getLocation();
		for(Location locs : gr.getOccupiedAdjacentLocations(loc)){
			Actor actor = gr.get(locs);
			if(actor instanceof Bug){
				touchBug((Bug) actor);
			}
		}
	}

	public String getImageSuffix(){
		return imageExtension;
	}

	public void changeImage(String suffix){
		if(suffix == null || suffix.equals("")){
			imageExtension = "";
		} else {
			imageExtension = "_" + suffix;
		}
		ImageIcon i = new ImageIcon(getColoredImage());
		icon.setIcon(i);
	}

	private Image getColoredImage() {
		String fileString = this.getClass().getName() + imageExtension + imageType;
		URL url = this.getClass().getClassLoader().getResource(fileString);
		if(url == null){
			url = (new Bug()).getClass().getResource("Bug.gif");
		}
		BufferedImage loadImg = null;
		try {
			loadImg = ImageIO.read(url);
		} catch (IOException e) {e.printStackTrace();}
		Color color = getColor();
		FilteredImageSource src = new FilteredImageSource(loadImg.getSource(), new BugTintFilter(color));
		return Toolkit.getDefaultToolkit().createImage(src);
	}


	public void changeMessage(String message){
		userMessage.setText(message);
		userMessage.setSize(userMessage.getPreferredSize());
		frame.setSize(new Dimension(240, 53 + userMessage.getHeight()));
	}

	@Override
	public void act() {
		alive = true;
		if(health <= 0){
			removeSelfFromGrid();
		} else {
			if(automove()){
				makeMove();
			} else {
				if(keySet){
					makeMove();
					keySet = false;
				}
			} 
			touchBugs();
		}
		paintWindow();
	}

	//this was taken from ImageDisplay.java in info.gridworld.gui
	private class BugTintFilter extends RGBImageFilter
	{
		private int tintR, tintG, tintB;
		public BugTintFilter(Color color)
		{
			canFilterIndexColorModel = true;
			int rgb = color.getRGB();
			tintR = (rgb >> 16) & 0xff;
			tintG = (rgb >> 8) & 0xff;
			tintB = rgb & 0xff;
		}
		public int filterRGB(int x, int y, int argb)
		{
			int alpha = (argb >> 24) & 0xff;
			int red = (argb >> 16) & 0xff;
			int green = (argb >> 8) & 0xff;
			int blue = argb & 0xff;
			double lum = (0.2989 * red + 0.5866 * green + 0.1144 * blue) / 255;
			double scale = 1 - (4 * ((lum - 0.5) * (lum - 0.5)));
			red = (int) (tintR * scale + red * (1 - scale));
			green = (int) (tintG * scale + green * (1 - scale));
			blue = (int) (tintB * scale + blue * (1 - scale));
			return (alpha << 24) | (red << 16) | (green << 8) | blue;
		}
	}
}
