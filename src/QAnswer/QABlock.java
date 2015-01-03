/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QAnswer;

/**
 *
 * @author khaledd
 */
public class QABlock {
    
    private int sentid;
    private int blocktype;
    private String possentece;
    private String senttext;

    public String getSenttext() {
        return senttext;
    }

    public void setSenttext(String senttext) {
        this.senttext = senttext;
    }

    public int getSentid() {
        return sentid;
    }

    public void setSentid(int sentid) {
        this.sentid = sentid;
    }

    public int getBlocktype() {
        return blocktype;
    }

    public void setBlocktype(int blocktype) {
        this.blocktype = blocktype;
    }

    public String getPossentece() {
        return possentece;
    }

    public void setPossentece(String possentece) {
        this.possentece = possentece;
    }
    
    
    
}
