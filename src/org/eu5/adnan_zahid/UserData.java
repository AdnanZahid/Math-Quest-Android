package org.eu5.adnan_zahid;

import java.io.Serializable;

import org.andengine.entity.sprite.Sprite;

public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
   
    String string;
    Sprite sprite;
   
    public UserData(){}
   
    public UserData(String string, Sprite sprite) {
            this.string = string;
            this.sprite = sprite;
    }

	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return this.sprite;
	}

	public String getString() {
		// TODO Auto-generated method stub
		return this.string;
	}
}