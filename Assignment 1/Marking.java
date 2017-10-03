/* Name: Alex Hale
 * ID: 260672475
 * Date: January 24, 2016
 * Title: Assignment 1, Question 2 - Marking
 */

// Declare a new class, which will house all the methods of the program.
public class Marking {
  // Declare the main method, where the program will start.
  public static void main(String[] args) {
    // The following are tests for the printMaximum() method, with a header to make things clearer in the console.
    System.out.println("printMaximum tests:");
    printMaximum(55,10); 
    printMaximum(-44, -16);
    printMaximum(-700, -300);
    
    // Tests for maximum()
    System.out.println("\nmaximum tests:");
    int x = 0; int y = 0;
    if (maximum(x, y) == x+y) {
     System.out.println("The two numbers are equal."); 
    } else {
      System.out.println("The larger number is " + maximum(x,y) + ".");
    }
    
    // Tests for finalGrade()
    System.out.println("\nfinalGrade tests:");
    System.out.println(finalGrade(28, 4, 18, 30));
    System.out.println(finalGrade(28, 4, 16, 38));
  }
  
  // The printMaximum method takes two values and prints a message declaring which one is larger.
  public static void printMaximum(double x, double y) {
    if (x > y) {
      // If the x value is greater, state it in a message, including the difference.
      System.out.println(x + " is " + (x-y) + " greater than " + y + "."); 
    } else if (y > x) {
      // If the y value is larger, state it in a message, including the difference.
     System.out.println(y + " is " + (y-x) + " greater than " + x + "."); 
    } else {
      // If the values are equal, state it in a message.
     System.out.println("The numbers " + x + " and " + y + " are equal!"); 
    }
  }
  
  /* The maximum method take two values and returns the larger one. If the values are the same it returns the first
   * value. If they are equal, then they are both the maximum vale, so we can return either one. */
  public static double maximum(double x, double y) {
    if (x > y) {
     return x; 
    } else if (y > x) {
     return y;
    } else {
     return x;  
    }
  }
  
  /* This method takes the pre-weighted grade of each portion of the COMP 202 assessment schedule, chooses the most
   * advantageous marking scheme, and returns the final grade (rounded down to the nearest integer). */
  public static int finalGrade(int assignment, int quiz, int midtermExam, int finalExam) {
    // find the percentage achieved on each exam in order to compare them
    double midtermPercent = midtermExam/20.0;
    double finalPercent = finalExam/40.0;
    
    if (maximum(midtermPercent, finalPercent) == midtermPercent) {
      /* Return the grade with everything weighted as  it was inputted. This case also runs if the midterm and final 
       * grades are the same, which works because in that case, the result is the same using both calculation methods. 
       */
      return (assignment + quiz + midtermExam + finalExam);
    } else {
      /* Return the grade using the final exam as 60% of the grade. Since all fractional marks are rounded down in
       * this question, chopping off the decimal when the value gets put into an int to return it works just fine. */
      return (assignment + quiz + finalExam*3/2);
    }
  }
}