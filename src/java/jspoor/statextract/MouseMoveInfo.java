package jspoor.statextract;

/**
 * This class is used to hold and calculate information and data related to one
 * single mouse movement.
 * 
 * @author arin ghazarian
 * 
 */
public class MouseMoveInfo {

	private int startX;
	private int StartY;
	private int endX;
	private int endY;

	private float mouseXVelocity;
	private float mouseYVelocity;
	private float mouseVelocity;
	private float mouseXAccel = 0;
	private float mouseYAccel = 0;
	private float mouseAccel = 0;
	private long movementDurationInMillis = 0;
	private float distanceTravelled = 0;
	private double moveAngel = 0;

	private byte xDirection = 0;
	private byte yDirection = 0;
	private boolean directionChanged = false;
	private long time = 0;

	/**
	 * constructor of the class
	 * 
	 * @param x1
	 *            start x position of the mouse cursor
	 * @param x2
	 *            end x position of the mouse cursor
	 * @param y1
	 *            start y position of the mouse cursor
	 * @param y2
	 *            end y position of the mouse cursor
	 * @param t1
	 *            movement start time
	 * @param t2
	 *            movement end time
	 * @param prevMoveInfo
	 *            A MOuseMoveInfo object holding the data for the previous mouse
	 *            movement
	 */
	public MouseMoveInfo(int x1, int x2, int y1, int y2, long t1, long t2,
			MouseMoveInfo prevMoveInfo) {
		time = t2;
		startX = x1;
		StartY = y1;
		endX = x2;
		endY = y2;
		movementDurationInMillis = t2 - t1;
		if (movementDurationInMillis == 0)
			movementDurationInMillis = 1;
		// else if (movementDurationInMillis>150)movementDurationInMillis=;
		distanceTravelled = (float) (Math.sqrt(Math.pow(y2 - y1, 2)
				+ Math.pow(x2 - x1, 2)));
		mouseXVelocity = Math.abs(x2 - x1) / (movementDurationInMillis / 1000f);
		mouseYVelocity = Math.abs(y2 - y1) / (movementDurationInMillis / 1000f);
		
		mouseVelocity = (float) (distanceTravelled)
				/ (movementDurationInMillis / 1000f);
		mouseXAccel = (mouseXVelocity - prevMoveInfo.getMouseXVelocity())
				/ (movementDurationInMillis / 1000f);
		mouseYAccel = (mouseYVelocity - prevMoveInfo.getMouseYVelocity())
				/ (movementDurationInMillis / 1000f);
		mouseAccel = (mouseVelocity - prevMoveInfo.getMouseVelocity())
				/ (movementDurationInMillis / 1000f);
		moveAngel = Math.toDegrees(Math.atan((double) (x2 - x1) / (y2 - y1)));
		xDirection = (byte) Math.signum(x2 - x1);
		yDirection = (byte) Math.signum(y2 - y1);
		if ((y2 - y1) == 0)
			moveAngel = 90;
		if (((xDirection < 0) && (yDirection < 0))
				|| ((xDirection > 0) && (yDirection < 0)))
			moveAngel += 180;
		if (moveAngel < 0)
			moveAngel = 360 + moveAngel;
		if ((xDirection != prevMoveInfo.getXDirection())
				|| (yDirection != prevMoveInfo.getYDirection()))
			directionChanged = true;
		else
			directionChanged = false;
	}

	/**
	 * calculate the distance between two given points
	 * 
	 * @param x1
	 *            x coordinate of the first point
	 * @param x2
	 *            x coordinate of the second point
	 * @param y1
	 *            y coordinate of the first point
	 * @param y2
	 *            y coordinate of the second point
	 * @return calculates the distance between two points
	 */
	public static double calcDistance(int x1, int x2, int y1, int y2) {
		return (Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2)));
	}

	/**
	 * This constructor should be used on first mouse move where no previous
	 * Mouse Move is available
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param t
	 */
	public MouseMoveInfo(int x1, int x2, int y1, int y2, long t) {
		time = t;
		startX = x1;
		StartY = y1;
		endX = x2;
		endY = y2;
		movementDurationInMillis = 0;
		distanceTravelled = (float) calcDistance(x1, x2, y1, y2);
		mouseXVelocity = 0;
		mouseYVelocity = 0;
		mouseVelocity = 0;
		mouseXAccel = 0;
		mouseYAccel = 0;
		mouseAccel = 0;
		moveAngel = Math.toDegrees(Math.atan((double) (x2 - x1) / (y2 - y1)));
		if ((y2 - y1) == 0)
			moveAngel = 90;
		if (((xDirection < 0) && (yDirection < 0))
				|| ((xDirection > 0) && (yDirection < 0)))
			moveAngel += 180;
		if (moveAngel < 0)
			moveAngel = 360 + moveAngel;
		xDirection = (byte) Math.signum(x2 - x1);
		yDirection = (byte) Math.signum(y2 - y1);

		directionChanged = false;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isDirectionChanged() {
		return directionChanged;
	}

	public void setDirectionChanged(boolean directionChanged) {
		this.directionChanged = directionChanged;
	}

	public byte getXDirection() {
		return xDirection;
	}

	public void setXDirection(byte direction) {
		xDirection = direction;
	}

	public byte getYDirection() {
		return yDirection;
	}

	public void setYDirection(byte direction) {
		yDirection = direction;
	}

	public double getMoveAngel() {
		return moveAngel;
	}

	public void setMoveAngel(double moveAngel) {
		this.moveAngel = moveAngel;
	}

	public float getMouseXVelocity() {
		return mouseXVelocity;
	}

	public void setMouseXVelocity(float mouseXVelocity) {
		this.mouseXVelocity = mouseXVelocity;
	}

	public float getMouseYVelocity() {
		return mouseYVelocity;
	}

	public void setMouseYVelocity(float mouseYVelocity) {
		this.mouseYVelocity = mouseYVelocity;
	}

	public float getMouseVelocity() {
		return mouseVelocity;
	}

	public void setMouseVelocity(float mouseVelocity) {
		this.mouseVelocity = mouseVelocity;
	}

	public float getMouseXAccel() {
		return mouseXAccel;
	}

	public void setMouseXAccel(float mouseXAccel) {
		this.mouseXAccel = mouseXAccel;
	}

	public float getMouseYAccel() {
		return mouseYAccel;
	}

	public void setMouseYAccel(float mouseYAccel) {
		this.mouseYAccel = mouseYAccel;
	}

	public float getMouseAccel() {
		return mouseAccel;
	}

	public void setMouseAccel(float mouseAccel) {
		this.mouseAccel = mouseAccel;
	}

	public long getMovementDurationInMillis() {
		return movementDurationInMillis;
	}

	public void setMovementDurationInMillis(long movementDurationInMillis) {
		this.movementDurationInMillis = movementDurationInMillis;
	}

	public float getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(float distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return StartY;
	}

	public void setStartY(int startY) {
		StartY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

}
