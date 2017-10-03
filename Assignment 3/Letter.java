/* Name: Alex Hale
 * ID: 260672475
 * Date: 18/3/2016 */

public class Letter {
  // create fields to store the character of the letter and whether it has been guessed
  private char value;
  private boolean isGuessed;
  
  // a constructor to set the values of value and isGuessed
  public Letter(char _value) {
    this.value = _value;
    this.isGuessed = false;
  }
  
  // a getter to get the character value of this Letter object
  public char getValue() {
    return this.value;
  }
  
  // a getter to return whether or not this Letter object has already been guessed
  public boolean getRevealed() {
    return this.isGuessed;
  }
  
  // switches this Letter object from not guessed to guessed
  public void reveal() {
    this.isGuessed = true;
  }
}