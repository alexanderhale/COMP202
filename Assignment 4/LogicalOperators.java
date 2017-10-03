/* Name: Alex Hale
 * ID: 260672475
 * Date: April 12, 2016 */

public class LogicalOperators {
  public static void main(String[] args) {
    boolean[] test = {true, false, true, false, true, false, true, false};
    System.out.println(conjunctionIter(test));
    System.out.println(conjunctionRec(test));
    System.out.println(disjunctionIter(test));
    System.out.println(disjunctionRec(test));
    
    System.out.println();
    
    boolean[] test1 = {true, true, true, true, true, false, false, false};
    System.out.println(conjunctionIter(test1));
    System.out.println(conjunctionRec(test1));
    System.out.println(disjunctionIter(test1));
    System.out.println(disjunctionRec(test1));
    
    System.out.println();
    
    boolean[] test2 = {false, false, false, false, false, false, false, false};
    System.out.println(conjunctionIter(test2));
    System.out.println(conjunctionRec(test2));
    System.out.println(disjunctionIter(test2));
    System.out.println(disjunctionRec(test2));
    
    System.out.println();
    
    boolean[] test3 = {true, true, true, true, true, true, true, true};
    System.out.println(conjunctionIter(test3));
    System.out.println(conjunctionRec(test3));
    System.out.println(disjunctionIter(test3));
    System.out.println(disjunctionRec(test3));
  }
  
  public static boolean conjunctionIter(boolean[] in) {
    // if the array is empty, print a message and return true by default
    if (in.length == 0) {
      System.out.println("The input array was empty.");
      return true;
    } else if (in.length == 1) {
      // if the array is of length 1, return the only value in the array
      return in[0];
    } else {
      // otherwise, calculate the conjunction of the set using iteration
      boolean result = in[0] && in[1];
      for (int i = 2; i < in.length; i++) {
        result = (result && in[i]);
      }
      return result;
    }
  }
  
  public static boolean conjunctionRec(boolean[] in) {
    // if the array is empty, print a message and return true by default
    if (in.length == 0) {
      System.out.println("The input array was empty.");
      return true;
    } else if (in.length == 1) {
      // if the array is of length 1, return the only value in the array
      return in[0];
    } else {
      // otherwise, call the conjunctionRec helper method
      return conjunctionRec(in, in.length - 1);
    }
  }
  
  /* The recursion is done in a helper method because we need an int to be passed through each 
   * recursion, which isn't allowed in the conjunctionRec method. The helper method is an overloaded method,
   * and gets called when conjunctionRec is called with both a boolean array AND an int.
   * */
  public static boolean conjunctionRec(boolean[] in, int n) {
    // base case: when the recursion has reached the innermost set of brackets, return the easily calculated value
    if (n == 1) {
      return (in[0] && in[1]);
    }
    
    // recursive step: continually move one bracket inward
    return conjunctionRec(in, n - 1) && in[n];
  }
  
  public static boolean disjunctionIter(boolean[] in) {
    // if the array is empty, print a message and return true by default
    if (in.length == 0) {
      System.out.println("The input array was empty.");
      return true;
    } else if (in.length == 1) {
      // if the array is of length 1, return the only value in the array
      return in[0];
    } else {
      // otherwise, calculate the disjunction of the set using iteration
      boolean result = (in[0] || in[1]);
      for (int i = 2; i < in.length - 1; i++) {
        result = (result || in[i]);
      }
      return result;
    }
  }
  
  // this method calculates the disjunction of a set of booleans by using recursive method calls
  public static boolean disjunctionRec(boolean[] in) {
    // if the array is empty, print a message and return true by default
    if (in.length == 0) {
      System.out.println("The input array was empty.");
      return true;
    } else if (in.length == 1) {
      // if the array is of length 1, return the only value in the array
      return in[0];
    } else {
      // otherwise, call the disjunctionRec helper method
      return disjunctionRec(in, in.length - 1);
    }
  }
  
  /* The recursion is done in a helper method because we need an int to be passed through each 
   * recursion, which isn't allowed in the disjunctionRec method. The helper method is an overloaded method,
   * and gets called when conjunctionRec is called with both a boolean array AND an int.
   * */
  public static boolean disjunctionRec(boolean[] in, int n) {
    // base case: when the recursion has reached the innermost set of brackets, return the easily calculated value
    if (n == 1) {
      return (in[0] || in[1]);
    }
    
    // recursive case: continually move one bracket inward
    return (disjunctionRec(in, n - 1) || in[n]);
  }
}