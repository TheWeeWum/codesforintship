import java.lang.Math;

/**
 * Triangle.java is a program which gets a triangle from the CoolShapeStuff
 * program
 * It calculates various information and prints out the triangle visually
 *
 * @author Liam Csiffary
 * @version 1.0
 * @since 2022-06-01
 */

public class Triangle {
	private final double RTOD = 180 / Math.PI;
	double a, b, c, s, x, h;
	double angleA, angleB, angleC;
	String triangleType;
	double[] sides = new double[3];
	double[] angles = new double[3];
	boolean valid;

	/**
	 * default triangle constructor, makes a 3, 4, 5, right angle triangle.
	 */
	public Triangle() {
		sides[0] = 3;
		sides[1] = 4;
		sides[2] = 5;
		this.a = 3;
		this.b = 4;
		this.c = 5;
		this.s = (a + b + c) / 2;

		this.angles = getAngles();
		this.angleA = angles[0] * RTOD;
		this.angleB = angles[1] * RTOD;
		this.angleC = angles[2] * RTOD;

		valid = isValid();
		this.triangleType = typeOfTriangle();
		this.h = getH();
		this.x = getX();
	}

	/**
	 * triangle constructor for 3 sides.
	 */
	public Triangle(double a, double b, double c) {

		// organizes the sides
		this.sides = longestSide(a, b, c);
		this.a = sides[0];
		this.b = sides[1];
		this.c = sides[2];
		// semiPerimeter
		this.s = (a + b + c) / 2;
		// if equilateral set all angles to 60
		// do this because trig and floating point errors would cause an equilateral
		// triangle's angles to all be slightly less than 60*
		if (this.a == this.b && this.b == this.c) {
			this.angleA = 60;
			this.angleB = 60;
			this.angleC = 60;

			// otherwise get the angles from the angles function
		} else {
			this.angles = getAngles();
			this.angleA = angles[0] * RTOD;
			this.angleB = angles[1] * RTOD;
			this.angleC = angles[2] * RTOD;
		}

		// check if triangle is valid
		valid = isValid();
		this.triangleType = typeOfTriangle();
		this.h = getH();
		this.x = getX();
	}

	/**
	 * triangle constructor for two sides and 1 angle, also needs the location of
	 * the angle.
	 */
	public Triangle(double a, double b, double angle, String location) {
		double tempSide;
		// if angle is between the two sides
		if (location.equals("a")) {
			// uses cosine law to find the other side
			tempSide = sAsCalculator(a, b, angle);

		} else if (location.equals("b")) { // if the angle is attached to side a only
			// use sine law to calculate the other angle
			double tempAngle = sinLawAngle(b, angle, a);

			// if the side does not exist
			if (Double.isNaN(tempAngle)) {
				System.out.println("Your b length is not long enough to complete the triangle");
				System.exit(1);
			}
			// calculates the last angles so that we can use cosine law to get the other
			// side
			double otherAngle = 180 - tempAngle - angle;
			tempSide = sAsCalculator(a, b, otherAngle);

		} else { // if the angle is attached to side b only
			// does the same thing as "b", but instead uses (a, angle, b) instead of (b,
			// angle, a)
			double tempAngle = sinLawAngle(a, angle, b);
			if (Double.isNaN(tempAngle)) {
				System.out.println("Your a length is not long enough to complete the triangle");
				System.exit(1);
			}
			double otherAngle = 180 - tempAngle - angle;
			tempSide = sAsCalculator(b, a, otherAngle);
		}

		// with all of this arrange the sides, and get the angles
		this.sides = longestSide(a, b, tempSide);
		this.a = sides[0];
		this.b = sides[1];
		this.c = sides[2];
		this.s = (a + b + c) / 2;
		if (this.a == this.b && this.b == this.c) {
			this.angleA = 60;
			this.angleB = 60;
			this.angleC = 60;
		} else {
			this.angles = getAngles();
			this.angleA = angles[0] * RTOD;
			this.angleB = angles[1] * RTOD;
			this.angleC = angles[2] * RTOD;
		}

		// check if valid and get the type of triangle
		valid = isValid();
		this.triangleType = typeOfTriangle();
		this.h = getH();
		this.x = getX();
	}

	/**
	 * triangle constructor if 1 side and 2 angles, missing is no longer required
	 * but it
	 * now serves the purpose of differentiation between constructors.
	 */
	public Triangle(double a, double angle1, double angle2, int missing) {
		// get the last angle and calculate other sides with sine law
		double otherAngle = 180 - angle1 - angle2;
		double side2 = sinLawSide(a, otherAngle, angle1);
		double side3 = sinLawSide(a, otherAngle, angle2);

		// does the usual
		this.sides = longestSide(a, side2, side3);

		this.a = sides[0];
		this.b = sides[1];
		this.c = sides[2];
		this.s = (a + b + c) / 2;
		if (this.a == this.b && this.b == this.c) {
			this.angleA = 60;
			this.angleB = 60;
			this.angleC = 60;
		} else {
			this.angles = getAngles();
			this.angleA = angles[0] * RTOD;
			this.angleB = angles[1] * RTOD;
			this.angleC = angles[2] * RTOD;
		}

		valid = isValid();
		this.triangleType = typeOfTriangle();
		this.h = getH();
		this.x = getX();
	}

	private double getX() {
		if (triangleType.equals("right angle")) {
			return 0;
		} else {
			return Math.sqrt((a * a) - (h * h));
		}
	}

	private double getH() {
		if (triangleType.equals("right angle")) {
			return a;
		} else {
			return height(c, a, b);
		}
	}

	private boolean isValid() {
		boolean valid = true;
		// if the angles add up to more than 180, used slightly more than 180 because of
		// sine and cosine
		// inconsistencies, also might be floating pointing errors.
		if (angleA + angleB + angleC > 180.00001) {
			System.out.println("The angles add up to more than 180, not a valid triangle");
			valid = false;
		}

		// if a and b's sum is less than c the triangle will not complete
		if (a + b < c) {
			System.out.println("The hypotenuse of the triangle is longer than the other sides combined");
			valid = false;
		}

		// if a + b == c than the triangle is not a triangle but a line
		if (a + b == c) {
			System.out.println(
					"The hypotenuse of the triangle is the same length as the other sides combined");
			System.out.println("this is not a triangle but a line, therefore invalid");
			valid = false;
		}

		// if valid is false then exit the code.
		if (valid == true) {
			return true;
		} else {
			System.exit(1);
			// to stop style checking errors
			return false;
		}
	}

	// uses cosine law to get side lengths
	private double sAsCalculator(double sideA, double sideB, double angle) {
		return Math.sqrt(
				sideA * sideA + sideB * sideB - 2 * sideA * sideB * Math.cos(angle / 180 * Math.PI));
	}

	// uses sine law to get a side length
	private double sinLawSide(double aSide, double aAngle, double bAngle) {
		return Math.sin(bAngle / 180 * Math.PI) * (aSide / Math.sin(aAngle / 180 * Math.PI));
	}

	// uses sine law to get an angle
	private double sinLawAngle(double aSide, double aAngle, double bSide) {
		return (Math.asin(bSide * Math.sin(aAngle / 180 * Math.PI) / aSide)) * 180 / Math.PI;
	}

	// gets semiperimeter for the user
	public double semiPerimeter() {
		return s;
	}

	// calculates area
	public double area() {
		double temp = (s * (s - a) * (s - b) * (s - c));
		return Math.sqrt(temp);
	}

	// calculates perimeter
	public double perimeter() {
		return (a + b + c);
	}

	/**
	 * generates the angles of a triangle.
	 */
	public double[] getAngles() {
		double[] angles = new double[3];
		// returns angles from left to right, or I suppose bottom left, top, then right
		// assumes that bottom is c, left is a, and right is b
		angles[2] = Math.acos((b * b + a * a - c * c) / (2 * b * a));
		angles[1] = Math.acos((a * a + c * c - b * b) / (2 * a * c));
		angles[0] = Math.acos((b * b + c * c - a * a) / (2 * b * c));
		return angles;
	}

	/**
	 * finds the longest side and returns a as smallest and c as largest.
	 */
	public double[] longestSide() {
		double[] returns = new double[3];
		if (a > b && a > c) {
			if (b < c) {
				returns[0] = b;
				returns[1] = c;
				returns[2] = a;
			} else {
				returns[0] = c;
				returns[1] = b;
				returns[2] = a;
			}

		} else if (b > a && b > c) {
			if (a < c) {
				returns[0] = a;
				returns[1] = c;
				returns[2] = b;
			} else {
				returns[0] = c;
				returns[1] = a;
				returns[2] = b;
			}

		} else {
			if (a < b) {
				returns[0] = a;
				returns[1] = b;
				returns[2] = c;
			} else {
				returns[0] = b;
				returns[1] = a;
				returns[2] = c;
			}
		}
		return returns;
	}

	/**
	 * longest side with specific inputs.
	 */
	public double[] longestSide(double sideA, double sideB, double sideC) {
		double[] returns = new double[3];
		if (sideA > sideB && sideA > sideC) {
			if (sideB < sideC) {
				returns[0] = sideB;
				returns[1] = sideC;
				returns[2] = sideA;
			} else {
				returns[0] = sideC;
				returns[1] = sideB;
				returns[2] = sideA;
			}

		} else if (sideB > sideA && sideB > sideC) {
			if (sideA < sideC) {
				returns[0] = sideA;
				returns[1] = sideC;
				returns[2] = sideB;
			} else {
				returns[0] = sideC;
				returns[1] = sideA;
				returns[2] = sideB;
			}

		} else {
			if (sideA < sideB) {
				returns[0] = sideA;
				returns[1] = sideB;
				returns[2] = sideC;
			} else {
				returns[0] = sideB;
				returns[1] = sideA;
				returns[2] = sideC;
			}
		}
		return returns;
	}

	/**
	 * gets the type of triangle.
	 */
	public String typeOfTriangle() {
		if (a == b && b == c) {
			return "equilateral";
		} else if (a == b || b == c || c == a) {
			return "isosceles";
		} else {
			if (c == Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))) {
				// NOTE: A right angle triangle can also be scalene if the all angles are
				// different
				// not useful for this program
				return "right angle";
			} else {
				return "scalene";
			}
		}
	}

	/**
	 * Determines whether the triangle is scalene or not.
	 */
	public String scalene() {
		if (triangleType.equals("scalene")) {
			return "";
		} else {
			if (angleA != angleB && angleB != angleC && angleA != angleC) {
				return "scalene";
			} else {
				return "";
			}
		}
	}

	/**
	 * Determines whether the triangle is scalene or not.
	 */
	public String obtuse() {
		if (triangleType.equals("right angle") || triangleType.equals("equilateral")) {
			return "";
		} else {
			if (angleA > 90 || angleB > 90 || angleC > 90) {
				return "obtuse";
			} else {
				return "acute";
			}
		}
	}

	/**
	 * default returns the height of a as base.
	 */
	public double height() {
		double temp = (s * (s - a) * (s - b) * (s - c));
		temp = Math.sqrt(temp);
		return (temp * 2 / a);
	}

	/**
	 * returns the first input as base.
	 */
	public double height(double sideA, double sideB, double sideC) {
		double temp = (s * (s - sideA) * (s - sideB) * (s - sideC));
		temp = Math.sqrt(temp);
		return (temp * 2 / sideA);
	}

	// default returns the median of a
	public double median() {
		return Math.sqrt((2 * b * b + 2 * c * c - a * a) / 4);
	}

	// returns the median of the first side you put in
	public double median(double sideA, double sideB, double sideC) {
		return Math.sqrt((2 * sideB * sideB + 2 * sideC * sideC - sideA * sideA) / 4);
	}

	// generates the radius of the inner circle
	public double inradius() {
		return area() / s;
	}

	// generates the radius of the outer circle
	public double circumradius() {
		// return a / (2 * Math.sin(angleC * 180 / Math.PI));
		return (a * b * c) / Math.sqrt((a + b + c) * (b + c - a) * (c + a - b) * (a + b - c));
	}

	// finds the coordinates of th middle of the inner circle
	public double[] innerCircleCenter() {

		// gets half the angles of angle a and b
		double angle1 = angleB / 2;
		double angle2 = angleA / 2;
		double[] returns = new double[2];
		double angleC = 180 - angle1 - angle2;

		// takes half the angles of A and B and the side c and finds the vertex of that
		// triangle
		double leftSide = c * Math.sin(angle1 / RTOD) / Math.sin(angleC / RTOD);
		double rightSide = c * Math.sin(angle2 / RTOD) / Math.sin(angleC / RTOD);

		// calculates the area of this triangle to get the height
		double area = (leftSide * rightSide * Math.sin(angleC / RTOD)) / 2;
		double height = 2 * area / c;

		// calculates the distance along the bottom of the triangle to the height line
		double dis = height / Math.tan(angle1 / RTOD);

		// returns the dis as x and height as y
		returns[0] = dis;
		returns[1] = height;
		return returns;
	}

	/**
	 * finds the middle of the circumscribed circle. Made super easy because the
	 * second line is flat
	 * this gives us the x coord instantly so all we need is an equation for the
	 * other line to find y
	 */
	public double[] outerCircleCenter() {
		double[] coords = new double[2];

		// gets the midpoint of line 1
		double midAX = x / 2;
		double midAY = h / 2;

		// line 2 is flat so the midpoint is just the length / 2
		double midpoint = c / 2;

		// gets the perpendicular slope at the midpoint of line 1
		// line two is flat so the perpendicular line's slope is infinite
		double mA = -1 / (h / x);
		double b = midAY - (midAX * mA);

		coords[0] = midpoint;
		coords[1] = mA * midpoint + b;

		return coords;

	}

	/**
	 * the orthocenter is the intersection of the perpendicular line to the
	 * height point of each line segment
	 * https://mathworld.wolfram.com/Orthocenter.html
	 */
	public double[] orthocenter() {
		double[] returns = new double[2];
		// calculates the one of the two lines needed to find the intersection
		double m = (x) / (h) * -1;
		double b = -1 * m * c;

		// x is given since we already calculated it
		double coordX = x;

		// calculate coord y with the created line and the given x
		double coordY = m * coordX + b;

		returns[0] = coordX;
		returns[1] = coordY;
		return returns;
	}

	// calculates the altitude of the given side
	// its just the height calculated from the vertex instead of the
	// vertex calculated from the height
	public double altitude(double side) {
		return 2 * area() / side;
	}

	// calculates the change in coordinates of two points
	private double[] changeFinder(
			double x1, double y1, double angle1, double x2, double y2, double angle2, boolean divide) {
		double[] change = new double[2];

		// calculates the longest side of the smaller triangle
		double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

		// 1 third of each angle to find the intersection of these angles anf the
		// longest side
		double smallAngle1 = angle1 / 3;
		double smallAngle2 = angle2 / 3;
		double otherAngle = 180 - smallAngle1 - smallAngle2;

		// gets the length from one vertex to the new vertex created
		double lengthToVertex = distance * Math.sin(
				smallAngle2 / 180 * Math.PI) / Math.sin(otherAngle / 180 * Math.PI);

		// calculates the x and y partitions of this length to vertex
		// for the top vertex I need to multiply the angle by 2
		if (divide == true) {
			change[0] = lengthToVertex * Math.cos(smallAngle1 * 2 / 180 * Math.PI);
			change[1] = lengthToVertex * Math.sin(smallAngle1 * 2 / 180 * Math.PI);
		} else {
			change[0] = lengthToVertex * Math.cos(smallAngle1 / 180 * Math.PI);
			change[1] = lengthToVertex * Math.sin(smallAngle1 / 180 * Math.PI);
		}

		return change;
	}

	/**
	 * calculates the vertices of the morley's triangle.
	 */
	private double[] morleysTrisector(double aAngle, double bx, double by, double bAngle, double cx) {
		double[] vertecies = new double[6];
		double cAngle = angleA;

		// left vertice
		double[] changes = changeFinder(0, 0, aAngle, bx, by, bAngle, true);

		vertecies[0] = changes[0];
		vertecies[1] = changes[1];

		// right vertice
		changes = changeFinder(bx, by, cAngle, cx, 0, bAngle, true);
		vertecies[2] = cx - changes[0];
		vertecies[3] = changes[1];

		// top vertice
		changes = changeFinder(0, 0, aAngle, cx, 0, cAngle, false);
		vertecies[4] = changes[0];
		vertecies[5] = changes[1];

		return vertecies;
	}

	// https://mathworld.wolfram.com/deLongchampsPoint.html
	private double[] deLongchampsPoint(double[] ortho, double[] circum) {
		double[] longPoint = new double[2];
		if (ortho[0] > circum[0]) {
			longPoint[0] = ortho[0] - circum[0];
		} else {
			longPoint[0] = circum[0] - ortho[0];
		}

		if (ortho[1] > circum[1]) {
			longPoint[1] = ortho[1] - circum[1];
		} else {
			longPoint[1] = circum[1] - ortho[1];
		}
		return longPoint;
	}

	private double[] ninePointCenter(double[] ortho, double[] circum) {
		double[] ninePoint = new double[2];
		ninePoint[0] = (ortho[0] + circum[0]) / 2;
		ninePoint[1] = (ortho[1] + circum[1]) / 2;

		return ninePoint;
	}

	/**
	 * removes all trailing zeros.
	 */
	private String fmt(double d) {
		if (d == (long) d) {
			return Long.toString((long) d);
		} else {
			return Double.toString(d);
		}
	}

	/**
	 * prints the triangle given by the user.
	 */
	public void printTriangle() {
		// takes points A as (0, 0)
		// and B as vertex (?, bases height)
		System.out.println("\n");

		//////////////////////// RIGHT ANGLE PRINTER //////////////////////////
		if (triangleType.equals("right angle")) {
			// for each in height, which is side a, +2 to fit B and the &'s
			for (int i = 0; i < a + 2; i++) {
				// for the top
				if (i == 0) {
					System.out.println("  B\n  &");

					// for the bottom
				} else if (i == a + 1) {
					System.out.print("C &");

					// prints all of the underscores, length of b times
					for (int j = 0; j < b; j++) {
						if (j == Math.floor(b / 2 + 0.5)) {
							System.out.print("b_");
						}
						System.out.print("__");
					}
					// put A and the & on the end of the line
					System.out.println("& A");

				} else {
					// prints the left side of the triangle
					if (i == Math.floor(a / 2 + 0.5)) {
						System.out.print("  a");
					} else {
						System.out.print("  |");
					}

					// how much to indent each part of the c line
					double totalDelayAmount = b * 2;
					int howMuchDelayEachTime = (int) Math.floor(totalDelayAmount / a + 0.5);

					// prints the spaces based on the delay
					for (int delay = 0; delay < (howMuchDelayEachTime * i) - 2; delay++) {
						System.out.print(" ");
					}
					// when to print the letter c, otherwise print the backslash
					if (i == Math.floor(a / 2 + 0.5)) {
						System.out.println("c");
					} else {
						System.out.println("\\");
					}
				}
			}

			////////////////////// EVERYTHING ELSE PRINTER ///////////////////////
		} else {
			// gets the height and the distance along line the hypotenuse to the base of the
			// height
			int height = (int) h;
			double increment;

			// for each line in the height of the triangle
			for (int i = 0; i <= height; i++) {
				if (i == 0) {
					for (int j = 0; j < x; j++) {
						System.out.print("  ");
					}
					System.out.println("  C");

					for (int j = 0; j < x; j++) {
						System.out.print("  ");
					}
					if (0 == height) {
						System.out.println("b & a");
					} else {
						System.out.println("  &");
					}

				}
				if (i == height) {
					if (height == 0) {
						System.out.print("A &");
						for (int j = 0; j < c; j++) {
							if (j == Math.floor(x)) {
								System.out.print(" c ");
							} else {
								System.out.print("__");
							}
						}
						System.out.println("& A");
					} else {
						System.out.print("B &");
						for (int j = 0; j < c; j++) {
							if (j == Math.floor(x)) {
								System.out.print("_c");
							} else {
								System.out.print("__");
							}
						}
						System.out.println("& A");
					}

				} else {
					// first half of the triangle sides printing
					// determines how much further each symbol must go
					increment = x / h;

					// prints spaces x (the distance to point C) - i * increment
					for (int j = 0; j < (x - i * increment) * 2; j++) {
						System.out.print(" ");
					}

					// to be changed, will have different symbols depending on the angle
					// System.out.print("~");
					if (i == Math.floor(height / 2 + 0.5)) {
						System.out.print("a");
					} else if (angleB < 35) {
						System.out.print("~");
					} else if (angleB > 75) {
						System.out.print("|");
					} else {
						System.out.print("/");
					}

					// prints a space i times increment old increment
					// * 2 times this is to set the base indentation to x
					for (int j = 0; j < (i * increment) * 2; j++) {
						System.out.print(" ");
					}

					if (i == 1) {
						// just to push the second slash out 1 more
						// lazy fix probably going to cause problems later.
						System.out.print(" ");
					} else if (i == 0) {
						System.out.print("  ");
					}

					// for test purposes
					if (i == Math.floor(height / 2 + 0.5)) {
						System.out.print("h");
					} else {
						System.out.print("."); // shows where this is moving the indentation to
					}

					// gets new increment, this time its the distance from point C to B
					double increment2 = (c - x) / h;
					for (int j = 0; j < increment2 * i * 2; j++) {
						System.out.print(" ");
					}

					// will eventually print different symbols depending on the angle
					if (i == Math.floor(height / 2 + 0.5)) {
						System.out.println("b");
					} else if (angleA < 35) {
						System.out.println("~");
					} else if (angleA > 75) {
						System.out.println("|");
					} else {
						System.out.println("\\");
					}
				}

			}
		}
		// prints the information of the triangle
		double[] morleysSideCoords = new double[6];
		final double[] innerCircCoord = innerCircleCenter();
		final double inrad = inradius();

		final double[] outerCircCoord = outerCircleCenter();
		final double[] orthocenter = orthocenter();
		final double[] longchampsPoint = deLongchampsPoint(orthocenter, outerCircCoord);
		final double[] ninePoint = ninePointCenter(orthocenter, outerCircCoord);

		if (triangleType.equals("right angle")) {
			morleysSideCoords = morleysTrisector(angleC, 0, a, angleB, b);
		} else {
			morleysSideCoords = morleysTrisector(angleB, x, h, angleC, c);
		}
		final double morleySideLen = Math.sqrt(
				Math.pow(morleysSideCoords[0] - morleysSideCoords[2], 2)
						+ Math.pow(morleysSideCoords[1] - morleysSideCoords[3], 2));

		final double morleyArea = morleySideLen * morleySideLen / 2;

		System.out.printf("\nSide a: %s, side b: %s, side c: %s\n", fmt(a), fmt(b), fmt(c));
		System.out.printf("Angle A: %s*, angle B: %s*, angle C: %s*\n",
				fmt(angleA), fmt(angleB), fmt(angleC));

		System.out.printf("Semiperimeter: %s, area: %s, perimeter: %s\n",
				fmt(s), fmt(area()), fmt(perimeter()));

		System.out.printf("Type of triangle %s %s %s\n\n", obtuse(), triangleType, scalene());

		System.out.printf("Height a: %s, height b: %s, height c: %s\n",
				fmt(height()), fmt(height(b, a, c)), fmt(height(c, b, a)));

		System.out.printf("Median a: %s, median b: %s, median c: %s\n",
				fmt(median()), fmt(median(b, a, c)), fmt(median(c, b, a)));

		System.out.printf("Inradius: %s, circumradius: %s\n", fmt(inradius()), fmt(circumradius()));

		if (triangleType.equals("right angle")) {
			System.out.printf("Coords of C: (0, 0), of B: (%s, 0), of A: (%s, 0)\n",
					fmt(x), fmt(h), fmt(c));

			System.out.printf("Centroid: (%s, %s), incenter: (%s, %s), circumcenter: (%s, %s)\n",
					fmt(b / 3), fmt(h / 3), fmt(inrad), fmt(inrad), fmt(b / 2), fmt(a / 2));

		} else {
			System.out.printf("Coords of B: (0, 0), of C: (%s, %s), of A: (%s, 0)\n",
					fmt(x), fmt(h), fmt(c));
			System.out.printf("Incenter: (%s, %s), circumcenter: (%s, %s)\n",
					fmt(innerCircCoord[0]), fmt(innerCircCoord[1]),
					fmt(outerCircCoord[0]), fmt(outerCircCoord[1]));
		}

		System.out.printf("Centroid: (%s, %s), Orthocenter: (%s, %s)\n",
				fmt((x + c) / 3), fmt(h / 3), fmt(orthocenter[0]), fmt(orthocenter[1]));

		System.out.printf("Altitude a: %s, b: %s, c: %s\n",
				fmt(altitude(a)), fmt(altitude(b)), fmt(altitude(c)));

		System.out.printf("Morley's triangle vertices: \n D: (%s, %s)\n E: (%s, %s)\n F: (%s, %s)\n",
				fmt(morleysSideCoords[0]), fmt(morleysSideCoords[1]),
				fmt(morleysSideCoords[4]), fmt(morleysSideCoords[5]),
				fmt(morleysSideCoords[2]), fmt(morleysSideCoords[3]));

		System.out.printf("Morley's triangle side lengths: %s\n", fmt(morleySideLen));
		System.out.printf("Morley's triangle area: %s, perimeter: %s, semiperimeter: %s\n",
				fmt(morleyArea), fmt(morleySideLen * 3), fmt(morleySideLen * 1.5));

		System.out.printf("The Longchamps Point is: (%s, %s), the nine point center is (%s, %s)\n",
				fmt(longchampsPoint[0]), fmt(longchampsPoint[1]), fmt(ninePoint[0]), fmt(ninePoint[1]));
	}
}
