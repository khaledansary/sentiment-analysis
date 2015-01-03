/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonParser;

/**
 *
 * @author khaledd
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;


public class Negative {


private Long start;

private Long end;

private String text;

/**
* 
* @return
* The start
*/
public Long getStart() {
return start;
}

/**
* 
* @param start
* The start
*/
public void setStart(Long start) {
this.start = start;
}

/**
* 
* @return
* The end
*/
public Long getEnd() {
return end;
}

/**
* 
* @param end
* The end
*/
public void setEnd(Long end) {
this.end = end;
}

/**
* 
* @return
* The text
*/
public String getText() {
return text;
}

/**
* 
* @param text
* The text
*/
public void setText(String text) {
this.text = text;
}

}
