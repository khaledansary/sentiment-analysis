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
import java.util.ArrayList;
import java.util.List;



public class AnnotateText {


private Long version;

private List<Group> groups = new ArrayList<Group>();

private Boolean validationError;

/**
* 
* @return
* The version
*/
public Long getVersion() {
return version;
}

/**
* 
* @param version
* The version
*/
public void setVersion(Long version) {
this.version = version;
}

/**
* 
* @return
* The groups
*/
public List<Group> getGroups() {
return groups;
}

/**
* 
* @param groups
* The groups
*/
public void setGroups(List<Group> groups) {
this.groups = groups;
}

/**
* 
* @return
* The validationError
*/
public Boolean getValidationError() {
return validationError;
}

/**
* 
* @param validationError
* The validationError
*/
public void setValidationError(Boolean validationError) {
this.validationError = validationError;
}

}
