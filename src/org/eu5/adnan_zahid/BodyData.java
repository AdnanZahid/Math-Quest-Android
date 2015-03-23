package org.eu5.adnan_zahid;

import java.io.Serializable;

import com.badlogic.gdx.physics.box2d.Body;

public class BodyData implements Serializable {
	private static final long serialVersionUID = 1L;

	Body body;

	public BodyData() {
	}

	public BodyData(Body body) {
		this.body = body;
	}

	public Body getBody() {
		// TODO Auto-generated method stub
		return this.body;
	}
}