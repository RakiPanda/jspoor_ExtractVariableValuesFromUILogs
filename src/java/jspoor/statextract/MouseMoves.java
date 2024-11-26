package jspoor.statextract;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

/**
 * This class represents a continuous mouse movement, consisted of a list of
 * <code>MouseMoveInfo</code> objects
 * 
 * 
 * @author arin ghazarian
 * 
 */
public class MouseMoves {
	ArrayList movesList;
	private float avgMouseXVelocity = 0;
	private float avgMouseYVelocity = 0;
	private float avgMouseVelocity = 0;
	private float avgMouseXAccel = 0;
	private float avgMouseYAccel = 0;
	private float avgMouseAccel = 0;
	private float maxXVelocity = 0;
	private float maxYVelocity = 0;
	private float maxVelocity = 0;
	private float maxXAccel = 0;
	private float maxYAccel = 0;
	private float maxAccel = 0;
	private float distanceTravelled = 0;
	private long moveDuration = 0;
	double totalAngelChanges = 0;
	int directionChangesCount = 0;
	int movesCount = 0;

	public MouseMoves() {
		movesList = new ArrayList();
	}

	public void addMove(MouseMoveInfo m) {
		movesList.add(m);
	}

	/**
	 * computes the values for some attributes related to the a series of
	 * continuous MouseMoves
	 */
	public void calcFeaturesValues() {
		movesCount = movesList.size();

		Iterator itr = movesList.iterator();
		int c = 1;
		float velocityChanges = 0;
		float xVelocityChanges = 0;
		float yVelocityChanges = 0;
		float xDistanceTravelled = 0;
		float yDistanceTravelled = 0;
		double prevMoveAngel = ((MouseMoveInfo) movesList.get(0))
				.getMoveAngel();
		while (itr.hasNext()) {
			MouseMoveInfo mouseMoveInfo = (MouseMoveInfo) itr.next();
			if (mouseMoveInfo.getMouseXVelocity() > maxXVelocity) {
				maxXVelocity = mouseMoveInfo.getMouseXVelocity();
			}
			if (mouseMoveInfo.getMouseYVelocity() > maxYVelocity) {
				maxYVelocity = mouseMoveInfo.getMouseYVelocity();
			}
			if (mouseMoveInfo.getMouseVelocity() > maxVelocity) {
				maxVelocity = mouseMoveInfo.getMouseVelocity();
			}
			if (mouseMoveInfo.getMouseXAccel() > maxXAccel) {
				maxXAccel = mouseMoveInfo.getMouseXAccel();
			}
			if (mouseMoveInfo.getMouseYAccel() > maxYAccel) {
				maxYAccel = mouseMoveInfo.getMouseYAccel();
			}
			if (mouseMoveInfo.getMouseAccel() > maxAccel) {
				maxAccel = mouseMoveInfo.getMouseAccel();
			}

			distanceTravelled += mouseMoveInfo.getDistanceTravelled();
			
			xDistanceTravelled += Math
					.abs(mouseMoveInfo.getMouseXVelocity()
							* ((float) mouseMoveInfo
									.getMovementDurationInMillis() / 1000));
			yDistanceTravelled += Math
					.abs(mouseMoveInfo.getMouseYVelocity()
							* ((float) mouseMoveInfo
									.getMovementDurationInMillis() / 1000));
			moveDuration += mouseMoveInfo.getMovementDurationInMillis();
			velocityChanges += Math
					.abs(mouseMoveInfo.getMouseAccel()
							* ((float) mouseMoveInfo
									.getMovementDurationInMillis() / 1000));
			xVelocityChanges += Math
					.abs(mouseMoveInfo.getMouseXAccel()
							* ((float) mouseMoveInfo
									.getMovementDurationInMillis() / 1000));
			yVelocityChanges += Math
					.abs(mouseMoveInfo.getMouseYAccel()
							* ((float) mouseMoveInfo
									.getMovementDurationInMillis() / 1000));
			totalAngelChanges += Math.abs(prevMoveAngel
					- mouseMoveInfo.getMoveAngel());
			prevMoveAngel = mouseMoveInfo.getMoveAngel();
			if (mouseMoveInfo.isDirectionChanged())
				directionChangesCount++;
		}
		if (moveDuration == 0) {
			avgMouseVelocity = 0;
			avgMouseXVelocity = 0;
			avgMouseYVelocity = 0;

			avgMouseAccel = 0;
			avgMouseXAccel = 0;
			avgMouseYAccel = 0;
		} else {
			avgMouseVelocity = distanceTravelled
					/ ((float) moveDuration / 1000);
			avgMouseXVelocity = xDistanceTravelled
					/ ((float) moveDuration / 1000);
			avgMouseYVelocity = yDistanceTravelled
					/ ((float) moveDuration / 1000);

			avgMouseAccel = velocityChanges / ((float) moveDuration / 1000);
			avgMouseXAccel = xVelocityChanges / ((float) moveDuration / 1000);
			avgMouseYAccel = yVelocityChanges / ((float) moveDuration / 1000);
		}

	}

	public int getDirectionChangesCount() {
		return directionChangesCount;
	}

	public int getMovesCount() {
		return movesCount;
	}

	public ArrayList getMovesList() {
		return movesList;
	}

	public float getAvgMouseXVelocity() {
		return avgMouseXVelocity;
	}

	public float getAvgMouseYVelocity() {
		return avgMouseYVelocity;
	}

	public float getAvgMouseVelocity() {
		return avgMouseVelocity;
	}

	public float getAvgMouseXAccel() {
		return avgMouseXAccel;
	}

	public float getAvgMouseYAccel() {
		return avgMouseYAccel;
	}

	public float getAvgMouseAccel() {
		return avgMouseAccel;
	}

	public float getMaxXVelocity() {
		return maxXVelocity;
	}

	public float getMaxYVelocity() {
		return maxYVelocity;
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public float getMaxXAccel() {
		return maxXAccel;
	}

	public float getMaxYAccel() {
		return maxYAccel;
	}

	public float getMaxAccel() {
		return maxAccel;
	}

	public float getDistanceTravelled() {
		return distanceTravelled;
	}

	public long getMoveDuration() {
		return moveDuration;
	}

	public void setMoveDuration(long moveDuration) {
		this.moveDuration = moveDuration;
	}

	public double getTotalAngelChanges() {
		return totalAngelChanges;
	}

	public void setTotalAngelChanges(double totalAngelChanges) {
		this.totalAngelChanges = totalAngelChanges;
	}

	/**
	 * print the calculated values to a file
	 * 
	 * @param out
	 *            the file to be written on
	 */
	public void printMouseMovesFeatures(PrintWriter out) {

		out.print("@attribute avgMouseXVelocity numeric");
		out.println(avgMouseXVelocity + ",");
		out.print("@attribute avgMouseYVelocity numeric");
		out.println(avgMouseYVelocity + ",");
		out.print("@attribute avgMouseVelocity numeric");
		out.println(avgMouseVelocity + ",");
		out.print("@attribute avgMouseXAccel numeric");
		out.println(avgMouseXAccel + ",");
		out.print("@attribute avgMouseYAccel numeric");
		out.println(avgMouseYAccel + ",");
		out.print("@attribute avgMouseAccel numeric");
		out.println(avgMouseAccel + ",");
		out.print("@attribute maxXVelocity numeric");
		out.println(maxXVelocity + ",");
		out.print("@attribute maxYVelocity numeric");
		out.println(maxYVelocity + ",");
		out.print("@attribute maxVelocity numeric");
		out.println(maxVelocity + ",");
		out.print("@attribute maxXAccel numeric");
		out.println(maxXAccel + ",");
		out.print("@attribute maxYAccel numeric");
		out.println(maxYAccel + ",");
		out.print("@attribute maxAccel numeric");
		out.println(maxAccel + ",");
		out.print("@attribute distanceTravelled numeric");
		out.println(distanceTravelled + ",");
		out.print("@attribute moveDuration numeric");
		out.println(moveDuration + ",");
		out.print("@attribute TotalAngelChanges numeric");
		out.println(totalAngelChanges + ",");
		out.print("@attribute directionChangesCount numeric");
		out.println(directionChangesCount + ",");
		out.print("@attribute mouseMovesCount numeric");
		out.println(movesCount + ",");

	}

	/**
	 * draws the mousemovements path held by this class on an image
	 * 
	 * @param image
	 *            image to be drawn on
	 * @param c
	 *            the color of drawing line
	 */
	public void drawMouseMovesPaths(BufferedImage image, Color c) {

		Graphics2D g2d = image.createGraphics();
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(5));
		Iterator itr = movesList.iterator();
		while (itr.hasNext()) {
			MouseMoveInfo mouseMoveInfo = (MouseMoveInfo) itr.next();
			g2d.drawLine(mouseMoveInfo.getStartX(), mouseMoveInfo.getStartY(),
					mouseMoveInfo.getEndX(), mouseMoveInfo.getEndY());
		}

	}

}
